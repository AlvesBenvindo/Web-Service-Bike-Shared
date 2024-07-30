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
import xml.soap.user.CiclistaResponse;
import xml.soap.user.LoginCiclistaRequest;
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
        System.out.println("Dentro do Serviço de Login");
        UserResponse response = new UserResponse();
        UserModel user = userRepo.findByEmail(request.getEmail());
        if (user != null) {
            SessionModel sessionModel = sessionRepo.findByUser(user.getId());
            if (sessionModel != null) {
                sessionRepo.deleteById(sessionModel.getId());
            }else if(user.getTipo()==2){
                /*sessionModel = sessionRepo.findByFingerprint(request.getMac());
                if (sessionModel != null) {
                    sessionRepo.deleteById(sessionModel.getId());
                }*/
            }
            if (user.getPassword().equals(HashPassword.hashing(request.getPassword()))) {
                SessionModel session = new SessionModel();
                session.setToken(jwtToken.generateToken(user.getEmail(), user.getTipo()));
                session.setUser(user.getId());
                if(user.getTipo()==2) session.setFingerprint(request.getMac());
                sessionRepo.save(session);

                BeanUtils.copyProperties(user, response);
                if (user.getTipo() == 2) {
                    CiclistaModel ciclista = ciclistaRepo.findByUserId(user.getId());
                    response.setRole("ciclista");
                    response.setCiclistaId(ciclista.getId());
                } else {
                    response.setRole("admin");
                }

                response.setToken(session.getToken());
                response.setEstado(true);
                response.setMensagem("Sessao iniciada com sucesso!");
            } else {
                response.setEstado(false);
                response.setMensagem("email ou password errados!");
            }
        } else {
            response.setEstado(false);
            response.setMensagem("email ou password errados!");
        }
        return response;
    }

    // public UserResponse login(LoginRequest request) {
    //     UserResponse response = new UserResponse();
    //     response.setEstado(false);
    //     UserModel user = userRepo.findByEmail(request.getEmail());
    //     if (user != null && user.getTipo()==1) {
    //         SessionModel sessionModel = sessionRepo.findByUser(user.getId());
    //         if (sessionModel != null) {
    //             sessionRepo.deleteById(sessionModel.getId());
    //         } else if (user.getPassword().equals(HashPassword.hashing(request.getPassword()))) {
    //             SessionModel session = new SessionModel();
    //             session.setToken(jwtToken.generateToken(user.getEmail(), user.getTipo()));
    //             session.setUser(user.getId());
    //             sessionRepo.save(session);

    //             BeanUtils.copyProperties(user, response);
    //             AdminModel admin = adminRepo.findByUserId(user.getId());
    //             if (admin!=null) {
    //                 response.setRole(admin.getRole());
    //             }

    //             response.setToken(session.getToken());
    //             response.setEstado(true);
    //             response.setMensagem("Sessao iniciada com sucesso!");
    //         } else{
    //             response.setMensagem("email ou password errados!");
    //         }
    //     } else {
    //         response.setMensagem("email ou password errados!");
    //     }
    //     return response;
    // }

    public CiclistaResponse loginCiclista(LoginCiclistaRequest request) {
        CiclistaResponse response = new CiclistaResponse();
        response.setEstado(false);
        UserModel user = userRepo.findByEmail(request.getEmail());
        if (user != null && user.getTipo()==2) {
            SessionModel sessionModel = sessionRepo.findByUser(user.getId());
            if (sessionModel != null) {
                sessionRepo.deleteById(sessionModel.getId());
            }
            sessionModel = sessionRepo.findByFingerprint(request.getFingerPrint());
            if (sessionModel != null) {
                // sessionRepo.deleteById(sessionModel.getId());
                sessionModel.setFingerprint(sessionModel.getFingerprint()+sessionModel.getId());
                sessionRepo.save(sessionModel);
            }
            if (user.getPassword().equals(HashPassword.hashing(request.getPassword()))) {
                SessionModel session = new SessionModel();
                session.setToken(jwtToken.generateToken(user.getEmail(), user.getTipo()));
                session.setUser(user.getId());
                session.setFingerprint(request.getFingerPrint());
                sessionRepo.save(session);

                BeanUtils.copyProperties(user, response);
                CiclistaModel ciclista = ciclistaRepo.findByUserId(user.getId());
                if (ciclista != null) {
                    response.setId(ciclista.getId());
                    response.setMensagem("email ou password errados!");
                    return response;
                }
                response.setUserId(user.getId());
                response.setToken(session.getToken());

                response.setEstado(true);
                response.setMensagem("Sessao iniciada com sucesso!");
            } else{
                response.setMensagem("email ou password errados!");
            }
        } else {
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
                } else {
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