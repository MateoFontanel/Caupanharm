package perso.discordbots.caupanharm.commands.general;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.commands.SlashCommand;
import perso.discordbots.caupanharm.controllers.ChannelController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Component
public class ConfigCommand implements SlashCommand {
    private final static Logger logger = LoggerFactory.getLogger(ConfigCommand.class);
    @Value("#{'${admin_discord_id}'.split(',')}")
    ArrayList<String> authorized_ids;

    @Autowired
    ChannelController channelController;

    @Override
    public String getName() {
        return "config";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String response = " ";

        if (!authorized_ids.contains(event.getInteraction().getUser().getId().asString())) {
            response = "**Error: **You do not have permission to execute this command.";
        } else {
            if ("setgame".equals(event.getOptions().get(0).getName())) {
                String game = event.getOption("setgame")
                        .flatMap(ApplicationCommandInteractionOption::getValue)
                        .map(ApplicationCommandInteractionOptionValue::asString)
                        .get();
                response = channelController.setChannel(event.getInteraction().getChannelId().asString(), game);
            } else {
                response = "Internal error";
            }
        }


        return event.reply().withEphemeral(true).withContent(response);

    }

}
