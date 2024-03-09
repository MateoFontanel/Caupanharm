package perso.discordbots.caupanharm.models.api.henrik;

public class MmrV1 {

    String currenttierpatched, name, tag;
    int currenttier, ranking_in_tier, mmr_change_to_last_game, elo;
    boolean old;
    Images images;

    public MmrV1() {

    }

    public String getCurrenttierpatched() {
        return currenttierpatched;
    }

    public void setCurrenttierpatched(String currenttierpatched) {
        this.currenttierpatched = currenttierpatched;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCurrenttier() {
        return currenttier;
    }

    public void setCurrenttier(int currenttier) {
        this.currenttier = currenttier;
    }

    public int getRanking_in_tier() {
        return ranking_in_tier;
    }

    public void setRanking_in_tier(int ranking_in_tier) {
        this.ranking_in_tier = ranking_in_tier;
    }

    public int getMmr_change_to_last_game() {
        return mmr_change_to_last_game;
    }

    public void setMmr_change_to_last_game(int mmr_change_to_last_game) {
        this.mmr_change_to_last_game = mmr_change_to_last_game;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public boolean isOld() {
        return old;
    }

    public void setOld(boolean old) {
        this.old = old;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "MmrV1{" +
                "currenttier_patched='" + currenttierpatched + '\'' +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", currenttier=" + currenttier +
                ", ranking_in_tier=" + ranking_in_tier +
                ", mmr_change_to_last_game=" + mmr_change_to_last_game +
                ", elo=" + elo +
                ", old=" + old +
                ", images=" + images +
                '}';
    }
}
