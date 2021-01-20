package qa.constants;

public enum SocialNetworks {
    VK( "VK", "vk.com"),
    TG("Тelegram", "@tg");

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    private final String name;
    private final String value;

    SocialNetworks(String name, String value) {
        this.value =value;
        this.name = name;
    }
}
