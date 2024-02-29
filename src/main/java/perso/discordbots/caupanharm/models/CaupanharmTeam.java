package perso.discordbots.caupanharm.models;

import java.util.ArrayList;
import java.util.List;

public class CaupanharmTeam {
    private String name;
    private List<String> members = new ArrayList<>();
    private List<String> tier2members = new ArrayList<>(), tier1members = new ArrayList<>();

    public CaupanharmTeam(String name, String discordId){
        this.name = name;
        members.add(discordId); // Tier 2 member AKA creator, all joining members will be tier 0, members can be promoted to tier 1 for more management options
        tier2members.add(discordId);
    }

    public CaupanharmTeam() {
        this.name = null;
        this.members = null;
        this.tier2members = null;
        this.tier1members = null;
    }

    @Override
    public String toString() {
        return "CaupanharmTeam{" +
                "name='" + name + '\'' +
                ", members=" + members +
                ", tier2users=" + tier2members +
                ", tier1users=" + tier1members +
                '}';
    }

    public String toDiscord(){
        StringBuilder output = new StringBuilder(String.format("Team **%s**", getName())
                + "\n\n__Members:__ \n")
                ;
        for(String id : members){
            output.append("<@").append(id).append(">\n");
        }

        return output.toString().trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getTier2members() {
        return tier2members;
    }

    public void setTier2members(List<String> tier2members) {
        this.tier2members = tier2members;
    }

    public List<String> getTier1members() {
        return tier1members;
    }

    public void setTier1members(List<String> tier1members) {
        this.tier1members = tier1members;
    }

    public void addMember(String discordUserId){
        members.add(discordUserId);
    }

    public void removeMember(String discordUserId){
        members.remove(discordUserId);
        tier1members.remove(discordUserId);
        tier2members.remove(discordUserId);
    }

    /**
     *
     * Check if user 1 (first param) has a higher tier in the team than user 2 (second param)
     * @param id1 user 1 discord id
     * @param id2 user 2 discord id
     * @return true if user 1 has hierarchical superiority to user 2, false if not
     */
    public boolean hasSuperiority(String id1, String id2){
        int tier1 = 0, tier2 = 0;
        if(tier2members.contains(id1)){
            tier1 = 2;
        }else if(tier1members.contains(id1)){
            tier1 = 1;
        }

        if(tier2members.contains(id2)){
            tier2 = 2;
        }else if(tier1members.contains(id2)){
            tier2 = 1;
        }

        return tier1 > tier2;
    }

    /**
     * Check if user is the team captain (tier 2 as of now, can be modified)
     * @param discordId
     * @return yes if so
     */
    public boolean isHigherTier(String discordId){
        return tier2members.contains(discordId);
    }
}
