package mm.mayorideas.api;

import com.google.gson.Gson;
import mm.mayorideas.db.CommentDBAccessor;
import mm.mayorideas.db.ImagesDBAccessor;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;

@Path("image")
public class ImagesAPI {

    @POST
    @Path("{idea_id}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.TEXT_PLAIN)
    public String saveImage(@PathParam("idea_id") int ideaID,
                            InputStream signature) {
        String id = "";
        try {
            id = ImagesDBAccessor.getInstance().addImage(signature, ideaID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @GET
    @Path("/get/{image_id}")
    @Produces("image/png")
    public Response getImage(@PathParam("image_id") int imageID){
        try {
            File f = ImagesDBAccessor.getInstance().getImage(imageID);
            return Response.ok(f, new MimetypesFileTypeMap().getContentType(f))
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Content-Disposition", "attachment; filename="+imageID+".png")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }

    @GET
    @Path("/get/idea/{idea_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getImageIdsForIdea(@PathParam("idea_id") int ideaID){
        try {
            Gson gson = new Gson();
            ImagesDBAccessor db = ImagesDBAccessor.getInstance();
            return gson.toJson(db.getImageIdsForIdea(ideaID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "[]";
    }
}
