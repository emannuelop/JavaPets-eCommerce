// package br.unitins;

// import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.CoreMatchers.notNullValue;
// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.junit.jupiter.api.Assertions.assertNull;

// import io.quarkus.test.junit.QuarkusTest;
// import io.quarkus.test.security.TestSecurity;
// import io.restassured.http.ContentType;

// import org.junit.jupiter.api.Test;

// import br.unitins.ecommerce.dto.municipio.CidadeDTO;
// import br.unitins.ecommerce.dto.municipio.CidadeResponseDTO;
// import br.unitins.ecommerce.service.municipio.CidadeService;

// import jakarta.inject.Inject;

// @QuarkusTest
// public class CidadeResourceTest {

//     @Inject
//     CidadeService municipioService;

//     @Test
//     public void getAllTest() {

//         given()
//                 .when().get("/municipios")
//                 .then()
//                 .statusCode(200);
//     }

//     @Test
//     @TestSecurity(user = "testUser", roles = {"Admin"})
//     public void insertTest() {

//         CidadeDTO municipio = new CidadeDTO(
//                 "Miracema do Tocantins", 
//                 5l);

//         given()
//                 .contentType(ContentType.JSON)
//                 .body(municipio)
//                 .when().post("/municipios")
//                 .then()
//                 .statusCode(201)
//                 .body("id", notNullValue(), "nome", is("Miracema do Tocantins"),
//                 "estado.get(\"nome\")", is("Tocantins"), "estado.sigla", is("TO"));
//     }

//     @Test
//     @TestSecurity(user = "testUser", roles = {"User"})
//     public void insertForbiddenTest() {

//         CidadeDTO municipio = new CidadeDTO(
//                 "Miracema do Tocantins", 
//                 5l);

//         given()
//                 .contentType(ContentType.JSON)
//                 .body(municipio)
//                 .when().post("/municipios")
//                 .then()
//                 .statusCode(403);
//     }

//     @Test
//     public void insertUnauthorizedTest() {

//         CidadeDTO municipio = new CidadeDTO(
//                 "Miracema do Tocantins", 
//                 5l);

//         given()
//             .contentType(ContentType.JSON)
//             .body(municipio)
//             .when().post("/municipios")
//             .then()
//             .statusCode(401);
//     }

//     @Test
//     @TestSecurity(user = "testUser", roles = {"Admin"})
//     public void updateTest() {

//         CidadeDTO municipioDto = new CidadeDTO(
//                 "Miracema do Tocantins",
//                 5l);

//         Long id = municipioService.insert(municipioDto).id();

//         CidadeDTO municipioUpdate = new CidadeDTO(
//             "Rio Verde",
//             3l
//         );

//         given()
//           .contentType(ContentType.JSON)
//           .body(municipioUpdate)
//           .when().put("/municipios/" + id)
//           .then()
//              .statusCode(204);

//         CidadeResponseDTO municipioResponse = municipioService.getById(id);

//         assertThat(municipioResponse.nome(), is("Rio Verde"));
//         assertThat(municipioResponse.estado().get("nome"), is("Goiás"));
//         assertThat(municipioResponse.estado().get("sigla"), is("GO"));
//     }

//     @Test
//     @TestSecurity(user = "testUser", roles = {"Admin"})
//     public void deleteTest() {

//         CidadeDTO municipio = new CidadeDTO(
//             "Miracema do Tocantins",
//             5l
//         );

//         Long id = municipioService.insert(municipio).id();

//         given()
//           .when().delete("/municipios/" + id)
//           .then()
//              .statusCode(204);

//         CidadeResponseDTO municipioResponse = null;

//         try {
            
//             municipioResponse =  municipioService.getById(id);
//         } catch (Exception e) {

//         }
//         finally {
//             assertNull(municipioResponse);   
//         }
//     }

//     @Test
//     @TestSecurity(user = "testUser", roles = {"Admin"})
//     public void countTest() {

//         given()
//             .when().get("/municipios/count")
//             .then()
//                 .statusCode(200);
//     }

//     @Test
//     @TestSecurity(user = "testUser", roles = {"Admin"})
//     public void getByIdTest() {

//         given()
//             .when().get("/municipios/" + 4)
//             .then()
//                 .statusCode(200);
//     }

//     @Test
//     public void getByNomeTest() {

//         given()
//             .when().get("/municipios/searchByNome/" + "goi")
//             .then()
//                 .statusCode(200);
//     }

//     @Test
//     public void getByNomeEstadoTest() {

//         given()
//             .when().get("/municipios/searchByNomeEstado/" + "ama")
//             .then()
//                 .statusCode(200);
//     }

//     @Test
//     public void getBySiglaEstadoTest() {

//         given()
//             .when().get("/municipios/searchBySiglaEstado/" + "go")
//             .then()
//                 .statusCode(200);
//     }
// }