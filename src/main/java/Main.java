import com.vk.api.sdk.client.*;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;

import java.util.List;

public class Main {

    public static void main(String[] args) throws ClientException, ApiException {


        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        UserActor actor = new UserActor(16268448,
                "a279298064505ef80bd4f9e1f4d7af448cfda14e080651054c4678186d4afa89e0637cb6b2596f74d8ac7");
        Authorizer clientAuth=new Authorizer();
        GetResponse getResponse = vk.wall().get(actor)
                .ownerId(16268448)
                .execute();
        List<WallPostFull> wallposts=getResponse.getItems();
        System.out.println(wallposts.get(2).getText());
        System.out.println("Hello world");
    }
}
