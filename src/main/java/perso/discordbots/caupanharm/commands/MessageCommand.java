package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.MessageInteractionEvent;
import discord4j.rest.util.PermissionSet;
import reactor.core.publisher.Mono;

public interface MessageCommand extends Command<MessageInteractionEvent> {

    String getName();

    default PermissionSet requiredPermissions() {
        return Command.super.requiredPermissions();
    }
    Mono<Void> handle(MessageInteractionEvent event);

}