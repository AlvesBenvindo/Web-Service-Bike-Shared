package ao.uan.fc.cc4.bikeshared.wsAsCliente.ofJOpenCage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigJOpenCage {

    @Bean
    public RestTemplate restTemplate333() {
        return new RestTemplate();
    }
    
}
