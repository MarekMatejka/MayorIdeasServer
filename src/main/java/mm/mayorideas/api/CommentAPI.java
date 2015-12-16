package mm.mayorideas.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mm.mayorideas.db.CommentDBAccessor;
import mm.mayorideas.gson.NewCommentPOSTGson;
import mm.mayorideas.model.Comment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

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
                    comment.getIdeaID(),
                    comment.getUserID(),
                    comment.getCommentText());
        }catch (SQLException e) {e.printStackTrace();}

        return ""+result;
    }

    @GET
    @Path("idea/{idea_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCommentsForIdea(@PathParam("idea_id") int ideaID) {
        List<Comment> comments = null;
        try {
            CommentDBAccessor db = CommentDBAccessor.getInstance();
            comments = db.getAllCommentsForIdea(ideaID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.toJson(comments);
    }

    @GET
    @Path("idea/last/{idea_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLast2CommentsForIdea(@PathParam("idea_id") int ideaID) {
        List<Comment> comments = null;
        try {
            CommentDBAccessor db = CommentDBAccessor.getInstance();
            comments = db.getLast2CommentsForIdea(ideaID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.toJson(comments);
    }
}
