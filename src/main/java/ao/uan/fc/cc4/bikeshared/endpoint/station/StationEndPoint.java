package ao.uan.fc.cc4.bikeshared.endpoint.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.bikeshared.endpoint.station.service.StationService;
import xml.soap.station.*;
import xml.soap.station.DownBikeRequest;
import xml.soap.station.DownBikeResponse;
import xml.soap.station.UpBikeRequest;
import xml.soap.station.UpBikeResponse;

@Endpoint
@Component
public class StationEndPoint {

    private static final String NAMESPACE_URI = "http://station.soap.xml";

    @Autowired
    private StationService stationService;

    /**
     * 
     * @param request
     * @return
     */
    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "GetStationDetailsRequest")
	@ResponsePayload
    public GetStationDetailsResponse getStation (@RequestPayload GetStationDetailsRequest request) {
        System.out.println("Entrando no serviço get Station");
		return stationService.getStation(request);
    }

    /**
     * 
     * @param request
     * @return
     */
    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "AddStationRequest")
	@ResponsePayload
    public StationResponse addStation (@RequestPayload AddStationRequest request) {
        System.out.println("Entrando no serviço add Station");
		return stationService.addStation(request);
    }

    /**
     *
     * @param request
     * @return
     */
    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "AllStationRequest")
    @ResponsePayload
    public AllStationResponse getAllStations (@RequestPayload AllStationRequest request) {
        System.out.println("Entrando no serviço add Station");
        return stationService.getAllStations(request);
    }

    /**
     *
     * @param request
     * @return
     */
    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "AllStationMoreProximeRequest")
    @ResponsePayload
    public AllStationResponse getAllStationsMoreProxime (@RequestPayload AllStationMoreProximeRequest request) {
        System.out.println("Entrando no serviço add Station");
        return stationService.getAllStationMoreProxime(request);
    }

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "UpBikeRequest")
	@ResponsePayload
    public UpBikeResponse upBike (@RequestPayload UpBikeRequest request) {
        System.out.println("Entrando no serviço de levantamento de Bike");
		return stationService.upBike(request);
    }

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "DownBikeRequest")
	@ResponsePayload
    public DownBikeResponse downBike (@RequestPayload DownBikeRequest request) {
        System.out.println("Entrando no serviço de devolução de Bike");
		return stationService.downBike(request);
    }

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "AddDockReq")
	@ResponsePayload
    public AddDockResp addDock (@RequestPayload AddDockReq request) {
        System.out.println("Entrando no serviço de Adcicionar doca");
		return stationService.addDock(request);
    }

    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "DeleteDockReq")
	@ResponsePayload
    public DeleteDockResp deleteDock (@RequestPayload DeleteDockReq request) {
        System.out.println("Entrando no serviço de eliminar doca");
		return stationService.deleteDock(request);
    }

}
