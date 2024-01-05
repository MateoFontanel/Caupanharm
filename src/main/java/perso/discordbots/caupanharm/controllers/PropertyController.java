package perso.discordbots.caupanharm.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class PropertyController {
    private static String RIOT_API_KEY;
    private static String DISCORD_BOT_TOKEN;
    private static String MONGODB_ADMIN_USR;
    private static String MONGODB_ADMIN_PWD;

    @Value("${riot_api_key}")
    public void setRiotApiKey(String riot_api_key){
        PropertyController.RIOT_API_KEY = riot_api_key;
    }

    @Value("${discord_bot_token}")
    public void setDiscordBotToken(String discord_bot_token){
        PropertyController.DISCORD_BOT_TOKEN = discord_bot_token;
    }

    @Value("${mongodb_admin_usr}")
    public void setMongodbAdminUsr(String mongodb_admin_usr){
        PropertyController.MONGODB_ADMIN_USR = mongodb_admin_usr;
    }

    @Value("${mongodb_admin_pwd}")
    public void setMongodbAdminPwd(String mongodb_admin_pwd){
        PropertyController.MONGODB_ADMIN_PWD = mongodb_admin_pwd;
    }

    public String getRiotApiKey(){return RIOT_API_KEY;}
    public String getDiscordBotToken(){return DISCORD_BOT_TOKEN;}
    public String getMongodbAdminUsr(){return MONGODB_ADMIN_USR;}
    public String getMongodbAdminPwd(){return MONGODB_ADMIN_PWD;}
}