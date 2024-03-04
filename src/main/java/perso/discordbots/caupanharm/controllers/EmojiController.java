package perso.discordbots.caupanharm.controllers;

import discord4j.discordjson.Id;
import discord4j.discordjson.json.EmojiData;
import discord4j.rest.RestClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class EmojiController {
    private final static Logger logger = LoggerFactory.getLogger(EmojiController.class);

    @Autowired
    RestClient client;

    Long guildId;

    static Map<String, String> emojis;

    public EmojiController(String guildId) {
        this.guildId = Long.parseLong(guildId);
    }

    @PostConstruct
    public void fetchEmojis() {
        Flux<EmojiData> flux = client.getEmojiService().getGuildEmojis(guildId);
        emojis = flux
                .filter(emojiData -> emojiData.id().isPresent() && emojiData.name().isPresent())
                .collectMap(
                        emojiData -> emojiData.name().get(),
                        emojiData -> emojiData.id().get().asString())
                .block();
    }


    public Map<String, String> getEmojis() {
        return emojis;
    }

    public String getEmojiByName(String name){
        return emojis.get(name);
    }

    public String formatEmoji(String emojiName){
        return String.format("<:%s:%s>", emojiName, getEmojiByName(emojiName));
    }


    public String formatEmojiWith1Value(String format, String emojiName, String value){
        return String.format(format, emojiName, getEmojiByName(emojiName) , value);
    }

    public String formatEmojiWith2Value(String format, String emojiName, String value1, String value2){
        return String.format(format, emojiName, getEmojiByName(emojiName) , value1, value2);
    }
}
