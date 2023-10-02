// package br.unitins;

// import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.CoreMatchers.notNullValue;
// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.junit.jupiter.api.Assertions.assertNull;

// import jakarta.inject.Inject;

// import org.junit.jupiter.api.Test;

// import br.unitins.ecommerce.dto.produto.ProdutoDTO;
// import br.unitins.ecommerce.dto.produto.ProdutoResponseDTO;
// import br.unitins.ecommerce.service.produto.ProdutoService;
// import io.quarkus.test.junit.QuarkusTest;
// import io.quarkus.test.security.TestSecurity;
// import io.restassured.http.ContentType;

// @QuarkusTest
// public class RacaoResourceTest {

//     @Inject
//     ProdutoService racaoService;

//     @Test
//     public void getAllTest() {

//         given()
//                 .when().get("/produtos")
//                 .then()
//                 .statusCode(200);
//     }

//     @Test
//     public void getByIdTest() {

//         given()
//                 .when().get("/produtos/" + 1)
//                 .then()
//                 .statusCode(200);
//     }

//     @Test
//     @TestSecurity(user = "testUser", roles = {"Admin"})
//     public void insertTest() {

//         ProdutoDTO racao = new ProdutoDTO(
//                 "Ração Pedigree Nutrição Essencial",
//                 "Para Cães Adultos de Porte Pequeno",
//                 2l,
//                 150.00,
//                 10);

//         given()
//                 .contentType(ContentType.JSON)
//                 .body(racao)
//                 .when().post("/produtos")
//                 .then()
//                 .statusCode(201)
//                 .body("id", notNullValue(), "nome", is("Ração Pedigree Nutrição Essencial"), "descricao",
//                         is("Para Cães Adultos de Porte Pequeno"), "nomeMarca", is("GoldeN"), "preco", is(150.0F),
//                         "estoque", is("Disponível"));
//     }

//     @Test
//     @TestSecurity(user = "testUser", roles = {"User"})
//     public void insertForbiddenTest() {

//         ProdutoDTO racao = new ProdutoDTO(
//                 "Ração Pedigree Nutrição Essencial",
//                 "Para Cães Adultos de Porte Pequeno",
//                 2l,
//                 150.00,
//                 10);

//         given()
//                 .contentType(ContentType.JSON)
//                 .body(racao)
//                 .when().post("/produtos")
//                 .then()
//                 .statusCode(403);
//     }

//     @Test
//     public void insertUnauthorizedTest() {

//         ProdutoDTO racao = new ProdutoDTO(
//                 "Ração Pedigree Nutrição Essencial",
//                 "Para Cães Adultos de Porte Pequeno",
//                 2l,
//                 150.00,
//                 10);

//         given()
//                 .contentType(ContentType.JSON)
//                 .body(racao)
//                 .when().post("/produtos")
//                 .then()
//                 .statusCode(401);
//     }

//     @Test
//     @TestSecurity(user = "testUser", roles = {"Admin"})
//     public void updateTest() {

//         ProdutoDTO racao = new ProdutoDTO(
//                 "Ração Pedigree Nutrição Essencial",
//                 "Para Cães Adultos de Porte Pequeno",
//                 2l,
//                 150.00,
//         10);

//         Long id = racaoService.insert(racao).id();

//         ProdutoDTO racaoUpdate = new ProdutoDTO(
//                 "Ração Pedigree Nutrição Essencial",
//                 "Para Cães Adultos de Porte Pequeno",
//                 1l,
//                 150.00,
//                 10);

//         given()
//                 .contentType(ContentType.JSON)
//                 .body(racaoUpdate)
//                 .when().put("/produtos/" + id)
//                 .then()
//                 .statusCode(204);

//         ProdutoResponseDTO racaoResponse = racaoService.getById(id);

//         assertThat(racaoResponse.nome(), is("Ração Pedigree Nutrição Essencial"));
//         assertThat(racaoResponse.descricao(), is("Para Cães Adultos de Porte Pequeno"));
//         assertThat(racaoResponse.nomeMarca(), is("Pedigree"));
//         assertThat(racaoResponse.preco(), is(150.0));
//         assertThat(racaoResponse.estoque(), is("Disponível"));

//     }

//     @Test
//     @TestSecurity(user = "testUser", roles = {"Admin"})
//     public void deleteTest() {

//         ProdutoDTO racao = new ProdutoDTO(
//                 "Ração Pedigree Nutrição Essencial",
//                 "Para Cães Adultos de Porte Pequeno",
//                 1l,
//                 150.00,
//                 10);

//         Long id = racaoService.insert(racao).id();

//         given()
//                 .when().delete("/produtos/" + id)
//                 .then()
//                 .statusCode(204);

//         ProdutoResponseDTO racaoResponse = null;

//         try {

//             racaoResponse = racaoService.getById(id);
//         } catch (Exception e) {

//         } finally {
//             assertNull(racaoResponse);
//         }
//     }

//     @Test
//     @TestSecurity(user = "testUser", roles = {"Admin"})
//     public void countTest() {

//         given()
//                 .when().get("/produtos/count")
//                 .then()
//                 .statusCode(200);
//     }

//     @Test
//     public void getByNomeTest() {

//         given()
//                 .when().get("/produtos/searchByNome/" + "Ração Whiskas")
//                 .then()
//                 .statusCode(200);
//     }

//     @Test
//     public void getByMarcaTest() {

//         given()
//                 .when().get("/produtos/searchByMarca/" + "Pedigree")
//                 .then()
//                 .statusCode(200);
//     }

//     @Test
//     public void filterByPrecoMinTest() {

//         given()
//                 .when().get("/produtos/filterByPrecoMin/" + 60.0)
//                 .then()
//                 .statusCode(200);

//     }

//     @Test
//     public void filterByPrecoMaxTest() {

//         given()
//                 .when().get("/produtos/filterByPrecoMax/" + 160.0)
//                 .then()
//                 .statusCode(200);

//     }

//     @Test
//     public void filterByEntrePrecoTest() {

//         given()
//                 .when().get("/produtos/filterByEntrePreco/" + 160.0 + "/" + 60.0)
//                 .then()
//                 .statusCode(200);

//     }

// }
