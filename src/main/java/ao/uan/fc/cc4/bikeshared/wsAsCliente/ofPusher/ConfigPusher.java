package ao.uan.fc.cc4.bikeshared.wsAsCliente.ofPusher;

import java.util.Collections;

import org.springframework.context.annotation.Configuration;

import com.pusher.rest.Pusher;

@Configuration
public class ConfigPusher {

    private String appId;
    private String apiKey;
    private String apiSecret;
    private Pusher pusher;

    public ConfigPusher (){
        appId = "1835919";
        apiKey = "fa2a91779f90c595be50";
        apiSecret = "063aa4c4960c8c24b157";
    }

    private void initPusher () {
        pusher = new Pusher(appId, apiKey, apiSecret);
        pusher.setCluster("us2");
        // pusher.setEncrypted(true);
    }
    
    public void triggerEvent () {
        this.initPusher();    
        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", "hello world"));

    }

}
