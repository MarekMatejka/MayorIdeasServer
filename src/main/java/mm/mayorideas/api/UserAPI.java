package mm.mayorideas.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mm.mayorideas.db.CommentDBAccessor;
import mm.mayorideas.db.UserDBAccessor;
import mm.mayorideas.gson.NewCommentPOSTGson;
import mm.mayorideas.gson.UserStats;
import mm.mayorideas.model.Comment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

@Path("user")
public class UserAPI {

    @GET
    @Path("stats/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatsForUser(@PathParam("user_id") int userID) {
        UserStats stats = null;
        try {
            UserDBAccessor db = UserDBAccessor.getInstance();
            stats = db.getUserStats(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.toJson(stats);
    }
}
