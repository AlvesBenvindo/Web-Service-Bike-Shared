package ao.uan.fc.cc4.bikeshared.wsAsCliente.ofReplica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
//import org.springframework.ws.soap.client.core.SoapActionCallback;

import ao.uan.fc.cc4.bikeshared.wsAsCliente.ofJuddi.JuddiService;
import xml.replication.ReplicateRequest;
import xml.replication.ReplicateResponse;

@Component
public class WSReplica extends WebServiceGatewaySupport {

    @Autowired
    JuddiService juddiService;

    public void writeInReplica (String table, String row) {
        System.out.println("Solicitando escrita em servidor cópia!!!");
        ReplicateRequest request = new ReplicateRequest();
        request.setTable(table);
        request.setRow(row);

        ReplicateResponse response = null;
        String urlReplicaServer = "http://127.0.0.1:8089/ws";
        try {
            response = (ReplicateResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                    urlReplicaServer,
                    request
            );
            if (response.isEstado()) System.out.println("Replicou com sucesso em " + urlReplicaServer);
            else System.out.println("Não replicou em " + urlReplicaServer);
        } catch (Exception exc) {
            System.out.println("não foi possível replicar, servidor réplica indisponível!!!");;
        }
    }
}
