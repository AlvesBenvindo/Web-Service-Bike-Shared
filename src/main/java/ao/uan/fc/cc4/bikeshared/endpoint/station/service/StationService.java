package ao.uan.fc.cc4.bikeshared.endpoint.station.service;

import java.util.List;
import java.util.Optional;

import ao.uan.fc.cc4.bikeshared.bd.aquisicaoBike.AquisicaoBikeModel;
import ao.uan.fc.cc4.bikeshared.bd.aquisicaoBike.AquisicaoBikeRepository;
import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaModel;
import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ao.uan.fc.cc4.bikeshared.bd.station.StationRepository;
import ao.uan.fc.cc4.bikeshared.endpoint.user.service.AuthenticationService;
import ao.uan.fc.cc4.bikeshared.utils.Coordinates;
import ao.uan.fc.cc4.bikeshared.utils.GeoLocationDTO;
import ao.uan.fc.cc4.bikeshared.utils.Utils;
import ao.uan.fc.cc4.bikeshared.wsAsCliente.ofJOpenCage.ServiceGeoLocation;
import ao.uan.fc.cc4.bikeshared.wsAsCliente.ofJuddi.JuddiService;
//import ao.uan.fc.cc4.bikeshared.wsAsCliente.ofReplica.WSReplica;
import ao.uan.fc.cc4.bikeshared.wsAsCliente.ofStation.WSstation;
import ao.uan.fc.cc4.bikeshared.bd.station.StationModel;
import xml.soap.station.*;
import xml.soap.AddDockResponse;
import xml.soap.AlterStateDockInDownBikeResponse;
import xml.soap.AlterStateDockInUpBikeResponse;
import xml.soap.DeleteDockResponse;
import xml.soap.GetStationResponse;

@Service
public class StationService {

    @Autowired
    private WSstation stationClient;
    @Autowired
    private AuthenticationService auth;
    @Autowired
    private Utils utils;
    @Autowired(required = true)
    private StationRepository stationRepo;
    @Autowired(required = true)
    private CiclistaRepository ciclistaRepo;
    @Autowired(required = true)
    private AquisicaoBikeRepository aquisicicaoRepo;
    @Autowired(required = true)
    private ServiceGeoLocation geoLocationService;
    @Autowired
    private JuddiService juddiService;
    //@Autowired(required = true)
    //private WSReplica serverWriter;

