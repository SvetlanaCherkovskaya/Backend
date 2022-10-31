package HW3;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AddMeal {
    private final String apiKey = "a1576dab743a4019bad649f72a67a689";
    private final String username = "sveta";
    private final String hash = "341287c8c92b93159c0fd4ff07badf960eff9751";

    @Test
    void addToShoppingList() {
        String id = given()
                .queryParam("apiKey", apiKey)
                .queryParam("username", username)
                .queryParam("hash", hash)
                .body("{\n" +
                        " \"item\": Fruit Ice cream,\n" +
                        " \"aisle\": Frozen,\n" +
                        " \"parse\": true\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/mealplanner/" + username + "/shopping-list/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();
        //System.out.println(id);

        given()
                .queryParam("apiKey", apiKey)
                .queryParam("username", username)
                .queryParam("hash", hash)
                .delete("https://api.spoonacular.com/mealplanner/" + username + "/shopping-list/items/" + id)
                .then()
                .statusCode(200);
    }
}
