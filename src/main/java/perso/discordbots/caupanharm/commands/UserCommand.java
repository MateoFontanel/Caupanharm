package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.UserInteractionEvent;
import discord4j.rest.util.PermissionSet;
import reactor.core.publisher.Mono;

public interface UserCommand extends Command<UserInteractionEvent> {

    String getName();

    default PermissionSet requiredPermissions() {
        return Command.super.requiredPermissions();
    }
    Mono<Void> handle(UserInteractionEvent event);
}
