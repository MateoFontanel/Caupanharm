package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.UserInteractionEvent;
import reactor.core.publisher.Mono;

public interface UserCommand extends Command<UserInteractionEvent> {

    String getName();

    Mono<Void> handle(UserInteractionEvent event);
}
