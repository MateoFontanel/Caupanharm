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
import perso.discordbots.caupanharm.models.RiotAPIUser;
import reactor.core.publisher.Mono;

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

        // Note: some if/else are cascading on purpose to reduce calls to APIs in non-necessary cases
        // CASE /link add
        if (event.getOption("add").isPresent()) {
            String completeUsername = getRiotCompleteUsernameFromOptions(event, "add");

            if (registeredUser != null) {
                response = "**Error:** You already linked an account to Caupanharm (" + registeredUser.getRiotUsername() + ")";
            } else {
                RiotAPIUser riotAPIUser = riotAPIController.getUserFromUsername(completeUsername);
                if (riotAPIUser.getPuuid() == null) {
                    response = "**Error: **This Riot account does not exist";
                } else if (userController.getUser("riotPuuid", riotAPIUser.getPuuid()) != null) {
                    response = "**Error:** This Riot account is already linked to another user\nIf you think this is a mistake, please contact an admin";
                } else {
                    userController.insertUser(discordId, riotAPIUser.getPuuid(), riotAPIUser.getFullName());
                    response = "Account " + riotAPIUser.getFullName() + " is now linked to Caupanharm";
                }
            }

            // CASE /link edit
        } else if (event.getOption("edit").isPresent()) {
            String completeUsername = getRiotCompleteUsernameFromOptions(event, "edit");

            if (registeredUser == null) {
                response = "**Error:** You have not linked your Riot account to Caupanharm yet\nTry using */link add* instead";
            } else {
                RiotAPIUser riotAPIUser = riotAPIController.getUserFromUsername(completeUsername);
                CaupanharmUser checkedUser = userController.getUser("riotPuuid", riotAPIUser.getPuuid());
                if (checkedUser != null) {
                    response = (Objects.equals(checkedUser.getDiscordId(), discordId)) ?
                            "**Error:** You are already linked to this Riot account" :
                            "**Error:** This Riot account is already linked to another user\nIf you think this is a mistake, please contact an admin";
                } else if (riotAPIUser.getPuuid() == null) {
                    response = "**Error: **This Riot account does not exist";
                } else {
                    userController.updateUser("discordId", discordId, "riotUsername", riotAPIUser.getFullName());
                    userController.updateUser("discordId", discordId, "riotPuuid", riotAPIUser.getPuuid());
                    response = "Your linked account was updated from " + registeredUser.getRiotUsername() + " to " + riotAPIUser.getFullName();
                }

            }

            // CASE /link remove
        } else if (event.getOption("remove").isPresent()) {
            if (registeredUser == null) {
                response = "**Error:** You didn't link your Riot account to Caupanharm yet\nTry using */link add*";
            } else {
                userController.deleteUser("discordId", event.getInteraction().getUser().getId().asString());
                response = "Your account (" + registeredUser.getRiotUsername() + ") was successfully deleted from Caupanharm";
            }

            // CASE /link check
        } else {
            response = (registeredUser != null) ?
                    "You are currently linked to " + registeredUser.getRiotUsername() :
                    "You didn't link your Riot account to Caupanharm yet\nTry using */link add*";
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

    private String getRiotCompleteUsernameFromOptions(ChatInputInteractionEvent event, String subcommandOption) {
        String username = getSubcommandValue(event, subcommandOption, "username");
        String tagline = getSubcommandValue(event, subcommandOption, "tagline");
        return username + "#" + tagline;
    }
}