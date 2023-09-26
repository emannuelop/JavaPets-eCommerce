package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.produto.PetDTO;
import br.unitins.ecommerce.dto.produto.PetResponseDTO;
import br.unitins.ecommerce.service.produto.PetService;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    PetService petService;

    private static final Logger LOG = Logger.getLogger(PetResource.class);

    @GET
    @PermitAll
    public List<PetResponseDTO> getAll() {
        LOG.info("Buscando todos os pet.");
        LOG.debug("ERRO DE DEBUG.");
        return petService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public PetResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.info("Buscando município por ID: " + id);
        LOG.debug("ERRO DE DEBUG.");
        return petService.getById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(PetDTO petDto) {
        LOG.infof("Inserindo um pet: %s", petDto.nome());

        Result result = null;

        try {
            PetResponseDTO pet = petService.insert(petDto);

            LOG.infof("Pet (%d) criado com sucesso.", pet.id());

            return Response.status(Status.CREATED).entity(pet).build();

        } catch (ConstraintViolationException e) {

            LOG.error("Erro ao incluir um pet.");

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
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, PetDTO petDto) {
        Result result = null;
        
        try {
            petService.update(id, petDto);
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
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {

        try {
            petService.delete(id);
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
    @RolesAllowed({"Admin"})
    public Long count() {
        LOG.info("Contando todos os pets.");
        LOG.debug("ERRO DE DEBUG.");
        return petService.count();
    }

    @GET
    @Path("/searchByNome/{nome}")
    @PermitAll
    public List<PetResponseDTO> getByNome(@PathParam("nome") String nome) throws NullPointerException {
        LOG.infof("Pesquisando Município po nome.", nome);
        LOG.debug("ERRO DE DEBUG.");
        return petService.getByNome(nome);
    }
}
