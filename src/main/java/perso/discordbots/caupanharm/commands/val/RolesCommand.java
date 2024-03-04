package perso.discordbots.caupanharm.commands.val;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.entity.User;
import discord4j.rest.util.PermissionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.controllers.EmojiController;
import perso.discordbots.caupanharm.controllers.UserController;
import perso.discordbots.caupanharm.models.CaupanharmUser;
import perso.discordbots.caupanharm.models.ValRoles;
import perso.discordbots.caupanharm.util.ResponseBuilder;
import perso.discordbots.caupanharm.commands.ButtonComponent;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class RolesCommand implements ButtonComponent {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    List<String> buttonIds = new ArrayList<>(List.of("get-roles", "set-controller", "set-duelist", "set-initiator", "set-sentinel"));

    @Autowired
    UserController userController;

    @Autowired
    EmojiController emojiController;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public List<String> getButtonIds() {
        return buttonIds;
    }

    @Override
    public PermissionSet requiredPermissions() {
        return ButtonComponent.super.requiredPermissions();
    }

    @Override
    public Mono<Void> handle(ButtonInteractionEvent event) {
        User user = event.getInteraction().getUser();
        CaupanharmUser caupanharmUser = userController.getUser("discordId", user.getId().asString());
        List<ValRoles> userRoles = caupanharmUser.getRoles();
        String requestedRoleString = event.getCustomId().split("-")[1]; // Example button name : add-duelist

        if ("get-roles".equals(event.getCustomId())) {
            if (userRoles.size() == 0)
                return ResponseBuilder.buildReply(event, "Tu ne t'es pas encore assigné de rôle", null, true, false);
            StringBuilder response = new StringBuilder("Vos rôles : ");
            Iterator<ValRoles> it = userRoles.listIterator();
            while (it.hasNext()) {
                ValRoles role = it.next();
                response.append(emojiController.formatEmojiWith1Value("<:%s:%s> %s", role.getName(), role.getFormattedName()));
                if (it.hasNext()) response.append(", ");
            }
            return ResponseBuilder.buildReply(event, response.toString(), null, true, false);
        }

        // User clicked on a role button
        for (ValRoles role : ValRoles.values()) {
            if (role.getName().equalsIgnoreCase(requestedRoleString)) { // Get the ValRoles constant associated with the requested role string
                if (userRoles.contains(role)) {
                    // Remove role
                    userRoles.remove(role);
                    userController.updateUser("discordId", caupanharmUser.getDiscordId(), "roles", userRoles);
                    logger.info(String.format("Removed role \"%s\" from user %s", role.getName(), user.getUsername()));
                    return ResponseBuilder.buildReply(event, String.format("Role %s removed", role.getFormattedName()), null, true, false);
                } else {
                    // Add role
                    userRoles.add(role);
                    userRoles.sort(Comparator.comparing(Enum::name)); // Sort enum constants alphabetically
                    userController.updateUser("discordId", caupanharmUser.getDiscordId(), "roles", userRoles);
                    logger.info(String.format("Added role \"%s\" to user %s", role.getName(), user.getUsername()));
                    return ResponseBuilder.buildReply(event, String.format("Role %s added", role.getFormattedName()), null, true, false);
                }
            }
        }


        return Mono.empty(); // should never be reached as customId will always be one of the cases (mandatory on client side)


    }


}
