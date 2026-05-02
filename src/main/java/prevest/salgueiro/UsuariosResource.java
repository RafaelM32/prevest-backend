package prevest.salgueiro;

import java.util.List;
import prevest.salgueiro.record.TipoDeUsuarios;
import prevest.salgueiro.repositore.UsuariosRepository;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/api/usuarios/tipos")
public class UsuariosResource {

    @Inject
    UsuariosRepository usuariosRepository;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTiposDeUsuarios() throws SQLException {
        try {
            List<TipoDeUsuarios> listaTipos = usuariosRepository.getTiposUsuarios();
            return Response.ok(listaTipos).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
