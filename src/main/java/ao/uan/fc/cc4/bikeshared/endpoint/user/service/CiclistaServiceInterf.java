package ao.uan.fc.cc4.bikeshared.endpoint.user.service;

import org.springframework.stereotype.Component;

import xml.soap.user.AddCiclistaRequest;
import xml.soap.user.AllCiclistasRequest;
import xml.soap.user.CiclistaIdRequest;
import xml.soap.user.CiclistaListResponse;
import xml.soap.user.CiclistaResponse;
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

@Component
public interface CiclistaServiceInterf extends LoginCiclista {

    CiclistaResponse addCiclista (AddCiclistaRequest request);
    CiclistaResponse getCiclista (CiclistaIdRequest res);
    CiclistaResponse updateCiclista (UpdateCiclistaRequest req);
    CiclistaResponse deleteCiclista (DeleteCiclistaRequest req);
    CiclistaListResponse getAllCiclista (AllCiclistasRequest req);
    GetSaldoResponse getSaldo (GetSaldoRequest request);
    TransferPointsResponse transferPoints (TransferPointsRequest request);
    SendMessageResponse sendMessage (SendMessageRequest request);
    CloseChatResponse closeChat (CloseChatRequest request);
    
}
