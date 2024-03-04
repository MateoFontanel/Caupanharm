package perso.discordbots.caupanharm.models;

public enum ValRanks {

    UNRANKED(0, "unranked", "Unranked"),
    UNKNOWN1(1, "unknown_1", "Unknown 1"),
    UNKNOWN2(2, "unknown_2", "Unknown 2"),
    IRON1(3, "iron_1", "Iron 1"),
    IRON2(4, "iron_2", "Iron 2"),
    IRON3(5, "iron_3", "Iron 3"),
    BRONZE1(6, "bronze_1", "Bronze 1"),
    BRONZE2(7, "bronze_2", "Bronze 2"),
    BRONZE3(8, "bronze_3", "Bronze 3"),
    SILVER1(9, "silver_1", "Silver 1"),
    SILVER2(10, "silver_2", "Silver 2"),
    SILVER3(11, "silver_3", "Silver 3"),
    GOLD1(12, "gold_1", "Gold 1"),
    GOLD2(13, "gold_2", "Gold 2"),
    GOLD3(14, "gold_3", "Gold 3"),
    PLATINUM1(15, "platinum_1", "Platinum 1"),
    PLATINUM2(16, "platinum_2", "Platinum 2"),
    PLATINUM3(17, "platinum_3", "Platinum 3"),
    DIAMOND1(18, "diamond_1", "Diamond 1"),
    DIAMOND2(19, "diamond_2", "Diamond 2"),
    DIAMOND3(20, "diamond_3", "Diamond 3"),
    ASCENDANT1(21, "ascendant_1", "Ascendant 1"),
    ASCENDANT2(22, "ascendant_2", "Ascendant 2"),
    ASCENDANT3(23, "ascendant_3", "Ascendant 3"),
    IMMORTAL1(24, "immortal_1", "Immortal 1"),
    IMMORTAL2(25, "immortal_2", "Immortal 2"),
    IMMORTAL3(26, "immortal_3", "Immortal 3"),
    RADIANT(27, "radiant", "Radiant");


    private final int id;
    private final String name, formattedName;

    ValRanks(int id, String name, String formattedName){
        this.id = id;
        this.name = name;
        this.formattedName = formattedName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
