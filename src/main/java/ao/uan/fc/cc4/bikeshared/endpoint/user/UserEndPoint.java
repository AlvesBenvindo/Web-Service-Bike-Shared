package ao.uan.fc.cc4.bikeshared.endpoint.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.bikeshared.endpoint.user.service.UserService;
import xml.soap.user.StartSession;
import xml.soap.user.UserResponse;
import xml.soap.user.UserRequest;

@Endpoint
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

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "StartSession")
	@ResponsePayload
	public UserResponse startSession (@RequestPayload StartSession request) {
		System.out.println("Entrando no serviço startSession");
		return userService.startSession(request);
	}

}