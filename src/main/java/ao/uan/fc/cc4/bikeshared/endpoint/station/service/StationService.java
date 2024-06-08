package ao.uan.fc.cc4.bikeshared.endpoint.station.service;

import java.util.List;
import java.util.Optional;

import ao.uan.fc.cc4.bikeshared.bd.session.SessionModel;
import ao.uan.fc.cc4.bikeshared.bd.session.SessionRepository;
import ao.uan.fc.cc4.bikeshared.cliente.station.StationClient;
import ao.uan.fc.cc4.bikeshared.config.security.JwtToken.Token;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ao.uan.fc.cc4.bikeshared.bd.station.StationRepository;
import ao.uan.fc.cc4.bikeshared.bd.station.StationModel;
import xml.soap.station.*;
import xml.soap.GetStationResponse;

@Service
public class StationService {

    private boolean headEstado;
    private String headMensagem;
    
    @Autowired(required = true)
    private StationRepository stationRepo;
    @Autowired(required = true)
    private SessionRepository sessionRepo;
    @Autowired
    private Token jwtToken;
    @Autowired
    StationClient stationClient;

    public StationResponse addStation (AddStationRequest request) {

        System.out.println("Dentro do Serviço "+ request.getBody().getEndpoint());
        StationResponse response = new StationResponse();
        System.out.println(request.getBody().getLocalName()+": "+request.getBody().getLatitude()+": "+request.getBody().getLongitude());
        StationModel stationModel = new StationModel();
        stationModel.setEndpoint(request.getBody().getEndpoint());
        stationModel.setName(request.getBody().getName());
        stationModel.setDocks(request.getBody().getDocks());
        stationModel.setDocksDisp(request.getBody().getDocksDisp());
        stationModel.setLocalName(request.getBody().getLocalName());
        stationModel.setLongitude(request.getBody().getLongitude());
        stationModel.setLatitude(request.getBody().getLatitude());
        stationRepo.save(stationModel);

        response.setEstado(true);
        response.setMensagem("User adicionado com sucesso!!!");
        response.setName(stationModel.getName());
        response.setDocks(stationModel.getDocks());
        response.setDocksDisp(stationModel.getDocksDisp());
        response.setLocalName(stationModel.getLocalName());
        response.setLongitude(stationModel.getLongitude());
        response.setLatitude(stationModel.getLatitude());

        return response;
    }

    public GetStationDetailsResponse getStation (GetStationDetailsRequest request) {

        System.out.println("Dentro do Serviço e ID da estação: " + request.getBody().getIdStation());
        GetStationDetailsResponse response = new GetStationDetailsResponse();

        SessionModel session = sessionRepo.findByToken(request.getHeader().getAuthToken());

        if (SessionModel.virifySession(session) != 0) {
            this.DestilaHeadResponse(session);
            response.setEstado(this.headEstado);
            response.setMensagem(this.headMensagem);
        } else {
            Optional<StationModel> stationModel = stationRepo.findById(request.getBody().getIdStation());
            System.out.println(5786);
            if(stationModel.isPresent()) {

                if (stationModel.get().getName().equals("CXX_station1")) System.setProperty("station.uri.port", "8081");;
                if (stationModel.get().getName().equals("CXX_station2")) System.setProperty("station.uri.port", "8082");;
                if (stationModel.get().getName().equals("CXX_station3")) System.setProperty("station.uri.port", "8083");;
                if (stationModel.get().getName().equals("CXX_station4")) System.setProperty("station.uri.port", "8084");;

                System.out.println(5786);
                GetStationResponse gsr = stationClient.getStationState(stationModel.get().getEndpoint());
                System.out.println(5786);
                if (gsr != null) {
                    response.setEstado(true);
                    response.setMensagem("Station encontrada com sucesso!!!");
                    response.setId(gsr.getId());
                    response.setName(gsr.getName());
                    response.setBonus(gsr.getBonus());
                    response.setLongitude(gsr.getLongitude());
                    response.setLatitude(gsr.getLatitude());
                    response.setLocalName(gsr.getLocalName());
                    gsr.getDockItem().forEach( dock -> {
                        DockType d = new DockType();
                        d.setState(dock.getState());
                        d.setIdDock(dock.getIdDock());
                        d.setReference(dock.getReference());
                        response.getDockItem().add(d);
                    });
                    return  response;
                }
                response.setEstado(false);
                response.setMensagem("Station data corrupted!!!");
                return  response;

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
        SessionModel session = sessionRepo.findByToken(request.getHeader().getAuthToken());

        if (SessionModel.virifySession(session) != 0) {
            this.DestilaHeadResponse(session);
            response.setEstado(this.headEstado);
            response.setMensagem(this.headMensagem);
        } else {
            List<StationModel> stationList = stationRepo.findAll();
            if (!stationList.isEmpty()) {
                response.setEstado(true);
                response.setMensagem("Estações encontradas com sucesso!");
                stationList.forEach(
                        station -> {
                            StationResponseType stationType = new StationResponseType();
                            BeanUtils.copyProperties(station, stationType);
                            response.getStationItem().add(stationType);
                        });
            } else {
                response.setEstado(false);
                response.setMensagem("Não há estações ainda!");
            }
        }
        return response;
    }


    private void DestilaHeadResponse (SessionModel session) {
        headEstado = false;
        if (SessionModel.virifySession(session) == 1) {
            headMensagem = "Token inválido, undefined!";
        } else {
            headMensagem = "Sessão expirada!";
        }
    }

}
