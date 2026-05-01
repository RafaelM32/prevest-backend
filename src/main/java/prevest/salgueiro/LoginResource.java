package prevest.salgueiro;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.FormParam;

@Path("/api/auth/login")
public class LoginResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@FormParam("username") String username, @FormParam("password") String password) {
        return "{\"message\": \"Login successful for user: " + username + "\"}";
}

}
