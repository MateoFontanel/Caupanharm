package perso.discordbots.caupanharm;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tracker extends Thread{

    private String summoner, champion, url;
    public Tracker(String summoner, String champion){
        this.summoner = summoner;
        this.champion = champion;
        url = "https://www.leagueofgraphs.com/summoner/champions/"+champion.toLowerCase()+"/euw/"+summoner.split("#")[0]+"-"+summoner.split("#")[1]+"#";
    }

    public String getSummoner() {
        return summoner;
    }

    public void setSummoner(String summoner) {
        this.summoner = summoner;
        url = "https://www.leagueofgraphs.com/summoner/champions/"+champion.toLowerCase()+"/euw/"+summoner.split("#")[0]+"-"+summoner.split("#")[1]+"#";
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
        url = "https://www.leagueofgraphs.com/summoner/champions/"+champion.toLowerCase()+"/euw/"+summoner.split("#")[0]+"-"+summoner.split("#")[1]+"#";
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                String content = Jsoup.connect(url).get().text();
                if(content.contains("No match found")){
                    System.out.println("Champion non joué");
                }else{
                    List<String> words = new ArrayList<>(List.of(content.split(" ")));
                    int gamesPlayed = Integer.parseInt(words.get(989));
                    float winrate = Float.parseFloat(words.get(991).replace("%",""));
                    int globalRank = Integer.parseInt(words.get(993).replace("#","").replace(",",""));
                    int localRank = Integer.parseInt(words.get(997).replace("#","").replace(",",""));
                    System.out.printf("Statistiques de %s sur %s:\n%d parties jouées\n%.1f%% de taux de victoire\nRang %d global\nRang %d local\n\n", summoner, champion.substring(0, 1).toUpperCase() + champion.substring(1), gamesPlayed, winrate, globalRank, localRank);
                }
                Thread.sleep(1000);
            }
            catch (InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
