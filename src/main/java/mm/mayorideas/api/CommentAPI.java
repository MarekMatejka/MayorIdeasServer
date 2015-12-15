package mm.mayorideas.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mm.mayorideas.db.CommentDBAccessor;
import mm.mayorideas.db.IdeaDBAccessor;
import mm.mayorideas.db.VoteDBAccessor;
import mm.mayorideas.gson.NewCommentPOSTGson;
import mm.mayorideas.gson.NewIdeaPOSTGson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Timestamp;

@Path("comment")
public class CommentAPI {

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addNewParcel(String message) {
        Gson gson = new Gson();
        Type type = new TypeToken<NewCommentPOSTGson>() {}.getType();
        NewCommentPOSTGson comment = gson.fromJson(message, type);
        int result = -1;
        try {
            CommentDBAccessor db = CommentDBAccessor.getInstance();
            result = db.addComment(
                    comment.getUserID(),
                    comment.getIdeaID(),
                    comment.getCommentText());
        }catch (SQLException e) {e.printStackTrace();}

        return ""+result;
    }
}
