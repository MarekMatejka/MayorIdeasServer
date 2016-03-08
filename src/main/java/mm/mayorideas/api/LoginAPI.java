package mm.mayorideas.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mm.mayorideas.db.IdeaDBAccessor;
import mm.mayorideas.db.UserDBAccessor;
import mm.mayorideas.gson.LoginDetails;
import mm.mayorideas.gson.LoginDetailsResponse;
import mm.mayorideas.gson.NewUserDetails;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Timestamp;

@Path("login")
public class LoginAPI {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String login(String message,
                        @Context HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");

        Gson gson = new Gson();
        Type type = new TypeToken<LoginDetails>() {}.getType();
        LoginDetails loginDetails = gson.fromJson(message, type);
        LoginDetailsResponse result = null;
        try {
            UserDBAccessor db = UserDBAccessor.getInstance();
            result = db.verifyLoginDetails(loginDetails);
        } catch (SQLException e) {e.printStackTrace();}
        return result != null ? gson.toJson(result) : "{}";
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String registerNewUser(String message,
                                  @Context HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new Gson();
        Type type = new TypeToken<NewUserDetails>() {}.getType();
        NewUserDetails newUserDetails = gson.fromJson(message, type);
        int result = -1;
        try {
            UserDBAccessor db = UserDBAccessor.getInstance();
            result = db.addNewUser(newUserDetails);
        } catch (SQLException e) {e.printStackTrace();}

        return result != -1 ? createLoginResponseForNewUser(gson, newUserDetails, result) : "{}";
    }

    private String createLoginResponseForNewUser(Gson gson, NewUserDetails newUserDetails, int result) {
        return gson.toJson(new LoginDetailsResponse(result, newUserDetails.getUsername(), newUserDetails.getName()));
    }

    @OPTIONS
    @Path("register")
    public Response registerOptions(@HeaderParam("Access-Control-Request-Headers") String request) {
        return getResponse(request);
    }

    @OPTIONS
    public Response loginOptions(@HeaderParam("Access-Control-Request-Headers") String request) {
        return getResponse(request);
    }

    private Response getResponse(@HeaderParam("Access-Control-Request-Headers") String request) {
        Response.ResponseBuilder rb = Response.ok();
        rb.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .header("Access-Control-Allow-Headers", request);
        return rb.build();
    }

}
