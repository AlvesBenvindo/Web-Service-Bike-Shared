package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaModel;
import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaRepository;
import ao.uan.fc.cc4.bikeshared.bd.session.SessionModel;
import ao.uan.fc.cc4.bikeshared.bd.session.SessionRepository;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import ao.uan.fc.cc4.bikeshared.config.security.JwtToken.Token;
import ao.uan.fc.cc4.bikeshared.utils.HashPassword;
import xml.soap.user.LoginRequest;
import xml.soap.user.LogoutRequest;
import xml.soap.user.UserResponse;
import xml.soap.user.ValidateTokenRequest;

@Service
public class AuthenticationService {

    @Autowired(required = true)
    private UserRepository userRepo;
    @Autowired(required = true)
    private SessionRepository sessionRepo;
    @Autowired(required = true)
    private CiclistaRepository ciclistaRepo;
    @Autowired
    private Token jwtToken;


    public UserResponse login(LoginRequest request) {
        UserModel superadmin = userRepo.findByNome("admin");
		if (superadmin == null) {
			superadmin = new UserModel();
			superadmin.setEmail("admin@gmail.com");
			superadmin.setNome("admin");
			superadmin.setSobrenome("admin");
			superadmin.setPassword(HashPassword.hashing("admin"));
			superadmin.setGenero("F");
			superadmin.setFoto(null);
            userRepo.save(superadmin);
		}
        System.out.println("Dentro do Serviço de Login");
        UserResponse response = new UserResponse();
        UserModel user = userRepo.findByEmail(request.getEmail());
        if (user != null) {
            System.out.println("findByUser");
            SessionModel sessionModel = sessionRepo.findByUser(user.getId());
            if (sessionModel != null) {
                System.out.println("deleteById");
                sessionRepo.deleteById(sessionModel.getId());
                System.out.println("deleteById-end");
            }else if(user.getTipo()==2){
                /*sessionModel = sessionRepo.findByFingerPrint(request.getFingerPrint());
                if (sessionModel != null) {
                    sessionRepo.deleteById(sessionModel.getId());
                }*/
            }
            System.out.println("verificando password");
            if (user.getPassword().equals(HashPassword.hashing(request.getPassword()))) {
                System.out.println("verificando password - sucesso");
                SessionModel session = new SessionModel();
                System.out.println("verificando password - sucesso");
                session.setToken(jwtToken.generateToken(user.getEmail(), user.getTipo()));
                System.out.println("verificando password - sucesso");
                session.setUser(user.getId());
                System.out.println("verificando password - sucesso");
                sessionRepo.save(session);
                System.out.println("verificando password - sucesso");

                BeanUtils.copyProperties(user, response);
                response.setToken(session.getToken());
                if (user.getTipo() == 2) {
                    CiclistaModel ciclista = ciclistaRepo.findByUserId(user.getId());
                    response.setCiclistaId(ciclista.getId());
                }
                response.setEstado(true);
                response.setMensagem("Sessao iniciada com sucesso!");
            }
        } else {
            response.setEstado(false);
            response.setMensagem("email ou password errados!");
        }
        return response;
    }

    public UserResponse logout(LogoutRequest request) {
        System.out.println("Dentro do Serviço de Logout");
        UserResponse response = new UserResponse();

        SessionModel sessionModel = sessionRepo.findByToken(request.getAuthToken());
        sessionRepo.deleteById(sessionModel.getId());
        response.setEstado(true);
        response.setMensagem("Sessão terminada com sucesso!");

        return response;
    }

    public UserResponse validationSession(ValidateTokenRequest request) {

        UserModel superadmin = userRepo.findByNome("admin");
		if (superadmin == null) {
			superadmin = new UserModel();
			superadmin.setEmail("admin@gmail.com");
			superadmin.setNome("admin");
			superadmin.setSobrenome("admin");
			superadmin.setPassword(HashPassword.hashing("admin"));
			superadmin.setGenero("F");
			superadmin.setFoto(null);
            userRepo.save(superadmin);
		}

        System.out.println("Dentro do Serviço Validate Session");
        UserResponse response = new UserResponse();

        SessionModel sessionModel = sessionRepo.findByToken(request.getAuthToken());
        if (sessionModel == null) response.setEstado(false);
        else {
            response.setEstado(true);
            Optional<UserModel> user = userRepo.findById(sessionModel.getUser());
            if (user.isPresent()) {
                // BeanUtils.copyProperties(user, response);
                response.setEmail(user.get().getEmail());
                response.setNome(user.get().getNome());
                response.setSobrenome(user.get().getSobrenome());
                response.setFoto(user.get().getFoto());
                response.setToken(sessionModel.getToken());
                if (user.get().getTipo() == 2) {
                    CiclistaModel ciclista = ciclistaRepo.findByUserId(user.get().getId());
                    response.setCiclistaId(ciclista.getId());
                }
            }
        }
        return response;
    }

    public boolean sessionIsValid (String authToken) {
        SessionModel session = sessionRepo.findByToken(authToken);
        if (session!=null) {
            Optional<UserModel> user = userRepo.findById(session.getUser());
            if (user.isPresent()) {
                if (user.get().getTipo() == 2){
                    System.out.println("tipo 2");
                    return true;
                }
                else {
                    System.out.println("tipo 1");
                    Integer confirm = (jwtToken.getSubject(session.getToken()).equals(" "))? 0 : 1;
                    switch (confirm) {
                        case 0: 
                            sessionRepo.delete(session);
                            return false;

                        case 1:
                            return true;
                    }
                }
            } else sessionRepo.delete(session);
        }
        return false;
    }

}
