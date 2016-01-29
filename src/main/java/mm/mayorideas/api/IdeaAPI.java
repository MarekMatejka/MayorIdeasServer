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
    public String addNewIdea(String message) {
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
                        idea.getLatitude(),
                        idea.getLongitude(),
                        idea.getAuthorID(),
                        new Timestamp(System.currentTimeMillis()));
        }catch (SQLException e) {e.printStackTrace();}

        return ""+result;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getIdeaByID(
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

    @GET
    @Path("closest")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTop10ClosestIdeas(@QueryParam("user_id") int userID,
                                       @QueryParam("lat") double latitude,
                                       @QueryParam("long") double longitude) {
        System.out.println("som tu closest");
        Gson gson = new Gson();
        String result = "";
        try {
            IdeaDBAccessor db = IdeaDBAccessor.getInstance();
            result = gson.toJson(db.getClosestIdeas(latitude, longitude, userID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    @GET
    @Path("my")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMyIdeas(@QueryParam("user_id") int userID) {
        Gson gson = new Gson();
        String result = "";
        try {
            IdeaDBAccessor db = IdeaDBAccessor.getInstance();
            result = gson.toJson(db.getMyIdeas(userID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    @GET
    @Path("following")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFollowingIdeas(@QueryParam("user_id") int userID) {
        Gson gson = new Gson();
        String result = "";
        try {
            IdeaDBAccessor db = IdeaDBAccessor.getInstance();
            result = gson.toJson(db.getFollowingIdeas(userID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    @GET
    @Path("trending")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTrendingIdeas(@QueryParam("user_id") int userID) {
        Gson gson = new Gson();
        String result = "";
        try {
            IdeaDBAccessor db = IdeaDBAccessor.getInstance();
            result = gson.toJson(db.getTrendingIdeas(userID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    @GET
    @Path("category/{category_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTrendingIdeas(
            @PathParam("category_id") int categoryID,
            @QueryParam("user_id") int userID) {
        Gson gson = new Gson();
        String result = "";
        try {
            IdeaDBAccessor db = IdeaDBAccessor.getInstance();
            result = gson.toJson(db.getIdeasInCategory(categoryID, userID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
}
