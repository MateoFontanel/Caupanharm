package perso.discordbots.caupanharm.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import perso.discordbots.caupanharm.models.api.*;
import perso.discordbots.caupanharm.models.CaupanharmUser;
import perso.discordbots.caupanharm.util.RequestBuilder;

import java.net.http.HttpResponse;

@Controller
public class APIController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${riot_api_key}")
    String riot_api_key;

    @Value("${henrik_api_key}")
    String henrik_api_key;

    @Value("${val_current_season}")
    String current_season;

    public APIController() {
    }

    public RiotUser getRiotUser(String completeUsername) {
        String username = completeUsername.split("#")[0].trim();
        String tagline = completeUsername.split("#")[1].trim();

        String uri = String.format("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s", username.replace(" ","%20"), tagline);
        HttpResponse<String> response = RequestBuilder.get(Apis.RIOT, uri, riot_api_key);
        if (response.statusCode() == 200) {
            try{
                return new ObjectMapper().readValue(response.body(), RiotUser.class);
            }catch(JsonProcessingException e){
                logger.error(String.valueOf(e));
            }
        }
        logger.error(response.body());
        return null;
    }

    public HenrikUser getHenrikUser(String completeUsername, boolean updateFirst){
        String username = completeUsername.split("#")[0].trim();
        String tagline = completeUsername.split("#")[1].trim();
        String uri = String.format("https://api.henrikdev.xyz/valorant/v1/account/%s/%s?force=%b", username.replace(" ","%20"), tagline, updateFirst);

        HttpResponse<String> response = RequestBuilder.get(Apis.HENRIKDEV, uri, henrik_api_key);
        switch(response.statusCode()){
            case 200:
                try{
                    HenrikApiResponse data = new ObjectMapper().readValue(response.body(), HenrikApiResponse.class);
                    return data.buildHenrikUser();
                }catch(JsonProcessingException e){
                    logger.error(String.valueOf(e));
                }
                break;

            default:
                logger.error(String.valueOf(response.statusCode()));
                logger.error(response.body());
                break;
        }

        return null;
    }

    public String getValorantRank(String puuid){
        String uri = String.format("https://api.henrikdev.xyz/valorant/v2/by-puuid/mmr/eu/%s", puuid);
        HttpResponse<String> response = RequestBuilder.get(Apis.HENRIKDEV, uri, henrik_api_key);
        switch(response.statusCode()){
            case 200:
                try{
                    HenrikApiResponse data = new ObjectMapper().readValue(response.body(), HenrikApiResponse.class);
                    return data.getMmr(current_season);
                }catch(JsonProcessingException e){
                    logger.error(String.valueOf(e));
                }
                break;

            default:
                logger.error(String.valueOf(response.statusCode()));
                logger.error(response.body());
                break;
        }

        return null;
    }

    public RiotLeagueUser getLeagueUser(RiotUser riotUser){
        String uri = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/"+ riotUser.getPuuid();
        HttpResponse<String> response = RequestBuilder.get(Apis.RIOT, uri, riot_api_key);
        if (response.statusCode() == 200) {
            try{
                return new ObjectMapper().readValue(response.body(), RiotLeagueUser.class);
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
