package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.telefone.AdicionarTelDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioBasicoResponseDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.service.usuario.UsuarioService;
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
import jakarta.ws.rs.core.Response.Status;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    // @RolesAllowed({ "Admin" })
    public List<UsuarioResponseDTO> getAllUsuario(@QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("2") int pageSize) {
        LOG.info("Buscando todos os usuários");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getAllUsuario(page, pageSize);
    }

    @GET
    @Path("/usuarios-basicos")
    // @RolesAllowed({ "Admin" })
    public List<UsuarioBasicoResponseDTO> getAllUsuarioBasico() {
        LOG.info("Buscando todos os usuários basicos");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getAllUsuarioBasico();
    }

    @GET
    @Path("/{id}")
    // @RolesAllowed({ "Admin" })
    public UsuarioResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.info("Buscando usuário por ID");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getById(id);
    }

    @GET
    @Path("/lista_desejo/{idUsuario}")
    @RolesAllowed({ "Admin", "User" })
    public ListaDesejoResponseDTO getListaDesejo(@PathParam("idUsuario") Long idUsuario) {
        LOG.infof("Buscando todos os produtos da lista desejo do usuário: ", idUsuario);
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getListaDesejo(idUsuario);
    }

    @POST
    // @RolesAllowed({ "Admin" })
    public Response insert(UsuarioDTO usuarioDto) {

        LOG.infof("Usuário criado com sucesso.");

        return Response
                .status(Status.CREATED) // 201
                .entity(usuarioService.insert(usuarioDto))
                .build();

    }

    @PATCH
    @Path("/lista_desejo")
    @RolesAllowed({ "Admin", "User" })
    public Response insertListaDesejo(ListaDesejoDTO listaDto) {

        Result result = null;
        try {
            usuarioService.insertListaDesejo(listaDto);
            LOG.infof("Produto inserido na lista Desejo.");
            return Response
                    .status(Status.CREATED) // 201
                    .build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um produto na lista de desejos.");
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
    // @RolesAllowed({ "Admin" })
    public Response update(@PathParam("id") Long id, UsuarioDTO usuarioDto) {

        usuarioService.update(id, usuarioDto);
        LOG.infof("Usuário (%d) atualizado com sucesso.", id);

        return Response
                .status(Status.NO_CONTENT) // 204
                .build();

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            usuarioService.delete(id);
            LOG.infof("Usuário excluído com sucesso: %d", id);

            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar usuário: parâmetros inválidos.", e);
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity("Parâmetros inválidos")
                    .build();
        } catch (NotFoundException e) {
            LOG.error("Usuário não encontrado.", e);
            return Response
                    .status(Status.NOT_FOUND)
                    .entity("Usuário não encontrado")
                    .build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar usuário.", e);
            return Response
                    .status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao excluir o usuário")
                    .build();
        }
    }

    @PATCH
    @Path("/lista_desejo/{idUsuario}/{idProduto}")
    @RolesAllowed({ "Admin", "User" })
    public Response deleteProdutoFromListaDesejo(@PathParam("idUsuario") Long idUsuario,
            @PathParam("idProduto") Long idProdutoListaDesejo) {
        try {
            usuarioService.deleteProdutoFromListaDesejo(idUsuario, idProdutoListaDesejo);
            LOG.infof("Produto (%d) removido da lista de desejos do usuário (%d).", idProdutoListaDesejo, idUsuario);

            return Response
                    .status(Status.NO_CONTENT)
                    .build();

        } catch (Exception e) {
            LOG.error("Erro ao remover produto da lista de desejos.", e);

            return Response
                    .status(Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GET
    @Path("/count")
    // @RolesAllowed({ "Admin" })
    public Long count() {
        LOG.infof("Contando os usuários");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.count();
    }

    @GET
    @Path("/lista_desejo/count/{id}")
    @RolesAllowed({ "Admin", "User" })
    public Integer countListaDesejo(@PathParam("id") Long id) {
        LOG.infof("Contando todos os produtos do usuário ", id);
        LOG.debug("ERRO DE DEBUG.");
        return usuarioService.countListaDesejo(id);
    }

    @GET
    @Path("/searchByNome/{nome}")
    // @RolesAllowed({ "Admin" })
    public List<UsuarioResponseDTO> getByNome(@PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("2") int pageSize) throws NullPointerException {
        LOG.info("Buscando usuário por nome");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getByNome(nome, page, pageSize);
    }

    @GET
    @Path("/count/search/{nome}")
    // @RolesAllowed({"Admin"})
    public Long count(@PathParam("nome") String nome) {
        LOG.infof("Contando todos os usuários");
        LOG.debug("ERRO DE DEBUG.");
        return usuarioService.countByNome(nome);
    }

    /*
     * @POST
     * 
     * @Path("/telefone/adiconartelefone")
     * // @RolesAllowed({ "Admin", "User", "User_Basic" })
     * public Response insertIntoCarrrinho(AdicionarTelDTO telefone) {
     * Result result = null;
     * 
     * TelefoneDTO tel = new TelefoneDTO(telefone.codigoArea(), telefone.numero());
     * 
     * try{
     * usuarioService.insertTelefone(telefone.idUsu(), tel);
     * 
     * LOG.info("Telefone inserido no com sucesso.");
     * return Response.status(Status.CREATED).build();
     * } catch (NullPointerException e) {
     * LOG.error("Erro ao adicionar telefone.", e);
     * 
     * result = new Result(e.getMessage(), false);
     * 
     * return Response.status(Status.NOT_FOUND).entity(result).build();
     * }
     * }
     */
}
