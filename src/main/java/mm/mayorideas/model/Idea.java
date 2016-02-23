package mm.mayorideas.model;

import java.sql.Timestamp;

public class Idea {

    private final int id;
    private final String title;
    private final int categoryID;
    private final String categoryName;
    private final String description;
    private final double latitude;
    private final double longitude;
    private final int authorID;
    private final String authorName;
    private final Timestamp dateCreated;
    private final int score;
    private final int numOfVotes;
    private final int numOfComments;
    private final int userVote;
    private final boolean isUserFollowing;
    private final int coverImageID;
    private final IdeaState ideaState;

    public Idea(
            int id,
            String title,
            int categoryID,
            String categoryName,
            String description,
            double latitude,
            double longitude,
            int authorID,
            String authorName,
            Timestamp dateCreated,
            int score,
            int numOfVotes,
            int numOfComments,
            int userVote,
            boolean isUserFollowing,
            int coverImageID,
            int ideaStateID) {
        this.id = id;
        this.title = title;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.authorID = authorID;
        this.authorName = authorName;
        this.dateCreated = dateCreated;
        this.score = score;
        this.numOfVotes = numOfVotes;
        this.numOfComments = numOfComments;
        this.userVote = userVote;
        this.isUserFollowing = isUserFollowing;
        this.coverImageID = coverImageID;
        this.ideaState = IdeaState.valueOf(ideaStateID);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
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

    public String getAuthorName() {
        return authorName;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public int getScore() {
        return score;
    }

    public int getNumOfVotes() {
        return numOfVotes;
    }

    public int getNumOfComments() {
        return numOfComments;
    }

    public int getUserVote() {
        return userVote;
    }

    public boolean getIsUserFollowing() {
        return isUserFollowing;
    }

    public boolean isUserFollowing() {
        return isUserFollowing;
    }

    public int getCoverImageID() {
        return coverImageID;
    }

    public IdeaState getIdeaState() {
        return ideaState;
    }
}