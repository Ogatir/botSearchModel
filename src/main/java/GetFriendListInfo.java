import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.friends.UserXtrLists;
import com.vk.api.sdk.objects.friends.responses.GetFieldsResponse;
import com.vk.api.sdk.queries.friends.FriendsGetOrder;
import com.vk.api.sdk.queries.users.UserField;
import java.util.List;

public class GetFriendListInfo {
    private VkApiClient vk;
    private UserActor actor;
    private List <UserXtrLists> friendList;

    public GetFriendListInfo(VkApiClient vk, UserActor actor, int userId){
        this.vk=vk;
        this.actor=actor;
        friendList=GetFriendList(userId);
    }

    public void PrintFriends(int userId){
        try {
            GetFieldsResponse getResponse = vk.friends().get(actor,UserField.BDATE,UserField.ABOUT,UserField.CONTACTS)
                    .userId(userId)
                    .order(FriendsGetOrder.NAME)
                    .execute();
            List<UserXtrLists> friendList = getResponse.getItems();
            for (UserXtrLists friend : friendList)
                System.out.println(friend.getFirstName() + " " + friend.getLastName() + " id="
                        + friend.getId()+" Contacts: "+friend.getMobilePhone());
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    public List<UserXtrLists> GetFriendList(int UserId){
        try {
            GetFieldsResponse getResponse = vk.friends().get(actor,UserField.BDATE,UserField.ABOUT,UserField.CONTACTS)
                    .userId(UserId)
                    .order(FriendsGetOrder.NAME)
                    .execute();
            return getResponse.getItems();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int GetFriendsCount(){
        return friendList.size();
    }


}
