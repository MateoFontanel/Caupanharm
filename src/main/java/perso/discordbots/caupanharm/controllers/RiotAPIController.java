package perso.discordbots.caupanharm.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import perso.discordbots.caupanharm.models.CaupanharmUser;
import perso.discordbots.caupanharm.models.LeagueAPIUser;
import perso.discordbots.caupanharm.models.RiotAPIUser;

import java.io.UnsupportedEncodingException;
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

    private boolean debug = true;

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    private HttpResponse<String> makeRequest(String uri){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("X-Riot-Token", riot_api_key)
                .build();

        logger.info(String.valueOf(request.uri()));
        logger.info(String.valueOf(request.headers()));

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if(debug) logger.info(String.valueOf(response));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public RiotAPIUser getRiotUser(String completeUsername) throws UnsupportedEncodingException {
        String username = completeUsername.split("#")[0].trim();
        String tagline = completeUsername.split("#")[1].trim();

        String uri = String.format("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s", username.trim().replace(" ","%20"), tagline);
        HttpResponse<String> response = makeRequest(uri); // So Username#Tagline becomes Username/Tagline
        if (response.statusCode() == 200) {
            try{
                return new ObjectMapper().readValue(response.body(), RiotAPIUser.class);
            }catch(JsonProcessingException e){
                logger.error(String.valueOf(e));
            }
        }
        logger.error(response.body());
        return null;
    }

    public LeagueAPIUser getLeagueUser(RiotAPIUser riotAPIUser){
        String uri = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/"+riotAPIUser.getPuuid();
        HttpResponse<String> response = makeRequest(uri);
        if (response.statusCode() == 200) {
            try{
                return new ObjectMapper().readValue(response.body(), LeagueAPIUser.class);
            }catch(JsonProcessingException e){
                logger.error(String.valueOf(e));
            }
        }
        logger.error(response.body());
        return null;
    }

    public boolean getActiveLeagueGame(CaupanharmUser caupanharmUser){
        String uri = "https://euw1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + caupanharmUser.getRiotId();
        HttpResponse<String> response = makeRequest(uri);
        switch(response.statusCode()){ // TODO retourner les bonnes valeurs (LeagueGame ou null)
            case 200:
                return true;
            case 404:
                return false;
            default:
                logger.warn("Unchecked code : "+response.statusCode());
                return false;
        }
    }
}
