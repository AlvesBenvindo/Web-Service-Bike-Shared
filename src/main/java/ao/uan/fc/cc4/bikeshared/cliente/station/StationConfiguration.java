package ao.uan.fc.cc4.bikeshared.cliente.station;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class StationConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller () {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        marshaller.setContextPath("xml.soap");

        return marshaller;

    }

    @Bean
    public StationClient stationClient (Jaxb2Marshaller marshaller) {

        StationClient client = new StationClient();
        client.setDefaultUri("http://127.0.0.1:8081/wsStation");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }

}
