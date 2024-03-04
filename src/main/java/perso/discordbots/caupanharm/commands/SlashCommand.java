package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.rest.util.PermissionSet;
import reactor.core.publisher.Mono;

/**
 * A simple interface defining our slash command class contract.
 *  a getName() method to provide the case-sensitive name of the command.
 *  and a handle() method which will house all the logic for processing each command.
 */
public interface SlashCommand extends Command<ChatInputInteractionEvent>{

    String getName();


    default PermissionSet requiredPermissions() {
        return Command.super.requiredPermissions();
    }

    Mono<Void> handle(ChatInputInteractionEvent event);

}