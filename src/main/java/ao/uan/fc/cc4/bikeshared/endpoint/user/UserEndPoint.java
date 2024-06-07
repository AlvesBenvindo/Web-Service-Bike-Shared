package ao.uan.fc.cc4.bikeshared.endpoint.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.bikeshared.endpoint.station.service.StationService;
import ao.uan.fc.cc4.bikeshared.endpoint.user.service.UserService;
import xml.soap.GetStationRequest;
import xml.soap.user.*;

@Endpoint
@Component
public class UserEndPoint {
	
	private static final String NAMESPACE_URI = "http://user.soap.xml";
	
	@Autowired
	private UserService userService;
	@Autowired
	private StationService stationService;

	/**
	 *
	 * @param {@link UserRequest}
	 * @return {@link UserResponse}
	 */
	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "AddUserRequest")
	@ResponsePayload
	public UserResponse addUser (@RequestPayload AddUserRequest request) {
		System.out.println("Entrando no serviço addUser");
		return userService.addUser(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "LoginRequest")
	@ResponsePayload
	public UserResponse login (@RequestPayload LoginRequest request) {
		System.out.println("Entrando no serviço de login login");
		return userService.login(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "LogoutRequest")
	@ResponsePayload
	public UserResponse logout (@RequestPayload LogoutRequest request) {
		System.out.println("Entrando no serviço logout");
		return userService.logout(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ValidateTokenRequest")
	@ResponsePayload
	public UserResponse validateSession (@RequestPayload ValidateTokenRequest request) {
		System.out.println("Entrando no serviço validateSession");
		return userService.validationSession(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UserIdRequest")
	@ResponsePayload
	public UserResponse getUserId (@RequestPayload UserIdRequest request) {
		System.out.println("Entrando no serviço getUserById");
		return (UserResponse) userService.getUser(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AllUsersRequest")
	@ResponsePayload
	public UserListResponse getAllUsers (@RequestPayload AllUsersRequest request) {
		System.out.println("Entrando no serviço getUserById");
		return userService.getAllUsers(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CiclistaIdRequest")
	@ResponsePayload
	public CiclistaResponse getCiclista (@RequestPayload CiclistaIdRequest request) {
		System.out.println("Entrando no serviço getUserById");
		return userService.getCiclista(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AllCiclistasRequest")
	@ResponsePayload
	public CiclistaListResponse getAllCiclista (@RequestPayload AllCiclistasRequest request) {
		System.out.println("Entrando no serviço getUserById");
		return userService.getAllCiclista(request);
	}

    /**
     * 
     * @param request
     * @return
     */
    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "GetSaldoRequest")
	@ResponsePayload
    public GetSaldoResponse getSaldo (@RequestPayload GetSaldoRequest request) {
        System.out.println("Entrando no serviço add Station");
		return userService.getSaldo(request);
    }

}