package ao.uan.fc.cc4.bikeshared.wsAsCliente.ofUDDI;

import org.springframework.stereotype.Component;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import xml.soap.uddi.GetServicesRequest;
import xml.soap.uddi.ServicesListResponse;

@Component
public class UDDI extends WebServiceGatewaySupport {

    // private static final Logger log = LoggerFactory.getLogger(StationClient.clas

    public ServicesListResponse discoverServices () {
        GetServicesRequest request = new GetServicesRequest();
        ServicesListResponse response = (ServicesListResponse) getWebServiceTemplate()
            .marshalSendAndReceive(
                "http://127.0.0.1:8089/ws_uddi/",
                request,
                new SoapActionCallback("http://uddi.soap.xml/RegistrerServiceRequest")
            );
        return response;
    }
    
}
