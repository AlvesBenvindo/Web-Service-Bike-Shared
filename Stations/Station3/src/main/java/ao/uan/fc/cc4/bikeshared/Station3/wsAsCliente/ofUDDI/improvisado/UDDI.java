package ao.uan.fc.cc4.bikeshared.Station3.wsAsCliente.ofUDDI.improvisado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import ao.uan.fc.cc4.bikeshared.Station3.bd.DataFixed.Model;
import ao.uan.fc.cc4.bikeshared.Station3.bd.DataFixed.ModelRepository;
import xml.soap.AlterStateDockInUpBikeRequest;
import xml.soap.AlterStateDockInUpBikeResponse;
import xml.soap.uddi.RegistrerServiceRequest;
import xml.soap.uddi.RegistrerServiceResponse;

@Component
public class UDDI extends WebServiceGatewaySupport {

    @Autowired(required = true)
    private ModelRepository stationRepo;

    // private static final Logger log = LoggerFactory.getLogger(StationClient.class);

    public RegistrerServiceResponse registOnService (String url) {
        RegistrerServiceRequest request = new RegistrerServiceRequest();
        System.out.println("Data of station: ");
        Model station = stationRepo.findAll().get(0);
        request.setEndpoint(station.getEndpoint());
        request.setLatitude(station.getLatitude());
        request.setLongitude(station.getLongitude());
        request.setName(station.getName());
        RegistrerServiceResponse response = (RegistrerServiceResponse) getWebServiceTemplate()
            .marshalSendAndReceive(
                url,
                request,
                new SoapActionCallback("http://uddi.soap.xml/RegistrerServiceRequest")
            );
        return response;
    }

    public AlterStateDockInUpBikeResponse updateDockStateInUpBike (String url, Long dock, int state) {
        AlterStateDockInUpBikeRequest request = new AlterStateDockInUpBikeRequest();
        request.setIdDock(dock);
        request.setState(state);
        AlterStateDockInUpBikeResponse response = (AlterStateDockInUpBikeResponse) getWebServiceTemplate()
            .marshalSendAndReceive(
                url,
                request,
                new SoapActionCallback("http://soap.xml/AlterStateDockInUpBikeRequest")
            );
        return response;
    }
    
}
