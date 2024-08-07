package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import ao.uan.fc.cc4.bikeshared.bd.admin.AdminModel;
import ao.uan.fc.cc4.bikeshared.bd.admin.AdminRepository;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import ao.uan.fc.cc4.bikeshared.utils.HashPassword;
//import ao.uan.fc.cc4.bikeshared.wsAsCliente.ofReplica.WSReplica;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.soap.user.*;

import java.util.List;
import java.util.Optional;  

@Service
public class UserService {

    @Autowired
    AuthenticationService auth;

    @Autowired(required = true)
    private UserRepository userRepo;
    @Autowired(required = true)
    private AdminRepository adminRepo;
    //@Autowired(required = true)
    //private WSReplica serverWriter;

    // private boolean headEstado;
    // private String headMensagem;

    public UserResponse addUserAdmin (AddUserRequest request) {
        System.out.println("Dentro do Serviço de cadastro de user ");
            System.out.println("Benvindo - Alves");
        UserResponse response = new UserResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else{
            /*
             * Verificando se o utilizador já existe
             */
            UserModel user = userRepo.findByEmail(request.getBody().getEmail());
            if (user != null) {
                response.setEstado(false);
                response.setMensagem("Utilizador já existe, tente outro email ou outro Email ou BI!!!");
                return response;
            }
            /*
             * Fim da verificação
             */
            UserModel userModel = new UserModel();
            userModel.setEmail(request.getBody().getEmail());
            userModel.setFoto(request.getBody().getFoto());
            userModel.setGenero(request.getBody().getGenero());
            userModel.setNome(request.getBody().getNome());
            userModel.setSobrenome(request.getBody().getSobrenome());
            userModel.setTipo(request.getBody().getTipo());
            userModel.setPassword(HashPassword.hashing(request.getBody().getPassword()));
            // BeanUtils.copyProperties(request.getBody(), userModel);
            userModel = userRepo.save(userModel);
            //serverWriter.writeInReplica("user", userRepo.save(userModel).returnString());

            AdminModel admin = new AdminModel();
            admin.setUserId(userModel.getId());
            admin.setRole(request.getBody().getRole());
            admin.setBi(request.getBody().getBi());
            admin.setTelefone(request.getBody().getTelefone());
            adminRepo.save(admin);

            response.setEstado(true);
            response.setMensagem("Utilizador adicionado com sucesso!!!");
        }
        return response;
    }

    public UserResponse getUser (UserIdRequest request) {

        UserResponse response = new UserResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Optional<UserModel> userOp = userRepo.findById(request.getBody().getUserId());
            if (userOp.isPresent() && userOp.get().getTipo()==1) {
                UserModel userModel = userOp.get();
                BeanUtils.copyProperties(userModel, response);
                response.setEstado(true);
                response.setMensagem("User encontrado com sucesso!");
            } else {
                response.setEstado(false);
                response.setMensagem("User não encontrado!");
            }
        }
        return response;
    }

    public UserListResponse getAllUsers (AllUsersRequest request) {
        UserListResponse response = new UserListResponse();
        
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else{
            List<UserModel> userList = userRepo.findAll();
            if (!userList.isEmpty()) {
                response.setEstado(true);
                response.setMensagem("User encontrado com sucesso!");
                userList.forEach(
                    user -> {
                        UserType userType = new UserType();
                        if (user.getTipo() == 1){
                            BeanUtils.copyProperties(user, userType);
                            response.getUser().add(userType);
                        }
                    });
            } else {
                response.setEstado(false);
                response.setMensagem("Nenhum user Registrado!");
            }
        }
        return response;
    }

}
