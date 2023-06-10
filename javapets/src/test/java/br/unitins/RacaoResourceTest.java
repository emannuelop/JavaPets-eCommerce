package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.racao.RacaoDTO;
import br.unitins.ecommerce.dto.racao.RacaoResponseDTO;
import br.unitins.ecommerce.service.racao.RacaoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
public class RacaoResourceTest {

    @Inject
    RacaoService racaoService;

    @Test
    public void getAllTest() {

        given()
                .when().get("/racoes")
                .then()
                .statusCode(200);
    }

    @Test
    public void getByIdTest() {

        given()
                .when().get("/racoes/" + 1)
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void insertTest() {

        RacaoDTO racao = new RacaoDTO(
                "Ração Pedigree Nutrição Essencial",
                "Para Cães Adultos de Porte Pequeno",
                2l,
                150.00,
                10,
                15.00,
                "Carne",
                2);

        given()
                .contentType(ContentType.JSON)
                .body(racao)
                .when().post("/racoes")
                .then()
                .statusCode(201)
                .body("id", notNullValue(), "nome", is("Ração Pedigree Nutrição Essencial"), "descricao",
                        is("Para Cães Adultos de Porte Pequeno"), "nomeMarca", is("GoldeN"), "preco", is(150.0F),
                        "estoque", is("Disponível"), "quantidadeQuilos", is(15.0F), "sabor", is("Carne"),
                        "escolhaAnimal.label", is("Cachorro"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"User"})
    public void insertForbiddenTest() {

        RacaoDTO racao = new RacaoDTO(
                "Ração Pedigree Nutrição Essencial",
                "Para Cães Adultos de Porte Pequeno",
                2l,
                150.00,
                10,
                15.00,
                "Carne",
                2);

        given()
                .contentType(ContentType.JSON)
                .body(racao)
                .when().post("/racoes")
                .then()
                .statusCode(403);
    }

    @Test
    public void insertUnauthorizedTest() {

        RacaoDTO racao = new RacaoDTO(
                "Ração Pedigree Nutrição Essencial",
                "Para Cães Adultos de Porte Pequeno",
                2l,
                150.00,
                10,
                15.00,
                "Carne",
                2);

        given()
                .contentType(ContentType.JSON)
                .body(racao)
                .when().post("/racoes")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void updateTest() {

        RacaoDTO racao = new RacaoDTO(
                "Ração Pedigree Nutrição Essencial",
                "Para Cães Adultos de Porte Pequeno",
                2l,
                150.00,
                10,
                15.00,
                "Carne",
                2);

        Long id = racaoService.insert(racao).id();

        RacaoDTO racaoUpdate = new RacaoDTO(
                "Ração Pedigree Nutrição Essencial",
                "Para Cães Adultos de Porte Pequeno",
                1l,
                150.00,
                10,
                15.00,
                "Frango",
                2);

        given()
                .contentType(ContentType.JSON)
                .body(racaoUpdate)
                .when().put("/racoes/" + id)
                .then()
                .statusCode(204);

        RacaoResponseDTO racaoResponse = racaoService.getById(id);

        assertThat(racaoResponse.nome(), is("Ração Pedigree Nutrição Essencial"));
        assertThat(racaoResponse.descricao(), is("Para Cães Adultos de Porte Pequeno"));
        assertThat(racaoResponse.nomeMarca(), is("Pedigree"));
        assertThat(racaoResponse.preco(), is(150.0));
        assertThat(racaoResponse.estoque(), is("Disponível"));
        assertThat(racaoResponse.quantidadeQuilos(), is(15.0));
        assertThat(racaoResponse.sabor(), is("Frango"));
        assertThat(racaoResponse.escolhaAnimal().getLabel(), is("Cachorro"));

    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void deleteTest() {

        RacaoDTO racao = new RacaoDTO(
                "Ração Pedigree Nutrição Essencial",
                "Para Cães Adultos de Porte Pequeno",
                1l,
                150.00,
                10,
                15.00,
                "Frango",
                2);

        Long id = racaoService.insert(racao).id();

        given()
                .when().delete("/racoes/" + id)
                .then()
                .statusCode(204);

        RacaoResponseDTO racaoResponse = null;

        try {

            racaoResponse = racaoService.getById(id);
        } catch (Exception e) {

        } finally {
            assertNull(racaoResponse);
        }
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void countTest() {

        given()
                .when().get("/racoes/count")
                .then()
                .statusCode(200);
    }

    @Test
    public void getByNomeTest() {

        given()
                .when().get("/racoes/searchByNome/" + "Ração Whiskas")
                .then()
                .statusCode(200);
    }

    @Test
    public void getByMarcaTest() {

        given()
                .when().get("/racoes/searchByMarca/" + "Pedigree")
                .then()
                .statusCode(200);
    }

    @Test
    public void getByEscolhaAnimalTest() {

        given()
                .when().get("/racoes/searchByEscolhaAnimal/" + 1)
                .then()
                .statusCode(200);
    }

    @Test
    public void filterByPrecoMinTest() {

        given()
                .when().get("/racoes/filterByPrecoMin/" + 60.0)
                .then()
                .statusCode(200);

    }

    @Test
    public void filterByPrecoMaxTest() {

        given()
                .when().get("/racoes/filterByPrecoMax/" + 160.0)
                .then()
                .statusCode(200);

    }

    @Test
    public void filterByEntrePrecoTest() {

        given()
                .when().get("/racoes/filterByEntrePreco/" + 160.0 + "/" + 60.0)
                .then()
                .statusCode(200);

    }

}
