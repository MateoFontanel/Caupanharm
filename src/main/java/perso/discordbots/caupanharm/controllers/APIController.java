package perso.discordbots.caupanharm.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import perso.discordbots.caupanharm.models.api.*;
import perso.discordbots.caupanharm.models.CaupanharmUser;
import perso.discordbots.caupanharm.models.api.henrik.AccountV1;
import perso.discordbots.caupanharm.models.api.henrik.MmrV1;
import perso.discordbots.caupanharm.models.api.riot.RiotLeagueUser;
import perso.discordbots.caupanharm.models.api.riot.RiotUser;
import perso.discordbots.caupanharm.util.RequestBuilder;

import java.net.http.HttpResponse;


@SuppressWarnings("unused")
@Controller
public class APIController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("unused")
    @Value("${riot_api_key}")
    String riot_api_key;

    @SuppressWarnings("unused")
    @Value("${henrik_api_key}")
    String henrik_api_key;


    public APIController(){
        getValorantRank("b441ec66-669d-550d-bd34-24a678c5eb6f");
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

    public AccountV1 getHenrikUser(String completeUsername, boolean updateFirst){
        String username = completeUsername.split("#")[0].trim();
        String tagline = completeUsername.split("#")[1].trim();
        String uri = String.format("https://api.henrikdev.xyz/valorant/v1/account/%s/%s?force=%b", username.replace(" ","%20"), tagline, updateFirst);

        HttpResponse<String> response = RequestBuilder.get(Apis.HENRIKDEV, uri, henrik_api_key);
        if (response.statusCode() == 200) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body());
                JsonNode dataNode = rootNode.get("data");
                String data = dataNode.toString();
                return objectMapper.readValue(data, AccountV1.class);
            } catch (JsonProcessingException e) {
                logger.error("Error parsing Henrik user");
                logger.error(String.valueOf(e));
            }
        } else {
            logger.error(String.valueOf(response.statusCode()));
            logger.error(response.body());
        }

        return null;
    }

    public String getValorantRank(String henrikPuuid){
        String uri = String.format("https://api.henrikdev.xyz/valorant/v1/by-puuid/mmr/eu/%s", henrikPuuid);
        HttpResponse<String> response = RequestBuilder.get(Apis.HENRIKDEV, uri, henrik_api_key);
        switch(response.statusCode()){
            case 200:
                try{
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(response.body());
                    JsonNode dataNode = rootNode.get("data");
                    String data = dataNode.toString();
                    MmrV1 result = objectMapper.readValue(data, MmrV1.class);

                    return result.getCurrenttierpatched();
                }catch(JsonProcessingException e){
                    logger.error("Error parsing Valorant rank");
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
