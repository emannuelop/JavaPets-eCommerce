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
import br.unitins.ecommerce.dto.compra.CupomDescontoDTO;
import br.unitins.ecommerce.dto.compra.CupomDescontoResponseDTO;
import br.unitins.ecommerce.service.compra.CupomDescontoService;

@Path("/cupomDescontos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CupomDescontoResource {

    @Inject
    CupomDescontoService cupomDescontoService;

    private static final Logger LOG = Logger.getLogger(CupomDescontoResource.class);

    @GET
    // @PermitAll
    public List<CupomDescontoResponseDTO> getAll(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("2") int pageSize) 
        {
        LOG.info("Buscando todos os cupomDesconto.");
        LOG.debug("ERRO DE DEBUG.");
        return cupomDescontoService.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    // @RolesAllowed({"Admin"})
    public CupomDescontoResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.info("Buscando município por ID: " + id);
        LOG.debug("ERRO DE DEBUG.");
        return cupomDescontoService.getById(id);
    }

    @POST
    // @RolesAllowed({"Admin"})
    public Response insert(CupomDescontoDTO cupomDescontoDto) {
        LOG.infof("Inserindo um cupomDesconto: %s", cupomDescontoDto.codigoCupom());

            CupomDescontoResponseDTO cupomDesconto = cupomDescontoService.insert(cupomDescontoDto);

            LOG.infof("CupomDesconto (%d) criado com sucesso.", cupomDesconto.id());

            return Response.status(Status.CREATED).entity(cupomDesconto).build();

    }

    @PUT
    @Path("/{id}")
    // @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, CupomDescontoDTO cupomDescontoDto) {
            cupomDescontoService.update(id, cupomDescontoDto);
            LOG.infof("cupomDesconto (%d) atualizado com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();
        
    }

    @DELETE
    @Path("/{id}")
    // @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {

        try {
            cupomDescontoService.delete(id);
            LOG.infof("cupomDesconto (%d) excluído com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar cupomDesconto: parâmetros inválidos.", e);
            throw e;
        } catch (NotFoundException e) {
            LOG.errorf("cupomDesconto (%d) não encontrado.", id);
            throw e;
        }
    }

    @GET
    @Path("/searchByNome/{nome}")
    // @PermitAll
    public List<CupomDescontoResponseDTO> getByNome(@PathParam("nome") String nome,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("2") int pageSize
        ) throws NullPointerException 
        {
        LOG.infof("Pesquisando cupomDesconto pelo nome.", nome);
        LOG.debug("ERRO DE DEBUG.");
        return cupomDescontoService.getByNome(nome, page, pageSize);
    }

    @GET
    @Path("/count")
    // @RolesAllowed({"Admin"})
    public Long count() {
        LOG.info("Contando todos os cupomDescontos.");
        LOG.debug("ERRO DE DEBUG.");
        return cupomDescontoService.count();
    }

    @GET
    @Path("/count/search/{nome}")
    // @RolesAllowed({"Admin"})
    public Long count (@PathParam("nome") String nome) {
        LOG.infof("Contando todos os cupomDesconto");
        LOG.debug("ERRO DE DEBUG.");
        return cupomDescontoService.countByNome(nome);
    }
}
