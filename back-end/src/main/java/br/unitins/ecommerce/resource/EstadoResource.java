package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.estado.EstadoDTO;
import br.unitins.ecommerce.dto.estado.EstadoResponseDTO;
import br.unitins.ecommerce.service.estado.EstadoService;
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

@Path("/estados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoService estadoService;

    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

    @GET
    @RolesAllowed({"Admin"})
    public List<EstadoResponseDTO> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("2") int pageSize) {
        LOG.infof("Buscando todos os estados");
        LOG.debug("ERRO DE DEBUG.");
        return estadoService.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    // @RolesAllowed({"Admin"})
    public EstadoResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.infof("Buscando estados por ID. ", id);
        LOG.debug("ERRO DE DEBUG.");
        return estadoService.getById(id);
    }

    @POST
    // @RolesAllowed({"Admin"})
    public Response insert(EstadoDTO estadoDto) {
        LOG.infof("Inserindo um estado: %s", estadoDto.nome());

        EstadoResponseDTO estado = estadoService.insert(estadoDto);
        LOG.infof("Estado (%d) criado com sucesso.", estado.id());
        return Response.status(Status.CREATED).entity(estado).build();

    }

    @PUT
    @Path("/{id}")
    // @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, EstadoDTO estadoDto) {

        LOG.infof("Atualizando um estado: %s", estadoDto.nome());
        estadoService.update(id, estadoDto);
        LOG.infof("Estado (%d) atualizado com sucesso.", id);
        return Response
                .status(Status.NO_CONTENT) // 204
                .build();

    }

    @DELETE
    @Path("/{id}")
    // @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {
        try {
            estadoService.delete(id);
            LOG.infof("Estado excluído com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar um estado: parâmetros inválidos.", e);
            throw e;
        }
    }

    @GET
    @Path("/count")
    // @RolesAllowed({"Admin"})
    public Long count() {
        LOG.infof("Contando todos os estados");
        LOG.debug("ERRO DE DEBUG.");
        return estadoService.count();
    }

    @GET
    @Path("/count/search/{nome}")
    // @RolesAllowed({"Admin"})
    public Long count(@PathParam("nome") String nome) {
        LOG.infof("Contando todos os estados");
        LOG.debug("ERRO DE DEBUG.");
        return estadoService.countByNome(nome);
    }

    @GET
    @Path("/searchByNome/{nome}")
    // @PermitAll
    public List<EstadoResponseDTO> getByNome(@PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("2") int pageSize) throws NullPointerException {
        LOG.infof("Buscando estado pelo nome. ", nome);
        LOG.debug("ERRO DE DEBUG.");
        return estadoService.getByNome(nome, page, pageSize);
    }

    @GET
    @Path("/searchBySigla/{sigla}")
    // @PermitAll
    public List<EstadoResponseDTO> getBySigla(@PathParam("sigla") String sigla) throws NullPointerException {
        LOG.infof("Buscando estado pela sigla. ", sigla);
        LOG.debug("ERRO DE DEBUG.");
        return estadoService.getBySigla(sigla);
    }
}
