package mm.mayorideas.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mm.mayorideas.db.IdeaDBAccessor;
import mm.mayorideas.gson.NewIdeaPOSTGson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Timestamp;

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
            IdeaDBAccessor db = IdeaDBAccessor.getInstance();
            result = db.addIdea(
                        idea.getTitle(),
                        idea.getCategoryID(),
                        idea.getDescription(),
                        idea.getLocation(),
                        idea.getAuthorID(),
                        new Timestamp(System.currentTimeMillis()));
        }catch (SQLException e) {e.printStackTrace();}

        return ""+result;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getParcelByID(
           @PathParam("id") String id,
           @QueryParam("user_id") int userID) {
       Gson gson = new Gson();
       String result = "";
       try {
           IdeaDBAccessor db = IdeaDBAccessor.getInstance();
           result = gson.toJson(db.getIdea(Integer.parseInt(id), userID));
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return result;
    }

    @GET
    @Path("top10")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTop10Ideas(@QueryParam("user_id") int userID) {
        System.out.println("som tu");
        Gson gson = new Gson();
        String result = "";
        try {
            IdeaDBAccessor db = IdeaDBAccessor.getInstance();
            result = gson.toJson(db.getTop10Ideas(userID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
}
