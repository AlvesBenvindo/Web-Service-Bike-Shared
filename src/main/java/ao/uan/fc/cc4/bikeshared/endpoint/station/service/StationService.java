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
import ao.uan.fc.cc4.bikeshared.utils.Utils;
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
    AuthenticationService auth;
    @Autowired(required = true)
    private StationRepository stationRepo;
    @Autowired(required = true)
    private CiclistaRepository ciclistaRepo;
    @Autowired(required = true)
    private AquisicaoBikeRepository aquisicicaoRepo;

    public StationResponse addStation (AddStationRequest request) {

        System.out.println("Dentro do Serviço "+ request.getBody().getEndpoint());
        StationResponse response = new StationResponse();
        System.out.println(request.getBody().getLocalName()+": "+request.getBody().getLatitude()+": "+request.getBody().getLongitude());
        StationModel stationModel = new StationModel();
        BeanUtils.copyProperties(request.getBody(), stationModel);
        stationRepo.save(stationModel);

        BeanUtils.copyProperties(stationModel, response);
        response.setEstado(true);
        response.setMensagem("Estação adicionado com sucesso!!!");

        return response;
    }

    public GetStationDetailsResponse getStation (GetStationDetailsRequest request) {

        System.out.println("Dentro do Serviço e ID da estação: " + request.getBody().getId());
        GetStationDetailsResponse response = new GetStationDetailsResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            Optional<StationModel> stationModel = stationRepo.findById(request.getBody().getId());
            System.out.println(5786);
            if(stationModel.isPresent()) {
                // if (stationModel.get().getName().equals("CXX_station1")) System.setProperty("station.uri.port", "8081");
                // if (stationModel.get().getName().equals("CXX_station2")) System.setProperty("station.uri.port", "8082");
                // if (stationModel.get().getName().equals("CXX_station3")) System.setProperty("station.uri.port", "8083");
                // if (stationModel.get().getName().equals("CXX_station4")) System.setProperty("station.uri.port", "8084");

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
        AllStationResponse response = new AllStationResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            List<StationModel> stationList = stationRepo.findAll();
            if (!stationList.isEmpty()) {
                response.setEstado(true);
                response.setMensagem("Estações encontradas com sucesso!");
                stationList.forEach(
                        station -> {
                            StationResponseType stationType = new StationResponseType();
                            BeanUtils.copyProperties(station, stationType);
                            stationType.setDocks(station.getQtdDocks());
                            stationType.setDocksDisp(station.getQtdDocksDispo());
                            response.getStationItem().add(stationType);
                        });
            } else {
                response.setEstado(false);
                response.setMensagem("Não há estações ainda!");
            }
        }
        return response;
    }
    
    public UpBikeResponse upBike (UpBikeRequest request) {
        /**
         * O valor que representa o levantamento da Bike é 1
         */
        UpBikeResponse response = new UpBikeResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            Optional<CiclistaModel> ciclista = ciclistaRepo.findById(request.getBody().getIdCiclista());
            if (ciclista.isPresent()) {
                /*
                 * Aqui está toda a lógica para levantar uma Bicicleta;
                 */
                Integer idStation = Utils.extractInteger(request.getBody().getReferenceDock().split("_")[0]);
                System.out.println(idStation);
                Optional<StationModel> station = stationRepo.findById(idStation.longValue());
                System.out.println("Endpoint: " + station.get().getEndpoint());
                if (station.isPresent()) {
                    //Pengando o estado actual da station em que será feito o levantamento
                    GetStationResponse gsr = stationClient.getStationState(station.get().getEndpoint());
                    gsr.getDockItem().forEach(dock ->{
                        if(dock.getReference().equals(request.getBody().getReferenceDock())){
                            /*if(dock.getInfo().equals(request.getBody().getInfo())){*/
                            //Aqui alteramos o estado do ciclista que está a levantar a bike;
                            if (ciclista.get().getState()==0){
                                ciclista.get().setState(1);
                                ciclista.get().setInfo(request.getBody().getInfo());
                                //Aqui alteramos o estado da doca que tinha a Bike
                                AlterStateDockInUpBikeResponse alteredDock = stationClient.updateDockStateInUpBike(
                                    station.get().getEndpoint(),
                                    dock.getIdDock(),
                                    0);
                                System.out.println(alteredDock.isEstado());
                                //Aqui virão os comandos para este processo na BD inserir na BD;
                                if(alteredDock.isEstado()==true){
                                    System.out.println("referencia: " + dock.getReference());
                                    //Actualizando o estado do ciclista
                                    ciclistaRepo.save(ciclista.get());
                                    //registrando o levantamento da bike
                                    AquisicaoBikeModel aquisicao = new AquisicaoBikeModel();
                                    aquisicao.setStation(station.get().getId());
                                    aquisicao.setTipo_aquisicao(1);
                                    aquisicao.setCiclistaId(ciclista.get().getId());
                                    aquisicicaoRepo.save(aquisicao);
                                    response.setEstado(true);
                                    response.setMensagem("Levantamento feito com sucesso");
                                }
                            } else {
                                response.setEstado(false);
                                response.setMensagem("Impossível levantar, ainda não devolveste a última bike que lavantaste!");
                            }
                            //}
                        }
                    });
                }else{
                    response.setEstado(false);
                    response.setMensagem("Estação não identificada!");
                }
            }else{
                response.setEstado(false);
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

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            Optional<CiclistaModel> ciclista = ciclistaRepo.findById(request.getBody().getIdCiclista());
            if (ciclista.isPresent()) {
                /*
                 * Aqui estará toda a lógica para devolver uma Bicicleta;
                 */
                Integer idStation = Utils.extractInteger(request.getBody().getReferenceDock().split("_")[0]);
                Optional<StationModel> station = stationRepo.findById(idStation.longValue());
                if (station.isPresent()) {
                    //Pengando o estado actual da station em que será feita a devolução
                    GetStationResponse gsr = stationClient.getStationState(station.get().getEndpoint());
                    gsr.getDockItem().forEach(dock ->{
                        if(dock.getReference().equals(request.getBody().getReferenceDock())){
                            /*if(dock.getInfo().equals(request.getBody().getInfo())){*/
                            //Aqui alteramos o estado do ciclista que está a devolver a bike;
                            if (ciclista.get().getState()==1) {
                                ciclista.get().setState(0);
                                //Aqui alteramos o estado da doca que terá a Bike
                                AlterStateDockInDownBikeResponse alteredDock = stationClient.updateDockStateInDownBike(
                                    station.get().getEndpoint(),
                                    dock.getIdDock(),
                                    1,
                                    request.getBody().getInfo()
                                );
                                    System.out.println(alteredDock.isEstado());
                                //Aqui estão os comandos para 0 processo de BD inserir na BD;
                                if(alteredDock.isEstado()==true){
                                    //Actualizando o estado do ciclista
                                    ciclistaRepo.save(ciclista.get());
                                    //registrando a devolução da bike
                                    AquisicaoBikeModel aquisicao = new AquisicaoBikeModel();
                                    aquisicao.setStation(station.get().getId());
                                    aquisicao.setTipo_aquisicao(2);
                                    aquisicao.setCiclistaId(ciclista.get().getId());
                                    aquisicicaoRepo.save(aquisicao);
                                    response.setEstado(true);
                                    response.setMensagem("Devolução feita com sucesso");
                                }
                            } else {
                                response.setEstado(false);
                                response.setMensagem("Impossível devolver, ainda não levantaste uma bike!");
                            }
                            //}
                        }
                    });
                }else{
                    response.setEstado(false);
                    response.setMensagem("Estação não identificada!");
                }
            }else{
                response.setEstado(false);
                response.setMensagem("Impossível devolver Bike!");
            }
        }
        return response;
    }

    public AddDockResp addDock (AddDockReq request) {

        AddDockResp response = new AddDockResp();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
                System.out.println("AQUIIII!");
            Optional<StationModel> station = stationRepo.findById(request.getBody().getIdStation());
            if (station.isPresent()) {
                /*
                 * Aqui estará toda a lógica para add uma doca em uma estação;
                 */
                //Acedendo ao serviço de add de docas da station designada!
                AddDockResponse gsr = stationClient.addDock(station.get().getEndpoint());
                //Aqui vem toda a lógica para adicionar uma nova doca
                if (gsr.isState()==true){
                    response.setEstado(true);
                    response.setMensagem("Doca adicionada com sucesso!");
                }
            }else{
                response.setEstado(false);
                response.setMensagem("Não foi possível adicionar doca!");
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
        }else {
            Integer idStation = Utils.extractInteger(request.getBody().getReference().split("_")[0]);
            Optional<StationModel> station = stationRepo.findById(idStation.longValue());
            if (station.isPresent()) {
                /*
                 * Aqui estará toda a lógica para eliminar uma doca em uma estação;
                 */
                //Acedendo ao serviço de delete de docas da station designada!
                DeleteDockResponse gsr = stationClient.deleteDock(station.get().getEndpoint(), request.getBody().getReference());
                //Aqui vem toda a lógica para adicionar uma nova doca
                if (gsr.isState()==true){
                    response.setEstado(true);
                    response.setMensagem("Doca removida com sucesso!");
                }
            }else{
                response.setEstado(false);
                response.setMensagem("Não foi possível remover doca!");
            }
        }
        return response;
    }

}
