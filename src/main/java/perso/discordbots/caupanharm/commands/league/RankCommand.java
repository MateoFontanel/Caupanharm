package perso.discordbots.caupanharm.commands.league;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.commands.SlashCommand;
import perso.discordbots.caupanharm.controllers.APIController;
import reactor.core.publisher.Mono;

// TODO not implemented yet
@Component
public class RankCommand implements SlashCommand {
    @Override
    public String getName() {
        return "lol_rank";
    }

    @Autowired
    APIController apiController;

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        //We reply to the command with "Pong!" and make sure it is ephemeral (only the command user can see it)
        return event.reply()
                .withEphemeral(true)
                .withContent(apiController.getRiotUser("Herrahan#EUW").toString());
    }

}