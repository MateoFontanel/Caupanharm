package perso.discordbots.caupanharm.commands.general;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.MessageCreateSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.controllers.EmojiController;
import perso.discordbots.caupanharm.models.ValRoles;
import perso.discordbots.caupanharm.util.ResponseBuilder;
import perso.discordbots.caupanharm.commands.SlashCommand;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitCommand implements SlashCommand {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("#{'${admin_discord_id}'.split(',')}")
    ArrayList<String> authorized_ids;

    @Autowired
    EmojiController emojiController;

    @Override
    public String getName() {
        return "init";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        if (!authorized_ids.contains(event.getInteraction().getUser().getId().asString()))
            return ResponseBuilder.buildReply(event, "**Error: **You do not have permission to execute this command.", null, true, false);
        String message = event.getOption("message")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get(); //This is warning us that we didn't check if its present, we can ignore this on required options

        switch (message) {
            case "val_roles":
                Snowflake channelId = event.getInteraction().getChannelId();
                Button button_roles = Button.primary("get-roles", "Voir mes rôles");

                Button button_controller = Button.secondary("set-controller",
                        ReactionEmoji.of(Long.parseLong(emojiController.getEmojiByName(ValRoles.CONTROLLER.getName())), ValRoles.CONTROLLER.getName(), false),
                        ValRoles.CONTROLLER.getFormattedName());

                Button button_duelist = Button.secondary("set-duelist",
                        ReactionEmoji.of(Long.parseLong(emojiController.getEmojiByName(ValRoles.DUELIST.getName())), ValRoles.DUELIST.getName(), false),
                        ValRoles.DUELIST.getFormattedName());

                Button button_initiator = Button.secondary("set-initiator",
                        ReactionEmoji.of(Long.parseLong(emojiController.getEmojiByName(ValRoles.INITIATOR.getName())), ValRoles.INITIATOR.getName(), false),
                        ValRoles.INITIATOR.getFormattedName());

                Button button_sentinel = Button.secondary("set-sentinel",
                        ReactionEmoji.of(Long.parseLong(emojiController.getEmojiByName(ValRoles.SENTINEL.getName())), ValRoles.SENTINEL.getName(), false),
                        ValRoles.SENTINEL.getFormattedName());

                List<Button> buttons = new ArrayList<>(List.of(button_roles, button_controller, button_duelist, button_initiator, button_sentinel));

                event.getClient().rest().getChannelById(channelId).createMessage("Vous pouvez désormais définir vos rôles en jeu !").subscribe();
                event.getClient().getChannelById(channelId)
                        .ofType(GuildMessageChannel.class)
                        .flatMap(channel -> channel.createMessage(
                                        MessageCreateSpec.builder()
                                                // Buttons must be in action rows
                                                .addComponent(ActionRow.of(buttons))
                                                .build()
                                )
                        ).subscribe();

                break;
        }


        return ResponseBuilder.buildReply(event, "Message sent.", null, true, false);
    }
}
