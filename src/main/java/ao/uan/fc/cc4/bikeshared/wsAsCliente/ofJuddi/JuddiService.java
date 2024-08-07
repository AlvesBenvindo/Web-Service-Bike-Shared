package ao.uan.fc.cc4.bikeshared.wsAsCliente.ofJuddi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ao.uan.fc.cc4.bikeshared.bd.station.StationRepository;
import ao.uan.fc.dam.ws.uddi.UDDINaming;

@Component
public class JuddiService {

    @Autowired(required = true)
    private StationRepository stationRepo;
    private UDDINaming uddiNaming;

    public JuddiService () throws Exception{
        uddiNaming = new UDDINaming("http://localhost:9090");
    }

    public List<String> searchReplicaService () {
        List<String> servicos = new ArrayList<String>();

        int i = 3;
        try {
            while (i > 0) {
                String urlService = uddiNaming.lookup("BS_Replica" + i);
                if (urlService != null) {
                    System.out.println(urlService);
                    servicos.add(urlService);
                }
                i--;
            }
            if (servicos.isEmpty()) servicos = null;
        } catch (Exception e) {
            System.out.println("JUDDI indisponível!!!");
            System.out.println("Réplicas indisponíveis!!!");
            return null;
        }
        
        return servicos;
    }

    public List<String> searchStationService () {
        List<String> servicos = new ArrayList<String>();

        int i = (stationRepo.findAll().size() * 3)/2;
        if (i==0) i = 10;
        try {
            while (i > 0) {
                String urlService = uddiNaming.lookup("CXX_Station" + i);
                if (urlService != null) {
                    System.out.println(urlService);
                    servicos.add(urlService);
                }
                i--;
            }
            if (servicos.isEmpty()) servicos = null;
        } catch (Exception e) {
            System.out.println("JUDDI indisponível!!!");
            System.out.println("Serviços indisponíveis!!!");
            return null;
        }
        
        return servicos;
    }

    public String checkStationService (int id) {
        String urlService = null;
        try {
            urlService = uddiNaming.lookup("CXX_station" + id);
            if (urlService != null) {
                System.out.println(urlService);
                return urlService;
            }
        } catch (Exception e) {
            System.out.println("JUDDI indisponível!!!");
            System.out.println("Serviço indisponíveis!!!");
            return null;
        }
        return urlService;
    }

    public String checkStationService (String name) {
        String urlService = null;
        try {
            urlService = uddiNaming.lookup(name);
            if (urlService != null) {
                System.out.println(urlService);
                return urlService;
            }
        } catch (Exception e) {
            System.out.println("JUDDI indisponível!!!");
            System.out.println("Serviço indisponíveis!!!");
            return null;
        }
        return urlService;
    }
    
}
