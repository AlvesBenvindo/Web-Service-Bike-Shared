package ao.uan.fc.cc4.bikeshared.endpoint.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.bikeshared.endpoint.user.service.UserService;
import xml.soap.user.*;

import java.security.NoSuchAlgorithmException;

@Endpoint
@Component
public class UserEndPoint {
	
	private static final String NAMESPACE_URI = "http://user.soap.xml";
	
	@Autowired
	private UserService userService;

	/**
	 *
	 * @param {@link UserRequest}
	 * @return {@link UserResponse}
	 */
	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "UserRequest")
	@ResponsePayload
	public UserResponse addUser (@RequestPayload UserRequest request) {
		System.out.println("Entrando no serviço addUser");
		return userService.addUser(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "LoginRequest")
	@ResponsePayload
	public UserResponse login (@RequestPayload LoginRequest request) {
		System.out.println("Entrando no serviço startSession");
		return userService.login(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UserIdRequest")
	@ResponsePayload
	public UserResponse getUserId (@RequestPayload UserIdRequest request) {
		System.out.println("Entrando no serviço getUserById");
		return userService.getUser(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "LogoutRequest")
	@ResponsePayload
	public UserResponse logout (@RequestPayload LogoutRequest request) {
		System.out.println("Entrando no serviço getUserById");
		return userService.logout(request);
	}

}