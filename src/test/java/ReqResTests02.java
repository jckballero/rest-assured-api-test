import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ReqResTests02 {

//  reutilizando con metodo befrore y filtros para imprimir
    @Before
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

//  Primer test
    @Test
    public void loginTest(){
        String response = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .post("/login")
                .then()
                .extract()
                .asString();


        System.out.println(response);
    }

    //Segundo test (este escenario dara error)
    @Test
    public void loginTest01(){

                given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .post("/login")
                .then()
                .statusCode(HttpStatus.SC_MULTIPLE_CHOICES) //o usar .statusCode(300)
                .body("token", notNullValue());

    }

    //Tercer test
    @Test
    public void getSingleUserTest(){
        given()
                .contentType(ContentType.JSON)
                .get("/users/7")
                .then()
                .statusCode(HttpStatus.SC_OK) //o usar .statusCode(200)
                .body("data.id", equalTo(7));
    }
}
