package perso.discordbots.caupanharm;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import discord4j.rest.util.AllowedMentions;

public class ResponseBuilder {

    public ResponseBuilder(){

    }

    public static InteractionApplicationCommandCallbackReplyMono build(ChatInputInteractionEvent event, String content, Boolean ephemeral, Boolean pingable) {
        if (pingable) {
            return event.reply()
                    .withEphemeral(ephemeral)
                    .withContent(content);
        } else {
            return event.reply()
                    .withEphemeral(ephemeral)
                    .withAllowedMentions(AllowedMentions.builder().build())
                    .withContent(content);
        }
    }

}
