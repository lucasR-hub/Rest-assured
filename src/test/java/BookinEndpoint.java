import io.restassured.RestAssured;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

public class BookinEndpoint {

    public String lerjson(String caminhoArquivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
    }

    private final String Base_URL = "https://restful-booker.herokuapp.com";

    public void getAllBookings() {
        RestAssured.baseURI = Base_URL;

        given()
                .header("Accept", "*/*")
                .when()
                .get("/booking/")
                .then() // Define as validações da resposta
                .statusCode(200)
                .log().all();
    }

    public void getBookingId() {
        RestAssured.baseURI = Base_URL;

        given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/1493")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Lukkiinha"))
                .body("lastname", equalTo("Brown"))
                .body("totalprice", equalTo(111))
                .body("depositpaid", is(true))
                .body("bookingdates.checkin", equalTo("2026-05-01"))
                .body("bookingdates.checkout", equalTo("2026-05-10"))
                .body("additionalneeds", equalTo("Coca-Cola"))
                .log().all();
    }

    public void postCreatBooking() throws IOException {
        RestAssured.baseURI = Base_URL;

        String conteudoJson = lerjson("src/test/resources/payloads/reserva.json");

        given()
                .header("Content-Type", "application/json")
                .body(conteudoJson)
                .when()
                .post("/booking/")
                .then()
                .statusCode(200)
                .body("booking.firstname", equalTo("Lukkiinha"))
                .body("booking.lastname", equalTo("Brown"))
                .body("booking.totalprice", equalTo(111))
                .body("booking.depositpaid", is(true))
                .body("booking.bookingdates.checkin", equalTo("2026-05-01"))
                .body("booking.bookingdates.checkout", equalTo("2026-05-10"))
                .body("booking.additionalneeds", equalTo("Coca-Cola"))
                .log().all();
    }
}