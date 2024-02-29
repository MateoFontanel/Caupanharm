package perso.discordbots.caupanharm.commands.Val;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.ResponseBuilder;
import perso.discordbots.caupanharm.commands.SlashCommand;
import perso.discordbots.caupanharm.controllers.RiotAPIController;
import perso.discordbots.caupanharm.controllers.TeamController;
import perso.discordbots.caupanharm.controllers.UserController;
import perso.discordbots.caupanharm.models.CaupanharmTeam;

import perso.discordbots.caupanharm.models.CaupanharmUser;
import reactor.core.publisher.Mono;


@Component
public class TeamCommand implements SlashCommand {
    private final static Logger logger = LoggerFactory.getLogger(TeamCommand.class);

    @Autowired
    UserController userController;
    @Autowired
    RiotAPIController riotAPIController;
    @Autowired
    TeamController teamController;

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
            return ResponseBuilder.build(event, "**Error**: You must have linked your Riot name to Caupanharm to perform this action.\nTry using */link add*", true, false);

        switch (option) {
            case "create":
                // Assert the user isn't already in a team
                if (requestingUser.getTeam() != null)
                    return ResponseBuilder.build(event, "**Error**: You are already in a team", true, false);

                // Assert the team doesn't already exist
                String teamToCreate = getStringFromOption(event, option, "name");
                CaupanharmTeam requestedTeam = teamController.getTeamByName(teamToCreate);
                if (requestedTeam != null)
                    return ResponseBuilder.build(event, "**Error**: A team with that name already exists", true, false);

                // Create team and update db
                CaupanharmTeam newTeam = new CaupanharmTeam(teamToCreate, requestingUserId);
                teamController.insertTeam(newTeam);
                userController.updateUser("discordId", requestingUserId, "team", newTeam.getName());
                return ResponseBuilder.build(event, String.format("%s has created team **%s**", "<@"+requestingUserId+">", newTeam.getName()), false, true);


            case "add":
                // Assert the requesting user is already in a team
                if (requestingUser.getTeam() == null)
                    return ResponseBuilder.build(event, "**Error**: You must be in a team to perform this action", true, false);

                // Assert the requesting user has permissions to add
                CaupanharmTeam teamToAddIn = teamController.getTeamByName(requestingUser.getTeam());
                if (!(teamToAddIn.getTier2members().contains(requestingUserId)) && !(teamToAddIn.getTier1members().contains(requestingUserId)))
                    return ResponseBuilder.build(event, "**Error**: You do not have permission to perform this action", true, false);

                // Assert the user to add has linked their Riot name to Caupanharm
                CaupanharmUser userToAdd = userController.getUser("discordId", getUserFromOption(event, option, "user").getId().asString());
                if (userToAdd == null)
                    return ResponseBuilder.build(event, "**Error**: This user has not linked their Riot name to Caupanharm yet", true, false);

                // Assert the user to add isn't already in a team
                if (userToAdd.getTeam() != null)
                    return ResponseBuilder.build(event, "**Error**: This user is already in a team", true, false);

                // Add the new team member
                teamToAddIn.addMember(userToAdd.getDiscordId());
                teamController.updateTeam("name", teamToAddIn.getName(), "members", teamToAddIn.getMembers());
                //add team to user in users collection
                userController.updateUser("discordId", userController.getUser("discordId", userToAdd.getDiscordId()).getDiscordId(), "team", teamToAddIn.getName());
                return ResponseBuilder.build(event, String.format("**%s** is now part of team **%s**", "<@" + userToAdd.getDiscordId() + ">", teamToAddIn.getName()), false, true);


            case "remove":
                // Assert the requesting user is already in a team
                if (requestingUser.getTeam() == null)
                    return ResponseBuilder.build(event, "**Error**: You must be in a team to perform this action", true, false);

                // Assert the requesting user has permissions to remove (tier 1 or 2)
                CaupanharmTeam teamToRemoveFrom = teamController.getTeamByName(requestingUser.getTeam());
                if (!(teamToRemoveFrom.getTier2members().contains(requestingUserId)) && !(teamToRemoveFrom.getTier1members().contains(requestingUserId)))
                    return ResponseBuilder.build(event, "**Error**: You do not have permission to perform this action", true, false);

                // Assert the requested user is in team
                User userToRemove = getUserFromOption(event, option, "user");
                if (!(teamToRemoveFrom.getMembers().contains(userToRemove.getId().asString())))
                    return ResponseBuilder.build(event, "**Error**: This user is not in your team", true, false);


                // Assert the requested user is of a lower tier
                if (!(teamToRemoveFrom.hasSuperiority(requestingUserId, userToRemove.getId().asString()))) {
                    return ResponseBuilder.build(event, "**Error**: You do not have permission to kick this team member", true, false);
                }

                //remove user from team in teams collection
                teamToRemoveFrom.removeMember(userToRemove.getId().asString());
                teamController.replaceTeam(teamToRemoveFrom.getName(), teamToRemoveFrom);
                //remove team name from user in users collection
                userController.updateUser("discordId", userController.getUser("discordId", userToRemove.getId().asString()).getDiscordId(), "team", null);
                return ResponseBuilder.build(event, String.format("**%s** removed %s from team **%s**", "<@"+ requestingUser.getDiscordId()+">", userToRemove.getMention(), teamToRemoveFrom.getName()), false, true);


            case "disband":
                String teamNameVerification = getStringFromOption(event, option, "name");
                CaupanharmTeam teamToRemove = teamController.getTeamByName(requestingUser.getTeam());
                // ASSERT user is in a team
                if(teamToRemove == null)
                    return ResponseBuilder.build(event, "**Error:** You must be in a team to perform this action", true, false);


                // ASSERT user copied correctly their team name
                if (!teamNameVerification.equals(teamToRemove.getName()))
                    return ResponseBuilder.build(event, "**Error**: Your team name doesn't match with input", true, false);

                // ASSERT user is in the team as max tier member
                if (!(teamToRemove.isHigherTier(requestingUserId)))
                    return ResponseBuilder.build(event, "**Error**: You do not have permission to perform this action", true, false);

                // Remove team from teams collection
                teamController.deleteTeam("name", teamToRemove.getName());

                // TODO DO THAT FOR EVERY MEMBER
                // Remove team name from user in users collection
                userController.updateUsers("team", teamToRemove.getName(), "team", null);

                return ResponseBuilder.build(event, String.format("%s has disbanded team %s", "<@" + requestingUserId + ">", teamToRemove.getName()), false, true);

            case "check":
                CaupanharmUser user = userController.getUser("discordId",requestingUserId);

                if(user.getTeam() == null)
                    return ResponseBuilder.build(event, "You are not currently in a team", false, true);

                CaupanharmTeam team = teamController.getTeamByName(user.getTeam());
                return ResponseBuilder.buildEmbed(event, team.fetchAndFormatTeamEmbed(), false);
        }
        return ResponseBuilder.build(event, "Internal server error", true, false);
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

}
