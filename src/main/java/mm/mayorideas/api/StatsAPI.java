package mm.mayorideas.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mm.mayorideas.db.StatsDBAccessor;
import mm.mayorideas.db.UserDBAccessor;
import mm.mayorideas.gson.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.sql.SQLException;

@Path("stats")
public class StatsAPI {

    @GET
    @Path("weekly")
    @Produces(MediaType.APPLICATION_JSON)
    public String getWeeklyStats(@Context HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new Gson();
        WeeklyStats stats = null;
        try {
            StatsDBAccessor db = StatsDBAccessor.getInstance();
            stats = db.getWeeklyStats();
        } catch (SQLException e) {e.printStackTrace();}

        return gson.toJson(stats);
    }

    @GET
    @Path("overall")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOverallIdeaStats(@Context HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        Gson gson = new Gson();
        OverallIdeaStats stats = null;
        try {
            StatsDBAccessor db = StatsDBAccessor.getInstance();
            stats = db.getOverallIdeaStats();
        } catch (SQLException e) {e.printStackTrace();}

        return gson.toJson(stats);
    }
}
