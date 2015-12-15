package mm.mayorideas.gson;

public class NewIdeaPOSTGson {

    private final String title;
    private final int categoryID;
    private final String description;
    private final String location;
    private final int authorID;

    public NewIdeaPOSTGson(
            String title,
            int categoryID,
            String description,
            String location,
            int authorID) {
        this.title = title;
        this.categoryID = categoryID;
        this.description = description;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public int getAuthorID() {
        return authorID;
    }
}
