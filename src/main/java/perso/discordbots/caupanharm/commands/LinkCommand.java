package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.controllers.RiotAPIController;
import perso.discordbots.caupanharm.controllers.UserController;
import perso.discordbots.caupanharm.models.CaupanharmUser;
import reactor.core.publisher.Mono;

import java.util.Objects;

// TODO check
@Component
public class LinkCommand implements SlashCommand {
    private final static Logger logger = LoggerFactory.getLogger(LinkCommand.class);

    @Autowired
    UserController userController;

    @Autowired
    RiotAPIController riotAPIController;

    @Override
    public String getName() {
        return "link";
    }

    //TODO remplacer les if en cascade par des elseif
    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String discordId = event.getInteraction().getUser().getId().asString();
        CaupanharmUser registeredUser = userController.getUser("discordId",discordId);
        String response;
        if(event.getOption("add").isPresent()){
            String username = getSubcommandValue(event, "add", "username");
            String tagline = getSubcommandValue(event, "add", "tagline");
            if(registeredUser == null){
                if(userController.getUser("riotUsername",username+"#"+tagline) != null){
                    response = "**Error:** This Riot account is already linked to another user\nIf you think this is a mistake, please contact an admin";
                }else{
                    String riotPuuid = riotAPIController.getUserFromUsername(username, tagline).getPuuid();
                    if(riotPuuid != null){
                        userController.insertUser(discordId,riotPuuid,username+"#"+tagline);
                        response = "Account "+username+"#"+tagline+" is now linked to Caupanharm";
                    }else{
                        response = "**Error: **This Riot account does not exist";
                    }

                }
            }else{
                response = "**Error:** You already linked an account to Caupanharm ("+registeredUser.getRiotUsername()+")";
            }

        } else if (event.getOption("edit").isPresent()) {
            String username = getSubcommandValue(event, "edit", "username");
            String tagline = getSubcommandValue(event, "edit", "tagline");
            if(registeredUser != null){
                CaupanharmUser checkedUser = userController.getUser("riotUsername",username+"#"+tagline);

                if(checkedUser != null){
                    if(Objects.equals(checkedUser.getDiscordId(), discordId)){
                        response = "**Error:** You are already linked to this Riot account";
                    }else{
                        response = "**Error:** This Riot account is already linked to another user\nIf you think this is a mistake, please contact an admin";
                    }
                }else{
                    String riotPuuid = riotAPIController.getUserFromUsername(username, tagline).getPuuid();
                    if(riotPuuid != null){
                        userController.updateUser("discordId", discordId, "riotUsername", username + "#" + tagline);
                        userController.updateUser("discordId", discordId, "riotPuuid", riotPuuid);
                        response = "Your linked account was updated from "+registeredUser.getRiotUsername()+" to "+username+"#"+tagline;
                    }else{
                        response = "**Error: **This Riot account does not exist";
                    }
                }
            }else{
                response = "**Error:** You didn't link your Riot account to Caupanharm yet\nTry using */link add* instead";
            }
        }else if (event.getOption("remove").isPresent()){
            if(registeredUser != null) {
                userController.deleteUser("discordId", event.getInteraction().getUser().getId().asString());
                response = "Your account ("+registeredUser.getRiotUsername()+") was successfully deleted from Caupanharm";
            }else{
                response = "**Error:** You didn't link your Riot account to Caupanharm yet\nTry using */link add*";
            }
        }else{// last possible option: "check" (option being a mandatory field, this is safe)
            response = (registeredUser != null) ?
                    "You are currently linked to "+registeredUser.getRiotUsername() :
                    "You didn't link your Riot account to Caupanharm yet\nTry using */link add*";
        }

        return event.reply()
                .withEphemeral(true)
                .withContent(response);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private String getSubcommandValue(ChatInputInteractionEvent event, String subcommandOption, String subcommandValue){
        return event.getOption(subcommandOption).get().getOption(subcommandValue)
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get(); //This is warning us that we didn't check if its present, we can ignore this on required options
    }
}