package ao.uan.fc.cc4.bikeshared.Station3.endpoint.dock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.bikeshared.Station3.endpoint.dock.service.DockService;
import xml.soap.AddDockRequest;
import xml.soap.AddDockResponse;
import xml.soap.AlterStateDockInDownBikeRequest;
import xml.soap.AlterStateDockInDownBikeResponse;
import xml.soap.AlterStateDockInUpBikeRequest;
import xml.soap.AlterStateDockInUpBikeResponse;
import xml.soap.DeleteDockRequest;
import xml.soap.DeleteDockResponse;
import xml.soap.GetStationRequest;
import xml.soap.GetStationResponse;

@Endpoint
@Component
public class DockEndPoint {
	
	private static final String NAMESPACE_URI = "http://soap.xml";
	
	@Autowired
	private DockService dockService;

	/**
	 *
	 * @param {@link UserRequest}
	 * @return {@link GetStationRequest}
	 */
	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "GetStationRequest")
	@ResponsePayload
	public GetStationResponse getstationData (@RequestPayload GetStationRequest request) {
		System.out.println("Entrando no serviço getstationData");
		return dockService.getStationData(request);
	}

	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "AlterStateDockInUpBikeRequest")
	@ResponsePayload
	public AlterStateDockInUpBikeResponse upBike (@RequestPayload AlterStateDockInUpBikeRequest request) {
		System.out.println("Entrando no serviço getstationData");
		return dockService.upBike(request);
	}

	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "AlterStateDockInDownBikeRequest")
	@ResponsePayload
	public AlterStateDockInDownBikeResponse downBike (@RequestPayload AlterStateDockInDownBikeRequest request) {
		System.out.println("Entrando no serviço getstationData");
		return dockService.downBike(request);
	}

	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "DeleteDockRequest")
	@ResponsePayload
	public DeleteDockResponse deleteDock (@RequestPayload DeleteDockRequest request) {
		System.out.println("Entrando no serviço getstationData");
		return dockService.deleteDock(request);
	}

	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "AddDockRequest")
	@ResponsePayload
	public AddDockResponse addDock (@RequestPayload AddDockRequest request) {
		System.out.println("Entrando no serviço getstationData");
		return dockService.addDock(request);
	}

}