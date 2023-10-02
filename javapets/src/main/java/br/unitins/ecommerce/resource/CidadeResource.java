package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.municipio.CidadeDTO;
import br.unitins.ecommerce.dto.municipio.CidadeResponseDTO;
import br.unitins.ecommerce.service.municipio.CidadeService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/cidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CidadeResource {

    @Inject
    CidadeService cidadeService;

    private static final Logger LOG = Logger.getLogger(CidadeResource.class);

    @GET
    public List<CidadeResponseDTO> getAll(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("2") int pageSize) {
        LOG.info("Buscando todas os cidades.");
        LOG.debug("ERRO DE DEBUG.");
        return cidadeService.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    public CidadeResponseDTO findById(@PathParam("id") Long id) {
        return cidadeService.findById(id);
    }

    @POST
    public Response insert(CidadeDTO dto) {
        LOG.infof("Inserindo uma cidade: %s", dto.nome());
        Result result = null;
        try {
            CidadeResponseDTO cidade = cidadeService.create(dto);
            LOG.infof("Cidade (%d) criada com sucesso.", cidade.id());
            return Response.status(Status.CREATED).entity(cidade).build();
        } catch(ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma cidade.");
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
    public Response update(@PathParam("id") Long id, CidadeDTO dto) {
        try {
            CidadeResponseDTO cidade = cidadeService.update(id, dto);
            return Response.ok(cidade).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }      
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        cidadeService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count(){
        return cidadeService.count();
    }

    @GET
    @Path("/count/search/{nome}")
    // @RolesAllowed({"Admin"})
    public Long count (@PathParam("nome") String nome) {
        LOG.infof("Contando todos as cidades");
        LOG.debug("ERRO DE DEBUG.");
        return cidadeService.countByNome(nome);
    }

    @GET
    @Path("/search/{nome}")
    public List<CidadeResponseDTO> search(@PathParam("nome") String nome,
    @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("2") int pageSize){
        return cidadeService.findByNome(nome, page, pageSize);
        
    }
}
