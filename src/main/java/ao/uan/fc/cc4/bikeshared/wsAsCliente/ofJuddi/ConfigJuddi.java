package ao.uan.fc.cc4.bikeshared.wsAsCliente.ofJuddi;

import ao.uan.fc.dam.ws.uddi.UDDINaming;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigJuddi {

    @Bean
    public UDDINaming juddiService () throws Exception {

        UDDINaming uddi = new UDDINaming("http://localhost:9090");

        return uddi;
    }
}
