import com.vk.api.sdk.actions.Friends;
import com.vk.api.sdk.client.*;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.friends.UserXtrLists;
import com.vk.api.sdk.objects.friends.responses.GetFieldsResponse;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.friends.FriendsList;
//import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.queries.friends.FriendsGetOrder;
import com.vk.api.sdk.queries.users.UserField;

import java.util.List;

public class Main {

    public static void main(String[] args) throws ClientException, ApiException {


        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

       /* Authorizer clientAuth=new Authorizer(
                "VfgVHh7PK8qHigKFQ2cf",
                "4a2151f18a0fcb802dd3d879b40997e298b3a0988e78f1c703296bb76538d4a17f69c412c5e65a85762ec",
                6430983,
                "65f46d37b56e5d6e18");*/
        //UserActor actor=  clientAuth.getAccessToken(vk);
        UserActor actor = new UserActor(16268448,
                "fb86295fb2452726eb3b6f1243145b0031929e1e00d97d4b18a118ab6f3194314c70c395436c14e6da769");
        GetFriends friendsGetter = new GetFriends(vk,actor);
        friendsGetter.PrintFriends();
        System.out.println("Hello world");
    }
}
