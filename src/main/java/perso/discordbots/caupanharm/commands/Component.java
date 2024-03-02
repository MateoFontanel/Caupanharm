package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.event.domain.interaction.ComponentInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.rest.util.PermissionSet;
import reactor.core.publisher.Mono;

public interface Component<T extends ComponentInteractionEvent> {

    String getName();

    default PermissionSet requiredPermissions() {
        return PermissionSet.none();
    }
    Mono<Message> handle(T event);
}
