package perso.discordbots.caupanharm.models.api.henrik;

public class Card {

    String small, large, wide, id;

    public Card(String small, String large, String wide, String id) {
        this.small = small;
        this.large = large;
        this.wide = wide;
        this.id = id;
    }

    public Card() {
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getWide() {
        return wide;
    }

    public void setWide(String wide) {
        this.wide = wide;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HenrikCard{" +
                "small='" + small + '\'' +
                ", large='" + large + '\'' +
                ", wide='" + wide + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
