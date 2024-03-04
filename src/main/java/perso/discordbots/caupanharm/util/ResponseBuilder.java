package perso.discordbots.caupanharm.util;

import discord4j.core.event.domain.interaction.DeferrableInteractionEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import discord4j.core.spec.InteractionReplyEditMono;
import discord4j.discordjson.possible.Possible;
import discord4j.rest.util.AllowedMentions;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public class ResponseBuilder {

    public ResponseBuilder() {

    }

    public static InteractionApplicationCommandCallbackReplyMono buildReply(DeferrableInteractionEvent event, String content, List<EmbedCreateSpec> embeds, boolean ephemeral, boolean pingable) {
        return event.reply()
                .withEphemeral(ephemeral)
                .withAllowedMentions((pingable) ? Possible.absent() : Possible.of(AllowedMentions.builder().build()))
                .withContent(content == null ? Possible.absent() : Possible.of(content))
                .withEmbeds(embeds == null ? Possible.absent() : Possible.of(embeds));
    }

    public static InteractionReplyEditMono buildDeferredReply(DeferrableInteractionEvent event, String content, List<EmbedCreateSpec> embeds, boolean pingable) {
        return event.editReply()
                .withAllowedMentions((pingable) ? Possible.of(Optional.empty()) : Possible.of(Optional.of(AllowedMentions.builder().build())))
                .withContent(content == null ? Possible.absent() : Possible.of(Optional.of(content)))
                .withEmbeds(embeds == null ? Possible.absent() : Possible.of(Optional.of(embeds)));
    }

}
