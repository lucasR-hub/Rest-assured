import io.restassured.RestAssured;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class BookingTest {
    BookinEndpoint bookinEndpoint  = new BookinEndpoint();

    private static final Log log = LogFactory.getLog(BookingTest.class);

    public String lerjson(String caminhoArquivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
    }


    @Test
    public void consultaPageObject() {
        bookinEndpoint.getAllBookings();

    }

    // Define um método de teste
    @Test
    public void testGetBooking() {
        // Configura a URL base para as requisições da API
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Configura e executa a requisição GET para o endpoint "/booking/"
        given() // Define as configurações da requisição (headers, parâmetros, etc.)
                .header("Accept", "*/*") //adiciona o header accept
         .when() // Indica o início da execução da requisição
                .get("/booking/") // Especifica o endpoint a ser chamado
         .then() // Define as validações da resposta
                .statusCode(200) // Verifica se o status code da resposta é 200 (OK)
                .log().all();    // Loga no console todos os detalhes da resposta (body, headers, etc.)
    }

    @Test
    public void bookingPorId(){
        bookinEndpoint.getBookingId();

    }

    @Test
    public void testeBuscarPoriD() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";


        given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/2106")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Lukkiinha"))
                .body("lastname", equalTo("Brown"))
                .body("totalprice", equalTo(111))
                .body("depositpaid", is(true))
                .body("bookingdates.checkin", equalTo("2026-05-01"))
                .body("bookingdates.checkout", equalTo("2026-05-10"))
                .body("additionalneeds", equalTo("Coca-Cola"))
                .log();

    }
    @Test
    public void creatBooking() throws IOException{
        bookinEndpoint.postCreatBooking();

    }

    @Test

    public void TesteFazerReserva() throws IOException {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

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
