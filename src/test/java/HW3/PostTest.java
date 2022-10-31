package HW3;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


public class PostTest {
    private final String apiKey = "a1576dab743a4019bad649f72a67a689";

    @Test
    void postRecipeTest() {
        given()
                .queryParam("apiKey", apiKey)
                .body("{\n" +
                        " \"title\": German Chocolate Drink with Xocai Healthy Chocolate↵\n" +
                        " \"ingredientList\": water\n" +
                        " \"language\": en\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .statusCode(200);
    }

    @Test
    void postRecipeTimeOutTest() {
        given()
                .queryParam("apiKey", apiKey)
                .body("{\n" +
                        " \"title\": Apple-Spinach Salad\n" +
                        " \"ingredientList\": apple\n" +
                        " \"language\": en\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .time(Matchers.lessThan(2000L));
    }

    @Test
    void postRecipeBodyTest() {
        final JsonPath response = given()
                .queryParam("apiKey", apiKey)
                .body("{\n" +
                        " \"title\": APea Soup With Smoked Pork Ribs\n" +
                        " \"ingredientList\": pork\n" +
                        " \"language\": en\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
    }

    @Test
    void postRecipeJSONTest() {
        final JsonPath response = (JsonPath) given()
                .queryParam("apiKey", apiKey)
                .body("{\n" +
                        " \"title\": Arame Edamame Cucumber Salad\n" +
                        " \"ingredientList\": cucumber\n" +
                        " \"language\": en\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .assertThat()
                .contentType(ContentType.JSON);
    }

    @Test
    void postRecipeBody2Test() {
        final String response = given()
                .queryParam("apiKey", apiKey)
                .body("{\n" +
                        " \"title\": Tomato soup with dumplings\n" +
                        " \"ingredientList\": tomato\n" +
                        " \"language\": en\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .extract()
                .asString();
        assertThat(response, containsString("Mediterranean"));
    }
}