    public TestStationResponse testStation (TestStationRequest request) {

        System.out.println("Dentro do Serviço de checkin de estação: " + request.getBody().getId());
        TestStationResponse response = new TestStationResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Optional<StationModel> station = stationRepo.findById(request.getBody().getId());
            System.out.println(578698797);
            if(station.isPresent()) {
                try{
                    GetStationResponse gsr = stationClient.getStationState(station.get().getEndpoint());
                    if (gsr != null) {
                        station.get().setState(1);
                        stationRepo.save(station.get());
                        response.setEstado(true);
                        response.setMensagem("Estação activa!!!");
                    } else {
                        station.get().setState(0);
                        stationRepo.save(station.get()); 
                        response.setEstado(false);
                        response.setMensagem("Estação inactiva!!!");
                    }
                } catch (Exception e) { // Catch any unexpected exceptions
                    station.get().setState(0);
                    stationRepo.save(station.get());
                    //serverWriter.writeInReplica("station", stationRepo.save(station.get()).returnString());
                    response.setEstado(false);
                    response.setMensagem("Estação inactiva!!!");
                }
            } else {
                response.setEstado(false);
                response.setMensagem("Estação não foi encontrada");
            }
        }
        return response;
    }

    public StationResponse addStation (AddStationRequest request) {
        System.out.println("Dentro do Serviço de registro de novas estações!!! ");
        StationResponse response = new StationResponse();
        GetStationResponse gsr = null;
        StationModel station = null;
        boolean pmr = false; //esta variável confirma que pelo menos uma estação foi encontrada no uddi e registrada
        List<String> servicos = juddiService.searchStationService();
        if (servicos != null) {
            for(String servico : servicos){
                try{
                    gsr = stationClient.getStationState(servico);
                    if (gsr != null) {
                        response.setEstado(true);
                        System.out.println(gsr.getName());
                        if (stationRepo.existsByEndpoint(servico)) {
                            station = stationRepo.findByEndpoint(servico);
                        } else {
                            pmr = true;
                            System.out.println("ok2x");
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
                            station = stationRepo.save(station);
                            //serverWriter.writeInReplica("station", station.returnString());
                        } catch (Exception exc) {
                            System.out.println("Não deu para salvar o registro!!!");
                        }
                        System.out.println("cadabra");
                    } else {
                        System.out.println("Estação inactiva!!!");
                    }
                } catch (Exception e) {
                    System.out.println("Estação inactiva!!!");
                }
            }
            if (pmr) {
                response.setMensagem("Novas estações foram encontradas e adicionadas!!!");
            } else {
                response.setMensagem("Estações foram encontradas!!!");
            }
        } else {
            response.setMensagem("Serviços de estações indisponíveis");
        }
        return response;
    }

    public GetStationDetailsResponse getStation (GetStationDetailsRequest request) {

        System.out.println("Dentro do Serviço e ID da estação: " + request.getBody().getId());
        GetStationDetailsResponse response = new GetStationDetailsResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Optional<StationModel> stationModel = stationRepo.findById(request.getBody().getId());
            System.out.println(5786);
            if(stationModel.isPresent()) {

                GetStationResponse gsr = stationClient.getStationState(stationModel.get().getEndpoint());

                if (gsr != null) {

                    int qtdDocks = gsr.getDockItem().size();
                    int qtdDocksDispo = 0;
                    for (xml.soap.DockType dock : gsr.getDockItem()) {
                        if (dock.getState()==0) qtdDocksDispo++;
                    }
                    stationModel.get().setQtdDocks(qtdDocks);
                    stationModel.get().setQtdDocksDispo(qtdDocksDispo);
                    stationRepo.save(stationModel.get());
                    //serverWriter.writeInReplica("station", stationRepo.save(stationModel.get()).returnString());

                    BeanUtils.copyProperties(gsr, response);
                    response.setEstado(true);
                    response.setMensagem("Station encontrada com sucesso!!!");

                    gsr.getDockItem().forEach( dock -> {
                        DockType d = new DockType();
                        d.setState(dock.getState());
                        d.setIdDock(dock.getIdDock());
                        d.setReference(dock.getReference());
                        response.getDockItem().add(d);
                    });

                    GeoLocationDTO geoLocation = geoLocationService.getGeoLocation(String.valueOf(stationModel.get().getLatitude()), String.valueOf(stationModel.get().getLongitude()));

                    if(geoLocation!=null){
            
                        System.out.println("País: "+geoLocation.getPais());
                        System.out.println("Província: "+geoLocation.getProvincia());
                        System.out.println("Município: "+geoLocation.getMunicipio());
                        System.out.println("Distrito: "+geoLocation.getDistrito());
                        System.out.println("Avenida: "+geoLocation.getAvenida());
        
                        response.setPais(geoLocation.getPais());
                        response.setProvincia(geoLocation.getProvincia());
                        response.setMunicipio(geoLocation.getMunicipio());
                        response.setDistrito(geoLocation.getDistrito());
                        response.setAvenida(geoLocation.getAvenida());

                    }

                    return response;
                }
                response.setEstado(false);
                response.setMensagem("Station data corrupted!!!");
                return response;
            }
            else {
                response.setEstado(false);
                response.setMensagem("Estação não foi encontrada");
            }
        }
        return response;
    }

    public AllStationResponse getAllStations (AllStationRequest request) {
        System.out.println(request.getHeader().getAuthToken());
        AllStationResponse response = new AllStationResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            //SessionModel session = sessionRepo.findByToken(request.getHeader().getAuthToken());
            //if (ciclistaRepo.existsByUserId(session.getUser())) {
                List<StationModel> stationList = stationRepo.findAll();
                if (!stationList.isEmpty()) {
                    response.setEstado(true);
                    response.setMensagem("Estações encontradas com sucesso!");
                    for(StationModel station : stationList) {
                        if(juddiService.checkStationService(station.getName())!=null){
                            System.out.println(juddiService.checkStationService(station.getName()));
                            StationResponseType stationType = new StationResponseType();
                            BeanUtils.copyProperties(station, stationType);
                            stationType.setDocks(station.getQtdDocks());
                            stationType.setDocksDisp(station.getQtdDocksDispo());
        
                            GeoLocationDTO geoLocation = geoLocationService.getGeoLocation(String.valueOf(station.getLatitude()), String.valueOf(station.getLongitude()));
        
                            if(geoLocation!=null){
        
                                stationType.setPais(geoLocation.getPais());
                                stationType.setProvincia(geoLocation.getProvincia());
                                stationType.setMunicipio(geoLocation.getMunicipio());
                                stationType.setDistrito(geoLocation.getDistrito());
                                stationType.setAvenida(geoLocation.getAvenida());
        
                            }
                            response.getStationItem().add(stationType);
                        }
                        
                    }
                } else {
                    response.setEstado(false);
                    response.setMensagem("Não há estações ainda!");
                }
            /*} else {
                List<StationModel> stationList = stationRepo.findAll();
                if (!stationList.isEmpty()) {
                    response.setEstado(true);
                    response.setMensagem("Estações encontradas com sucesso!");
                    stationList.forEach( station -> {
                        StationResponseType stationType = new StationResponseType();
                        BeanUtils.copyProperties(station, stationType);
                        stationType.setDocks(station.getQtdDocks());
                        stationType.setDocksDisp(station.getQtdDocksDispo());

                        GeoLocationDTO geoLocation = geoLocationService.getGeoLocation(String.valueOf(station.getLatitude()), String.valueOf(station.getLongitude()));

                        if(geoLocation!=null){

                            stationType.setPais(geoLocation.getPais());
                            stationType.setProvincia(geoLocation.getProvincia());
                            stationType.setMunicipio(geoLocation.getMunicipio());
                            stationType.setDistrito(geoLocation.getDistrito());
                            stationType.setAvenida(geoLocation.getAvenida());

                        }
                        response.getStationItem().add(stationType);
                    });
                } else {
                    response.setEstado(false);
                    response.setMensagem("Não há estações ainda!");
                }
            }*/
        }
        return response;
    }

    public AllStationResponse getAllStationMoreProxime (AllStationMoreProximeRequest request) {
        AllStationResponse response = new AllStationResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            List<StationModel> stationList = stationRepo.findAll();
            if (!stationList.isEmpty()) {
                response.setEstado(true);
                stationList.forEach( station -> {
                    if(juddiService.checkStationService(station.getName())!=null){
                        Double distancia = geoLocationService.
                            calcDistanceHaversine(
                                new Coordinates(request.getBody().getLatitude(), request.getBody().getLongitude()), 
                                new Coordinates(station.getLatitude().doubleValue(), station.getLongitude().doubleValue()), 
                                request.getBody().getRadius()
                            );
                        
                        if(distancia>0){
                            StationResponseType stationType = new StationResponseType();
                            BeanUtils.copyProperties(station, stationType);
                            stationType.setDocks(station.getQtdDocks());
                            stationType.setDocksDisp(station.getQtdDocksDispo());
                            stationType.setDistancia(distancia.intValue());

                            GeoLocationDTO geoLocation = geoLocationService.getGeoLocation(String.valueOf(station.getLatitude()), String.valueOf(station.getLongitude()));

                            if(geoLocation!=null){

                                stationType.setPais(geoLocation.getPais());
                                stationType.setProvincia(geoLocation.getProvincia());
                                stationType.setMunicipio(geoLocation.getMunicipio());
                                stationType.setDistrito(geoLocation.getDistrito());
                                stationType.setAvenida(geoLocation.getAvenida());

                            }
                            response.getStationItem().add(stationType);
                        }
                    }
                });
                if (response.getStationItem().isEmpty()) response.setMensagem("Não há estações num raio de "+request.getBody().getRadius()+" metros");
            } else {
                response.setEstado(false);
                response.setMensagem("Não há estações no sistema ainda!");
            }
        }
        return response;
    }
    
    public UpBikeResponse upBike (UpBikeRequest request) {
        /**
         * O valor que representa o levantamento da Bike é 1
         */
        UpBikeResponse response = new UpBikeResponse();
        response.setEstadoCiclista(0);
        response.setEstado(false);

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Optional<CiclistaModel> ciclista = ciclistaRepo.findById(request.getBody().getIdCiclista());
            if (ciclista.isPresent()) {
                /*
                 * Aqui está toda a lógica para levantar uma Bicicleta;
                 */
                if (ciclista.get().getState()==0){
                    Integer idStation = Utils.extractInteger(request.getBody().getReferenceDock().split("_")[0]);
                    String urlStation = juddiService.checkStationService(idStation);
                    boolean check = false;
                    if (urlStation != null) {
                        //Pengando o estado actual da station em que será feito o levantamento
                        GetStationResponse gsr = stationClient.getStationState(urlStation);
                        for(xml.soap.DockType dock : gsr.getDockItem()) {
                            if(dock.getReference().equals(request.getBody().getReferenceDock())){
                                System.out.println(dock.getReference().equals(request.getBody().getReferenceDock()));
                                System.out.println(dock.getReference().equals(request.getBody().getReferenceDock()));
                                ciclista.get().setState(1);
                                ciclista.get().setInfo(request.getBody().getInfo());
                                //Aqui alteramos o estado da doca que tinha a Bike
                                AlterStateDockInUpBikeResponse alteredDock = stationClient.updateDockStateInUpBike(
                                    urlStation,
                                    dock.getIdDock(),
                                    0);
                                System.out.println(alteredDock.isEstado());
                                //Aqui virão os comandos para este processo na BD inserir na BD;
                                if(alteredDock.isEstado()==true){
                                    System.out.println("referencia: " + dock.getReference());
                                    //Actualizando o estado do ciclista
                                    ciclistaRepo.save(ciclista.get());
                                    //serverWriter.writeInReplica("ciclista", ciclistaRepo.save(ciclista.get()).returnString());

                                    //registrando o levantamento da bike
                                    AquisicaoBikeModel aquisicao = new AquisicaoBikeModel();
                                    aquisicao.setStation(gsr.getName());
                                    aquisicao.setTipo_aquisicao(1);
                                    aquisicao.setCiclistaId(ciclista.get().getId());
                                    aquisicao.setCreatedAt(utils.getInstante());
                                    aquisicicaoRepo.save(aquisicao);
                                    //serverWriter.writeInReplica("aquisicao", aquisicicaoRepo.save(aquisicao).returnString());
                                    response.setEstadoCiclista(ciclista.get().getState());
                                    response.setEstado(true);
                                    response.setMensagem("Levantamento feito com sucesso");

                                    check = true;
                                    break;
                                } else {
                                    response.setMensagem("Impossível levantar bike");
                                    break;
                                }
                            }
                        }
                        if (!check) {
                            response.setMensagem("Seleccione a doca correcta!!!");
                        }
                    } else {
                        response.setMensagem("Estação indisponível!!!");
                    }
                } else {
                    response.setMensagem("Impossível levantar, ainda não devolveste a última bike que lavantaste!");
                }
            }else{
                response.setMensagem("Impossível levantar Bike!");
            }
        }
        return response;
    }

    public DownBikeResponse downBike (DownBikeRequest request) {
        /**
         * O valor que representa a devolução da Bike é 2
         */
        DownBikeResponse response = new DownBikeResponse();
        response.setEstado(false);

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Optional<CiclistaModel> ciclista = ciclistaRepo.findById(request.getBody().getIdCiclista());
            if (ciclista.isPresent()) {
                /*
                 * Aqui estará toda a lógica para devolver uma Bicicleta;
                 */
                if (ciclista.get().getState()==1) {
                    Integer idStation = Utils.extractInteger(request.getBody().getReferenceDock().split("_")[0]);
                    String urlStation = juddiService.checkStationService(idStation);
                    boolean check = false;
                    if (urlStation != null) {
                        //Pengando o estado actual da station em que será feita a devolução
                        GetStationResponse gsr = stationClient.getStationState(urlStation);
                        for(xml.soap.DockType dock : gsr.getDockItem()) {
                            if(dock.getReference().equals(request.getBody().getReferenceDock())){
                                ciclista.get().setState(0);
                                ciclista.get().setPoints(ciclista.get().getPoints()+gsr.getBonus());
                                //Aqui alteramos o estado da doca que terá a Bike
                                AlterStateDockInDownBikeResponse alteredDock = stationClient.updateDockStateInDownBike(
                                    urlStation,
                                    dock.getIdDock(),
                                    1,
                                    request.getBody().getInfo()
                                );
                                System.out.println(alteredDock.isEstado());
                                //Aqui estão os comandos para 0 processo de BD inserir na BD;
                                if(alteredDock.isEstado()==true){
                                    //Actualizando o estado do ciclista
                                    ciclistaRepo.save(ciclista.get());
                                    //serverWriter.writeInReplica("ciclista", ciclistaRepo.save(ciclista.get()).returnString());

                                    //registrando a devolução da bike
                                    AquisicaoBikeModel aquisicao = new AquisicaoBikeModel();
                                    aquisicao.setStation(gsr.getName());
                                    aquisicao.setTipo_aquisicao(2);
                                    aquisicao.setCiclistaId(ciclista.get().getId());
                                    aquisicao.setCreatedAt(utils.getInstante());
                                    aquisicicaoRepo.save(aquisicao);
                                    //serverWriter.writeInReplica("aquisicao", aquisicicaoRepo.save(aquisicao).returnString());
                                    response.setEstado(true);
                                    response.setMensagem("Devolução feita com sucesso, recebeste "+gsr.getBonus()+" pontos de bónus!");

                                    check = true;
                                    break;
                                } else {
                                    response.setMensagem("Impossível devolver bike!!!");
                                    break;
                                }
                            } else response.setMensagem("Seleccione a doca correcta!!!");
                        }
                        if (!check) {
                            response.setMensagem("Seleccione a doca correcta!!!");
                        }
                    }else{
                        response.setMensagem("Estação não identificada!");
                    }
                } else {
                    response.setMensagem("Impossível devolver, ainda não levantaste uma bike!");
                }
            }else{
                response.setMensagem("Impossível devolver Bike!");
            }
        }
        return response;
    }

    public AddDockResp addDock (AddDockReq request) {
        System.out.println("Entrou no serviço add dock");
        AddDockResp response = new AddDockResp();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            System.out.println("Entrou no serviço add dock-2");
            System.out.println("Nome da estação: "+request.getBody().getStation());
            String urlService = juddiService.checkStationService(request.getBody().getStation());
            try {
                System.out.println("Entrou no serviço add dock-3");
                /*
                 * Aqui estará toda a lógica para add uma doca em uma estação;
                 */
                //Acedendo ao serviço de add de docas da station designada!
                AddDockResponse gsr = stationClient.addDock(urlService);
                //Aqui vem toda a lógica para adicionar uma nova doca
                if (gsr.isState()==true){
                    response.setEstado(true);
                    response.setMensagem("Doca adicionada com sucesso!");
                }
            } catch (Exception exc) {
                response.setEstado(false);
                response.setMensagem("Não foi possível adicionar doca, estação indisponível");
            }
        }
        return response;
    }

    public DeleteDockResp deleteDock (DeleteDockReq request) {

        DeleteDockResp response = new DeleteDockResp();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Integer idStation = Utils.extractInteger(request.getBody().getReference().split("_")[0]);
            String urlStation = juddiService.checkStationService(idStation);
            if (urlStation != null) {
                /*
                 * Aqui estará toda a lógica para eliminar uma doca em uma estação;
                 */
                //Acedendo ao serviço de delete de docas da station designada!
                try {
                    DeleteDockResponse gsr = stationClient.deleteDock(urlStation, request.getBody().getReference());
                    //Aqui vem toda a lógica para adicionar uma nova doca
                    if (gsr.isState()==true){
                        response.setEstado(true);
                        response.setMensagem("Doca removida com sucesso!");
                    }
                } catch (Exception e) {
                    response.setEstado(false);
                    response.setMensagem("Não foi possível remover doca, estação indisponível!!!");
                }
            }else{
                response.setEstado(false);
                response.setMensagem("Não foi possível remover doca, estação indisponível!!!");
            }
        }
        return response;
    }

}
