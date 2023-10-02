package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.produto.CategoriaDTO;
import br.unitins.ecommerce.dto.produto.CategoriaResponseDTO;
import br.unitins.ecommerce.service.produto.CategoriaService;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    private static final Logger LOG = Logger.getLogger(CategoriaResource.class);

    @GET
    @PermitAll
    public List<CategoriaResponseDTO> getAll(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("2") int pageSize) {
        LOG.info("Buscando todos os categoria.");
        LOG.debug("ERRO DE DEBUG.");
        return categoriaService.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    // @RolesAllowed({"Admin"})
    public CategoriaResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.info("Buscando município por ID: " + id);
        LOG.debug("ERRO DE DEBUG.");
        return categoriaService.getById(id);
    }

    @POST
    // @RolesAllowed({"Admin"})
    public Response insert(CategoriaDTO categoriaDto) {
        LOG.infof("Inserindo um categoria: %s", categoriaDto.nome());

        Result result = null;

        try {
            CategoriaResponseDTO categoria = categoriaService.insert(categoriaDto);

            LOG.infof("Categoria (%d) criado com sucesso.", categoria.id());

            return Response.status(Status.CREATED).entity(categoria).build();

        } catch (ConstraintViolationException e) {

            LOG.error("Erro ao incluir um categoria.");

            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());

        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());

            result = new Result(e.getMessage(), false);
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    // @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, CategoriaDTO categoriaDto) {
        Result result = null;
        
        try {
            categoriaService.update(id, categoriaDto);
            LOG.infof("Município (%d) atualizado com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro de validação ao atualizar o município.", e);
            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());

        } catch (Exception e) {
            LOG.fatal("Erro ao atualizar o município " + id + ".", e);
            result = new Result(e.getMessage(), false);
    
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @Path("/{id}")
    // @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {

        try {
            categoriaService.delete(id);
            LOG.infof("Município (%d) excluído com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar município: parâmetros inválidos.", e);
            throw e;
        } catch (NotFoundException e) {
            LOG.errorf("Município (%d) não encontrado.", id);
            throw e;
        }
    }

    @GET
    @Path("/count")
    // @RolesAllowed({"Admin"})
    public Long count() {
        LOG.info("Contando todos os categorias.");
        LOG.debug("ERRO DE DEBUG.");
        return categoriaService.count();
    }

    @GET
    @Path("/count/search/{nome}")
    // @RolesAllowed({"Admin"})
    public Long count (@PathParam("nome") String nome) {
        LOG.infof("Contando todos os categorias");
        LOG.debug("ERRO DE DEBUG.");
        return categoriaService.countByNome(nome);
    }

    @GET
    @Path("/searchByNome/{nome}")
    @PermitAll
    public List<CategoriaResponseDTO> getByNome(@PathParam("nome") String nome,
    @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("2") int pageSize) throws NullPointerException {
        LOG.infof("Pesquisando Categoria por nome.", nome);
        LOG.debug("ERRO DE DEBUG.");
        return categoriaService.getByNome(nome, page, pageSize);
    }
}
