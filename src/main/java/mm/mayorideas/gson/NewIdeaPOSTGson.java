package mm.mayorideas.gson;

public class NewIdeaPOSTGson {

    private final String title;
    private final int categoryID;
    private final String description;
    private final double latitude;
    private final double longitude;
    private final int authorID;

    public NewIdeaPOSTGson(
            String title,
            int categoryID,
            String description,
            double latitude,
            double longitude,
            int authorID) {
        this.title = title;
        this.categoryID = categoryID;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.authorID = authorID;
    }

    public String getTitle() {
        return title;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getAuthorID() {
        return authorID;
    }
}
