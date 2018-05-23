import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import java.util.Date;
import java.util.List;

public class GetWall {

    private VkApiClient vk;
    private UserActor actor;
    private List <WallPostFull> userWallPosts;


    public  GetWall(VkApiClient vk, UserActor actor, int UserId){
        this.vk=vk;
        this.actor=actor;
        this.userWallPosts=GetUserWallPosts(UserId,100);
    }

    public void PrintWallContent(int userId, int count){
            for (WallPostFull post : userWallPosts)
                System.out.println("Created by "+post.getFromId()+ " Text:" + post.getText()+
                        " Likes="+post.getLikes().getCount());

    }
    private List <WallPostFull> GetUserWallPosts(int userId, int count){
        try {
            GetResponse getResponse = vk.wall().get(actor)
                    .ownerId(userId)
                    .count(count)
                    .execute();
            return getResponse.getItems();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public int PostCount(){
        return userWallPosts.size();
    }

    public long GetWallPostTime(int postNum){
        WallPostFull post = userWallPosts.get(postNum);
        Date date = new Date(post.getDate());
        return  date.getTime();
    }



    private WallPostFull GetOneWallPost(int UserId, int postNumber){
        List <WallPostFull> posts;
        if((posts = GetUserWallPosts(UserId,100))!=null)
            return posts.get(postNumber);
        else return null;
    }
}
