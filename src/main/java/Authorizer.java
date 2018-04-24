import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.apache.http.HttpRequest;

public class Authorizer {

  private String CLIENT_SECRET="VfgVHh7PK8qHigKFQ2cf";
  private String CODE="4a2151f18a0fcb802dd3d879b40997e298b3a0988e78f1c703296bb76538d4a17f69c412c5e65a85762ec";
  private int CLIENT_ID=6430983;
  private String REDIRECT_URL="https://oauth.vk.com/blank.html";
  private String code="3bb5f1d9bf6522c4ca";

  public Authorizer(){

  }

  public Authorizer(String CLIENT_SECRET,String CODE,int CLIENT_ID,String code){
        this.CLIENT_SECRET=CLIENT_SECRET;
        this.CODE=CODE;
        this.CLIENT_ID=CLIENT_ID;
        this.code=code;
  }

  public UserActor getAccessToken(VkApiClient vk){
      String uri ="https://oauth.vk.com/authorize?client_id=6430983"+
              "&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=friends&response_type=code&v=5.73";

      try {
          UserAuthResponse authResponse = vk.oauth()
                  .userAuthorizationCodeFlow(CLIENT_ID, CLIENT_SECRET, REDIRECT_URL, code)
                  .execute();
          String accessToken = authResponse.getAccessToken();
          UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
          return actor;
      } catch (Exception e){
          System.out.println(e.getMessage());
          return null;
      }
  }
}
