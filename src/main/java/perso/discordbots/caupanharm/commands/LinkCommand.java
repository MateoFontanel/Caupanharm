package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.controllers.UserController;
import reactor.core.publisher.Mono;

@Component
public class LinkCommand implements SlashCommand {

    @Autowired
    UserController userController;

    @Override
    public String getName() {
        return "link";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String response = "todo remove and initialize in ifelse";
        if(event.getOption("add").isPresent()){
            String username = getSubcommandValue(event, "add", "username");
            String tagline = getSubcommandValue(event, "add", "tagline");
            userController.insertUser(event.getInteraction().getUser().getId().asString(),"todo",username+"#"+tagline);

        } else if (event.getOption("edit").isPresent()) {
            String username = getSubcommandValue(event, "edit", "username");
            String tagline = getSubcommandValue(event, "edit", "tagline");
            userController.updateUser("discordId",event.getInteraction().getUser().getId().asString(),"riotUsername",username+"#"+tagline);

        }else{
            userController.deleteUser("discordId",event.getInteraction().getUser().getId().asString());
        }

        return event.reply()
                .withEphemeral(true)
                .withContent(response);
    }

    private String getSubcommandValue(ChatInputInteractionEvent event, String subcommandOption, String subcommandValue){
        return event.getOption(subcommandOption).get().getOption(subcommandValue)
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
    }
}