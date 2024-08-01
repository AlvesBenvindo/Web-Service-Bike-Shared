package ao.uan.fc.cc4.bikeshared.Station4;

import ao.uan.fc.dam.ws.uddi.UDDINaming;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class Station4Application {

	public static void main(String[] args) {
		SpringApplication.run(Station4Application.class, args);
		System.out.println("Station 4 ON FIRE !!!");
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner run() throws Exception {
		return args -> {
			UDDINaming juddiService = null;
			try{
				// publish to UDDI
				System.out.printf("Publishing '%s' to UDDI at %s%n", "CXX_Station4", "http://localhost:9090");
				juddiService = new UDDINaming("http://localhost:9090");
				juddiService.rebind("CXX_Station4", "http://localhost:8084/wsStation");

				// wait
				System.out.println("Awaiting connections");
				System.out.println("Press enter to shutdown");
				System.in.read();
			} catch (Exception e) {
				System.out.printf("Caught exception: %s%n", e);
				e.printStackTrace();

			} finally {
				try {
					if (juddiService != null) {
						// delete from UDDI
						juddiService.unbind("CXX_Station4");
						System.out.printf("Deleted '%s' from UDDI%n", "CXX_Station4");
					}
				} catch (Exception e) {
					System.out.printf("Caught exception when deleting: %s%n", e);
				}
			}
		};
	}

}
