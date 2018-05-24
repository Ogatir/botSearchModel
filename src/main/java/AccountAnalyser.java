import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;

import java.util.*;

public class AccountAnalyser {

    private int UserId;
    private UnifiedCriteria criteria= new UnifiedCriteria();
    private VkApiClient vk;
    private UserActor actor;
    GetUserInfo userGetter;

    public AccountAnalyser(int userId, VkApiClient vk, UserActor actor){
        this.UserId=userId;
        this.vk=vk;
        this.actor=actor;
    }

    public int Analyse(){
        boolean [] criteriaArray = new boolean[6];
        userGetter = new GetUserInfo(vk,actor,UserId);
        GetWallInfo wallGetter = new GetWallInfo(vk,actor,UserId);
        GetFriendListInfo friendlistInfo = new GetFriendListInfo(vk,actor,UserId);
        criteriaArray[0]=userGetter.HasPhoto();
        criteriaArray[1] = userGetter.IsVerified();
        criteriaArray[2]=PostFreqHigh(wallGetter);
        criteriaArray[3]= LotsOfFriendsAndLittleLikes(wallGetter,friendlistInfo);
        criteriaArray[4]=LittleSources(wallGetter);
        criteriaArray[5]=ManyLinks(wallGetter);
        criteria.SetCriterias(criteriaArray);
        PrintResults(criteriaArray);
        return 0;
    }

    private boolean PostFreqHigh(GetWallInfo wallGetter){
        long[] postCreationTimes=new long[wallGetter.PostCount()];
        long[] postIntervals=new long[wallGetter.PostCount()-1];
        ArrayList<ArrayList<Long>> intervalGroups = new ArrayList<>();
        intervalGroups.add(new ArrayList<>());

        for (int i=0;i<wallGetter.PostCount();i++)
            postCreationTimes[i] = wallGetter.GetWallPostTime(i);

        for(int i=0;i<wallGetter.PostCount()-1;i++){
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
        System.out.printf("Среднее число постов в сутки = %f\n",averageGroupSize);
        if (averageGroupSize>2)
            return true;
        else return false;
    }

    private boolean LittleAmmountOfLikes (GetWallInfo wallGetter){
        int [] likesCountArray = wallGetter.GetLikesCount();
        int count=0;
        for (int i=0;i<wallGetter.PostCount();i++){
            count+=likesCountArray[i];
        }
        float averageLikesPerPost =(float) count/(float)likesCountArray.length;
        System.out.printf("Average likes per post = %f\n",averageLikesPerPost);
        if (averageLikesPerPost<0.5)
            return true;
        else return false;
    }

    private boolean LotsOfFriendsAndLittleLikes(GetWallInfo wallGetter, GetFriendListInfo friendlistInfo){
        int [] likesCountArray = wallGetter.GetLikesCount();
        int count=0;
        for (int i=0;i<wallGetter.PostCount();i++){
            count+=likesCountArray[i];
        }
        float averageLikesPerPost =(float) count/(float)likesCountArray.length;
        System.out.printf("Среднее количество лайков за пост = %f\n",averageLikesPerPost);
        if (averageLikesPerPost<0.5 && friendlistInfo.GetFriendsCount()>30)
            return true;
        else return false;
    }

    private boolean LittleSources(GetWallInfo wallGetter){
        Map<Integer,Integer> publications = wallGetter.GetPublishers();
        publications.remove(UserId);
        System.out.println("Всего постов: "+publications.size());
        if (publications.size()==0){
            System.out.println("На стене исключительно записи пользователя");
        } else for (Map.Entry<Integer, Integer> pub : publications.entrySet())
        {
            System.out.printf("Пользователь: %d запостил %d раз\n",pub.getKey(),pub.getValue());
        }
        if (publications.size()!=0) {
            int max = Collections.max(publications.values());
            if (max > publications.size())
                return true;
        } return false;
    }

    private boolean ManyLinks(GetWallInfo wallGetter){
        TreeMap<Integer,Boolean> linksPresent = wallGetter.GetLinksPresent();
        int linksCount=0;
        for (Map.Entry<Integer, Boolean> pub : linksPresent.entrySet())
        {
            System.out.printf("Пост %d содержит ссылку = %b \n",pub.getKey(),pub.getValue());
            if (pub.getValue())
                linksCount++;
        }
        double linksToCount = (double)linksCount/linksPresent.size();
        System.out.printf("Отношение количества ссылок к количеству постов = %f",linksToCount);
        if (linksToCount>0.2)
        return true;
             else return false;
    }
    public void PrintResults(boolean[] criteriaArray){
        DBG.LogWithoutTime("\n---------------------------------");
        DBG.Log("Имя: %s",userGetter.GetFullName());
        DBG.Log("Наличие фотографии: "+criteriaArray[0]);
        DBG.Log("Подтверждение: "+criteriaArray[1]);
        DBG.Log("Высокая частота постов : "+criteriaArray[2]);
        DBG.Log("Мало лайков: "+criteriaArray[3]);
        DBG.Log("Мало источников: "+criteriaArray[4]);
        DBG.Log("Много ссылок: "+criteriaArray[5]);
        DBG.LogWithoutTime("---------------------------------");
    }







}
