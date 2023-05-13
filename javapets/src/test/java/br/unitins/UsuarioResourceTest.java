package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.usuario.PessoaFisicaDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.model.usuario.Perfil;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.service.hash.HashService;
import br.unitins.ecommerce.service.token.TokenJwtService;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
public class UsuarioResourceTest {

    @Inject
    UsuarioService usuarioService;

    @Inject
    HashService hashService;

    @Inject
    TokenJwtService tokenJwtService;

    @Test
    public void getAllTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Danilo Da Silva",
                "89912402394",
                "Danil789@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        usuarioService.insert(usuarioDto);

        Usuario usuario = usuarioService.getByLoginAndSenha("Danilo", "senha1234");

        Set<Perfil> perfis = new HashSet<>();

        perfis.add(Perfil.ADMIN);

        usuario.setPerfis(perfis);

        String token = tokenJwtService.generateJwt(usuario);

        given()
                .header("Authorization", "Bearer " + token) // Adiciona o cabeçalho de autenticação
                .when()
                .get("/usuarios")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = { "Admin", "User" })
    public void insertTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Danilo Da Silva",
                "89912376394",
                "DaniloDaSi@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        given()
                .contentType(ContentType.JSON)
                .body(usuarioDto)
                .when().post("/usuarios")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "nome", is("Danilo Da Silva"),
                        "email", is("DaniloDaSi@unitins.br"),
                        "cpf", is("89912376394"),
                        "endereco.logradouro", is("Avenida Tocantins"),
                        "endereco.bairro", is("Setor Bueno"),
                        "endereco.numero", is("8780"),
                        "endereco.complemento", is("apto 3"),
                        "endereco.cep", is("77780-920"),
                        "endereco.municipio.nome", is("Belém"),
                        "endereco.municipio.estado.nome", is("Pará"),
                        "endereco.municipio.estado.sigla", is("PA"),
                        "telefonePrincipal.codigoDeArea", is("067"),
                        "telefonePrincipal.numero", is("98467-8901"),
                        "telefoneOpcional.codigoDeArea", is("067"),
                        "telefoneOpcional.numero", is("4002-8922"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = { "Admin", "User" })
    public void updateTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Danilo Da Silva",
                "21012376094",
                "DanilDaSi722@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        Long id = usuarioService.insert(usuarioDto).id();

        PessoaFisicaDTO updatePessoaFisicaDTO = new PessoaFisicaDTO(
                "Erick Santos",
                "64702973802",
                "ErickSantos@unitins.br",
                1);

        EnderecoDTO updateEnderecoDTO = new EnderecoDTO(
                "Avenida Juscelino Kubistchek",
                "Setor Alvorada",
                "8901",
                "apto 1",
                "77572-020",
                4l);

        TelefoneDTO updateTelefonePrincipalDTO = new TelefoneDTO("067", "96821-0293");

        TelefoneDTO updateTelefoneOpcionalDTO = new TelefoneDTO("011", "97202-9313");

        UsuarioDTO updateUsuarioDto = new UsuarioDTO(
                "ErickNicolas",
                "conteudoA2",
                updatePessoaFisicaDTO,
                updateEnderecoDTO,
                updateTelefonePrincipalDTO,
                updateTelefoneOpcionalDTO);

        given()
                .contentType(ContentType.JSON)
                .body(updateUsuarioDto)
                .when().put("/usuarios/" + id)
                .then()
                .statusCode(204);

        UsuarioResponseDTO usuarioResponse = usuarioService.getById(id);

        Map<String, Object> municipio = (Map<String, Object>) usuarioResponse.endereco().get("municipio");

        Map<String, Object> estado = (Map<String, Object>) municipio.get("estado");

        assertThat(usuarioResponse.nome(), is("Erick Santos"));
        assertThat(usuarioResponse.email(), is("ErickSantos@unitins.br"));
        assertThat(usuarioResponse.cpf(), is("64702973802"));
        assertThat(usuarioResponse.endereco().get("logradouro"), is("Avenida Juscelino Kubistchek"));
        assertThat(usuarioResponse.endereco().get("bairro"), is("Setor Alvorada"));
        assertThat(usuarioResponse.endereco().get("numero"), is("8901"));
        assertThat(usuarioResponse.endereco().get("complemento"), is("apto 1"));
        assertThat(usuarioResponse.endereco().get("cep"), is("77572-020"));
        assertThat(municipio.get("nome"), is("Belém"));
        assertThat(estado.get("nome"), is("Pará"));
        assertThat(estado.get("sigla"), is("PA"));
        assertThat(usuarioResponse.telefonePrincipal().get("codigoDeArea"), is("067"));
        assertThat(usuarioResponse.telefonePrincipal().get("numero"), is("96821-0293"));
        assertThat(usuarioResponse.telefoneOpcional().get("codigoDeArea"), is("011"));
        assertThat(usuarioResponse.telefoneOpcional().get("numero"), is("97202-9313"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = { "Admin", "User" })
    public void updateTelefonePrincipalTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Emannuel",
                "19012346084",
                "Emannuel2209@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        Long id = usuarioService.insert(usuarioDto).id();

        TelefoneDTO updateTelefonePrincipalDTO = new TelefoneDTO("063", "98513-5709");

        given()
                .contentType(ContentType.JSON)
                .body(updateTelefonePrincipalDTO)
                .when().patch("/usuarios/update_telefone_principal/" + id)
                .then()
                .statusCode(204);

        UsuarioResponseDTO usuarioResponse = usuarioService.getById(id);

        Map<String, Object> municipio = (Map<String, Object>) usuarioResponse.endereco().get("municipio");

        Map<String, Object> estado = (Map<String, Object>) municipio.get("estado");

        assertThat(usuarioResponse.nome(), is("Emannuel"));
        assertThat(usuarioResponse.email(), is("Emannuel2209@unitins.br"));
        assertThat(usuarioResponse.cpf(), is("19012346084"));
        assertThat(usuarioResponse.endereco().get("logradouro"), is("Avenida Tocantins"));
        assertThat(usuarioResponse.endereco().get("bairro"), is("Setor Bueno"));
        assertThat(usuarioResponse.endereco().get("numero"), is("8780"));
        assertThat(usuarioResponse.endereco().get("complemento"), is("apto 3"));
        assertThat(usuarioResponse.endereco().get("cep"), is("77780-920"));
        assertThat(municipio.get("nome"), is("Belém"));
        assertThat(estado.get("nome"), is("Pará"));
        assertThat(estado.get("sigla"), is("PA"));
        assertThat(usuarioResponse.telefonePrincipal().get("codigoDeArea"), is("063"));
        assertThat(usuarioResponse.telefonePrincipal().get("numero"), is("98513-5709"));
        assertThat(usuarioResponse.telefoneOpcional().get("codigoDeArea"), is("067"));
        assertThat(usuarioResponse.telefoneOpcional().get("numero"), is("4002-8922"));

    }

    @Test
    @TestSecurity(user = "testUser", roles = { "Admin", "User" })
    public void updateEnderecoTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Hitalo",
                "89412316094",
                "Hitalo@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        Long id = usuarioService.insert(usuarioDto).id();

        EnderecoDTO updateEnderecoDTO = new EnderecoDTO(
                "Avenida Juscelino Kubistchek",
                "Setor Alvorada",
                "8901",
                "apto 1",
                "77572-020",
                4l);

        given()
                .contentType(ContentType.JSON)
                .body(updateEnderecoDTO)
                .when().patch("/usuarios/update_endereco/" + id)
                .then()
                .statusCode(204);

        UsuarioResponseDTO usuarioResponse = usuarioService.getById(id);

        Map<String, Object> municipio = (Map<String, Object>) usuarioResponse.endereco().get("municipio");

        Map<String, Object> estado = (Map<String, Object>) municipio.get("estado");

        assertThat(usuarioResponse.nome(), is("Hitalo"));
        assertThat(usuarioResponse.email(), is("Hitalo@unitins.br"));
        assertThat(usuarioResponse.cpf(), is("89412316094"));
        assertThat(usuarioResponse.endereco().get("logradouro"), is("Avenida Juscelino Kubistchek"));
        assertThat(usuarioResponse.endereco().get("bairro"), is("Setor Alvorada"));
        assertThat(usuarioResponse.endereco().get("numero"), is("8901"));
        assertThat(usuarioResponse.endereco().get("complemento"), is("apto 1"));
        assertThat(usuarioResponse.endereco().get("cep"), is("77572-020"));
        assertThat(municipio.get("nome"), is("Belém"));
        assertThat(estado.get("nome"), is("Pará"));
        assertThat(estado.get("sigla"), is("PA"));
        assertThat(usuarioResponse.telefonePrincipal().get("codigoDeArea"), is("067"));
        assertThat(usuarioResponse.telefonePrincipal().get("numero"), is("98467-8901"));
        assertThat(usuarioResponse.telefoneOpcional().get("codigoDeArea"), is("067"));
        assertThat(usuarioResponse.telefoneOpcional().get("numero"), is("4002-8922"));

    }

    @Test
    @TestSecurity(user = "testUser", roles = { "Admin", "User" })
    public void deleteTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Danilo Da Silva",
                "89042376391",
                "Danilojasdfns@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        Long id = usuarioService.insert(usuarioDto).id();

        given()
                .when().delete("/usuarios/" + id)
                .then()
                .statusCode(204);

        UsuarioResponseDTO usuarioResponse = null;

        try {

            usuarioResponse = usuarioService.getById(id);
        } catch (Exception e) {

        } finally {
            assertNull(usuarioResponse);
        }
    }

    @Test
    @TestSecurity(user = "testUser", roles = { "Admin" })
    public void countTest() {

        given()
                .when().get("/usuarios/count")
                .then()
                .statusCode(200);
    }

    @Test
    public void getByIdTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Danilo Da Silva",
                "89012376394",
                "DaniloDaSilva@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        Long id = usuarioService.insert(usuarioDto).id();

        Usuario usuario = usuarioService.getByLoginAndSenha("Danilo", "senha1234");

        Set<Perfil> perfis = new HashSet<>();

        perfis.add(Perfil.ADMIN);

        usuario.setPerfis(perfis);

        String token = tokenJwtService.generateJwt(usuario);

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/usuarios/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void getByNomeTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Danilo Da Silva",
                "89012376794",
                "DaniloDaSilv@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        String nome = usuarioService.insert(usuarioDto).nome();

        Usuario usuario = usuarioService.getByLoginAndSenha("Danilo", "senha1234");

        Set<Perfil> perfis = new HashSet<>();

        perfis.add(Perfil.ADMIN);

        usuario.setPerfis(perfis);

        String token = tokenJwtService.generateJwt(usuario);

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/usuarios/searchByNome/" + nome)
                .then()
                .statusCode(200);
    }

    @Test
    public void getListaDesejoTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Danilo Da Silva",
                "89076237639",
                "DaniloDaSil@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        Long idUsuario = usuarioService.insert(usuarioDto).id();

        ListaDesejoDTO listaDesejoDTO = new ListaDesejoDTO(idUsuario, 2l);
        ListaDesejoDTO listaDesejoDTO2 = new ListaDesejoDTO(idUsuario, 5l);

        usuarioService.insertListaDesejo(listaDesejoDTO);
        usuarioService.insertListaDesejo(listaDesejoDTO2);

        Usuario usuario = usuarioService.getByLoginAndSenha("Danilo", "senha1234");

        Set<Perfil> perfis = new HashSet<>();

        perfis.add(Perfil.ADMIN);

        usuario.setPerfis(perfis);

        String token = tokenJwtService.generateJwt(usuario);

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/usuarios/lista_desejo/" + idUsuario)
                .then()
                .statusCode(200);
    }

    @Test
    public void insertListaDesejoTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Danilo Da Silva",
                "89912376396",
                "Danilo123@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        Long idUsuario = usuarioService.insert(usuarioDto).id();

        ListaDesejoDTO listaDesejoDTO = new ListaDesejoDTO(idUsuario, 1l);

        Usuario usuario = usuarioService.getByLoginAndSenha("Danilo", "senha1234");

        Set<Perfil> perfis = new HashSet<>();

        perfis.add(Perfil.ADMIN);

        usuario.setPerfis(perfis);

        String token = tokenJwtService.generateJwt(usuario);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(listaDesejoDTO)
                .when().post("/usuarios/lista_desejo")
                .then()
                .statusCode(201);

        ListaDesejoResponseDTO listaResponse = usuarioService.getListaDesejo(idUsuario);

        assertThat(listaResponse.usuario().get("id"), is(idUsuario));
        assertThat(listaResponse.usuario().get("login"), is("Danilo"));
        assertThat(listaResponse.usuario().get("email"), is("Danilo123@unitins.br"));
        assertThat(listaResponse.usuario().get("email"), is("Danilo123@unitins.br"));
        assertThat(listaResponse.produtos().get(0).get("id"), is(1l));
        assertThat(listaResponse.produtos().get(0).get("nome"), is("Ração Pedigree Nutrição Essencial"));
    }

    @Test
    public void deleteProdutoFromListaDesejoTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Danilo Da Silva",
                "89012376391",
                "DaniloDaS@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        Long idUsuario = usuarioService.insert(usuarioDto).id();

        ListaDesejoDTO listaDesejoDTO = new ListaDesejoDTO(idUsuario, 4l);
        ListaDesejoDTO listaDesejoDTO2 = new ListaDesejoDTO(idUsuario, 3l);

        usuarioService.insertListaDesejo(listaDesejoDTO);
        usuarioService.insertListaDesejo(listaDesejoDTO2);

        Usuario usuario = usuarioService.getByLoginAndSenha("Danilo", "senha1234");

        Set<Perfil> perfis = new HashSet<>();

        perfis.add(Perfil.ADMIN);

        usuario.setPerfis(perfis);

        String token = tokenJwtService.generateJwt(usuario);

        given()
                .header("Authorization", "Bearer " + token)
                .pathParam("idUsuario", idUsuario)
                .pathParam("idProduto", 3l)
                .when().delete("/usuarios/lista_desejo/{idUsuario}/{idProduto}")
                .then()
                .statusCode(204);

        ListaDesejoResponseDTO listaResponse = null;

        Boolean ifProdutoRemovido = false;

        listaResponse = usuarioService.getListaDesejo(idUsuario);

        for (Map<String, Object> produto : listaResponse.produtos()) {

            if (produto.get("id") == (Object) 3l) {

                ifProdutoRemovido = true;
            }
        }

        assertFalse(ifProdutoRemovido);
    }

    @Test
    public void countListaDesejoTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                "Danilo Da Silva",
                "89012376491",
                "Danilo098@unitins.br",
                1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Avenida Tocantins",
                "Setor Bueno",
                "8780",
                "apto 3",
                "77780-920",
                4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        Long idUsuario = usuarioService.insert(usuarioDto).id();

        Usuario usuario = usuarioService.getByLoginAndSenha("Danilo", "senha1234");

        Set<Perfil> perfis = new HashSet<>();

        perfis.add(Perfil.ADMIN);

        usuario.setPerfis(perfis);

        String token = tokenJwtService.generateJwt(usuario);

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/usuarios/lista_desejo/count/" + idUsuario)
                .then()
                .statusCode(200);
    }
}
