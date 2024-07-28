package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import xml.soap.user.CiclistaResponse;
import xml.soap.user.LoginRequest;

public interface LoginCiclista {

    CiclistaResponse login (LoginRequest req);

}
