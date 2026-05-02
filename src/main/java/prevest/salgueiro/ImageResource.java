package prevest.salgueiro;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import java.util.Map;

import prevest.salgueiro.service.ImageService;

import java.io.IOException;

@Path("/images")
public class ImageResource {

    @Inject
    ImageService imageService;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@RestForm("file") FileUpload file) {
        try {
            // O arquivo temporário é enviado para o serviço
            String imageUrl = imageService.uploadImage(file.uploadedFile().toFile());
            
            // Retorna a URL da imagem para salvar no banco via SQL puro
            return Response.ok(Map.of("url", imageUrl)).build();
        } catch (IOException e) {
            return Response.serverError().entity("Erro no upload: " + e.getMessage()).build();
        }
    }
}