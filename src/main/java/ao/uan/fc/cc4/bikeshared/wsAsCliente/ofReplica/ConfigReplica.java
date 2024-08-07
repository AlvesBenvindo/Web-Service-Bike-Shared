package ao.uan.fc.cc4.bikeshared.wsAsCliente.ofReplica;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ConfigReplica {

    @Bean
    public Jaxb2Marshaller marshallerReplic () {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("xml.replication");
        return marshaller;

    }

    @Bean
    public WSReplica serverWriter (Jaxb2Marshaller marshallerReplic) {

        WSReplica client = new WSReplica();
        client.setDefaultUri("http://127.0.0.1:8089/ws");
        client.setMarshaller(marshallerReplic);
        client.setUnmarshaller(marshallerReplic);
        return client;
    }

}
