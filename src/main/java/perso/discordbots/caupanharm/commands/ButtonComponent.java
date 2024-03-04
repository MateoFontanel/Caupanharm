package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.rest.util.PermissionSet;
import reactor.core.publisher.Mono;

import java.util.List;

@SuppressWarnings("unused")
public interface ButtonComponent extends Component<ButtonInteractionEvent> {
    String getName();

    List<String> getButtonIds();

    default PermissionSet requiredPermissions() {
        return Component.super.requiredPermissions();
    }

    Mono<Void> handle(ButtonInteractionEvent event);
}
