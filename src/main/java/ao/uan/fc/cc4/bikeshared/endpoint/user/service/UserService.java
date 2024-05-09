package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import ao.uan.fc.cc4.bikeshared.bd.session.SessionModel;
import ao.uan.fc.cc4.bikeshared.bd.session.SessionRepository;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xml.soap.user.StartSession;
import xml.soap.user.UserRequest;
import xml.soap.user.UserResponse;

import java.util.List;

@Service
public class UserService {

    protected String token = "f6656tvt#@QaaA354dugy6i7@#$%cyr";

    @Autowired(required = true)
    private UserRepository userRepo;
    @Autowired(required = true)
    private SessionRepository sessionRepo;

    public UserResponse addUser (UserRequest request) {

        System.out.println("Dentro do Serviço "+request.getBI());
        UserResponse response = new UserResponse();

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(request, userModel);

        UserModel userModel2 = userRepo.save(userModel);

        BeanUtils.copyProperties(userModel2, response);
        response.setEstado(true);
        response.setMensagem("User adicionado com sucesso!!!");

        return response;
    }

    public UserResponse startSession (StartSession request) {

        System.out.println("Dentro do Serviço startSession"+request.getEmail());
        UserResponse response = new UserResponse();

        UserModel userModel = findUserByEmail(request.getEmail());
        if (userModel!=null) {
            SessionModel session = new SessionModel();
            session.setToken(this.token);
            sessionRepo.save(session);
            response.setToken(session.getToken());
            response.setEstado(true);
            response.setMensagem("Success!");
            BeanUtils.copyProperties(userModel, response);
        } else {
            response.setEstado(false);
            response.setMensagem("No success!");
        }

        return response;
    }





    public UserModel findUserByEmail ( String email ) {
        List<UserModel> userList = userRepo.findAll();

        for (int i = 0; userList.size()!=0 && i < userList.size(); i++)
            if (userList.get(i).getEmail().equals(email))
                return userList.get(i);

        return null;
    }

}
