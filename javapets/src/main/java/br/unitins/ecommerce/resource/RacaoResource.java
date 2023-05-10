package br.unitins.ecommerce.resource;

import java.util.List;

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
import br.unitins.ecommerce.dto.racao.RacaoDTO;
import br.unitins.ecommerce.dto.racao.RacaoResponseDTO;
import br.unitins.ecommerce.service.racao.RacaoService;

@Path("/racoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RacaoResource {
    
    @Inject
    RacaoService racaoService;

    @GET
    public List<RacaoResponseDTO> getAll() {

        return racaoService.getAll();
    }

    @GET
    @Path("/{id}")
    public RacaoResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {

        return racaoService.getById(id);
    }

    @POST
    public Response insert(RacaoDTO racaoDto) {

        try {

            return Response
                    .status(Status.CREATED) // 201
                    .entity(racaoService.insert(racaoDto))
                    .build();
        } catch (ConstraintViolationException e) {

            Result result = new Result(e.getConstraintViolations());

            return Response
                    .status(Status.NOT_FOUND)
                    .entity(result)
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, RacaoDTO racaoDto) {

        try {

            racaoService.update(id, racaoDto);

            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();
        } catch (ConstraintViolationException e) {

            Result result = new Result(e.getConstraintViolations());

            return Response
                    .status(Status.NOT_FOUND)
                    .entity(result)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException {

        racaoService.delete(id);

        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/count")
    public Long count() {

        return racaoService.count();
    }

    @GET
    @Path("/searchByNome/{nome}")
    public List<RacaoResponseDTO> getByNome(@PathParam("nome") String nome) {

        return racaoService.getByNome(nome);
    }

    @GET
    @Path("/searchByEscolhaAnimal/{escolha_animal}")
    public List<RacaoResponseDTO> getByEscolhaAnimal(@PathParam("escolha_animal") Integer id) throws IndexOutOfBoundsException {

        return racaoService.getByEscolhaAnimal(id);
    }

    @GET
    @Path("/searchByMarca/{marca}")
    public List<RacaoResponseDTO> getByMarca (@PathParam("marca") String nomeMarca) {

        return racaoService.getByMarca(nomeMarca);
    }

    @GET
    @Path("/filterByPrecoMin/{precoMin}")
    public List<RacaoResponseDTO> filterByPrecoMin (@PathParam("precoMin") Double preco) {

        return racaoService.filterByPrecoMin(preco);
    }

    @GET
    @Path("/filterByPrecoMax/{precoMax}")
    public List<RacaoResponseDTO> filterByPrecoMax (@PathParam("precoMax") Double preco) {

        return racaoService.filterByPrecoMax(preco);
    }

    @GET
    @Path("/filterByEntrePreco/{precoMin}/{precoMax}")
    public List<RacaoResponseDTO> filterByEntrePreco (@PathParam("precoMin") Double precoMin, @PathParam("precoMax") Double precoMax) {

        return racaoService.filterByEntrePreco(precoMin, precoMax);
    }

}
