package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.SpringBot;
import perso.discordbots.caupanharm.controllers.UserController;
import perso.discordbots.caupanharm.models.CaupanharmUser;
import reactor.core.publisher.Mono;

@Component
public class TrackCommand implements SlashCommand{

    @Override
    public String getName() {
        return "track";
    }

    @Autowired
    UserController userController;

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String discordId = event.getInteraction().getUser().getId().asString();
        CaupanharmUser registeredUser = userController.getUser("discordId", discordId);

        String response;
        if(registeredUser == null){
            response = "**Error: **You have not linked your Riot account yet. Try using */link add*";
        }else{
            // 1) get command value
            boolean tracking = event.getOption("set")
                    .flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asBoolean)
                    .get(); //This is warning us that we didn't check if its present, we can ignore this on required options

            if(registeredUser.isTracked() && tracking){
                response = "**Error: **Tracking is already on for your account";
            }else if(!(registeredUser.isTracked()) && !tracking){
                response = "**Error: **Tracking is already off for your account";
            }else{
                // 2) set tracking on in the database
                userController.updateUser("discordId", discordId, "tracked", tracking);
                response = "Your tracking status was set to "+ (tracking ? "*on*" : "*off*");
            }
        }

        return event.reply()
                .withEphemeral(true)
                .withContent(response);
    }
}
