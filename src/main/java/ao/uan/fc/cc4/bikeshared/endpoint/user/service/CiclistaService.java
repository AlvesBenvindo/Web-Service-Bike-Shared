package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaModel;
import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaRepository;
import ao.uan.fc.cc4.bikeshared.bd.message.MessageModel;
import ao.uan.fc.cc4.bikeshared.bd.message.MessageRepository;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import ao.uan.fc.cc4.bikeshared.utils.HashPassword;
import xml.soap.user.AddCiclistaRequest;
import xml.soap.user.AllCiclistasRequest;
import xml.soap.user.CiclistaIdRequest;
import xml.soap.user.CiclistaListResponse;
import xml.soap.user.CiclistaResponse;
import xml.soap.user.CiclistaType;
import xml.soap.user.CloseChatRequest;
import xml.soap.user.CloseChatResponse;
import xml.soap.user.DeleteCiclistaRequest;
import xml.soap.user.GetSaldoRequest;
import xml.soap.user.GetSaldoResponse;
import xml.soap.user.SendMessageRequest;
import xml.soap.user.SendMessageResponse;
import xml.soap.user.TransferPointsRequest;
import xml.soap.user.TransferPointsResponse;
import xml.soap.user.UpdateCiclistaRequest;

@Service
public class CiclistaService {

    @Autowired(required = true)
    private UserRepository userRepo;
    @Autowired(required = true)
    private CiclistaRepository ciclistaRepo;
    @Autowired(required = true)
    private MessageRepository messageRepo;
    @Autowired
    private AuthenticationService auth;

    public CiclistaResponse addCiclista (AddCiclistaRequest request) {

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
		}

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

    public CiclistaResponse getCiclista (CiclistaIdRequest request) {
        CiclistaResponse response = new CiclistaResponse();
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            Optional<CiclistaModel> ciclistaOp = ciclistaRepo.findById(request.getBody().getCiclistaId());
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

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            Optional<UserModel> user = userRepo.findById(request.getBody().getId());
            BeanUtils.copyProperties(request.getBody(), user.get());
            user.get().setPassword(HashPassword.hashing(request.getBody().getPassword()));
            userRepo.save(user.get());
            response.setEstado(true);
            response.setMensagem("Ciclista: "+user.get().getNome()+" "+user.get().getSobrenome()+", seus dados foram actualizados com sucesso");
        }
        return new CiclistaResponse();
    }

    public CiclistaResponse deleteCiclista (DeleteCiclistaRequest request) {
        CiclistaListResponse response = new CiclistaListResponse();

        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            Optional<CiclistaModel> ciclista = ciclistaRepo.findById(request.getBody().getCiclistaId());
            if (ciclista.isPresent()) {
                userRepo.deleteById(ciclista.get().getUserId());
                Optional<UserModel> user = userRepo.findById(ciclista.get().getUserId());
                ciclistaRepo.deleteById(ciclista.get().getId());
                response.setEstado(true);
                response.setMensagem("Ciclista: "+user.get().getNome()+" "+user.get().getSobrenome()+" eliminado com sucesso!");
            }
        }

        return new CiclistaResponse();
    }

    public CiclistaListResponse getAllCiclista (AllCiclistasRequest request) {

        CiclistaListResponse response = new CiclistaListResponse();
        
        if (!auth.sessionIsValid(request.getHeader().getAuthToken())) {
            //this.DestilaHeadResponse(session);
            response.setEstado(false);
            response.setMensagem("Token inválido, undefined!");
        }else {
            List<CiclistaModel> ciclistaList = ciclistaRepo.findAll();
            if (!ciclistaList.isEmpty()) {
                response.setEstado(true);
                response.setMensagem("Lista de ciclista encontrada com sucesso!");
                ciclistaList.forEach(
                    ciclista -> {
                        CiclistaType ciclistaType = new CiclistaType();
                        Optional<UserModel> userOp = userRepo.findById(ciclista.getUserId());
                        if (userOp.isPresent()) {
                            BeanUtils.copyProperties(userOp.get(), ciclistaType);
                        }
                        ciclistaType.setCiclistaId(ciclista.getId());
                        ciclista.setPoints(ciclista.getPoints());
                        ciclistaType.setState(ciclista.getState());
                        response.getUser().add(ciclistaType);
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
        }else {
            Optional<CiclistaModel> dador = ciclistaRepo.findById(request.getBody().getDadorId());
            Optional<CiclistaModel> beneficiario = ciclistaRepo.findById(request.getBody().getBeneficiarioId());
            if (dador.isPresent() && beneficiario.isPresent() && request.getBody().getBeneficiarioId()!=request.getBody().getDadorId()){
                if (dador.get().getPoints()>= request.getBody().getPoints()) {
                    dador.get().setPoints(dador.get().getPoints()-request.getBody().getPoints());
                    beneficiario.get().setPoints(beneficiario.get().getPoints()+request.getBody().getPoints());
                    ciclistaRepo.save(dador.get());
                    ciclistaRepo.save(beneficiario.get());
                    response.setEstado(true);
                    response.setMensagem("Pontos transferidos com sucesso!");
                } else {
                    response.setEstado(false);
                    response.setMensagem("Não tens pontos suficientes!");
                }
            } else {
                response.setEstado(false);
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
        }else {
            Optional<UserModel> emissor = userRepo.findById(request.getBody().getEmissorId());
            Optional<UserModel> receptor = userRepo.findById(request.getBody().getReceptorId());
            if (
                emissor.isPresent() && receptor.isPresent()
                && request.getBody().getReceptorId()!=request.getBody().getEmissorId()
            ){
                //Guardando a mensagem
                MessageModel message = new MessageModel();
                message.setEmissorId(emissor.get().getId());
                message.setReceptorId(receptor.get().getId());
                message.setMessage(request.getBody().getMessage());
                messageRepo.save(message);
                response.setEstado(true);
                response.setMensagem("Mensagem enviada com sucesso!");
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
    
}
