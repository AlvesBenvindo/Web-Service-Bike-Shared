package ao.uan.fc.cc4.bikeshared.endpoint.replication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ao.uan.fc.cc4.bikeshared.endpoint.replication.service.ReplicationService;
import xml.replication.ReplicateRequest;
import xml.replication.ReplicateResponse;

@Endpoint
public class ReplicationEndpoint {

    private static final String NAMESPACE_URI = "http://replication.xml";

    @Autowired(required = true)
    private ReplicationService repli;


    @PayloadRoot(namespace= NAMESPACE_URI, localPart = "ReplicateRequest")
	@ResponsePayload
    public ReplicateResponse replicate (@RequestPayload ReplicateRequest request) {
        return repli.replicate(request);
    }
    
}
