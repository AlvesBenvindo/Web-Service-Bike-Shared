package ao.uan.fc.cc4.bikeshared;

import ao.uan.fc.cc4.bikeshared.bd.station.StationModel;
import ao.uan.fc.cc4.bikeshared.bd.station.StationRepository;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import ao.uan.fc.cc4.bikeshared.utils.HashPassword;
import ao.uan.fc.cc4.bikeshared.wsAsCliente.ofStation.WSstation;
import ao.uan.fc.dam.ws.uddi.UDDINaming;
import xml.soap.GetStationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BikeSharedApplication {

	@Autowired(required = true)
	UDDINaming juddiService;
    @Autowired(required = true)
    private UserRepository userRepo;
    @Autowired(required = true)
    private StationRepository stationRepo;
	@Autowired
    private WSstation stationClient;

	public static void main(String[] args) {

		SpringApplication.run(BikeSharedApplication.class, args);
		System.out.println("Web Service Bike Shared On Fire!!!");

	}

	@Bean
	@Profile("!test")
	public CommandLineRunner executar () {
        UserModel superadmin = userRepo.findByNome("admin");
		if (superadmin == null) {
			superadmin = new UserModel();
			superadmin.setEmail("admin@gmail.com");
			superadmin.setNome("admin");
			superadmin.setSobrenome("admin");
			superadmin.setPassword(HashPassword.hashing("admin"));
			superadmin.setGenero("F");
			superadmin.setFoto(null);
            userRepo.save(superadmin);
		};
		return args -> {
			GetStationResponse gsr = null;
			StationModel station = null;
			boolean control = false;
			boolean pmu = false; //esta variável confirma que pelo menos uma estação foi encontrada no uddi
			int i = (stationRepo.findAll().size() * 3)/2;
			try {
				while (i > 0) {
					String url = juddiService.lookup("CXX_Station" + i);
					if (url != null) {
						pmu = true;
						try{
							gsr = stationClient.getStationState(url);
							if (gsr != null) {
								if (stationRepo.existsByEndpoint(url)) {
									station = stationRepo.findByEndpoint(url);
								} else {
									station = new StationModel();
									control = true;
								}
								System.out.println("XXXXXXXXX "+station.getEndpoint());
								station.setBonus(gsr.getBonus());
								station.setLatitude(gsr.getLatitude());
								station.setLongitude(gsr.getLongitude());
								station.setLocalName(gsr.getLocalName());
								station.setName(gsr.getName());
								station.setQtdDocks(gsr.getDockItem().size());
								station.setState(1);
								int qtdDispo = 0;
								for (xml.soap.DockType dock : gsr.getDockItem()) {
									if (dock.getState() == 0) qtdDispo++;
								}
								station.setQtdDocksDispo(qtdDispo);
								stationRepo.save(station);
							} else {
								System.out.println("Estação inactiva inactiva!!!");
							}
						} catch (Exception e) {
							System.out.println("Estação inactiva inactiva!!!");
						}
					}
					i--;
				}
				
				if (!pmu) {
					System.out.println("Serviços de estações indisponíveis");
				} else if (control) {
					System.out.println("Novas estações foram encontradas e adicionadas!!!");
				} else {
					System.out.println("Estações foram encontradas!!!");
				}
			} catch (Exception e) {
				System.out.println(" JUDDI indisponível !!!");
				System.out.println("Serviços indisponíveis");
			}
		};
	}

}
