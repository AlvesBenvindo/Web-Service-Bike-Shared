package ao.uan.fc.cc4.bikeshared.endpoint.replication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ao.uan.fc.cc4.bikeshared.bd.admin.*;
import ao.uan.fc.cc4.bikeshared.bd.aquisicaoBike.*;
import ao.uan.fc.cc4.bikeshared.bd.ciclista.*;
import ao.uan.fc.cc4.bikeshared.bd.session.*;
import ao.uan.fc.cc4.bikeshared.bd.station.*;
import ao.uan.fc.cc4.bikeshared.bd.user.*;
import xml.replication.ReplicateRequest;
import xml.replication.ReplicateResponse;

@Service
public class ReplicationService {

    @Autowired(required = true)
    AdminRepository adminRepo;
    @Autowired(required = true)
    AquisicaoBikeRepository aquiRepo;
    @Autowired(required = true)
    CiclistaRepository ciRepo;
    @Autowired(required = true)
    SessionRepository sessionRepo;
    @Autowired(required = true)
    StationRepository stationRepo;
    @Autowired(required = true)
    UserRepository userRepo;

    public ReplicateResponse replicate (ReplicateRequest request) {
        ReplicateResponse response = new ReplicateResponse();
        try{
            if(request.getTable().equals("admin")) {

                AdminModel a = AdminModel.parse(request.getRow());
                adminRepo.save(a);

                response.setEstado(true);
                response.setMensagem("Sucesso ao replicar");
            } else if(request.getTable().equals("aquisicao")) {

                AquisicaoBikeModel a = AquisicaoBikeModel.parse(request.getRow());
                aquiRepo.save(a);

                response.setEstado(true);
                response.setMensagem("Sucesso ao replicar");
            } else if(request.getTable().equals("ciclista")) {

                CiclistaModel a = CiclistaModel.parse(request.getRow());
                ciRepo.save(a);

                response.setEstado(true);
                response.setMensagem("Sucesso ao replicar");
            } else if(request.getTable().equals("session")) {

                SessionModel a = SessionModel.parse(request.getRow());
                sessionRepo.save(a);

                response.setEstado(true);
                response.setMensagem("Sucesso ao replicar");
            } else if(request.getTable().equals("station")) {

                StationModel a = StationModel.parse(request.getRow());
                stationRepo.save(a);

                response.setEstado(true);
                response.setMensagem("Sucesso ao replicar");
            } else if(request.getTable().equals("user")) {

                UserModel a = UserModel.parse(request.getRow());
                userRepo.save(a);

                response.setEstado(true);
                response.setMensagem("Sucesso ao replicar");
            }
        } catch (Exception e) {
            response.setMensagem("Não foi possível replicar");
        }

        return response;
    }
    
}
