package perso.discordbots.caupanharm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Controller
public class ChannelController {
    private final static Logger logger = LoggerFactory.getLogger(ChannelController.class);
    private final static File channelsFile = new File("./src/main/resources/channels.json");

    private final static HashMap<String, String> games = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    HashMap<String, String> channels;

    public ChannelController() throws IOException {
        games.put("VAL", "VALORANT");
        games.put("LOL", "League Of Legends");
        try {
            //noinspection unchecked
            channels = mapper.readValue(channelsFile, HashMap.class);
            logger.info("Retrieved authorized channels list");
        } catch (Exception e) {
            channels = new HashMap<>();
            mapper.writeValue(channelsFile, channels);
            logger.info("Created the list of authorized channels");
        }
    }

    public String setChannel(String channelId, String game) {
        String channelGame = channels.get(channelId);

        switch (game) {
            case "VAL":
            case "LOL":
                if (!channels.containsKey(channelId)) { // First case, the channel doesn't monitor any game so far
                    channels.put(channelId, game);
                    logger.info("Channel "+channelId+" added to "+game+" monitoring");
                    if (write(channelsFile, channels))
                        return "Caupanharm will now start listening to " + games.get(game) + " commands in this channel";
                } else if (!Objects.equals(channels.get(channelId), game)) { // Second case, the channel already monitor another game
                    channels.replace(channelId, game);
                    logger.info("Channel "+channelId+" added to "+game+" monitoring");
                    if (write(channelsFile, channels))
                        return "Caupanharm will now start listening to " + games.get(game) + " commands in this channel";
                } else { // Last case, the channel already monitors the given game
                    return "**Error**: Caupanharm already monitors commands for " + games.get(game) + " in this channel";
                }

            default: // Case None/NULL
                if (channels.containsKey(channelId)) {
                    channels.remove(channelId);
                    logger.info("Channel "+channelId+" removed from monitoring");
                    if (write(channelsFile, channels))
                        return "Caupanharm won't listen to further game commands in this channel";
                } else {
                    return "**Error**: Caupanharm already doesn't listen to any game's commands in this channel";
                }
        }

        return "Internal error, please contact Herrahan";
    }

    public boolean getAuthorization(String channelId, String game) {
        // TODO
        return false;
    }

    private boolean write(File file, Object data) {
        try {
            mapper.writeValue(file, data);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
