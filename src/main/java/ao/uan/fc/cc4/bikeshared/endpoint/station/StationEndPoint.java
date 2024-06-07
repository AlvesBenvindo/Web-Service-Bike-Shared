package ao.uan.fc.cc4.bikeshared.endpoint.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.bikeshared.endpoint.station.service.StationService;
import xml.soap.station.*;

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
    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "GetStationRequest")
	@ResponsePayload
    public StationResponse getStation (@RequestPayload GetStationRequest request) {
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
    public AllStationResponse getAllUsers (@RequestPayload AllStationRequest request) {
        System.out.println("Entrando no serviço add Station");
        return stationService.getAllStations(request);
    }

}
