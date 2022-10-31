package HW3;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class GetTest {

    private final String apiKey = "a1576dab743a4019bad649f72a67a689";

    @Test
    void getRecipeTest() {
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("query", "salad")
                .queryParam("diet", "Vegan")
                .queryParam("maxVitaminA", "100")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .statusCode(200);

    }

    @Test
    void getRecipeTimeOutTest() {
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("query", "pizza")
                .queryParam("maxReadyTime", "90")
                .queryParam("italian", "italian")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .time(Matchers.lessThan(2000L));
    }

    @Test
    void getRecipeBodyTest() {
        final JsonPath response = given()
                .queryParam("apiKey", apiKey)
                .queryParam("query", "burger")
                .queryParam("cuisine", "italian")
                .queryParam("intolerances", "gluten")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("number"), equalTo(10));
    }

    @Test
    void getRecipeJSONTest() {
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("query", "burger")
                .queryParam("cuisine", "italian")
                .queryParam("intolerances", "gluten")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .assertThat()
                .contentType(ContentType.JSON);

    }

    @Test
    void getRecipeBody2Test() {
        final String response = given()
                .queryParam("apiKey", apiKey)
                .queryParam("query", "sauce")
                .queryParam("cuisine", "italian")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .extract()
                .asString();
        assertThat(response, containsString("offset"));
    }
}
