package ao.uan.fc.cc4.bikeshared.Station3.wsAsCliente.ofUDDI.improvisado;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ConfigStation {

    @Bean
    public Jaxb2Marshaller marshaller () {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        marshaller.setContextPath("xml.soap.uddi");

        return marshaller;

    }

    @Bean
    public UDDI uddiService (Jaxb2Marshaller marshaller) {

        UDDI uddi = new UDDI();
        uddi.setDefaultUri("http://127.0.0.1:8089/ws_uddi/");
        uddi.setMarshaller(marshaller);
        uddi.setUnmarshaller(marshaller);

        return uddi;
    }

}
