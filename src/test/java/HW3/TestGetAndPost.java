package HW3;

import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;



public class TestGetAndPost extends AbstractClass {

    String query = "pasta";
    String excludeCuisine = "french";

    @Test
    public void getSearchRecipesWithQuery() {
       given()
               .expect()
               .body("totalResults", equalTo(223))
               .when()
               .get(getBaseUrl()+ "/recipes/complexSearch?apiKey="+getApiKey()+"&query={query}", query)
               .then()
               .statusCode(200);

    }

    @Test
    public void getSearchRecipesWithExcludeCuisine () {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("excludeCuisine", excludeCuisine)
                .expect()
                .body("results[0].title", containsString("Brown Rice"))
                .when()
                .get(getBaseUrl()+"/recipes/complexSearch")
                .then()
                .statusCode(200);
    }

    @Test
    public void getSearchRecipesWithDiet () {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("diet", "vegetarian")
                .expect()
                .body("results[1].title", containsString("Garlic"))
                .when()
                .get(getBaseUrl()+"/recipes/complexSearch")
                .then()
                .statusCode(200);
    }

    @Test
    public void getSearchRecipesWithIngredientsAndIntolerances () {
        given()
                .expect()
                .body("results[0].title",containsString("Pork"))
                .when()
                .get(getBaseUrl()+"/recipes/complexSearch?apiKey={apiKey}&intolerances={intolerances}&includeIngredients={includeIngredients}",getApiKey(),"aggs","pork")
                .then()
                .statusCode(200);
    }

    @Test
    public void getSearchRecipesWithTheSameValueInIngredientsAndIntolerances() {
        given()
                .expect()
                .body("offset", equalTo(0))
                .when()
                .get(getBaseUrl()+"/recipes/complexSearch?apiKey={apiKey}&intolerances={intolerances}&includeIngredients={includeIngredients}",getApiKey(),"aggs","aggs")
                .then()
                .statusCode(200);
    }

    @Test
    public void postClassifyCuisineForSushi () {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("language", "en")
                .queryParam("title", "sushi")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .expect()
                .body("cuisine", equalTo("Japanese"))
                .when()
                .post(getBaseUrl() + "/recipes/cuisine")
                .then()
                .statusCode(200);

    }

    @Test
    public void postClassifyCuisineForTitleChallah() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "challah")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .expect()
                .body("cuisine", equalTo("Jewish"))
                .when()
                .post(getBaseUrl() + "/recipes/cuisine")
                .then()
                .statusCode(200);

    }

    @Test
    public void postClassifyCuisineForTitleBurgerCake() {
        given()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("title", "Burger Cake")
                .expect()
                .body("cuisine", equalTo("American"))
                .when()
                .post(getBaseUrl() + "/recipes/cuisine")
                .then()
                .statusCode(200);
    }

    @Test
    public void postClassifyCuisineForTitleBurgerCakeDeLang () {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("language", "en")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("title", "Burger Cake")
                .expect()
                .body("cuisine", equalTo("American"))
                .when()
                .post(getBaseUrl() + "/recipes/cuisine")
                .then()
                .statusCode(200);
    }

    @Test
    public void postClassifyCuisineForBurgerWithIngredientList() {
        given()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("title", "Burger Cake")
                .formParam("ingredientList", "1 large beefsteak tomato\n" +
                        "½ tsps black pepper\n" +
                        "some canola oil\n" +
                        "6  split english muffins\n" +
                        "1½ Tbsps fresh flat-leaf parsley\n" +
                        "3 pounds ground beef chuck\n" +
                        "2 tsps kosher salt\n" +
                        "3 Tbsps salted butter")
                .expect()
                .body("cuisine", equalTo("American"))
                .when()
                .post(getBaseUrl() + "/recipes/cuisine")
                .then()
                .statusCode(200);
    }


}
