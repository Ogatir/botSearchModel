import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.responses.GetResponse;

import java.util.*;

public class GetWallInfo {

    private VkApiClient vk;
    private UserActor actor;
    private List <WallPostFull> userWallPosts;


    public GetWallInfo(VkApiClient vk, UserActor actor, int UserId){
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

    public float GetWallPostFreq(){
        long[] postCreationTimes=new long[this.PostCount()];
        long[] postIntervals=new long[this.PostCount()-1];
        ArrayList<ArrayList<Long>> intervalGroups = new ArrayList<>();
        intervalGroups.add(new ArrayList<>());

        for (int i=0;i<this.PostCount();i++)
            postCreationTimes[i] = this.GetWallPostTime(i);

        for(int i=0;i<this.PostCount()-1;i++){
            postIntervals[i]=postCreationTimes[i]-postCreationTimes[i+1];
            System.out.printf("%d и %d: %d\n",i,i+1,postIntervals[i]);
            int oneDay = 86400;
            if (postIntervals[i]< oneDay){
                int lastIndexOfOut = intervalGroups.size()-1;
                intervalGroups.get(lastIndexOfOut).add(postIntervals[i]);
            }
            else {
                intervalGroups.add(new ArrayList<>());
                int lastIndexOfOut = intervalGroups.size()-1;
                intervalGroups.get(lastIndexOfOut).add(postIntervals[i]);
            }

        }
        for (int i=0;i<intervalGroups.size();i++){
            System.out.printf("%d: ",i+1);
            for (int j=0;j<intervalGroups.get(i).size();j++){
                System.out.printf("%d ",intervalGroups.get(i).get(j));
            }
            System.out.println();
        }

        int groupsNumber = intervalGroups.size();
        float averageGroupSize=0;
        int count=0;
        for (int i=0;i<groupsNumber;i++){
            count+=intervalGroups.get(i).size();
        }
        averageGroupSize = (float)count/(float)groupsNumber;
        return averageGroupSize;
    }
    public long GetWallPostTime(int postNum){
        WallPostFull post = userWallPosts.get(postNum);
        Date date = new Date(post.getDate());
        return  date.getTime();
    }

    public int[] GetLikesCount(){
        int[] likesCountArray = new int[userWallPosts.size()];
        for (int i=0;i<userWallPosts.size();i++){
            likesCountArray[i]=userWallPosts.get(i).getLikes().getCount();
        }
        return likesCountArray;
    }

    public TreeMap<Integer,Integer> GetPublishers(){
        TreeMap<Integer,Integer> publishersCount = new TreeMap<Integer, Integer>();

        for(WallPostFull post:userWallPosts){
            int postId=post.getFromId();
            if (!publishersCount.containsKey(postId)){
                publishersCount.put(postId,1);
            } else {
                int count = publishersCount.get(postId)+1;
                publishersCount.put(postId,count);
            }
        }
        return  publishersCount;
    }

    public TreeMap<Integer,Boolean> GetLinksPresent(){
        TreeMap<Integer,Boolean> linksPresent = new TreeMap<>();

        for (WallPostFull post:userWallPosts){
            if (post.getAttachments()!=null) {
                for (WallpostAttachment attachment : post.getAttachments()) {
                        if (attachment.getLink() != null) {
                            linksPresent.put(post.getId(), true);
                            continue;
                        } else linksPresent.put(post.getId(), false);
                }
            }
        }
        return linksPresent;
    }

    private WallPostFull GetOneWallPost(int UserId, int postNumber){
        List <WallPostFull> posts;
        if((posts = GetUserWallPosts(UserId,100))!=null)
            return posts.get(postNumber);
        else return null;
    }
}
