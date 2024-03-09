package perso.discordbots.caupanharm.models.api.henrik;

public class AccountV1 {
    String puuid, region, name, tag, last_update;
    int account_level;
    long last_update_raw;
    Card card;

    public AccountV1(String puuid, String region, String name, String tag, String last_update, int account_level, long last_update_raw, Card card) {
        this.puuid = puuid;
        this.region = region;
        this.name = name;
        this.tag = tag;
        this.last_update = last_update;
        this.account_level = account_level;
        this.last_update_raw = last_update_raw;
        this.card = card;
    }

    public AccountV1() {
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public int getAccount_level() {
        return account_level;
    }

    public void setAccount_level(int account_level) {
        this.account_level = account_level;
    }

    public long getLast_update_raw() {
        return last_update_raw;
    }

    public void setLast_update_raw(long last_update_raw) {
        this.last_update_raw = last_update_raw;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "HenrikUser{" +
                "puuid='" + puuid + '\'' +
                ", region='" + region + '\'' +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", last_update='" + last_update + '\'' +
                ", account_level=" + account_level +
                ", last_update_raw=" + last_update_raw +
                ", card=" + card +
                '}';
    }
}
