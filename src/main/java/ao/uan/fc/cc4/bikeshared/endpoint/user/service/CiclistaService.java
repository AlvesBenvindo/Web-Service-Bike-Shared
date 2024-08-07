package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ao.uan.fc.cc4.bikeshared.bd.aquisicaoBike.AquisicaoBikeModel;
import ao.uan.fc.cc4.bikeshared.bd.aquisicaoBike.AquisicaoBikeRepository;
import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaModel;
import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaRepository;
import ao.uan.fc.cc4.bikeshared.bd.session.SessionModel;
import ao.uan.fc.cc4.bikeshared.bd.session.SessionRepository;
import ao.uan.fc.cc4.bikeshared.bd.station.StationModel;
import ao.uan.fc.cc4.bikeshared.bd.station.StationRepository;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import ao.uan.fc.cc4.bikeshared.utils.HashPassword;
//import ao.uan.fc.cc4.bikeshared.wsAsCliente.ofReplica.WSReplica;
import xml.soap.user.AddCiclistaRequest;
import xml.soap.user.AllCiclistasRequest;
import xml.soap.user.ChegadaType;
import xml.soap.user.CiclistaListResponse;
import xml.soap.user.CiclistaResponse;
import xml.soap.user.CiclistaType;
import xml.soap.user.DeleteCiclistaRequest;
import xml.soap.user.GetCiclistaRequest;
import xml.soap.user.GetSaldoRequest;
import xml.soap.user.GetSaldoResponse;
import xml.soap.user.HistoricTrajectRequest;
import xml.soap.user.HistoricTrajectResponse;
import xml.soap.user.PartidaType;
import xml.soap.user.TrajectType;
import xml.soap.user.TransferPointsRequest;
import xml.soap.user.TransferPointsResponse;
import xml.soap.user.UpdateCiclistaRequest;

@Service
public class CiclistaService{

    @Autowired(required = true)
    private UserRepository userRepo;
    @Autowired(required = true)
    private CiclistaRepository ciclistaRepo;
    @Autowired(required = true)
    private AquisicaoBikeRepository aquisicicaoRepo;
    @Autowired(required = true)
    private StationRepository stationRepo;
    @Autowired(required = true)
    private SessionRepository sessionRepo;
    @Autowired
    private AuthenticationService auth;
    //@Autowired(required = true)
    //private WSReplica serverWriter;

    public CiclistaResponse addCiclista (AddCiclistaRequest request) {

        System.out.println("Dentro do Serviço de cadastro de Ciclista ");
        CiclistaResponse response = new CiclistaResponse();

        UserModel userModel = userRepo.findByEmail(request.getEmail());
        /*
        * Verificando se o utilizador já existe
        */
        if (userModel != null) {
            response.setEstado(false);
            response.setMensagem("Este Email já está a ser utilizado!!!");
            return response;
        }
        /*
        * Fim da verificação
        */
        userModel = new UserModel();
        BeanUtils.copyProperties(request, userModel);
        userModel.setPassword(HashPassword.hashing(request.getPassword()));
        userModel = userRepo.save(userModel);
        //serverWriter.writeInReplica("user", userModel.returnString());
        
        CiclistaModel ciclistaModel = new CiclistaModel();
        ciclistaModel.setUserId(userModel.getId());
        ciclistaModel.setPoints(10);
        ciclistaModel.setState(0);
        ciclistaRepo.save(ciclistaModel);
        //serverWriter.writeInReplica("ciclista", ciclistaRepo.save(ciclistaModel).returnString());
        

        response.setEstado(true);
        response.setMensagem("Utilizador adicionado com sucesso!!!");

        return response;
    }

