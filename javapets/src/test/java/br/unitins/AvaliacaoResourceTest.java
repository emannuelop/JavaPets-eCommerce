package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.avaliacao.AvaliacaoDTO;
import br.unitins.ecommerce.dto.avaliacao.AvaliacaoResponseDTO;
import br.unitins.ecommerce.service.avaliacao.AvaliacaoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class AvaliacaoResourceTest {

    @Inject
    AvaliacaoService avaliacaoService;

    @Test
    public void getAllTest() {

        given()
                .when().get("/avaliacoes")
                .then()
                .statusCode(200);
    }

    @Test
    public void getByIdTest() {

        AvaliacaoDTO avaliacao = new AvaliacaoDTO(
                "Gostei demais",
                5,
                4l,
                2l);

        Long id = avaliacaoService.insert(avaliacao).id();

        given()
                .when().get("/avaliacoes/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void insertTest() {

        AvaliacaoDTO avaliacao = new AvaliacaoDTO(
                "Ruim",
                1,
                2l,
                1l);

        given()
                .contentType(ContentType.JSON)
                .body(avaliacao)
                .when().post("/avaliacoes")
                .then()
                .statusCode(201)
                .body("id", notNullValue(), "comentario", is("Ruim"), "estrela.label", is("⭐"), "produto.id", is(2),
                        "produto.nome", is("Ração Golden Fórmula Mini Bits"), "usuario.id", is(1), "usuario.nome",
                        is("João Aguiar"), "usuario.email", is("joao_aguia@gmail.com"));
    }

    @Test
    public void updateTest() {

        AvaliacaoDTO avaliacao = new AvaliacaoDTO(
                "Gostei demais",
                4,
                4l,
                2l);

        Long id = avaliacaoService.insert(avaliacao).id();

        AvaliacaoDTO avaliacaoUpdate = new AvaliacaoDTO(
                "Gostei demais demais",
                5,
                4l,
                2l);

        given()
                .contentType(ContentType.JSON)
                .body(avaliacaoUpdate)
                .when().put("/avaliacoes/" + id)
                .then()
                .statusCode(204);

        AvaliacaoResponseDTO avaliacaoResponse = avaliacaoService.getById(id);

        assertThat(avaliacaoResponse.id(), is(6l));
        assertThat(avaliacaoResponse.comentario(), is("Gostei demais demais"));
        assertThat(avaliacaoResponse.estrela().getLabel(), is("⭐⭐⭐⭐⭐"));
        assertThat(avaliacaoResponse.produto().get("id"), is(4l));
        assertThat(avaliacaoResponse.produto().get("nome"), is("Ração GranPlus Choice"));
        assertThat(avaliacaoResponse.usuario().get("id"), is(2l));
        assertThat(avaliacaoResponse.usuario().get("nome"), is("Maria Fernanda"));
        assertThat(avaliacaoResponse.usuario().get("email"), is("mariaF@gmail.com"));

    }

    @Test
    public void deleteTest() {

        AvaliacaoDTO avaliacao = new AvaliacaoDTO(
                "Gostei demais",
                4,
                4l,
                2l);

        Long id = avaliacaoService.insert(avaliacao).id();

        given()
                .when().delete("/avaliacoes/" + id)
                .then()
                .statusCode(204);

        AvaliacaoResponseDTO avaliacaoResponse = null;

        try {

            avaliacaoResponse = avaliacaoService.getById(id);
        } catch (Exception e) {

        } finally {
            assertNull(avaliacaoResponse);
        }
    }

    @Test
    public void countTest() {

        given()
                .when().get("/avaliacoes/count")
                .then()
                .statusCode(200);
    }

    @Test
    public void getByYearTest() {

        Integer dataAvalicao = 2023;

        given()
                .when().get("/avaliacoes/searchByYear/" + dataAvalicao)
                .then()
                .statusCode(200);
    }

}
