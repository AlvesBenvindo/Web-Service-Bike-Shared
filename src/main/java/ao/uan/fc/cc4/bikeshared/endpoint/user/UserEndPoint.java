package ao.uan.fc.cc4.bikeshared.endpoint.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.bikeshared.endpoint.user.service.AuthenticationService;
import ao.uan.fc.cc4.bikeshared.endpoint.user.service.CiclistaService;
import ao.uan.fc.cc4.bikeshared.endpoint.user.service.UserService;
import xml.soap.user.*;

@Endpoint
@Component
public class UserEndPoint {
	
	private static final String NAMESPACE_URI = "http://user.soap.xml";
	
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationService auth; 
	@Autowired
	private CiclistaService ciclistaService; 

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "LoginRequest")
	@ResponsePayload
	public UserResponse login (@RequestPayload LoginRequest request) {
		System.out.println("Entrando no serviço de login login");
		return auth.login(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "LogoutRequest")
	@ResponsePayload
	public UserResponse logout (@RequestPayload LogoutRequest request) {
		System.out.println("Entrando no serviço logout");
		return auth.logout(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ValidateTokenRequest")
	@ResponsePayload
	public UserResponse validateSession (@RequestPayload ValidateTokenRequest request) {
		System.out.println("Entrando no serviço validateSession");
		return auth.validationSession(request);
	}

	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "AddUserRequest")
	@ResponsePayload
	public UserResponse addUserAdmin (@RequestPayload AddUserRequest request) {
		System.out.println("Entrando no serviço addUser");
		return userService.addUserAdmin(request);
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

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddCiclistaRequest")
	@ResponsePayload
	public CiclistaResponse addCiclista (@RequestPayload AddCiclistaRequest request) {
		System.out.println("Entrando no serviço getCiclista");
		return ciclistaService.addCiclista(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetCiclistaRequest")
	@ResponsePayload
	public CiclistaResponse getCiclista (@RequestPayload GetCiclistaRequest request) {
		System.out.println("Entrando no serviço getCiclista");
		return ciclistaService.getCiclista(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateCiclistaRequest")
	@ResponsePayload
	public CiclistaResponse updateCiclista (@RequestPayload UpdateCiclistaRequest request) {
		System.out.println("Entrando no update Ciclista ");
		return ciclistaService.updateCiclista(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteCiclistaRequest")
	@ResponsePayload
	public CiclistaResponse deleteCiclista (@RequestPayload DeleteCiclistaRequest request) {
		System.out.println("Entrando no deleteCiclista ");
		return ciclistaService.deleteCiclista(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AllCiclistasRequest")
	@ResponsePayload
	public CiclistaListResponse getAllCiclista (@RequestPayload AllCiclistasRequest request) {
		System.out.println("Entrando no serviço getUserById");
		return ciclistaService.getAllCiclista(request);
	}

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "GetSaldoRequest")
	@ResponsePayload
    public GetSaldoResponse getSaldo (@RequestPayload GetSaldoRequest request) {
        System.out.println("Entrando no serviço de consultar saldo");
		return ciclistaService.getSaldo(request);
    }

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "TransferPointsRequest")
	@ResponsePayload
    public TransferPointsResponse getSaldo (@RequestPayload TransferPointsRequest request) {
        System.out.println("Entrando no serviço de transferência de pontos");
		return ciclistaService.transferPoints(request);
    }

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "HistoricTrajectRequest")
	@ResponsePayload
    public HistoricTrajectResponse getHistoricTraject (@RequestPayload HistoricTrajectRequest request) {
        System.out.println("Entrando no serviço de carregamento de histórico de trajectória");
		return ciclistaService.getHistoricTraject(request);
    }

}