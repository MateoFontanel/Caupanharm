package perso.discordbots.caupanharm.commands.Val;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.controllers.EmojiController;
import perso.discordbots.caupanharm.models.ValRoles;
import perso.discordbots.caupanharm.models.api.HenrikUser;
import perso.discordbots.caupanharm.util.ResponseBuilder;
import perso.discordbots.caupanharm.commands.SlashCommand;
import perso.discordbots.caupanharm.controllers.APIController;
import perso.discordbots.caupanharm.controllers.TeamController;
import perso.discordbots.caupanharm.controllers.UserController;
import perso.discordbots.caupanharm.models.CaupanharmTeam;

import perso.discordbots.caupanharm.models.CaupanharmUser;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.List;


@Component
public class TeamCommand implements SlashCommand {
    private final static Logger logger = LoggerFactory.getLogger(TeamCommand.class);

    @Autowired
    UserController userController;

    @Autowired
    APIController apiController;
    @Autowired
    TeamController teamController;

    @Autowired
    EmojiController emojiController;

    @Override
    public String getName() {
        return "team";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String requestingUserId = event.getInteraction().getUser().getId().asString();
        String option = event.getOptions().get(0).getName();

        CaupanharmUser requestingUser = userController.getUser("discordId", requestingUserId);
        if (requestingUser == null) // Check that the user is registered in Caupanharm
            return ResponseBuilder.buildReply(event, "**Error**: You must have linked your Riot name to Caupanharm to perform this action.\nTry using */link add*", null, true, false);

        switch (option) {
            case "create":
                // Assert the user isn't already in a team
                if (requestingUser.getTeam() != null)
                    return ResponseBuilder.buildReply(event, "**Error**: You are already in a team", null, true, false);

                // Assert the team doesn't already exist
                String teamToCreate = getStringFromOption(event, option, "name");
                CaupanharmTeam requestedTeam = teamController.getTeamByName(teamToCreate);
                if (requestedTeam != null)
                    return ResponseBuilder.buildReply(event, "**Error**: A team with that name already exists", null, true, false);

                // Create team and update db
                CaupanharmTeam newTeam = new CaupanharmTeam(teamToCreate, requestingUser);
                teamController.insertTeam(newTeam);
                userController.updateUser("discordId", requestingUserId, "team", newTeam.getName());
                return ResponseBuilder.buildReply(event, String.format("%s has created team **%s**", "<@" + requestingUserId + ">", newTeam.getName()), null, false, true);


            case "add":
                // Assert the requesting user is already in a team
                if (requestingUser.getTeam() == null)
                    return ResponseBuilder.buildReply(event, "**Error**: You must be in a team to perform this action", null, true, false);

                // Assert the requesting user has permissions to add
                CaupanharmTeam teamToAddIn = teamController.getTeamByName(requestingUser.getTeam());
                if (!(teamToAddIn.getTier2members().contains(requestingUserId)) && !(teamToAddIn.getTier1members().contains(requestingUserId)))
                    return ResponseBuilder.buildReply(event, "**Error**: You do not have permission to perform this action", null, true, false);

                // Assert the user to add has linked their Riot name to Caupanharm
                User userToAdd = getUserFromOption(event, option, "user");
                CaupanharmUser caupanharmUserToAdd = userController.getUser("discordId", userToAdd.getId().asString());

                // Assert the user to add isn't already in a team
                if (caupanharmUserToAdd.getTeam() != null)
                    return ResponseBuilder.buildReply(event, "**Error**: This user is already in a team", null, true, false);

                // Add the new team member
                teamToAddIn.addMember(caupanharmUserToAdd);
                teamController.updateTeam("name", teamToAddIn.getName(), "members", teamToAddIn.getMembers());
                //add team to user in users collection
                userController.updateUser("discordId", userController.getUser("discordId", caupanharmUserToAdd.getDiscordId()).getDiscordId(), "team", teamToAddIn.getName());
                return ResponseBuilder.buildReply(event, String.format("**%s** is now part of team **%s**", "<@" + caupanharmUserToAdd.getDiscordId() + ">", teamToAddIn.getName()), null, false, true);


            case "remove":
                // Assert the requesting user is already in a team
                if (requestingUser.getTeam() == null)
                    return ResponseBuilder.buildReply(event, "**Error**: You must be in a team to perform this action", null, true, false);

                // Assert the requesting user has permissions to remove (tier 1 or 2)
                CaupanharmTeam teamToRemoveFrom = teamController.getTeamByName(requestingUser.getTeam());
                if (!(teamToRemoveFrom.getTier2members().contains(requestingUser)) && !(teamToRemoveFrom.getTier1members().contains(requestingUser)))
                    return ResponseBuilder.buildReply(event, "**Error**: You do not have permission to perform this action", null, true, false);

                // Assert the requested user is in team
                User userToRemove = getUserFromOption(event, option, "user");
                CaupanharmUser caupanharmUserToRemove = userController.getUser("discordId", userToRemove.getId().asString());

                if (!(teamToRemoveFrom.getMembers().contains(caupanharmUserToRemove)))
                    return ResponseBuilder.buildReply(event, "**Error**: This user is not in your team", null, true, false);


                // Assert the requested user is of a lower tier
                if (!(teamToRemoveFrom.hasSuperiority(requestingUserId, userToRemove.getId().asString()))) {
                    return ResponseBuilder.buildReply(event, "**Error**: You do not have permission to kick this team member", null, true, false);
                }

                //remove user from team in teams collection
                teamToRemoveFrom.removeMember(caupanharmUserToRemove);
                teamController.replaceTeam(teamToRemoveFrom.getName(), teamToRemoveFrom);
                //remove team name from user in users collection
                userController.updateUser("discordId", userController.getUser("discordId", userToRemove.getId().asString()).getDiscordId(), "team", null);
                return ResponseBuilder.buildReply(event, String.format("**%s** removed %s from team **%s**", "<@" + requestingUser.getDiscordId() + ">", userToRemove.getMention(), teamToRemoveFrom.getName()), null, false, true);


            case "disband":
                String teamNameVerification = getStringFromOption(event, option, "name");
                CaupanharmTeam teamToRemove = teamController.getTeamByName(requestingUser.getTeam());
                // ASSERT user is in a team
                if (teamToRemove == null)
                    return ResponseBuilder.buildReply(event, "**Error:** You must be in a team to perform this action", null, true, false);


                // ASSERT user copied correctly their team name
                if (!teamNameVerification.equals(teamToRemove.getName()))
                    return ResponseBuilder.buildReply(event, "**Error**: Your team name doesn't match with input", null, true, false);

                // ASSERT user is in the team as max tier member
                if (!(teamToRemove.isHigherTier(requestingUserId)))
                    return ResponseBuilder.buildReply(event, "**Error**: You do not have permission to perform this action", null, true, false);

                // Remove team from teams collection
                teamController.deleteTeam("name", teamToRemove.getName());

                // Remove team name from each user in users collection
                userController.updateUsers("team", teamToRemove.getName(), "team", null);

                return ResponseBuilder.buildReply(event, String.format("%s has disbanded team %s", "<@" + requestingUserId + ">", teamToRemove.getName()), null, false, true);

            case "check":
                CaupanharmUser user = userController.getUser("discordId", requestingUserId);

                if (user.getTeam() == null)
                    return ResponseBuilder.buildReply(event, "You are not currently in a team", null, false, true);

                CaupanharmTeam team = teamController.getTeamByName(user.getTeam());
                return ResponseBuilder.buildReply(event, null, List.of(fetchAndFormatTeamEmbed(team)), false, false);
        }
        return ResponseBuilder.buildReply(event, "Internal server error", null, true, false);
    }

