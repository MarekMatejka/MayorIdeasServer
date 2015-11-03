package mm.mayorideas.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mm.mayorideas.db.DBAccessor;
import mm.mayorideas.db.IdeaDBAccessor;
import mm.mayorideas.gson.NewIdeaPOSTGson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.sql.SQLException;

@Path("idea")
public class IdeaAPI {

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addNewParcel(String message) {
        Gson gson = new Gson();
        Type type = new TypeToken<NewIdeaPOSTGson>() {}.getType();
        NewIdeaPOSTGson idea = gson.fromJson(message, type);
        int result = -1;
        try {
            IdeaDBAccessor db = (IdeaDBAccessor) DBAccessor.getInstance(DBAccessor.Type.IDEA);
            result = db.addIdea(
                        idea.getTitle(),
                        idea.getCategoryID(),
                        idea.getDescription(),
                        idea.getLocation(),
                        idea.getAuthorID(),
                        idea.getDateCreated());
        }catch (SQLException e) {e.printStackTrace();}

        return ""+result;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getParcelByID(@PathParam("id") String id) {
        Gson gson = new Gson();
        String result = "";
        try {
            IdeaDBAccessor db = (IdeaDBAccessor) DBAccessor.getInstance(DBAccessor.Type.IDEA);
            result = gson.toJson(db.getIdea(Integer.parseInt(id)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
