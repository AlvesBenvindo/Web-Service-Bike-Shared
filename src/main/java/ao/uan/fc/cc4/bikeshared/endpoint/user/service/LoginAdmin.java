package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import org.springframework.stereotype.Component;

import xml.soap.user.LoginRequest;
import xml.soap.user.UserResponse;

@Component
public interface LoginAdmin {

    UserResponse login (LoginRequest req);
    
}
