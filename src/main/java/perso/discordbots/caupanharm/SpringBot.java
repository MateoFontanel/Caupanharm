package perso.discordbots.caupanharm;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.rest.RestClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import perso.discordbots.caupanharm.controllers.*;
import perso.discordbots.caupanharm.threads.TrackerDeprecated;

import java.io.IOException;


@SpringBootApplication(scanBasePackages = "perso.discordbots.caupanharm")
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class})
public class SpringBot {
    private static TrackerDeprecated trackerDeprecated;
    private final static Logger logger = LoggerFactory.getLogger(SpringBot.class);

    @Value("${discord_bot_token}")
    String discord_bot_token;

    @Value("${emojis_guild_id}")
    String emojis_guild_id;

    @Value("${mongodb_db_name}")
    String db_name;


    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBot.class)
                .build()
                .run(args);
    }


    public static void setTracker(String summoner, String champion) {
        trackerDeprecated.interrupt();
        trackerDeprecated = new TrackerDeprecated(summoner, champion);
        trackerDeprecated.start();
    }

    public static void setTracker(TrackerDeprecated newTrackerDeprecated) {
        trackerDeprecated = newTrackerDeprecated;
        trackerDeprecated.start();
    }

    public static TrackerDeprecated getTracker() {
        return trackerDeprecated;
    }


    /*
    @Bean
    public GameTracker gameTracker(){ return new GameTracker();}
     */

    @Bean
    public APIController apiController(){
        return new APIController();
    }

    @Bean
    public UserController userController() {
        return new UserController(db_name);
    }

    @Bean
    public ChannelController channelController() throws IOException {
        return new ChannelController();
    }

    @Bean
    public TeamController teamController(){
        return new TeamController();
    }

    @Bean
    public EmojiController emojiController(){
        return new EmojiController(emojis_guild_id);
    }

    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        return DiscordClientBuilder.create(discord_bot_token).build()
                .gateway()
                .setInitialPresence(ignore -> ClientPresence.online(ClientActivity.listening("Herrahan")))
                .login()
                .block();
    }

    @Bean
    public RestClient discordRestClient(GatewayDiscordClient client) {
        return client.getRestClient();
    }
}
