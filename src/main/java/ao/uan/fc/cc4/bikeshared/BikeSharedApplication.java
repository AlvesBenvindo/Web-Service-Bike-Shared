package ao.uan.fc.cc4.bikeshared;

import ao.uan.fc.cc4.bikeshared.bd.station.StationModel;
import ao.uan.fc.cc4.bikeshared.bd.station.StationRepository;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import ao.uan.fc.cc4.bikeshared.utils.HashPassword;
import ao.uan.fc.cc4.bikeshared.wsAsCliente.ofJuddi.JuddiService;
import ao.uan.fc.cc4.bikeshared.wsAsCliente.ofStation.WSstation;
import xml.soap.GetStationResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BikeSharedApplication {

	@Autowired
	JuddiService juddiService;
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
			boolean pmr = false; //esta variável confirma que pelo menos uma estação foi encontrada no uddi e registrada
			List<String> servicos = juddiService.searchStationService();
			if (servicos != null) {
				for(String servico : servicos){
					try{
						gsr = stationClient.getStationState(servico);
						if (gsr != null) {
							if (stationRepo.existsByEndpoint(servico)) {
								station = stationRepo.findByEndpoint(servico);
							} else {
								pmr = true;
								station = new StationModel();
								station.setEndpoint(servico);
							}
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
							System.out.println("abra");
							try{
								stationRepo.save(station);
							} catch (Exception exc) {
								System.out.println("Não deu paara salvar o registro!!!");
							}
							System.out.println("cadabra");
							stationRepo.save(station);
						} else {
							System.out.println("Estação inactiva!!!");
						}
					} catch (Exception e) {
						System.out.println("Estação inactiva!!!");
					}
				}
				if (pmr) {
					System.out.println("Novas estações foram encontradas e adicionadas!!!");
				} else {
					System.out.println("Existem estações disponíveis!!!");
				}
			} else {
				System.out.println("Serviços de estações indisponíveis");
			}
		};
	}

}