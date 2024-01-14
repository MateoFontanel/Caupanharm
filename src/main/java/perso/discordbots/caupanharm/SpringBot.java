package perso.discordbots.caupanharm;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.rest.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import perso.discordbots.caupanharm.controllers.PropertyController;
import perso.discordbots.caupanharm.controllers.RiotAPIController;
import perso.discordbots.caupanharm.controllers.UserController;


@SpringBootApplication(scanBasePackages = "perso.discordbots.caupanharm")
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class})
public class SpringBot {
    private static Tracker tracker;
    private final static Logger logger = LoggerFactory.getLogger(SpringBot.class);

    @Value("${discord_bot_token}")
    String discord_bot_token;

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBot.class)
                .build()
                .run(args);

    }

    public static void setTracker(String summoner, String champion) {
        tracker.interrupt();
        tracker = new Tracker(summoner, champion);
        tracker.start();
    }

    public static void setTracker(Tracker newTracker) {
        tracker = newTracker;
        tracker.start();
    }

    public static Tracker getTracker() {
        return tracker;
    }


    @Bean
    public RiotAPIController riotAPIController(){
        return new RiotAPIController();
    }

    @Bean
    public UserController userController() {
        return new UserController("caupanharm_db");
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
