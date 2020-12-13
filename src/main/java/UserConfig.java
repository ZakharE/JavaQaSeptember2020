public interface UserConfig extends org.aeonbits.owner.Config {
    String LOGIN = System.getProperty("login", "naxayo5421@wncnw.com");
    String PASSWORD = System.getProperty("password", "123123qwe");

    String hostname();
}
