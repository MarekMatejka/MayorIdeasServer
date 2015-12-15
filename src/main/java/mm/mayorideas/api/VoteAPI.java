package mm.mayorideas.api;

import mm.mayorideas.db.VoteDBAccessor;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.sql.SQLException;

@Path("vote")
public class VoteAPI {

    @PUT
    @Path("cast")
    public void castVote(
            @QueryParam("user_id") int userID,
            @QueryParam("idea_id") int ideaID,
            @QueryParam("vote") int vote) {

        try {
            VoteDBAccessor db = VoteDBAccessor.getInstance();
            db.castVote(ideaID, userID, vote);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PUT
    @Path("delete")
    public void deleteVote(
            @QueryParam("user_id") int userID,
            @QueryParam("idea_id") int ideaID) {

        try {
            VoteDBAccessor db = VoteDBAccessor.getInstance();
            db.deleteVote(ideaID, userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
