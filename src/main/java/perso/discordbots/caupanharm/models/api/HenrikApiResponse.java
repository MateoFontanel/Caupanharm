package perso.discordbots.caupanharm.models.api;

import java.util.LinkedHashMap;

public class HenrikApiResponse {
    int status;
    LinkedHashMap<String, Object> data;

    public HenrikApiResponse() {
    }

    public HenrikUser buildHenrikUser(){
        HenrikCard henrikCard = null;
        if(data.containsKey("card")){
            LinkedHashMap<String, String> card =  (LinkedHashMap<String, String>) data.get("card");
            henrikCard = new HenrikCard(card.get("small"), card.get("large"), card.get("wide"), card.get("id"));
        }
        return new HenrikUser(data.get("puuid").toString(),
                data.get("region").toString(),
                data.get("name").toString(),
                data.get("tag").toString(),
                data.containsKey("last_update") ? data.get("last_update").toString() : null,
                Integer.parseInt(data.get("account_level").toString()),
                data.containsKey("last_update_raw") ? Long.parseLong(data.get("last_update_raw").toString()) : 0,
                henrikCard);
    }

    public String getMmr(){

        return null;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LinkedHashMap<String, Object> getData() {
        return data;
    }

    public void setData(LinkedHashMap<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HenrikApiCall{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
