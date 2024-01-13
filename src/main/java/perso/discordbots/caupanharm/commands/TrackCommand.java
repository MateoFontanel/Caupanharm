package perso.discordbots.caupanharm.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.SpringBot;
import reactor.core.publisher.Mono;

@Component
public class TrackCommand implements SlashCommand{

    @Override
    public String getName() {
        return "track";
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        //System.out.println(riot_api_key);
        boolean checkStatus = event.getOption("etat").isPresent();
        boolean checkSummoner = event.getOption("summoner").isPresent();
        boolean checkChampion = event.getOption("champion").isPresent();

        if(checkStatus && !checkSummoner && !checkChampion){
            boolean etat = event.getOption("etat")
                    .flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asBoolean)
                    .get(); //This is warning us that we didn't check if its present, we can ignore this on required options

            String response;
            if(etat){
                // TODO envoyer signal start à tous les trackers
                response = "Tracking activé";
            }else{
                SpringBot.getTracker().interrupt();
                response = "Tracking désactivé";
            }

            return event.reply()
                    .withEphemeral(true)
                    .withContent(response);
        }

        if(!checkStatus && checkSummoner && checkChampion){
            String summoner = event.getOption("summoner")
                    .flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asString)
                    .get(); //This is warning us that we didn't check if its present, we can ignore this on required options

            String champion = event.getOption("champion")
                    .flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asString)
                    .get(); //This is warning us that we didn't check if its present, we can ignore this on required options

            SpringBot.setTracker(summoner, champion);

            return event.reply()
                    .withEphemeral(true)
                    .withContent("Starting tracking of "+summoner+" on "+champion);
        }

        // Cas où les paramètres d'entrées sont incorrects
        return event.reply()
                .withEphemeral(true)
                .withContent("Erreur");
    }
}
