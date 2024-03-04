package perso.discordbots.caupanharm.models.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id","accountId","puuid", "name", "profileIconId", "revisionDate", "summonerLevel"})
public class RiotLeagueUser {
    private String id, accountId, puuid, name;
    private int profileIconId;
    private long revisionDate, summonerLevel;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("accountId")
    public String getAccountId() {
        return accountId;
    }

    @JsonProperty("accountId")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("puuid")
    public String getPuuid() {
        return puuid;
    }

    @JsonProperty("puuid")
    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("profileIconId")
    public int getProfileIconId() {
        return profileIconId;
    }

    @JsonProperty("profileIconId")
    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    @JsonProperty("revisionDate")
    public long getRevisionDate() {
        return revisionDate;
    }

    @JsonProperty("revisionDate")
    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }

    @JsonProperty("summonerLevel")
    public long getSummonerLevel() {
        return summonerLevel;
    }

    @JsonProperty("summonerLevel")
    public void setSummonerLevel(long summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    @Override
    public String toString() {
        return "LeagueAPIUser{" +
                "id='" + id + '\'' +
                ", accountId='" + accountId + '\'' +
                ", puuid='" + puuid + '\'' +
                ", name='" + name + '\'' +
                ", profileIconId=" + profileIconId +
                ", revisionDate=" + revisionDate +
                ", summonerLevel=" + summonerLevel +
                '}';
    }
}
