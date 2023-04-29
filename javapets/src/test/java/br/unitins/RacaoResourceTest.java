package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.racao.RacaoDTO;
import br.unitins.ecommerce.dto.racao.RacaoResponseDTO;
import br.unitins.ecommerce.service.racao.RacaoService;
import io.quarkus.test.junit.QuarkusTest;
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

        RacaoDTO racao = new RacaoDTO(
                "Ração Pedigree Nutrição Essencial",
                "Para Cães Adultos de Porte Pequeno",
                2l,
                150.00,
                10,
                15.00,
                "Carne",
                1);

        Long id = racaoService.insert(racao).id();

        given()
                .when().get("/racoes/" + id)
                .then()
                .statusCode(200);
    }

    @Test
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
    public void countTest() {

        given()
                .when().get("/racoes/count")
                .then()
                .statusCode(200);
    }

    @Test
    public void getByNomeTest() {

        RacaoDTO racao = new RacaoDTO(
                "Ração Pedigree Nutrição Essencial",
                "Para Cães Adultos de Porte Pequeno",
                1l,
                150.00,
                10,
                15.00,
                "Frango",
                2);

        String nome = racaoService.insert(racao).nome();

        given()
                .when().get("/racoes/searchByNome/" + nome)
                .then()
                .statusCode(200);
    }

    @Test
    public void getByEscolhaAnimalTest() {

        RacaoDTO racao = new RacaoDTO(
                "Ração Pedigree Nutrição Essencial",
                "Para Cães Adultos de Porte Pequeno",
                1l,
                150.00,
                10,
                15.00,
                "Frango",
                2);

        Integer animal = racaoService.insert(racao).escolhaAnimal().getId();

        given()
                .when().get("/racoes/searchByEscolhaAnimal/" + animal)
                .then()
                .statusCode(200);
    }

    @Test
    public void getByMarcaTest() {

        RacaoDTO racao = new RacaoDTO(
                "Ração Pedigree Nutrição Essencial",
                "Para Cães Adultos de Porte Pequeno",
                1l,
                150.00,
                10,
                15.00,
                "Frango",
                2);

        String marca = racaoService.insert(racao).nomeMarca();

        given()
                .when().get("/racoes/searchByMarca/" + marca)
                .then()
                .statusCode(200);
    }

    @Test
    public void filterByPrecoMinTest() {

        Double precoMinimo = 60.0;

        given()
                .when().get("/racoes/filterByPrecoMin/" + precoMinimo)
                .then()
                .statusCode(200);

    }

    @Test
    public void filterByPrecoMaxTest() {

        Double precoMaximo = 160.0;

        given()
                .when().get("/racoes/filterByPrecoMax/" + precoMaximo)
                .then()
                .statusCode(200);

    }

    @Test
    public void filterByEntrePrecoTest() {

        Double precoMaximo = 160.0;
        Double precoMinimo = 60.0;

        given()
                .when().get("/racoes/filterByEntrePreco/" + precoMaximo + "/" + precoMinimo)
                .then()
                .statusCode(200);

    }

}
