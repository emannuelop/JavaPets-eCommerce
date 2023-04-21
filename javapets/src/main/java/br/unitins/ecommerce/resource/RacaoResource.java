package br.unitins.ecommerce.resource;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Path("/filterByEntrePreco/{precoMin}&{precoMax}")
    public List<RacaoResponseDTO> filterByEntrePreco (@PathParam("precoMin") Double precoMin, @PathParam("precoMax") Double precoMax) {

        return racaoService.filterByEntrePreco(precoMin, precoMax);
    }

}
