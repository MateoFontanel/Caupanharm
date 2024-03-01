package perso.discordbots.caupanharm.listeners;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.interaction.MessageInteractionEvent;
import discord4j.core.event.domain.interaction.UserInteractionEvent;
import discord4j.core.object.entity.Member;
import discord4j.rest.util.Permission;
import discord4j.rest.util.PermissionSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;
import perso.discordbots.caupanharm.commands.Command;
import perso.discordbots.caupanharm.commands.MessageCommand;
import perso.discordbots.caupanharm.commands.SlashCommand;
import perso.discordbots.caupanharm.commands.UserCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CommandListener {

    @Autowired
    public CommandListener(List<SlashCommand> slashCommands, List<MessageCommand> messageCommands, List<UserCommand> userCommands, GatewayDiscordClient client) {
        client.on(ChatInputInteractionEvent.class, event -> handleCommand(event, slashCommands)).subscribe();
        client.on(MessageInteractionEvent.class, event -> handleCommand(event, messageCommands)).subscribe();
        client.on(UserInteractionEvent.class, event -> handleCommand(event, userCommands)).subscribe();
    }

    public <T extends ApplicationCommandInteractionEvent> Mono<Void> handleCommand(T event, List<? extends Command<T>> commands) {
        return Flux.fromIterable(commands)
                .filter(command -> command.getName().equals(event.getCommandName()))
                .next()
                .flatMap(command -> {
                    if (!hasPermission(command, event)) {
                        return event.reply("You do not have permission for this command.").withEphemeral(true);
                    }
                    return command.handle(event)
                            .onErrorResume(error ->  event.reply(error.getMessage()).withEphemeral(true));
                });
    }


    private boolean hasPermission(Command<? extends ApplicationCommandInteractionEvent> command, ApplicationCommandInteractionEvent event) {
        if (command.requiredPermissions() == null || command.requiredPermissions().isEmpty())
            return true;

        // If this command wasn't executed in a guild, we can't check permissions
        if (Boolean.FALSE.equals(event.getInteraction().getGuild().hasElement().block())) {
            return false;
        }

        PermissionSet memberPermissions = event.getInteraction().getMember().map(Member::getBasePermissions).get().block();

        for (Permission permission : command.requiredPermissions()) {
            if (!memberPermissions.contains(permission))
                return false;
        }

        // User has all required permissions
        return true;
    }
}