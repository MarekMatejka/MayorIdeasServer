package mm.mayorideas.api;

import com.google.gson.Gson;
import mm.mayorideas.db.FollowsDBAccessor;
import mm.mayorideas.db.IdeaDBAccessor;

import javax.ws.rs.*;
import java.sql.SQLException;

@Path("follows")
public class FollowsAPI {

    @PUT
    @Path("f")
    public void followIdea(
            @QueryParam("user_id") int userID,
            @QueryParam("idea_id") int ideaID) {

        try {
            FollowsDBAccessor db = FollowsDBAccessor.getInstance();
            db.addFollows(ideaID, userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PUT
    @Path("u")
    public void unfollowIdea(
            @QueryParam("user_id") int userID,
            @QueryParam("idea_id") int ideaID) {

        try {
            FollowsDBAccessor db = FollowsDBAccessor.getInstance();
            db.deleteFollows(ideaID, userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
