package ao.uan.fc.cc4.bikeshared.cliente.station;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import xml.soap.GetStationRequest;
import xml.soap.GetStationResponse;


public class StationClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(StationClient.class);

    public GetStationResponse getStationState () {

        GetStationRequest request = new GetStationRequest();

        System.out.println("Data of station: ");

        GetStationResponse response = (GetStationResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                        "http://localhost:8081/wsStation/station",
                        request,
                        new SoapActionCallback("http://soap.xml/GetStationRequest"));

        return response;
    }

}
