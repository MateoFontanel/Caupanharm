package perso.discordbots.caupanharm.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import perso.discordbots.caupanharm.models.RiotAPIUser;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;

@Controller
public class RiotAPIController {
    private final static Logger logger = LoggerFactory.getLogger(RiotAPIController.class);

    @Value("${riot_api_key}")
    String riot_api_key;

    private HttpResponse<String> makeRequest(String uri){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("X-Riot-Token", riot_api_key)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            logger.info(String.valueOf(response));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public RiotAPIUser getUserFromUsername(String completeUsername){
        HttpResponse<String> response = makeRequest("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/"+completeUsername.replace("#","/")); // So Username#Tagline becomes Username/Tagline
        if (response.statusCode() == 200) {
            try{
                return new ObjectMapper().readValue(response.body(), RiotAPIUser.class);
            }catch(JsonProcessingException e){
                logger.error(String.valueOf(e));
            }
        }
        logger.error(response.body());
        return new RiotAPIUser();
    }
}
