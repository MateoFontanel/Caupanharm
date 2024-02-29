package perso.discordbots.caupanharm.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import perso.discordbots.caupanharm.controllers.RiotAPIController;
import perso.discordbots.caupanharm.controllers.UserController;
import perso.discordbots.caupanharm.models.CaupanharmUser;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameTracker extends Thread {
    private final Logger logger = LoggerFactory.getLogger(GameTracker.class);
    private List<String> ingameUsersIds = new ArrayList<>();

    @Autowired
    UserController userController;

    @Autowired
    RiotAPIController riotAPIController;

    @EventListener(ApplicationReadyEvent.class) // Starts the thread once Spring Boot has been initialized
    public void run() {
        riotAPIController.setDebug(false); // TODO logs de l'api riot désactivé pour la lisibilité des logs de ce thread
        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                List<CaupanharmUser> trackedUsers = userController.getUsers("tracked",Boolean.TRUE);
                for(CaupanharmUser user : trackedUsers){
                    // take into consideration Riot's API rates:
                    //      max 20 requests per second = max 20 users, need to delay calls to allow more users, for example 15 users being called then 15 others the next second, etc.
                    //      max 100 requests per 120 seconds = approx 1 request every 830ms on average per tracked user ; as a first step, I will round it to 1s as this does not take into account calls made by other classes of the project. Later on, I should TODO create a rate controller

                    boolean userIsInGame = riotAPIController.getActiveLeagueGame(user);
                    if(userIsInGame && !(ingameUsersIds.contains(user.getRiotId()))){
                        logger.info(user.getRiotUsername() + " entered a game.");
                        ingameUsersIds.add(user.getRiotId());
                    }else if(!userIsInGame && ingameUsersIds.contains(user.getRiotId())){
                        logger.info(user.getRiotUsername() + "'s game ended.");
                        ingameUsersIds.remove(user.getRiotId());
                    }

                    Thread.sleep(500);
                }

                if(ingameUsersIds.size() == 0){
                    Thread.sleep(5000);
                }else{
                    Thread.sleep(5000/ingameUsersIds.size());
                }


            }
            catch (InterruptedException ex)
            {
                Thread.currentThread().interrupt();

            }
        }
    }

}
