package br.unitins.ecommerce.resource;

import java.io.IOException;
import java.util.List;

import org.jboss.logging.Logger;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.produto.ProdutoDTO;
import br.unitins.ecommerce.dto.produto.ProdutoResponseDTO;
import br.unitins.ecommerce.form.ImageForm;
import br.unitins.ecommerce.service.file.FileService;
import br.unitins.ecommerce.service.produto.ProdutoService;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    @Inject
    FileService fileService;

    private static final Logger LOG = Logger.getLogger(ProdutoResource.class);

    @GET
    public List<ProdutoResponseDTO> getAll(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("2") int pageSize
    ) {
        LOG.info("Buscando todas os produtos");
        LOG.debug("ERRO DE DEBUG.");
        return produtoService.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    public ProdutoResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.infof("Buscando produtos por ID. ", id);
        LOG.debug("ERRO DE DEBUG.");
        return produtoService.getById(id);
    }

    @GET
    @Path("/download/{nomeImagem}")
    // @RolesAllowed({ "Admin", "User" })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {

        try {
            ResponseBuilder response = Response.ok(fileService.download(nomeImagem));

            response.header("Content-Disposition", "attachment;filename=" + nomeImagem);
            LOG.infof("Download do arquivo %s concluído com sucesso.", nomeImagem);

            return response.build();

        } catch (Exception e) {
            LOG.errorf("Erro ao realizar o download do arquivo: %s", nomeImagem, e);
            return Response
                    .status(Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @POST
    // @RolesAllowed({"Admin"})
    public Response insert(ProdutoDTO produtoDto) {

        LOG.infof("Inserindo um produto: %s", produtoDto.nome());
        Result result = null;
        try {
            LOG.infof("Produto inserido na lista Desejo.");
            return Response
                    .status(Status.CREATED) // 201
                    .entity(produtoService.insert(produtoDto))
                    .build();

        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um produto.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());

        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);

        }
        return Response
                .status(Status.NOT_FOUND)
                .entity(result)
                .build();
    }

    @PUT
    @Path("/{id}")
    // @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, ProdutoDTO produtoDto) {
        Result result = null;
        try {
            produtoService.update(id, produtoDto);
            LOG.infof("Produto (%d) atualizado com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();

        } catch (ConstraintViolationException e) {
            LOG.errorf("Erro ao atualizar um produto. ", id, e);
            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());

        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);

        }
        return Response
                .status(Status.NOT_FOUND)
                .entity(result)
                .build();
    }

    @PATCH
    @Path("/atualizar-imagem/{id}")
    // @RolesAllowed({ "Admin" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm ImageForm form, @PathParam("id") Long id) {

        String nomeImagem = "";

        try {
            nomeImagem = fileService.salvarImagemUsuario(form.getImagem(), form.getNomeImagem());
            LOG.infof("Imagem salva com sucesso: %s", nomeImagem);

        } catch (IOException e) {
            LOG.error("Erro ao salvar a imagem do produto.", e);
            Result result = new Result(e.getMessage(), false);

            return Response.status(Status.CONFLICT).entity(result).build();
        }

        produtoService.update(id, nomeImagem);

        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @DELETE
    @Path("/{id}")
    // @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException {

        try {
            produtoService.delete(id);
            LOG.infof("Produto excluído com sucesso.", id);

            return Response
                    .status(Status.NO_CONTENT)
                    .build();

        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar produto: parâmetros inválidos.", e);
            throw e;
        }
    }

    @GET
    @Path("/count")
    // @RolesAllowed({"Admin"})
    public Long count() {
        LOG.info("Contando todos os produtos.");
        LOG.debug("ERRO DE DEBUG.");
        return produtoService.count();
    }

    @GET
    @Path("/count/search/{nome}")
    // @RolesAllowed({"Admin"})
    public Long count (@PathParam("nome") String nome) {
        LOG.infof("Contando todos os produtos");
        LOG.debug("ERRO DE DEBUG.");
        return produtoService.countByNome(nome);
    }

    @GET
    @Path("/searchByNome/{nome}")
    @PermitAll
    public List<ProdutoResponseDTO> getByNome(@PathParam("nome") String nome,
    @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("2") int pageSize) {
        LOG.infof("Buscando produto pelo nome. ", nome);
        LOG.debug("ERRO DE DEBUG.");
        return produtoService.getByNome(nome, page, pageSize);
    }

    // @GET
    // @Path("/searchByMarca/{marca}")
    // @PermitAll
    // public List<ProdutoResponseDTO> getByMarca(@PathParam("marca") String nomeMarca) {
    //     LOG.infof("Buscando pelo nome da marca. ", nomeMarca);
    //     LOG.debug("ERRO DE DEBUG.");
    //     return produtoService.getByMarca(nomeMarca);
    // }

    // @GET
    // @Path("/filterByPrecoMin/{precoMin}")
    // @PermitAll
    // public List<ProdutoResponseDTO> filterByPrecoMin(@PathParam("precoMin") Double preco) {
    //     LOG.infof("Filtrando pelo preço mínimo. ", preco);
    //     LOG.debug("ERRO DE DEBUG.");
    //     return produtoService.filterByPrecoMin(preco);
    // }

    // @GET
    // @Path("/filterByPrecoMax/{precoMax}")
    // @PermitAll
    // public List<ProdutoResponseDTO> filterByPrecoMax(@PathParam("precoMax") Double preco) {
    //     LOG.infof("Filtrando pelo preço máximo. ", preco);
    //     LOG.debug("ERRO DE DEBUG.");
    //     return produtoService.filterByPrecoMax(preco);
    // }

    // @GET
    // @Path("/filterByEntrePreco/{precoMin}/{precoMax}")
    // @PermitAll
    // public List<ProdutoResponseDTO> filterByEntrePreco(@PathParam("precoMin") Double precoMin,
    //         @PathParam("precoMax") Double precoMax) {
    //     LOG.infof("Filtrando entre os preços mínimo e máximo. ", precoMin, " e ", precoMax);
    //     LOG.debug("ERRO DE DEBUG.");
    //     return produtoService.filterByEntrePreco(precoMin, precoMax);
    // }

}
