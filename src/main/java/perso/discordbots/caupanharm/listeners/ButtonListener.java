package perso.discordbots.caupanharm.listeners;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.commands.Val.RolesCommand;
import reactor.core.publisher.Mono;

@Component
public class ButtonListener {

    @Autowired
    RolesCommand rolesCommand;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ButtonListener(GatewayDiscordClient client) {
        client.on(ButtonInteractionEvent.class, this::handleCommand).subscribe();

    }

    public <T extends ApplicationCommandInteractionEvent> Mono<Void> handleCommand(ButtonInteractionEvent event) {
        // Check to which command/class the pressed button belongs
        String buttonId = event.getCustomId();
        if (rolesCommand.getButtonIds().contains(buttonId)) {
            return rolesCommand.handle(event);
        } else {
            // Ignore it
            return Mono.empty();
        }
    }
}