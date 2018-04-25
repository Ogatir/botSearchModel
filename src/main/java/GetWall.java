import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.friends.UserXtrLists;
import com.vk.api.sdk.objects.friends.responses.GetFieldsResponse;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.queries.friends.FriendsGetOrder;
import com.vk.api.sdk.queries.users.UserField;

import java.util.List;

public class GetWall {

    private VkApiClient vk;
    private UserActor actor;

    public  GetWall(VkApiClient vk, UserActor actor){
        this.vk=vk;
        this.actor=actor;
    }


    public void PrintWallContent(int userId, int count){
        try {
            GetResponse getResponse = vk.wall().get(actor)
                    .ownerId(userId)
                    .count(count)
                    .execute();
            List <WallPostFull> wallPosts= getResponse.getItems();
            for (WallPostFull post : wallPosts)
                System.out.println("Created by "+post.getCreatedBy() + " Text:" + post.getText()+
                        " Likes="+post.getLikes().getCount());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
