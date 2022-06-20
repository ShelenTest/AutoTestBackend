package HW4;

import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;



public class TestGetAndPost extends AbstractClass {

    String query = "pasta";
    String excludeCuisine = "french";

    @Test
    public void getSearchRecipesWithQuery() {
       given().spec(getRequestSpecification())
               .expect()
               .body("totalResults", equalTo(223))
               .when()
               .get(getBaseUrl()+ "/recipes/complexSearch?&query={query}", query)
               .then()
               .spec(responseSpecification);

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
                .spec(responseSpecification);
    }

    @Test
    public void getSearchRecipesWithDiet () {
        given()
                .spec(getRequestSpecification())
                .queryParam("diet", "vegetarian")
                .expect()
                .body("results[1].title", containsString("Garlic"))
                .when()
                .get(getBaseUrl()+"/recipes/complexSearch")
                .then()
                .spec(responseSpecification);
    }

    @Test
    public void getSearchRecipesWithIngredientsAndIntolerances () {
        given().spec(getRequestSpecification())
                .expect()
                .body("results[0].title",containsString("Pork"))
                .when()
                .get(getBaseUrl()+"/recipes/complexSearch?&intolerances={intolerances}&includeIngredients={includeIngredients}","aggs","pork")
                .then()
                .spec(responseSpecification);
    }

    @Test
    public void getSearchRecipesWithTheSameValueInIngredientsAndIntolerances() {
        given().spec(getRequestSpecification())
                .expect()
                .body("offset", equalTo(0))
                .when()
                .get(getBaseUrl()+"/recipes/complexSearch?intolerances={intolerances}&includeIngredients={includeIngredients}","aggs","aggs")
                .then()
                .spec(responseSpecification);
    }

    @Test
    public void postClassifyCuisineForSushi () {
        given().spec(getRequestSpecification())
                .queryParam("language", "en")
                .queryParam("title", "sushi")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .expect()
                .body("cuisine", equalTo("Japanese"))
                .when()
                .post(getBaseUrl() + "/recipes/cuisine")
                .then()
                .spec(responseSpecification);

    }

    @Test
    public void postClassifyCuisineForTitleChallah() {
        given()
                .spec(getRequestSpecification())
                .queryParam("title", "challah")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .expect()
                .body("cuisine", equalTo("Jewish"))
                .when()
                .post(getBaseUrl() + "/recipes/cuisine")
                .then()
                .spec(responseSpecification);

    }

    @Test
    public void postClassifyCuisineForTitleBurgerCake() {
        given()
                .spec(getRequestSpecification())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("title", "Burger Cake")
                .expect()
                .body("cuisine", equalTo("American"))
                .when()
                .post(getBaseUrl() + "/recipes/cuisine")
                .then()
                .spec(responseSpecification);
    }

    @Test
    public void postClassifyCuisineForTitleBurgerCakeDeLang () {
        given()
                .spec(getRequestSpecification())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("title", "Burger Cake")
                .expect()
                .body("cuisine", equalTo("American"))
                .when()
                .post(getBaseUrl() + "/recipes/cuisine")
                .then()
                .spec(responseSpecification);
    }

    @Test
    public void postClassifyCuisineForBurgerWithIngredientList() {
        given()
                .spec(getRequestSpecification())
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
                .spec(responseSpecification);
    }


}
