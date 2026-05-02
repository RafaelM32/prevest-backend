package prevest.salgueiro;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


import jakarta.ws.rs.core.Response;
import prevest.salgueiro.repositore.UsuariosRepository;
import prevest.salgueiro.util.CpfUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;

@Path("/api/cadastro")
public class CadastroResource {

    @Inject
    UsuariosRepository usuariosRepository;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastro(
            @FormParam("email") String email,
            @FormParam("senha") String senha,
            @FormParam("dataNascimento") String dataNascimento,
            @FormParam("cpf") String cpf,
            @FormParam("tipoUsuario") String tipoUsuario,
            @FormParam("foto") String fotoBase64,
            @FormParam("telefone") String telefone,
            @FormParam("nome") String nome) {

                
        try {

            if (!CpfUtil.isValidCPF(cpf)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"CPF inválido\"}")
                        .build();
            } else {

                if(usuariosRepository.usuarioJaCadastrado(cpf)) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("{\"error\": \"Usuário com este CPF já cadastrado\"}")
                            .build();
                }

                usuariosRepository.cadastrarUsuario(email, senha, dataNascimento, cpf, tipoUsuario,
                        null, nome, telefone);
                return Response.ok("{\"message\": \"Cadastro realizado com sucesso\"}").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao cadastrar usuário: " + e.getMessage() + "\"}")
                    .build();
        }

    }
}