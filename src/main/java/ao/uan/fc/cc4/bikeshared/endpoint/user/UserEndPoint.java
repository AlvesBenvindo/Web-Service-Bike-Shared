package ao.uan.fc.cc4.bikeshared.endpoint.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.bikeshared.endpoint.user.service.UserService;
import xml.soap.user.UserResponse;
import xml.soap.user.UserRequest;

@Endpoint
public class UserEndPoint {
	
	private static final String NAMESPACE_URI = "http://user.soap.xml";
	
	@Autowired
	private UserService userService;

	/**
	 *
	 * @param request
	 * @return
	 */
	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "UserRequest")
	@ResponsePayload
	public UserResponse addUser(@RequestPayload UserRequest request) {
		System.out.println("Entrando na request");
		return userService.addUser(request);
	}

}