package perso.discordbots.caupanharm.commands.general;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.event.domain.interaction.MessageInteractionEvent;
import discord4j.core.object.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import perso.discordbots.caupanharm.commands.MessageCommand;
import reactor.core.publisher.Mono;

public class GngngnCommand implements MessageCommand {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public String getName() {
        return "gngngn";
    }

    @Override
    public Mono<Void> handle(MessageInteractionEvent event) {
        System.out.println("gngngn");
        logger.info("gngngn");
        logger.info(event.getInteraction().getMessage().get().getContent());
        if(event.getInteraction().getMessage().isPresent()){
            Message message = event.getInteraction().getMessage().get();
        }


        return null;
    }
}
