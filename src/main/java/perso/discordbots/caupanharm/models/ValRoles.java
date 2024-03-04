package perso.discordbots.caupanharm.models;


public enum ValRoles {

    CONTROLLER("controller", "Contrôleur", "controller"),

    DUELIST("duelist", "Duelliste", "duelist"),

    INITIATOR("initiator", "Initiateur", "initiator"),

    SENTINEL("sentinel", "Sentinelle", "sentinel"),

    FLEX("flex", "Flex", null);

    private final String name, formattedName, emojiName;

    ValRoles(String name, String formattedName, String emojiName) {
        this.name = name;
        this.formattedName = formattedName;
        this.emojiName = emojiName;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public String getEmojiName() {
        return emojiName;
    }


}
