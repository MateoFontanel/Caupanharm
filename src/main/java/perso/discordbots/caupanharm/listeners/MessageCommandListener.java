package perso.discordbots.caupanharm.listeners;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.interaction.MessageInteractionEvent;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.commands.MessageCommand;
import perso.discordbots.caupanharm.commands.SlashCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Component
public class MessageCommandListener {

    private final Collection<MessageCommand> messageCommands;

    public MessageCommandListener(List<MessageCommand> messageCommands, GatewayDiscordClient client) {
        this.messageCommands = messageCommands;

        client.on(MessageInteractionEvent.class, this::handle).subscribe();
    }


    public Mono<Void> handle(MessageInteractionEvent event) {
        //Convert our list to a flux that we can iterate through
        return Flux.fromIterable(messageCommands)
                //Filter out all commands that don't match the name this event is for
                .filter(command -> command.getName().equals(event.getCommandName()))
                //Get the first (and only) item in the flux that matches our filter
                .next()
                //Have our command class handle all logic related to its specific command.
                .flatMap(command -> command.handle(event));
    }
}