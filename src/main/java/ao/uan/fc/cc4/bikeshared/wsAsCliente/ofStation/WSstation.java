package ao.uan.fc.cc4.bikeshared.wsAsCliente.ofStation;

import org.springframework.stereotype.Component;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

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

@Component
public class WSstation extends WebServiceGatewaySupport {

    // private static final Logger log = LoggerFactory.getLogger(StationClient.class);

    public GetStationResponse getStationState (String url) {
        GetStationRequest request = new GetStationRequest();
        System.out.println("Data of station: ");
        GetStationResponse response = (GetStationResponse) getWebServiceTemplate()
            .marshalSendAndReceive(
                url,
                request,
                new SoapActionCallback("http://soap.xml/GetStationRequest")
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

    public AlterStateDockInDownBikeResponse updateDockStateInDownBike (String url, Long dock, int state, String info) {
        AlterStateDockInDownBikeRequest request = new AlterStateDockInDownBikeRequest();
        request.setIdDock(dock);
        request.setState(state);
        request.setInfo(info);
        AlterStateDockInDownBikeResponse response = (AlterStateDockInDownBikeResponse) getWebServiceTemplate()
            .marshalSendAndReceive(
                url,
                request,
                new SoapActionCallback("http://soap.xml/AlterStateDockInDownBikeRequest")
            );
        return response;
    }

    public AddDockResponse addDock (String url) {
        AddDockRequest request = new AddDockRequest();
        AddDockResponse response  = (AddDockResponse) getWebServiceTemplate()
            .marshalSendAndReceive(
                url,
                request,
                new SoapActionCallback("http://soap.xml/AddDockRequest")
            );
        return response;
    }

    public DeleteDockResponse deleteDock (String url, String reference) {
        DeleteDockRequest request = new DeleteDockRequest();
        request.setReference(reference);
        DeleteDockResponse response  = (DeleteDockResponse) getWebServiceTemplate()
            .marshalSendAndReceive(url,
            request,
            new SoapActionCallback("http://soap.xml/DeleteDockRequest")
        );
        return response;
    }

}
