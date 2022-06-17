package HW3;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestChain extends AbstractClass{

    @Test
    public void TestChain() {

        Response response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/json")
                .body("{\n"
                        + " \"username\": \"shelenrig\",\n"
                        + " \"firstName\": \"Elena\",\n"
                        + " \"lastName\": \"Shevcova\",\n"
                        + " \"email\": \"04elen91@mail.ru\",\n"
                        + "}")
                .when()
                .request(Method.POST, getBaseUrl()+"/users/connect");
        assertThat(response.getStatusCode(), is(200));
        JsonPath body = response.getBody().jsonPath();
        String username = body.get("username");
        String hash = body.get("hash");

        String id = given()
                .queryParam("hash", hash)
                .queryParam("apiKey", getApiKey())
                .pathParam("username", username)
                .contentType("application/json")
                .body("{\n"
                        + " \"date\": 1644881179,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post(getBaseUrl()+"/mealplanner/{username}/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .queryParam("hash", hash)
                .queryParam("apiKey", getApiKey())
                .pathParam("username", username)
                .pathParam("id", id)
                .delete(getBaseUrl()+"/mealplanner/{username}/items/{id}")
                .then()
                .statusCode(200);
    }

}
