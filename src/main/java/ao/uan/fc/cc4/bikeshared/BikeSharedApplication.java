package ao.uan.fc.cc4.bikeshared;

import ao.uan.fc.dam.ws.uddi.UDDINaming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BikeSharedApplication {

	@Autowired(required = true)
	UDDINaming juddiService;

	public static void main(String[] args) {

		SpringApplication.run(BikeSharedApplication.class, args);
		System.out.println("Web Service Bike Shared On Fire!!!");

	}

	@Bean
	@Profile("!test")
	public CommandLineRunner executar () {
		return args -> {
			try {
				int i = 10;
				List<String> urls = new ArrayList<>();
				while (i > 0) {
					String url = juddiService.lookup("CXX_Station" + i--);
					if (url != null) urls.add(url);
				}
				if (urls.isEmpty()) System.out.println("Nada encontrado");
				else {
					i = 0;
					for (String url : urls){
						System.out.println("\t" + (++i) + "º - Serviço encontrado com sucesso!!!");
						System.out.println("\t" + url);
					}
				}
			} catch (Exception e) {
				System.out.println(" JUDDI indisponível !!!");
			}
		};
	}

}
