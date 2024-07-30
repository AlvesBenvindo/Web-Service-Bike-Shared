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
import ao.uan.fc.cc4.bikeshared.bd.message.MessageModel;
import ao.uan.fc.cc4.bikeshared.bd.message.MessageRepository;
import ao.uan.fc.cc4.bikeshared.bd.session.SessionModel;
import ao.uan.fc.cc4.bikeshared.bd.session.SessionRepository;
import ao.uan.fc.cc4.bikeshared.bd.station.StationModel;
import ao.uan.fc.cc4.bikeshared.bd.station.StationRepository;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import ao.uan.fc.cc4.bikeshared.utils.HashPassword;
import xml.soap.user.AddCiclistaRequest;
import xml.soap.user.AllCiclistasRequest;
import xml.soap.user.AllMessagesRequest;
import xml.soap.user.AllMessagesResponse;
import xml.soap.user.ChegadaType;
import xml.soap.user.CiclistaListResponse;
import xml.soap.user.CiclistaResponse;
import xml.soap.user.CiclistaType;
import xml.soap.user.CloseChatRequest;
import xml.soap.user.CloseChatResponse;
import xml.soap.user.DeleteCiclistaRequest;
import xml.soap.user.GetCiclistaRequest;
import xml.soap.user.GetSaldoRequest;
import xml.soap.user.GetSaldoResponse;
import xml.soap.user.HistoricTrajectRequest;
import xml.soap.user.HistoricTrajectResponse;
import xml.soap.user.MessageType;
import xml.soap.user.PartidaType;
import xml.soap.user.SendMessageRequest;
import xml.soap.user.SendMessageResponse;
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
    private MessageRepository messageRepo;
    @Autowired(required = true)
    private AquisicaoBikeRepository aquisicicaoRepo;
    @Autowired(required = true)
    private StationRepository stationRepo;
    @Autowired(required = true)
    private SessionRepository sessionRepo;
    @Autowired
    private AuthenticationService auth;

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
        
        CiclistaModel ciclistaModel = new CiclistaModel();
        ciclistaModel.setUserId(userModel.getId());
        ciclistaModel.setPoints(10);
        ciclistaModel.setState(0);
        ciclistaRepo.save(ciclistaModel);

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
        CiclistaListResponse response = new CiclistaListResponse();
        response.setStateCode(200);

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            System.out.println(12345678);
            Optional<UserModel> user = userRepo.findById(request.getBody().getId());
            BeanUtils.copyProperties(request.getBody(), user.get());
            userRepo.save(user.get());
            response.setEstado(true);
            response.setMensagem("Ciclista: "+user.get().getNome()+" "+user.get().getSobrenome()+", seus dados foram actualizados com sucesso");
        }
        return new CiclistaResponse();
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
                if (doador.isPresent() && receptor.isPresent()) {
                    if (doador.get().getPoints() >= request.getBody().getPoints()) {
                        doador.get().setPoints(doador.get().getPoints()-request.getBody().getPoints());
                        receptor.get().setPoints(receptor.get().getPoints()+request.getBody().getPoints());
                        ciclistaRepo.save(doador.get());
                        ciclistaRepo.save(receptor.get());
                        response.setEstado(true);
                        response.setMensagem("Pontos transferidos com sucesso!");
                    } else {
                        response.setMensagem("Não tens pontos suficientes!");
                    }
                } else {
                    response.setMensagem("Utilizador não encontrado!!");
                }
            } else {
                response.setMensagem("Impossível transferir pontos!");
            }
        }
        return response;
    }

    public SendMessageResponse sendMessage (SendMessageRequest request) {
        SendMessageResponse response = new SendMessageResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
            response.setStateCode(401);
        }else {
            Optional<CiclistaModel> emissor = ciclistaRepo.findById(request.getBody().getEmissorId());
            SessionModel sessao = sessionRepo.findByFingerprint(request.getBody().getMac());
            if ( sessao != null ){
                CiclistaModel receptor = ciclistaRepo.findByUserId(sessao.getUser());
                if (emissor.isPresent() && receptor != null && request.getBody().getMac().equals(sessao.getFingerprint())) {
                    //Guardando a mensagem
                    MessageModel message = new MessageModel();
                    message.setEmissorId(emissor.get().getId());
                    message.setReceptorId(receptor.getId());
                    message.setMessage(request.getBody().getMessage());
                    messageRepo.save(message);
                    response.setEstado(true);
                    response.setMensagem("Mensagem enviada com sucesso!");
                } else {
                    response.setEstado(false);
                    response.setMensagem("Utilizador não encontrado!");
                }
            } else {
                response.setEstado(false);
                response.setMensagem("Impossível enviar mensagem!");
            }
        }
        return response;
    }

    public CloseChatResponse closeChat (CloseChatRequest request) {
        CloseChatResponse response = new CloseChatResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())){
            response.setEstado(false);
            response.setStateCode(401);
        } else {
            List<MessageModel> messages = messageRepo.findAll();
            for (MessageModel message : messages) {
                System.out.println(message.getReceptorId()+" ---- "+request.getBody().getEmissorId());
                if (message.getEmissorId()==request.getBody().getEmissorId() || message.getReceptorId()==request.getBody().getEmissorId())
                    messageRepo.delete(message);
            }
            response.setEstado(true);
        }
        return response;
    }

    public AllMessagesResponse loadMessages (AllMessagesRequest request) {
        AllMessagesResponse response = new AllMessagesResponse();
        Optional<CiclistaModel> ciclista = ciclistaRepo.findById(request.getBody().getCiclistaId());
        if (ciclista.isPresent()) {
            List<MessageModel> messages = messageRepo.findAll();
            if (messages.size()!=0) {
                messages.forEach(message -> {
                    if (message.getReceptorId()==ciclista.get().getId()) {
                        MessageType messageType = new MessageType();
                        Optional<UserModel> user = userRepo.findById(message.getEmissorId());
                        if (user.isPresent()) {
                            messageType.setCiclista(user.get().getNome());
                        } else {
                            messageType.setCiclista("---");
                        }
                        messageType.setMessage(message.getMessage());
                        response.getMessageItem().add(messageType);
                    }
                });
                if (response.getMessageItem().size()!=0){
                    response.setEstado(true);
                    response.setMensagem("Todas suas mensagens retornadas");
                } else {
                    response.setEstado(false);
                    response.setMensagem("Sem mensagens!!!");
                }
            } else {
                response.setEstado(false);
                response.setMensagem("Não há mensagens!!!");
            }
        } else {
            response.setEstado(false);
            response.setMensagem("Impossível teres mensagens!!!!");
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
                        System.out.println("OkoKoKookokOKo");
                        if (aquisicoes.get(i) != null) {
                            Optional<StationModel> station = stationRepo.findById(aquisicoes.get(i).getStation());
                            partida.setEstacao(station.get().getName()+" - "+station.get().getLocalName());
                            partida.setData(station.get().getCreatedAt());
                            System.out.println("data da aquisição: "+station.get().getCreatedAt());
                            partida.setLatitude(station.get().getLatitude());
                            partida.setLongitude(station.get().getLongitude());
                            trajecto.setPartida(partida);
                        }
                        if (aquisicoes.get(i+1) != null) {
                            Optional<StationModel> station = stationRepo.findById(aquisicoes.get(i+1).getStation());
                            chegada.setEstacao(station.get().getName()+" - "+station.get().getLocalName());
                            chegada.setData(station.get().getCreatedAt());
                            chegada.setLatitude(station.get().getLatitude());
                            chegada.setLongitude(station.get().getLongitude());
                            chegada.setBonus(station.get().getBonus());
                            trajecto.setChegada(chegada);
                        }
                        response.getTrajectList().add(trajecto);
                    }
                    response.setEstado(true);
                    response.setMensagem("Aqui estão todos os teus levantamentos e devoluções!!!");
                } else response.setMensagem("Nenhum histórico foi gerado!");
            } else response.setMensagem("Impossível ver histórico!!!");
        }
        return response;
    }
    
}
