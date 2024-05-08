package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.soap.user.UserRequest;
import xml.soap.user.UserResponse;

@Service
public class UserService {

    @Autowired(required = true)
    private UserRepository userRepo;

    public UserResponse addUser (UserRequest request) {

        System.out.println("Dentro do Serviço "+request.getBI());
        UserResponse response = new UserResponse();

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(request, userModel);
        System.out.println("Dentro do Serviço "+userModel.getToken());
        userModel.setToken("f6656tvt#@QaaA354dugy6i7@#$%cyr");

        UserModel userModel2 = userRepo.save(userModel);


        BeanUtils.copyProperties(userModel2, response);
        response.setEstado(true);
        response.setMensagem("User adicionado com sucesso!!!");

        return response;
    }

}
