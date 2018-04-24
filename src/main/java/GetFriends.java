import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.friends.FriendsList;
import com.vk.api.sdk.objects.friends.UserXtrLists;
import com.vk.api.sdk.objects.friends.responses.GetFieldsResponse;
import com.vk.api.sdk.queries.friends.FriendsGetOrder;
import com.vk.api.sdk.queries.users.UserField;

import java.util.List;

public class GetFriends {
    private VkApiClient vk;
    private UserActor actor;

    public  GetFriends(VkApiClient vk, UserActor actor){
        this.vk=vk;
        this.actor=actor;
    }

    public void PrintFriends(){
        try {
            GetFieldsResponse getResponse = vk.friends().get(actor, UserField.SEX)
                    .userId(16268448)
                    .order(FriendsGetOrder.NAME)
                    .execute();
            List<UserXtrLists> friendList = getResponse.getItems();
            for (UserXtrLists friend : friendList)
                System.out.println(friend.getFirstName() + " " + friend.getLastName() + " id=" + friend.getId());
        } catch (Exception e){

        }
    }
}
