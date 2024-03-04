package perso.discordbots.caupanharm.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import perso.discordbots.caupanharm.models.Apis;
import perso.discordbots.caupanharm.models.CaupanharmUser;
import perso.discordbots.caupanharm.models.LeagueAPIUser;
import perso.discordbots.caupanharm.models.RiotAPIUser;
import perso.discordbots.caupanharm.util.RequestBuilder;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpResponse;

@Controller
public class RiotAPIController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${riot_api_key}")
    String riot_api_key;

    public RiotAPIUser getRiotUser(String completeUsername) throws UnsupportedEncodingException {
        String username = completeUsername.split("#")[0].trim();
        String tagline = completeUsername.split("#")[1].trim();

        String uri = String.format("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s", username.trim().replace(" ","%20"), tagline);
        HttpResponse<String> response = RequestBuilder.get(Apis.RIOT, uri, riot_api_key);
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
        HttpResponse<String> response = RequestBuilder.get(Apis.RIOT, uri, riot_api_key);
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
        HttpResponse<String> response = RequestBuilder.get(Apis.RIOT, uri, riot_api_key);
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
