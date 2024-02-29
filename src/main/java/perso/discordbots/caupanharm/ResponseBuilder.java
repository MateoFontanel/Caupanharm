package perso.discordbots.caupanharm;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.Embed;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import discord4j.rest.util.AllowedMentions;

import java.util.ArrayList;
import java.util.List;

public class ResponseBuilder {

    public ResponseBuilder(){

    }

    public static InteractionApplicationCommandCallbackReplyMono build(ChatInputInteractionEvent event, String content, boolean ephemeral, boolean pingable) {
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

    public static InteractionApplicationCommandCallbackReplyMono buildEmbed(ChatInputInteractionEvent event, EmbedCreateSpec embed, boolean ephemeral){
        return event.reply()
                .withEphemeral(ephemeral)
                .withEmbeds(embed);
    }
    public static InteractionApplicationCommandCallbackReplyMono buildEmbeds(ChatInputInteractionEvent event, List<EmbedCreateSpec> embeds, boolean ephemeral){
        return event.reply()
                .withEphemeral(ephemeral)
                .withEmbeds(embeds);
    }

}
