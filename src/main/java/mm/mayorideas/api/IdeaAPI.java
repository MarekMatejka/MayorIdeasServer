package mm.mayorideas.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mm.mayorideas.db.IdeaDBAccessor;
import mm.mayorideas.db.VoteDBAccessor;
import mm.mayorideas.gson.NewIdeaPOSTGson;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
           @QueryParam("user_id") int userID,
           @Context HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
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
    public String getTrendingIdeas(@QueryParam("user_id") int userID,
                                   @Context HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
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

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTrendingIdeas(@Context HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new Gson();
        String result = "";
        try {
            IdeaDBAccessor db = IdeaDBAccessor.getInstance();
            result = gson.toJson(db.getAllIdeas());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    @PUT
    @Path("state")
    public void changeIdeaState(
            @QueryParam("idea_id") int ideaID,
            @QueryParam("state") String state,
            @Context HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        try {
            IdeaDBAccessor db = IdeaDBAccessor.getInstance();
            db.updateState(ideaID, state);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @OPTIONS
    @Path("state")
    public Response forceCollectParcelOptions(@HeaderParam("Access-Control-Request-Headers") String request) {
        return getResponse(request);
    }

    private Response getResponse(@HeaderParam("Access-Control-Request-Headers") String request) {
        Response.ResponseBuilder rb = Response.ok();
        rb.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT")
                .header("Access-Control-Allow-Headers", request);
        return rb.build();
    }
}
