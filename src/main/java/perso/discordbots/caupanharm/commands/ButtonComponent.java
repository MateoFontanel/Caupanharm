package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.rest.util.PermissionSet;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ButtonComponent extends Component<ButtonInteractionEvent> {
    String getName();

    List<String> getButtonIds();

    default PermissionSet requiredPermissions() {
        return Component.super.requiredPermissions();
    }

    Mono<Message> handle(ButtonInteractionEvent event);
}
