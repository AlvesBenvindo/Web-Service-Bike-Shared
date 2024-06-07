package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaModel;
import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaRepository;
import ao.uan.fc.cc4.bikeshared.bd.session.SessionModel;
import ao.uan.fc.cc4.bikeshared.bd.session.SessionRepository;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import ao.uan.fc.cc4.bikeshared.config.security.JwtToken.Token;
import ao.uan.fc.cc4.bikeshared.utils.HashPassword;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.soap.GetStationRequest;
import xml.soap.user.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class UserService {

    private boolean headEstado;
    private String headMensagem;

    @Autowired(required = true)
    private UserRepository userRepo;
    @Autowired(required = true)
    private SessionRepository sessionRepo;
    @Autowired(required = true)
    private CiclistaRepository ciclistaRepo;
    @Autowired
    private Token jwtToken;

    public UserResponse login(LoginRequest request) {
        System.out.println("Dentro do Serviço startSession"+request.getEmail());
        UserResponse response = new UserResponse();

        UserModel userModel = userRepo.findByEmail(request.getEmail());

        if (userModel != null) {

            SessionModel sessionModel = sessionRepo.findByUser(userModel.getId());
            if (sessionModel != null) {
                sessionRepo.deleteById(sessionModel.getId());
            }

            sessionModel = sessionRepo.findByFingerPrint(request.getFingerPrint());
            if (sessionModel != null) {
                sessionRepo.deleteById(sessionModel.getId());
            }

            if (userModel.getPassword().equals(HashPassword.hashing(request.getPassword()))) {
                SessionModel session = new SessionModel();
                session.setToken(jwtToken.generateToken(userModel.getEmail(), userModel.getTipo()));
                session.setFingerPrint(request.getFingerPrint());
                session.setUser(userModel.getId());
                sessionRepo.save(session);
                response.setToken(session.getToken());
                response.setEstado(true);
                response.setMensagem("Sessao iniciada com sucesso!");

                if (userModel.getTipo() == 1) BeanUtils.copyProperties(userModel, response);
                else {
                    response.setNome(userModel.getNome());
                    response.setSobrenome(userModel.getSobrenome());
                    response.setFoto(userModel.getFoto());
                    response.setId(userModel.getId());
                    response.setEmail(userModel.getEmail());
                    response.setToken(session.getToken());
                    if (userModel.getTipo() == 2) {
                        CiclistaModel ciclista = ciclistaRepo.findByUser(userModel.getId());
                        response.setCiclistaId(ciclista.getId());
                    }
                }
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
        System.out.println("Dentro do Serviço End-Session"+request.getAuthToken());
        SessionModel sessionModel = sessionRepo.findByToken(request.getAuthToken());

        sessionRepo.deleteById(sessionModel.getId());
        response.setEstado(true);
        response.setMensagem("Sessão terminada com sucesso!");

        return response;
    }

    public UserResponse validationSession(ValidateTokenRequest request) {
        UserResponse response = new UserResponse();

        System.out.println("Dentro do Serviço Validate Session"+request.getAuthToken());
        SessionModel sessionModel = sessionRepo.findByToken(request.getAuthToken());

        if (sessionModel == null) response.setEstado(false);
        else {
            response.setEstado(true);
            Optional<UserModel> user = userRepo.findById(sessionModel.getUser());
            if (user.isPresent()) {
                response.setNome(user.get().getNome());
                response.setSobrenome(user.get().getSobrenome());
                response.setFoto(user.get().getFoto());
                response.setId(user.get().getId());
                response.setEmail(user.get().getEmail());
                if (user.get().getTipo() == 2) {
                    CiclistaModel ciclista = ciclistaRepo.findByUser(user.get().getId());
                    response.setCiclistaId(ciclista.getId());
                }
            }
        }

        return response;
    }

    public UserResponse addUser (AddUserRequest request) {

        System.out.println("Dentro do Serviço "+request.getBi());
        UserResponse response = new UserResponse();

        UserModel userModel = userRepo.findByEmail(request.getEmail());
        if (userModel != null) {
            response.setEstado(false);
            if (request.getTipo() == 1) response.setMensagem("Utilizador já existe, tente outro email ou outro BI!!!");
            else response.setMensagem("Este Email já está a ser utilizado!!!");
            return response;
        }

        userModel = new UserModel();
        BeanUtils.copyProperties(request, userModel);
        userModel.setPassword(HashPassword.hashing(request.getPassword()));

        userModel = userRepo.save(userModel);
        if (userModel.getTipo() == 2) {
            CiclistaModel ciclistaModel = new CiclistaModel();
            ciclistaModel.setUser(userModel.getId());
            ciclistaModel.setPoints(100);
            ciclistaModel.setState(1);
            ciclistaRepo.save(ciclistaModel);
        }

        response.setEstado(true);
        if (userModel.getTipo() == 2) response.setMensagem("Conta criada com sucesso!!!");
        else response.setMensagem("Utilizador adicionado com sucesso!!!");

        return response;
    }

    public UserResponse getUser (UserIdRequest request) {

        UserResponse response = new UserResponse();
        SessionModel session = sessionRepo.findByToken(request.getHeader().getAuthToken());

        if (SessionModel.virifySession(session) != 0) {
            this.DestilaHeadResponse(session);
            response.setEstado(this.headEstado);
            response.setMensagem(this.headMensagem);
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

    public UserListResponse getAllUsers (AllUsersRequest request) {

        UserListResponse response = new UserListResponse();
        SessionModel session = sessionRepo.findByToken(request.getHeader().getAuthToken());

        if (SessionModel.virifySession(session) != 0) {
            this.DestilaHeadResponse(session);
            response.setEstado(this.headEstado);
            response.setMensagem(this.headMensagem);
        }
        else {
            List<UserModel> userList = userRepo.findAll();
            if (!userList.isEmpty()) {
                response.setEstado(true);
                response.setMensagem("User encontrado com sucesso!");
                userList.forEach(
                    user -> {
                        UserType userType = new UserType();
                        BeanUtils.copyProperties(user, userType);
                        response.getUser().add(userType);
                    });
            } else {
                response.setEstado(false);
                response.setMensagem("Nenhum user Registrado!");
            }
        }
        return response;
    }

    public CiclistaResponse getCiclista (CiclistaIdRequest request) {

        CiclistaResponse response = new CiclistaResponse();
        SessionModel session = sessionRepo.findByToken(request.getHeader().getAuthToken());

        if (SessionModel.virifySession(session) != 0) {
            this.DestilaHeadResponse(session);
            response.setEstado(this.headEstado);
            response.setMensagem(this.headMensagem);
        }
        else {
            Optional<CiclistaModel> ciclistaOp = ciclistaRepo.findById(request.getBody().getCiclistaId());
            if (ciclistaOp.isPresent()) {
                Optional<UserModel> userOp = userRepo.findById(ciclistaOp.get().getUser());
                response.setEstado(true);
                response.setMensagem("User encontrado com sucesso!");
                BeanUtils.copyProperties(userOp.get(), response);
                response.setCiclistaId(ciclistaOp.get().getId());
                response.setPoints(ciclistaOp.get().getPoints());
                response.setState(ciclistaOp.get().getState());
            } else {
                response.setEstado(false);
                response.setMensagem("User não encontrado!");
            }
        }
        return response;
    }

    public CiclistaListResponse getAllCiclista (AllCiclistasRequest request) {

        CiclistaListResponse response = new CiclistaListResponse();
        SessionModel session = sessionRepo.findByToken(request.getHeader().getAuthToken());

        if (SessionModel.virifySession(session) != 0) {
            this.DestilaHeadResponse(session);
            response.setEstado(this.headEstado);
            response.setMensagem(this.headMensagem);
        }
        else {
            List<CiclistaModel> ciclistaList = ciclistaRepo.findAll();
            if (!ciclistaList.isEmpty()) {
                response.setEstado(true);
                response.setMensagem("Lista de ciclista encontrada com sucesso!");
                ciclistaList.forEach(
                    ciclista -> {
                        CiclistaType ciclistaType = new CiclistaType();
                        Optional<UserModel> userOp = userRepo.findById(ciclista.getUser());
                        if (userOp.isPresent()) {
                            BeanUtils.copyProperties(userOp.get(), ciclistaType);
                        }
                        ciclistaType.setCiclistaId(ciclista.getId());
                        ciclista.setPoints(ciclista.getPoints());
                        ciclistaType.setState(ciclista.getState());
                        response.getUser().add(ciclistaType);
                    });
            } else {
                response.setEstado(false);
                response.setMensagem("User não encontrado!");
            }
        }

        return response;
    }

    public GetSaldoResponse getSaldo (GetSaldoRequest request) {
        GetSaldoResponse response = new GetSaldoResponse();
        SessionModel session = sessionRepo.findByToken(request.getHeader().getAuthToken());

        if (SessionModel.virifySession(session) != 0) {
            this.DestilaHeadResponse(session);
            response.setEstado(this.headEstado);
            response.setMensagem(this.headMensagem);
        }
        else {
            Optional<CiclistaModel> ciclista = ciclistaRepo.findById(request.getBody().getCiclistaId());
            if (ciclista.isPresent()) {
                response.setEstado(true);
                response.setMensagem("Ciclista encontrado com sucesso!");
                response.setSaldo(ciclista.get().getPoints());
            } else {
                response.setEstado(false);
                response.setMensagem("User não encontrado!");
            }
        }
        return response;
    }

    private void DestilaHeadResponse (SessionModel session) {
        headEstado = false;
        if (SessionModel.virifySession(session) == 1) {
            headMensagem = "Token inválido, undefined!";
        } else {
            headMensagem = "Sessão expirada!";
        }
    }

}
