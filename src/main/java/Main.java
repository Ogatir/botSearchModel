import com.vk.api.sdk.client.*;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
public class Main {

    public static void main(String[] args){

        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        Authorizer clientAuth=new Authorizer(
                "VfgVHh7PK8qHigKFQ2cf",
                "4a2151f18a0fcb802dd3d879b40997e298b3a0988e78f1c703296bb76538d4a17f69c412c5e65a85762ec",
                6430983,
                "cc5d25e3748c455f47");
       // UserActor actor=  clientAuth.getAccessToken(vk);
        UserActor actor = new UserActor(16268448,
                "487c94c8a7c2c228a8cdc957157afc162f862a3c914d2576e5e6578f6f4d07349849a8aa3e105ea664886");

        AccountAnalyser analyser = new AccountAnalyser(444761730, vk,actor);
        int[] idArray = {
                444761730,
                53447095,
                79232504,
                209201650,
                233229807,
                484111912
        };
        analyser.Analyse();
        analyser.CollectStatistics(idArray);
    }
}
