package perso.discordbots.caupanharm.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import perso.discordbots.caupanharm.controllers.UserController;

import java.util.ArrayList;
import java.util.List;

public class CaupanharmTeam {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserController userController;

    private String name;
    private List<String> members = new ArrayList<>(), tier1members = new ArrayList<>(), tier2members = new ArrayList<>();

    public CaupanharmTeam(String name, CaupanharmUser user) {
        this.name = name;
        members.add(user.getDiscordId());
        tier2members.add(user.getDiscordId());// Tier 2 member AKA creator, all joining members will be tier 0, members can be promoted to tier 1 for more management options
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

    public List<String> getTier1members() {
        return tier1members;
    }

    public void setTier1members(List<String> tier1members) {
        this.tier1members = tier1members;
    }

    public List<String> getTier2members() {
        return tier2members;
    }

    public void setTier2members(List<String> tier2members) {
        this.tier2members = tier2members;
    }

    public void addMember(CaupanharmUser user) {
        members.add(user.getDiscordId());
    }

    public void removeMember(CaupanharmUser user) {
        members.remove(user);
        tier1members.remove(user);
        tier2members.remove(user);
    }

    /**
     * Check if user 1 (first param) has a higher tier in the team than user 2 (second param)
     *
     * @param id1 user 1 discord id
     * @param id2 user 2 discord id
     * @return true if user 1 has hierarchical superiority to user 2, false if not
     */
    public boolean hasSuperiority(String id1, String id2) {
        int tier1 = 0, tier2 = 0;
        if (tier2members.contains(id1)) {
            tier1 = 2;
        } else if (tier1members.contains(id1)) {
            tier1 = 1;
        }

        if (tier2members.contains(id2)) {
            tier2 = 2;
        } else if (tier1members.contains(id2)) {
            tier2 = 1;
        }

        return tier1 > tier2;
    }

    /**
     * Check if user is the team captain (tier 2 as of now, can be modified)
     *
     * @param discordId of the user
     * @return yes if so
     */
    public boolean isHigherTier(String discordId) {
        return tier2members.contains(discordId);
    }
}
