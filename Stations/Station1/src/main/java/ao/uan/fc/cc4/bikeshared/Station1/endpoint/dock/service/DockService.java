package ao.uan.fc.cc4.bikeshared.Station1.endpoint.dock.service;

import ao.uan.fc.cc4.bikeshared.Station1.bd.DataFixed.Model;
import ao.uan.fc.cc4.bikeshared.Station1.bd.DataFixed.ModelRepository;
import ao.uan.fc.cc4.bikeshared.Station1.bd.dock.DockModel;
import ao.uan.fc.cc4.bikeshared.Station1.bd.dock.DockRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xml.soap.AddDockRequest;
import xml.soap.AddDockResponse;
import xml.soap.AlterStateDockInDownBikeRequest;
import xml.soap.AlterStateDockInDownBikeResponse;
import xml.soap.AlterStateDockInUpBikeRequest;
import xml.soap.AlterStateDockInUpBikeResponse;
import xml.soap.DeleteDockRequest;
import xml.soap.DeleteDockResponse;
import xml.soap.DockType;
import xml.soap.GetStationRequest;
import xml.soap.GetStationResponse;


@Service
public class DockService {

    @Autowired(required = true)
    private DockRepository dockRepo;
    @Autowired(required = true)
    private ModelRepository stationRepo;

    public GetStationResponse getStationData (GetStationRequest request) {

        GetStationResponse response = new GetStationResponse();
        List<Model> station = stationRepo.findAll();
        
        response.setEstado(true);
        response.setId(station.get(0).getId());
        response.setName(station.get(0).getName());
        response.setBonus(station.get(0).getBonus());
        response.setLongitude(station.get(0).getLongitude());
        response.setLatitude(station.get(0).getLatitude());
        response.setLocalName(station.get(0).getLocalName());

        List<DockModel> dockList = dockRepo.findAll();
        if (!dockList.isEmpty()) {
            dockList.forEach(
                dock -> {
                    DockType dockType = new DockType();
                    dockType.setState(dock.getState());
                    dockType.setIdDock(dock.getId());
                    dockType.setReference(dock.getReference());
                    response.getDockItem().add(dockType); 
                }
            );
        } else {
            response.setMensagem("Esta estação ainda não tem docas registradas!!!");
        }
        return response;
    }

    public AlterStateDockInUpBikeResponse upBike (AlterStateDockInUpBikeRequest request) {
        AlterStateDockInUpBikeResponse response = new AlterStateDockInUpBikeResponse();
        Optional<DockModel> dock = dockRepo.findById(request.getIdDock());
        if (dock.isPresent()) {
            dock.get().setState(request.getState());
            dockRepo.save(dock.get());
            response.setEstado(true);
        } else response.setEstado(false);
        return response;
    }

    public AlterStateDockInDownBikeResponse downBike (AlterStateDockInDownBikeRequest request) {
        AlterStateDockInDownBikeResponse response = new AlterStateDockInDownBikeResponse();
        Optional<DockModel> dock = dockRepo.findById(request.getIdDock());
        if (dock.isPresent()) {
            dock.get().setState(request.getState());
            dockRepo.save(dock.get());
            response.setEstado(true);
        } else response.setEstado(false);
        return response;
    }

    public AddDockResponse addDock (AddDockRequest request) {
        AddDockResponse response = new AddDockResponse();
        DockModel dock = new DockModel();
        Model station = (stationRepo.findAll().isEmpty())? null: stationRepo.findAll().get(0);
        if (station!=null) {
            int qtdDocks = dockRepo.findAll().size();
            dock.setState(1);
            dock.setReference("ST"+station.getId()+"_D"+(qtdDocks+1));
            dockRepo.save(dock);
            response.setState(true);
        } else response.setState(false);
        return response;
    }

    public DeleteDockResponse deleteDock (DeleteDockRequest request) {
        DeleteDockResponse response = new DeleteDockResponse();
        DockModel dock = dockRepo.findByReference(request.getReference());
        if (dock !=null) {
            dockRepo.delete(dock);
            response.setState(true);
        } else response.setState(false);
        return response;
    }

}
