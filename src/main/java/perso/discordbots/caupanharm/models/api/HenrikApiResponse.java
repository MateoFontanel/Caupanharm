package perso.discordbots.caupanharm.models.api;

import java.util.LinkedHashMap;

// Hotfix class used to fetch specific data from Henrik's endpoints
// TODO ? A full implementation would require to create classes for every data type of the API. Easily doable. Also very long to do (:
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

    public String getMmr(String season){ // TODO there gotta be a better way to do that RIGHT ??
        data = ((LinkedHashMap<String,Object>) data.get("by_season"));
        if(data.get(season) == null){
            return "unranked";
        }else{
            data = ((LinkedHashMap<String,Object>) data.get(season));
            return data.get("final_rank_patched").toString();
        }

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
