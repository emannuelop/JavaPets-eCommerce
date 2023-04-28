package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.racao.RacaoDTO;
import br.unitins.ecommerce.service.racao.RacaoService;

import javax.inject.Inject;

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

        Long id = 1l;

        given()
                .when().get("/racoes/" + id)
                .then()
                .statusCode(200);
    }

}
