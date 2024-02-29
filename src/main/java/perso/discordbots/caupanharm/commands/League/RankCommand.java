package perso.discordbots.caupanharm.commands.League;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.commands.SlashCommand;
import perso.discordbots.caupanharm.controllers.RiotAPIController;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;

// TODO not implemented yet
@Component
public class RankCommand implements SlashCommand {
    @Override
    public String getName() {
        return "lol_rank";
    }

    @Autowired
    RiotAPIController riotAPIController;

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) throws UnsupportedEncodingException {
        //We reply to the command with "Pong!" and make sure it is ephemeral (only the command user can see it)
        return event.reply()
                .withEphemeral(true)
                .withContent(riotAPIController.getRiotUser("Herrahan#EUW").toString());
    }

}