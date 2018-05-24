import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.users.User;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;
import java.util.List;

public class GetUserInfo {

    private VkApiClient vk;
    private UserActor actor;
    UserXtrCounters user;


    public GetUserInfo(VkApiClient vk, UserActor actor, int UserId){
        this.vk=vk;
        this.actor=actor;
        this.user=GetUser(UserId);
    }

    public void PrintUserInfo(int UserId){
        String id = String.valueOf(UserId);
            System.out.printf("First name: %s\nLast name: %s\n" +
                    "Followers: %s\nBirthDate: %s\nLast seen: %s\nAbout: %s\n",user.getFirstName(),user.getLastName(),
                    user.getFollowersCount(),user.getBdate(),user.getLastSeen(),user.getAbout());


    }

    public UserXtrCounters GetUser(int UserId){
        String id = String.valueOf(UserId);
        try{
            List<UserXtrCounters> users = vk.users().get(actor)
                    .userIds(id)
                    .fields(UserField.BDATE, UserField.FOLLOWERS_COUNT,UserField.LAST_SEEN,UserField.VERIFIED,
                    UserField.MOVIES, UserField.MUSIC,UserField.CAREER,UserField.ABOUT,UserField.CONTACTS,
                            UserField.INTERESTS,UserField.HAS_PHOTO)
                    .lang(Lang.RU)
                    .execute();
            return users.get(0);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean HasPhoto(){
        if (user.hasPhoto())
            return  true;
        else return false;
    }

    public boolean IsVerified(){
        if (user.isVerified())
            return true;
        else return false;
    }

    public int GetFollowersCount(){
        return user.getFollowersCount();
    }

    public String GetFullName(){
        if (user.getFirstName()!=null && user.getLastName()!=null)
            return user.getFirstName()+" "+user.getLastName();
        return null;
    }
}
