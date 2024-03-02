package perso.discordbots.caupanharm.listeners;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.*;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.rest.util.Permission;
import discord4j.rest.util.PermissionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.commands.Command;
import perso.discordbots.caupanharm.commands.MessageCommand;
import perso.discordbots.caupanharm.commands.SlashCommand;
import perso.discordbots.caupanharm.commands.UserCommand;
import perso.discordbots.caupanharm.commands.Val.RolesCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class ButtonListener {

    @Autowired
    RolesCommand rolesCommand;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ButtonListener(GatewayDiscordClient client) {
        client.on(ButtonInteractionEvent.class, event -> event.deferReply().then(handleCommand(event))).subscribe();

    }

    public <T extends ApplicationCommandInteractionEvent> Mono<Message> handleCommand(ButtonInteractionEvent event) {
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