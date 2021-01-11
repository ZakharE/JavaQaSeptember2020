package qa.config;

public interface UserConfig {
    String LOGIN = System.getProperty("login", "naxayo5421@wncnw.com");
    String PASSWORD = System.getProperty("password", "123123qwe");
    String HOST = System.getProperty("host", "https://otus.ru");
    String BLOG = System.getProperty("host", "https://otus.ru/nest/posts/");
    String BLOG_FAVORITES = System.getProperty("host", "https://otus.ru/nest/marked/");
}
