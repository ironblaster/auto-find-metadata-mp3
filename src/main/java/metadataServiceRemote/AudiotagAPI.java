package metadataServiceRemote;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import config.Config;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import pojo.Metadata;

public class AudiotagAPI {
	

	
	
	public Metadata apitestPost(File audio) throws IOException  {
		
		
	       HttpPost post = new HttpPost("https://audiotag.info/api");

	       FileBody fileBody = new FileBody(audio);
	        
	        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
	        		.addTextBody("apikey", Config.getApiaudiotag())
	        		.addTextBody("action", "identify")
	        		.addPart("file",fileBody);
	        		 
	        HttpEntity entity = builder.build();

	        	
	        	  post.setEntity(entity);

	        try (CloseableHttpClient httpClient = HttpClients.createDefault();
	             CloseableHttpResponse response = httpClient.execute(post)) {
	        	
	        	
	        	

	        	JsonObject t = Json.parse(EntityUtils.toString(response.getEntity()));
			
	        	return responsemp3(t.getString("token"));
	        
	        }
	       
	       
	       
		}
	
	public static Metadata responsemp3(String token) throws ParseException, IOException {
		Metadata song = new Metadata();
		//risposta in base al token
     boolean control=false;
     while (control == false) {
     HttpPost postresult = new HttpPost("https://audiotag.info/api");

     // add request parameter, form parameters

     List<NameValuePair> urlParameters = new ArrayList<>();
    urlParameters.add(new BasicNameValuePair("apikey", Config.getApiaudiotag()));
    urlParameters.add(new BasicNameValuePair("action", "get_result"));
    urlParameters.add(new BasicNameValuePair("token", token));
     
		postresult.setEntity(new UrlEncodedFormEntity(urlParameters));
    
    try (CloseableHttpClient httpClient = HttpClients.createDefault();
	             CloseableHttpResponse response = httpClient.execute(postresult)) {

 	   
 	   		
 	   		JsonObject t = Json.parse(EntityUtils.toString(response.getEntity()));
 	   		System.out.println(t.getString("result"));
 	   		
 	   		if (t.getString("result").equals("found")) {
 	   		
 	   		
 	   		JsonArray data = t.getArray("data");
 	   		JsonObject dat = data.getObject(0);
 	   		JsonArray trak =dat.getArray("tracks");
 	   		JsonArray datiCanzone = trak.get(0);
 	   		
 	   		try {
 	   		song.setTitle(datiCanzone.getString(0));
 	   		}catch (Exception e) {}
 	   		
 	   		try {
 	   		song.setAuthor(datiCanzone.getString(1));
 	   		}catch (Exception e) {}
 	   		
 	   		try {
 	   		song.setAlbum(datiCanzone.getString(2));
 	   		}catch (Exception e) {}
 	   	
 	   		control=true;
 	   		
 	   		
 	   		}
 	   		else{
 	   			if (!t.getString("result").equals("wait"))
 	   				control=true;
 	   		}		
 	   		}
 	   		
 	   		try{Thread.sleep(1000);}catch (Exception e) {}
     }
    
		
     return song;
	}
}
