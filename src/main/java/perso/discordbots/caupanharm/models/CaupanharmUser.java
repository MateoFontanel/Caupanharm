package perso.discordbots.caupanharm.models;

import java.util.ArrayList;
import java.util.List;

public class CaupanharmUser {
    // POJO (Plain Old Java Object) class defining a user. This class is a POJO because it contains getters and
    // setters for every member variable as well as an empty constructor.
    private String discordId, riotId, riotPuuid, riotUsername, henrikPuuid, team;
    private boolean tracked;

    private List<ValRoles> roles;

    public CaupanharmUser(String discordId, String riotId, String riotPuuid, String henrikPuuid, String riotUsername) {
        this.discordId = discordId;
        this.riotId = riotId;
        this.riotPuuid = riotPuuid;
        this.henrikPuuid = henrikPuuid;
        this.riotUsername = riotUsername;
        this.tracked = false;
        this.team = null;
        this.roles = new ArrayList<>();
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
                ", henrikPuuid='" + henrikPuuid + '\'' +
                ", team='" + team + '\'' +
                ", tracked=" + tracked +
                ", roles=" + roles +
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

    public String getHenrikPuuid() {
        return henrikPuuid;
    }

    public void setHenrikPuuid(String henrikPuuid) {
        this.henrikPuuid = henrikPuuid;
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

    public void setRoles(List<ValRoles> roles) { // Not used here, only required for Mongodb to fetch data
        this.roles = roles;
    }

    public List<ValRoles> getRoles() {
        return roles;
    }

    public void addRole(ValRoles role){
        roles.add(role);
    }

    public void removeRole(ValRoles role){
        roles.remove(role);
    }
}

