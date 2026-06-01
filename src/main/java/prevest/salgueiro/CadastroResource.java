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

import org.jboss.logging.Logger;

import prevest.salgueiro.util.CriptoUtil;

@Path("/api/cadastro")
public class CadastroResource {
    
    private static final Logger LOG = Logger.getLogger(CadastroResource.class);

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
                LOG.error("CPF invalido");
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"CPF inválido\"}")
                        .build();
            } else {

                if(usuariosRepository.usuarioJaCadastrado(cpf)) {
                    LOG.info("Cliente ja cadastrado");
                    return Response.status(Response.Status.CONFLICT)
                            .entity("{\"error\": \"Usuário com este CPF já cadastrado\"}")
                            .build();
            }
                
                String senhaCriptografada = CriptoUtil.criptografarSenha(senha);

                usuariosRepository.cadastrarUsuario(email, senhaCriptografada, dataNascimento, cpf, tipoUsuario,
                        null, nome, telefone);
                return Response.ok("{\"message\": \"Cadastro realizado com sucesso\"}").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("Deu erro ao cadastrar: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao cadastrar usuário: " + e.getMessage() + "\"}")
                    .build();
        }

    }
}