package ao.uan.fc.cc4.bikeshared.wsAsCliente.ofUDDI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ConfigUDDI {

    @Bean
    public Jaxb2Marshaller marshallerUDDI () {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        marshaller.setContextPath("xml.soap.uddi");

        return marshaller;

    }

    @Bean
    public UDDI uddiService (Jaxb2Marshaller marshallerUDDI) {

        UDDI uddi = new UDDI();
        uddi.setDefaultUri("http://127.0.0.1:8089/ws_uddi/");
        uddi.setMarshaller(marshallerUDDI);
        uddi.setUnmarshaller(marshallerUDDI);

        return uddi;
    }

}
