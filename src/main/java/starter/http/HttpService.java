package starter.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by tj on 7/28/16.
 */
public class HttpService {

  private CloseableHttpClient client;

  public HttpService(){
    // TODO: can we inject the client here
    client = HttpClients.createDefault();
  }


  public String initConnection(String url){
    // send a get request and wait indefinitely for a connection
    // return a JSON response as a raw string with the game url, auth tokens, etc
    HttpGet httpGet = new HttpGet(url);
    return requestHelper(httpGet);
  }

  public String sendUpdate(String url, String json){
    HttpPost httpPost = new HttpPost(url);
    StringEntity entity = new StringEntity(json, ContentType.create("application/json", "UTF-8"));
    httpPost.setEntity(entity);
    return requestHelper(httpPost);
  }

  private String requestHelper(HttpUriRequest request){
    StringBuilder sb = new StringBuilder();
    try(
      CloseableHttpResponse response = client.execute(request);
      BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    ) {
      br.lines().forEach(line -> sb.append(line));
    }catch(IOException ex){
      ex.printStackTrace();
    }
    return sb.toString();
  }

}
