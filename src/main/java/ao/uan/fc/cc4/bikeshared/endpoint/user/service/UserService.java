package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import ao.uan.fc.cc4.bikeshared.bd.admin.AdminModel;
import ao.uan.fc.cc4.bikeshared.bd.admin.AdminRepository;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import ao.uan.fc.cc4.bikeshared.utils.HashPassword;
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

    // private boolean headEstado;
    // private String headMensagem;

    public UserResponse addUserAdmin (AddUserRequest request) {

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

        System.out.println("Dentro do Serviço de cadastro de user ");
        UserResponse response = new UserResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else{
            /*
             * Verificando se o utilizador já existe
             */
            UserModel userModel = userRepo.findByEmail(request.getBody().getEmail());
            if (userModel != null) {
                response.setEstado(false);
                response.setMensagem("Utilizador já existe, tente outro email ou outro Email ou BI!!!");
                return response;
            }
            /*
             * Fim da verificação
             */
            userModel = new UserModel();
            BeanUtils.copyProperties(request.getBody(), userModel);
            userModel.setPassword(HashPassword.hashing(request.getBody().getPassword()));
            userModel = userRepo.save(userModel);

            AdminModel admin = new AdminModel();
            admin.setUserId(userModel.getId());
            BeanUtils.copyProperties(request.getBody(), admin);
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
