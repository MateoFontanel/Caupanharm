package perso.discordbots.caupanharm.databases;

public class CaupanharmUser {
    // POJO (Plain Old Java Object) class defining a user. This class is a POJO because it contains getters and
    // setters for every member variable as well as an empty constructor.
    private String discordId, riotId, riotUsername;

    public CaupanharmUser(String discordId, String riotId, String riotUsername){
        this.discordId = discordId;
        this.riotId = riotId;
        this.riotUsername = riotUsername;
    }

    // empty constructor required when we fetch data from the database -- getters and setters are later used to
    // set values for member variables
    public CaupanharmUser(){
        discordId = "";
        riotId = "";
        riotUsername = "";
    }

    @Override
    public String toString() {
        return "CaupanharmUser{" + "Discord ID=" + discordId +
                ", Riot ID=" + riotId +
                ", Riot Name=" + riotUsername +
                '}';
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public String getRiotId() {
        return riotId;
    }

    public void setRiotId(String riotId) {
        this.riotId = riotId;
    }

    public String getRiotUsername() {
        return riotUsername;
    }

    public void setRiotUsername(String riotUsername) {
        this.riotUsername = riotUsername;
    }
}
