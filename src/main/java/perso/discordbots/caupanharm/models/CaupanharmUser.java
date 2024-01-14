package perso.discordbots.caupanharm.models;

public class CaupanharmUser {
    // POJO (Plain Old Java Object) class defining a user. This class is a POJO because it contains getters and
    // setters for every member variable as well as an empty constructor.
    private String discordId, riotPuuid, riotUsername;

    public CaupanharmUser(String discordId, String riotPuuid, String riotUsername){
        this.discordId = discordId;
        this.riotPuuid = riotPuuid;
        this.riotUsername = riotUsername;
    }

    // empty constructor required when we fetch data from the database -- getters and setters are later used to
    // set values for member variables
    public CaupanharmUser(){
        discordId = "";
        riotPuuid = "";
        riotUsername = "";
    }

    @Override
    public String toString() {
        return "CaupanharmUser{" + "Discord ID=" + discordId +
                ", Riot PUUID=" + riotPuuid +
                ", Riot Name=" + riotUsername +
                '}';
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public String getRiotPuuid() {
        return riotPuuid;
    }

    public void setRiotPuuid(String riotPuuid) {
        this.riotPuuid = riotPuuid;
    }

    public String getRiotUsername() {
        return riotUsername;
    }

    public void setRiotUsername(String riotUsername) {
        this.riotUsername = riotUsername;
    }
}
