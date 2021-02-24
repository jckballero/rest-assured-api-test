import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReqResTests03 {

    //  reutilizando con metodo befrore y filtros para imprimir
    @Before
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        /*Nuevas configuraciones (5), para sustituir contentype*/
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    //Tercer test
    @Test
    public void getSingleUserTest() {
        given()
                .get("/users/7")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", equalTo(7));
    }

    //Cuarto test DELETE
    @Test
    public void deleteUserTest() {
        given()
                .delete("/users/7")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    //Quinto test PATCH
    @Test
    public void patchUserTest() {
        String nameUpdated = given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .patch("/users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath().getString("name");

        assertThat(nameUpdated, equalTo("morpheus"));
    }

    //Quinto test PUT
    @Test
    public void putUserTest() {
        String jodUpdated = given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .put("/users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath().getString("job");

        assertThat(jodUpdated, equalTo("zion resident"));
    }

    //Sexto test optener header, content type etc
    @Test
    public void getAllUsersTest() {
        Response response = given()
                .get("users?page2");

        Headers headers = response.getHeaders();
        int statusCode = response.getStatusCode();
        String body = response.getBody().asString();
        String contentType = response.getContentType();

        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        System.out.println("body: " + body);
        System.out.println("Content type: " + contentType);
        System.out.println("Headers: " + headers.toString());
        System.out.println("*************************************");
        System.out.println(headers.get("Content-Type"));
        System.out.println(headers.get("Transfer-Encoding"));
    }
}
