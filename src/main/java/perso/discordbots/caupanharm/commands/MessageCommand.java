package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.interaction.MessageInteractionEvent;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;

public interface MessageCommand extends Command<MessageInteractionEvent> {

    String getName();

    Mono<Void> handle(MessageInteractionEvent event);

}