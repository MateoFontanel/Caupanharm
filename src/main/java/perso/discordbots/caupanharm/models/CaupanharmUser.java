package perso.discordbots.caupanharm.models;

public class CaupanharmUser {
    // POJO (Plain Old Java Object) class defining a user. This class is a POJO because it contains getters and
    // setters for every member variable as well as an empty constructor.
    private String discordId;
    private String riotId;
    private String riotPuuid;
    private String riotUsername;

    private String team;
    private boolean tracked;

    public CaupanharmUser(String discordId, String riotId, String riotPuuid, String riotUsername, boolean tracked) {
        this.discordId = discordId;
        this.riotId = riotId;
        this.riotPuuid = riotPuuid;
        this.riotUsername = riotUsername;
        this.tracked = tracked;
        this.team = null;
    }

    // empty constructor required when we fetch data from the database -- getters and setters are later used to
    // set values for member variables
    public CaupanharmUser() {

    }

    @Override
    public String toString() {
        return "CaupanharmUser{" +
                "discordId='" + discordId + '\'' +
                ", riotId='" + riotId + '\'' +
                ", riotPuuid='" + riotPuuid + '\'' +
                ", riotUsername='" + riotUsername + '\'' +
                ", team='" + team + '\'' +
                ", tracked=" + tracked +
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

    public boolean isTracked() {
        return tracked;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }


    public void setTracked(boolean tracked) {
        this.tracked = tracked;
    }
}

