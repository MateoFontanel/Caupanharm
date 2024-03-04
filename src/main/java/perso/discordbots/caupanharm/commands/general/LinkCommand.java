package perso.discordbots.caupanharm.commands.general;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.util.ResponseBuilder;
import perso.discordbots.caupanharm.commands.SlashCommand;
import perso.discordbots.caupanharm.controllers.APIController;
import perso.discordbots.caupanharm.controllers.UserController;
import perso.discordbots.caupanharm.models.CaupanharmUser;
import perso.discordbots.caupanharm.models.api.RiotLeagueUser;
import perso.discordbots.caupanharm.models.api.RiotUser;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

// TODO clear MongoDB logs
@Component
public class LinkCommand implements SlashCommand {
    private final static Logger logger = LoggerFactory.getLogger(LinkCommand.class);

    @Autowired
    UserController userController;
    @Autowired
    APIController apiController;


    @Override
    public String getName() {
        return "link";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String requestingUserDiscordId = event.getInteraction().getUser().getId().asString();
        CaupanharmUser requestingCaupanharmUser = userController.getUser("discordId", requestingUserDiscordId);
        String response;

        // Note: some if/else are cascading on purpose to reduce calls to APIs in non-necessary cases // TODO REMOVE THEM USING RESPONSEBUILDER INSTEAD
        switch (event.getOptions().get(0).getName()) {
            case ("add"):
                String newUsername = getSubcommandValue(event, "add", "username");

                if (requestingCaupanharmUser != null)
                    return ResponseBuilder.buildReply(event, "**Error:** You already linked an account to Caupanharm (" + requestingCaupanharmUser.getRiotUsername() + ")", null, true, false);

                if (!(newUsername.contains("#")))
                    return ResponseBuilder.buildReply(event, "**Error:** Invalid username", null, true, false);

                RiotUser newRiotUser = null;
                newRiotUser = apiController.getRiotUser(newUsername);
                if (newRiotUser == null) {
                    response = "**Error: **This Riot account does not exist";
                } else if (userController.getUser("riotPuuid", newRiotUser.getPuuid()) != null) {
                    response = "**Error:** This Riot account is already linked to another user\nIf you think this is a mistake, please contact an admin";
                } else {
                    RiotLeagueUser newRiotLeagueUser = apiController.getLeagueUser(newRiotUser);
                    String newHenrikPuuid = apiController.getHenrikUser(newRiotUser.getFullName(), true).getPuuid();
                    CaupanharmUser newCaupanharmUser = new CaupanharmUser(requestingUserDiscordId,
                            (apiController.getLeagueUser(newRiotUser) == null) ? null : newRiotLeagueUser.getId(), // league id, null if riot user never played LoL
                            newRiotUser.getPuuid(),
                            newHenrikPuuid,
                            newRiotUser.getFullName());
                    userController.insertUser(newCaupanharmUser);
                    response = "Account " + newCaupanharmUser.getRiotUsername() + " is now linked to Caupanharm";
                }

                break;

            case "edit":
                newUsername = getSubcommandValue(event, "edit", "username");

                if (requestingCaupanharmUser == null) {
                    response = "**Error:** You have not linked your Riot account to Caupanharm yet\nTry using */link add* instead";
                } else {
                    newRiotUser = apiController.getRiotUser(newUsername);
                    if (newRiotUser == null) {
                        response = "**Error: **This Riot account does not exist";
                    } else {
                        CaupanharmUser newCaupanharmUser = userController.getUser("riotPuuid", newRiotUser.getPuuid());
                        if (newCaupanharmUser != null) {
                            response = newCaupanharmUser.getRiotPuuid().equals(requestingCaupanharmUser.getRiotPuuid()) ?
                                    "**Error:** You are already linked to this Riot account" :
                                    "**Error:** This Riot account is already linked to another user\nIf you think this is a mistake, please contact an admin";
                        } else {
                            RiotLeagueUser riotLeagueUser = apiController.getLeagueUser(newRiotUser);

                            CaupanharmUser editedCaupanharmUser = new CaupanharmUser(
                                    requestingCaupanharmUser.getDiscordId(),
                                    apiController.getLeagueUser(newRiotUser) == null ? null : riotLeagueUser.getId(), // riotId means League id which can be null if the user never played League
                                    newRiotUser.getPuuid(),
                                    apiController.getHenrikUser(newRiotUser.getFullName(), true).getPuuid(), // henrik puuid of the new username
                                    newRiotUser.getFullName());
                            editedCaupanharmUser.setTeam(requestingCaupanharmUser.getTeam());
                            editedCaupanharmUser.setTracked(requestingCaupanharmUser.isTracked());
                            editedCaupanharmUser.setRoles(requestingCaupanharmUser.getRoles());

                            userController.replaceUser(requestingCaupanharmUser.getDiscordId(), editedCaupanharmUser);
                            response = "Your linked account was updated from " + requestingCaupanharmUser.getRiotUsername() + " to " + editedCaupanharmUser.getRiotUsername();
                        }
                    }
                }
                break;

            case "remove":
                if (requestingCaupanharmUser == null) {
                    response = "**Error:** You didn't link your Riot account to Caupanharm yet\nTry using */link add*";
                } else {
                    userController.deleteUser("discordId", event.getInteraction().getUser().getId().asString());
                    response = "Your account (" + requestingCaupanharmUser.getRiotUsername() + ") was successfully deleted from Caupanharm";
                }
                break;

            case "check":
                response = (requestingCaupanharmUser != null) ?
                        "You are currently linked to " + requestingCaupanharmUser.getRiotUsername() :
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