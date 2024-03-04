package perso.discordbots.caupanharm.models;


public enum ValRoles {

    CONTROLLER("controller", "Contrôleur"),

    DUELIST("duelist", "Duelliste"),

    INITIATOR("initiator", "Initiateur"),

    SENTINEL("sentinel", "Sentinelle"),

    FLEX("flex", "Flex");

    private final String name, formattedName;

    ValRoles(String name, String formattedName) {
        this.name = name;
        this.formattedName = formattedName;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return formattedName;
    }

}
