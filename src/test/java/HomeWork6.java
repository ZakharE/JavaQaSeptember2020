import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.testng.Assert.assertEquals;

public class HomeWork6 {
    private final String baseUri = "https://petstore.swagger.io/v2/user";

    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .log(LogDetail.ALL)
            .setBaseUri(baseUri)
            .setContentType(ContentType.JSON)
            .build();

    /*
    При успешном создании юзера в поле "message" возвращается число отличное от нуля
     */
    @Test
    public void successfulCreateUser() {
        User user = new User()
                .withEmail("email@email.com")
                .withPassword("pass")
                .withFirstName("name")
                .withLastName("lastname");
        given()
                .with().spec(requestSpecification)
                .body(user)
                .post()
                .then().assertThat()
                .statusCode(HTTP_OK)
                .body("message", Matchers.not(Matchers.equalTo(0)));
    }

    /*
     * При использовании заголовка Content-type отличного от 'application/json' возвращается код 415  */
    @Test
    public void createUserWithWrongContentType() {
        given()
                .with().spec(requestSpecification)
                .contentType(ContentType.TEXT)
                .post()
                .then().assertThat()
                .statusCode(HTTP_UNSUPPORTED_TYPE);
    }

    /* При запросе существующего юзера возвращается акутуальная информация о нем
     */
    @Test
    public void successfulGetUser() {
        User user = new User()
                .withEmail("someEmail@mail.ru")
                .withPassword("pass")
                .withUsername("asdd")
                .withPassword("dddd")
                .withFirstName("name")
                .withLastName("lastname");
        given()
                .with().spec(requestSpecification)
                .body(user)
                .post()
                .then().assertThat()
                .statusCode(HTTP_OK);
        User actualUser = given()
                .with().spec(requestSpecification)
                .pathParam("username", user.getUsername())
                .get("/{username}")
                .then().extract().body().as(User.class);

        assertEquals(user, actualUser);
    }

    /* При запросе несуществующего пользователя возвращается статус 404  */
    @Test
    public void getUserWithWrongContentType() {

        given()
                .with().spec(requestSpecification)
                .contentType(ContentType.TEXT)
                .pathParam("username", "fghjkl")
                .get("/{username}")
                .then().assertThat()
                .statusCode(HTTP_NOT_FOUND);
    }
}
