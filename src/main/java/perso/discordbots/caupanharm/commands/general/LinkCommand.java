package perso.discordbots.caupanharm.commands.general;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.ResponseBuilder;
import perso.discordbots.caupanharm.commands.SlashCommand;
import perso.discordbots.caupanharm.controllers.RiotAPIController;
import perso.discordbots.caupanharm.controllers.UserController;
import perso.discordbots.caupanharm.models.CaupanharmUser;
import perso.discordbots.caupanharm.models.LeagueAPIUser;
import perso.discordbots.caupanharm.models.RiotAPIUser;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;

// TODO clear MongoDB logs
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

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String discordId = event.getInteraction().getUser().getId().asString();
        CaupanharmUser registeredUser = userController.getUser("discordId", discordId);
        String response;

        // Note: some if/else are cascading on purpose to reduce calls to APIs in non-necessary cases // TODO REMOVE THEM USING RESPONSEBUILDER INSTEAD
        switch (event.getOptions().get(0).getName()) {
            case ("add"):
                String completeUsername = getSubcommandValue(event, "add", "username");

                if (registeredUser != null)
                    return ResponseBuilder.build(event, "**Error:** You already linked an account to Caupanharm (" + registeredUser.getRiotUsername() + ")", true, false);

                if (!(completeUsername.contains("#")))
                    return ResponseBuilder.build(event, "**Error:** Invalid username", true, false);

                RiotAPIUser riotAPIUser = null;
                try {
                    riotAPIUser = riotAPIController.getRiotUser(completeUsername);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                if (riotAPIUser == null) {
                    response = "**Error: **This Riot account does not exist";
                } else if (userController.getUser("riotPuuid", riotAPIUser.getPuuid()) != null) {
                    response = "**Error:** This Riot account is already linked to another user\nIf you think this is a mistake, please contact an admin";
                } else {
                    LeagueAPIUser leagueAPIUser = riotAPIController.getLeagueUser(riotAPIUser);
                    userController.insertUser(discordId,
                            (riotAPIController.getLeagueUser(riotAPIUser) == null) ? null : leagueAPIUser.getId(),
                            riotAPIUser.getPuuid(),
                            riotAPIUser.getFullName());
                    response = "Account " + riotAPIUser.getFullName() + " is now linked to Caupanharm";
                }

                break;

            case "edit":
                completeUsername = getSubcommandValue(event, "edit", "username");

                if (registeredUser == null) {
                    response = "**Error:** You have not linked your Riot account to Caupanharm yet\nTry using */link add* instead";
                } else {
                    try {
                        riotAPIUser = riotAPIController.getRiotUser(completeUsername);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    if (riotAPIUser == null) {
                        response = "**Error: **This Riot account does not exist";
                    } else {
                        CaupanharmUser checkedUser = userController.getUser("riotPuuid", riotAPIUser.getPuuid());
                        if (checkedUser != null) {
                            response = (Objects.equals(checkedUser.getDiscordId(), discordId)) ?
                                    "**Error:** You are already linked to this Riot account" :
                                    "**Error:** This Riot account is already linked to another user\nIf you think this is a mistake, please contact an admin";
                        } else {
                            LeagueAPIUser leagueAPIUser = riotAPIController.getLeagueUser(riotAPIUser);
                            userController.updateUser("discordId", discordId, "riotUsername", riotAPIUser.getFullName());
                            userController.updateUser("discordId", discordId, "riotId", (riotAPIController.getLeagueUser(riotAPIUser) == null) ? null : leagueAPIUser.getId());
                            userController.updateUser("discordId", discordId, "riotPuuid", riotAPIUser.getPuuid());
                            response = "Your linked account was updated from " + registeredUser.getRiotUsername() + " to " + riotAPIUser.getFullName();
                        }
                    }
                }
                break;

            case "remove":
                if (registeredUser == null) {
                    response = "**Error:** You didn't link your Riot account to Caupanharm yet\nTry using */link add*";
                } else {
                    userController.deleteUser("discordId", event.getInteraction().getUser().getId().asString());
                    response = "Your account (" + registeredUser.getRiotUsername() + ") was successfully deleted from Caupanharm";
                }
                break;

            case "check":
                response = (registeredUser != null) ?
                        "You are currently linked to " + registeredUser.getRiotUsername() :
                        "You didn't link your Riot account to Caupanharm yet\nTry using */link add*";
                break;

            default:
                response = "Internal error";
        }

        return event.reply()
                .withEphemeral(true)
                .withContent(response);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private String getSubcommandValue(ChatInputInteractionEvent event, String subcommandOption, String subcommandValue) {
        return event.getOption(subcommandOption).get().getOption(subcommandValue)
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get(); //This is warning us that we didn't check if its present, we can ignore this on required options
    }
}