    public CiclistaResponse getCiclista (GetCiclistaRequest request) {
        CiclistaResponse response = new CiclistaResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Optional<CiclistaModel> ciclistaOp = ciclistaRepo.findById(request.getBody().getId());
            if (ciclistaOp.isPresent()) {
                Optional<UserModel> userOp = userRepo.findById(ciclistaOp.get().getUserId());
                response.setId(ciclistaOp.get().getId());
                BeanUtils.copyProperties(ciclistaOp.get(), response);
                BeanUtils.copyProperties(userOp.get(), response);
                response.setEstado(true);
                response.setMensagem("User encontrado com sucesso!");
            } else {
                response.setEstado(false);
                response.setMensagem("User não encontrado!");
            }
        }
        return response;
    }

    public CiclistaResponse updateCiclista (UpdateCiclistaRequest request) {
        CiclistaResponse response = new CiclistaResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            System.out.println(12345678);
            Optional<CiclistaModel> ciclista = ciclistaRepo.findById(request.getBody().getId());
            if (ciclista.isPresent()) {
                Optional<UserModel> user = userRepo.findById(ciclista.get().getUserId());
                if (user.isPresent()) {
                    //BeanUtils.copyProperties(request.getBody(), user.get());
                    user.get().setEmail(request.getBody().getEmail());
                    user.get().setFoto(request.getBody().getFoto());
                    user.get().setGenero(request.getBody().getGenero());
                    user.get().setSobrenome(request.getBody().getSobrenome());
                    user.get().setNome(request.getBody().getNome());
                    user.get().setPassword(HashPassword.hashing(request.getBody().getPassword()));
                    userRepo.save(user.get());
                    //serverWriter.writeInReplica("user", userRepo.save(user.get()).returnString());
                    response.setEstado(true);
                    response.setMensagem("Ciclista: "+user.get().getNome()+" "+user.get().getSobrenome()+", seus dados foram actualizados com sucesso");
                    response.setNome(user.get().getNome());
                    response.setSobrenome(user.get().getSobrenome());
                    response.setEmail(user.get().getEmail());
                    response.setFoto(user.get().getFoto());
                    response.setGenero(user.get().getGenero());
                    response.setId(ciclista.get().getId());
                } else {
                    response.setMensagem("Ciclista não encontrado!!!");
                }
            } else {
                response.setMensagem("Ciclista não encontrado!!!");
            }
            System.out.println("qualquer coisa...");
        }
        System.out.println("actualizou");
        return response;
    }

    public CiclistaResponse deleteCiclista (DeleteCiclistaRequest request) {
        CiclistaResponse response = new CiclistaResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(true);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Optional<CiclistaModel> ciclista = ciclistaRepo.findById(request.getBody().getCiclistaId());
            if (ciclista.isPresent()) {
                Optional<UserModel> user = userRepo.findById(ciclista.get().getUserId());
                SessionModel session = sessionRepo.findByUser(ciclista.get().getUserId());
                String nome_completo = user.get().getNome()+" "+user.get().getSobrenome();
                userRepo.deleteById(ciclista.get().getUserId());
                ciclistaRepo.deleteById(ciclista.get().getId());
                sessionRepo.deleteById(session.getId());
                response.setEstado(true);
                response.setMensagem("Ciclista: "+nome_completo+" eliminado com sucesso!");
            } else {
                response.setEstado(false);
                response.setMensagem("Ciclista não encontrado!!!");
            }
        }

        return response;
    }

    public CiclistaListResponse getAllCiclista (AllCiclistasRequest request) {

        CiclistaListResponse response = new CiclistaListResponse();
        
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            List<CiclistaModel> ciclistaList = ciclistaRepo.findAll();
            if (!ciclistaList.isEmpty()) {
                response.setEstado(true);
                response.setMensagem("Lista de ciclista encontrada com sucesso!");
                ciclistaList.forEach(
                    ciclista -> {
                        System.out.println(ciclista.getPoints());
                        CiclistaType ciclistaType = new CiclistaType();
                        Optional<UserModel> userOp = userRepo.findById(ciclista.getUserId());
                        if (userOp.isPresent()) {
                            BeanUtils.copyProperties(userOp.get(), ciclistaType);
                        }
                        ciclistaType.setCiclistaId(ciclista.getId());
                        ciclistaType.setPoints(ciclista.getPoints());
                        ciclistaType.setState(ciclista.getState());
                        ciclistaType.setInfo(ciclista.getInfo());
                        response.getCiclista().add(ciclistaType);
                    });
            } else {
                response.setEstado(false);
                response.setMensagem("User não encontrado!");
            }
        }
        return response;
    }

    public GetSaldoResponse getSaldo (GetSaldoRequest request) {
        GetSaldoResponse response = new GetSaldoResponse();
        
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Optional<CiclistaModel> ciclista = ciclistaRepo.findById(request.getBody().getCiclistaId());
            if (ciclista.isPresent()) {
                response.setEstado(true);
                response.setMensagem("Ciclista encontrado com sucesso!");
                response.setSaldo(ciclista.get().getPoints());
            } else {
                response.setEstado(false);
                response.setMensagem("User não encontrado!");
            }
        }
        return response;
    }

    public TransferPointsResponse transferPoints (TransferPointsRequest request) {
        TransferPointsResponse response = new TransferPointsResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            if (request.getBody().getReceptorId() != request.getBody().getDoadorId()) {
                Optional<CiclistaModel> doador = ciclistaRepo.findById(request.getBody().getDoadorId());
                Optional<CiclistaModel> receptor = ciclistaRepo.findById(request.getBody().getReceptorId());
                //if (doador.isPresent() && receptor.isPresent()) {
                    if (doador.get().getPoints() >= request.getBody().getPoints()) {
                        doador.get().setPoints(doador.get().getPoints()-request.getBody().getPoints());
                        receptor.get().setPoints(receptor.get().getPoints()+request.getBody().getPoints());
                        // ciclistaRepo.save(doador.get());
                        try {
                            ciclistaRepo.save(doador.get());
                            //serverWriter.writeInReplica("ciclista", ciclistaRepo.save(doador.get()).returnString());
                            ciclistaRepo.save(receptor.get());
                            //serverWriter.writeInReplica("ciclista", ciclistaRepo.save(receptor.get()).returnString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        response.setEstado(true);
                        response.setMensagem("Pontos transferidos com sucesso!");
                    } else {
                        response.setMensagem("Não tens pontos suficientes!");
                    }
                //} else {
                //    response.setMensagem("Utilizador não encontrado!!");
                //}
            } else {
                response.setMensagem("Impossível transferir pontos!");
            }
        }
        return response;
    }

    public HistoricTrajectResponse getHistoricTraject (HistoricTrajectRequest request) {
        HistoricTrajectResponse response = new HistoricTrajectResponse();
        response.setEstado(false);
        
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else{
            Optional<CiclistaModel> ciclista = ciclistaRepo.findById(request.getBody().getCiclistaId());
            if (ciclista.isPresent()) {
                List<AquisicaoBikeModel> aquisicoes = aquisicicaoRepo.findByCiclistaId(ciclista.get().getId());
                if (!aquisicoes.isEmpty()) {
                    for (int i = 0; i < aquisicoes.size(); i+=2) {
                        System.out.println(1+i);
                        TrajectType trajecto = new TrajectType();
                        PartidaType partida = new PartidaType();
                        ChegadaType chegada = new ChegadaType();
                        try{
                            if (aquisicoes.get(i) != null) {
                                System.out.println("sucesso!!!");
                                StationModel station = stationRepo.findByName(aquisicoes.get(i).getStation());
                                partida.setEstacao(station.getName()+" - "+station.getLocalName());
                                partida.setData(station.getCreatedAt());
                                partida.setLatitude(station.getLatitude());
                                partida.setLongitude(station.getLongitude());
                                trajecto.setPartida(partida);
                            }
                            if (aquisicoes.get(i+1) != null) {
                                System.out.println("sucesso!!!");
                                StationModel station = stationRepo.findByName(aquisicoes.get(i+1).getStation());
                                System.out.println("dentro do sucesso!!!");
                                chegada.setEstacao(station.getName()+" - "+station.getLocalName());
                                chegada.setData(station.getCreatedAt());
                                chegada.setLatitude(station.getLatitude());
                                chegada.setLongitude(station.getLongitude());
                                chegada.setBonus(station.getBonus());
                                trajecto.setChegada(chegada);
                            }
                        } catch (Exception e) {
                            
                        }
                        response.getTrajectList().add(trajecto);
                    }
                    System.out.println("sucesso!!!");
                    response.setEstado(true);
                    response.setMensagem("Aqui estão todos os teus levantamentos e devoluções!!!");
                } else response.setMensagem("Nenhum histórico foi gerado!");
            } else response.setMensagem("Impossível ver histórico!!!");
        }
        System.out.println("sucesso!!!");
        return response;
    }
    
}
