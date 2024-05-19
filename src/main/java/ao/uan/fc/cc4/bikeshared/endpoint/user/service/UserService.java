package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import ao.uan.fc.cc4.bikeshared.bd.session.SessionModel;
import ao.uan.fc.cc4.bikeshared.bd.session.SessionRepository;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import ao.uan.fc.cc4.bikeshared.config.security.token.Token;
import ao.uan.fc.cc4.bikeshared.utils.HashPassword;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.soap.user.*;
import java.util.Optional;


@Service
public class UserService {

    @Autowired(required = true)
    private UserRepository userRepo;
    @Autowired(required = true)
    private SessionRepository sessionRepo;
    @Autowired
    private Token jwtToken;

    public UserResponse login(LoginRequest request) {
        System.out.println("Dentro do Serviço startSession"+request.getEmail());
        UserResponse response = new UserResponse();

        UserModel userModel = userRepo.findByEmail(request.getEmail());

        if (userModel != null) {
            if (userModel.getPassword().equals(HashPassword.hashing(request.getPassword()))) {
                SessionModel session = new SessionModel();
                session.setToken(jwtToken.generateToken(userModel.getEmail(), /*tipo: */ 1));
                sessionRepo.save(session);
                response.setToken(session.getToken());
                response.setEstado(true);
                response.setMensagem("Sessao iniciada com sucesso!");
                BeanUtils.copyProperties(userModel, response);
            } else {
                response.setEstado(false);
                response.setMensagem("email ou password errados!");
            }
            return response;
        }

        response.setEstado(false);
        response.setMensagem("email ou password errados!");
        return response;
    }

    public UserResponse logout(LogoutRequest request) {
        UserResponse response = new UserResponse();
        System.out.println("Dentro do Serviço startSession"+request.getAuthToken());
        SessionModel sessionModel = sessionRepo.findByToken(request.getAuthToken());

        if (sessionModel == null){
            response.setEstado(false);
            response.setMensagem("Sessão já foi terminada anteriormente!");
        } else {
            sessionRepo.deleteById(sessionModel.getId());
            response.setEstado(true);
            response.setMensagem("Sessão terminada com sucesso!");
        }

        return response;
    }

    public UserResponse addUser (UserRequest request) {

        System.out.println("Dentro do Serviço "+request.getBI());
        UserResponse response = new UserResponse();

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(request, userModel);
        userModel.setPassword(HashPassword.hashing(request.getPassword()));

        UserModel userModel2 = userRepo.save(userModel);

        BeanUtils.copyProperties(userModel2, response);
        response.setEstado(true);
        response.setMensagem("User adicionado com sucesso!!!");

        return response;
    }

    public UserResponse getUser (UserIdRequest request) {

        UserResponse response = new UserResponse();
        SessionModel session = sessionRepo.findByToken(request.getHeader().getAuthToken());

        if ( session == null ){
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }
        else if (jwtToken.getSubject(request.getHeader().getAuthToken()).equals(" ")) {
            response.setEstado(false);
            response.setMensagem("Sessão expirada!");
            sessionRepo.deleteById(session.getId());
        }
        else {
            Optional<UserModel> userOp = userRepo.findById(request.getBody().getUserId());

            if (userOp.isPresent()) {
                UserModel userModel = userOp.get();
                response.setEstado(true);
                response.setMensagem("User encontrado com sucesso!");
                BeanUtils.copyProperties(userModel, response);
            } else {
                response.setEstado(false);
                response.setMensagem("User não encontrado!");
            }
        }

        return response;
    }


}
