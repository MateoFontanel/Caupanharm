package perso.discordbots.caupanharm.models;

public class CaupanharmChannel {
        String id, game;

    @Override
    public String toString() {
        return "CaupanharmChannel{" +
                "id='" + id + '\'' +
                ", game='" + game + '\'' +
                '}';
    }

    public CaupanharmChannel(String id, String game) {
        this.id = id;
        this.game = game;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
