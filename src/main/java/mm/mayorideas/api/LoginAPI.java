package mm.mayorideas.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mm.mayorideas.db.IdeaDBAccessor;
import mm.mayorideas.db.UserDBAccessor;
import mm.mayorideas.gson.LoginDetails;
import mm.mayorideas.gson.LoginDetailsResponse;
import mm.mayorideas.gson.NewUserDetails;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Timestamp;

@Path("login")
public class LoginAPI {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String login(String message) {
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
    public String registerNewUser(String message) {
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

}