    private String getStringFromOption(ChatInputInteractionEvent event, String option, String param) {
        return event.getOption(option).get().getOption(param)
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get(); //This is warning us that we didn't check if its present, we can ignore this on required options
    }

    private User getUserFromOption(ChatInputInteractionEvent event, String option, String param) {
        return event.getOption(option).get().getOption(param)
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asUser)
                .get().block(); //This is warning us that we didn't check if its present, we can ignore this on required options
    }

    public EmbedCreateSpec fetchAndFormatTeamEmbed(CaupanharmTeam team) {


        EmbedCreateSpec.Builder embedBuilder = EmbedCreateSpec.builder()
                .color(Color.YELLOW)
                .title(String.format("Team **%s**", team.getName()));

        StringBuilder col1 = new StringBuilder();
        StringBuilder col2 = new StringBuilder();
        StringBuilder col3 = new StringBuilder();
        Iterator<CaupanharmUser> it_users = userController.getUsersFromDiscordId(team.getMembers()).listIterator();
        while (it_users.hasNext()) {
            CaupanharmUser member = it_users.next();
            logger.info(apiController.getHenrikUser(member.getRiotUsername(), true).toString());


            col1.append(String.format("<@%s>", member.getDiscordId()));

            if(member.getRoles().size() == 0){
                col3.append("\u200b"); // Discord doesn't allow empty strings so this is a way to bypass that
            }else{
                Iterator<ValRoles> it_roles = member.getRoles().listIterator();
                while (it_roles.hasNext()) {
                    ValRoles role = it_roles.next();
                    col3.append(emojiController.formatEmoji(role.getEmojiName()));
                    if (it_roles.hasNext()) col3.append(" ");
                }
            }


            if (it_users.hasNext()) {
                col1.append("\n");
                col2.append("\n");
                col3.append("\n");
            }
        }

        embedBuilder.addField("__Membre__", col1.toString(), true);
        embedBuilder.addField("__Rang__", col2.toString(), true);
        embedBuilder.addField("__Rôles__", col3.toString(), true);

        return embedBuilder.build();
    }
}
