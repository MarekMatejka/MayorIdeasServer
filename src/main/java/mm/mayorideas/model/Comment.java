package mm.mayorideas.model;

import java.sql.Timestamp;

public class Comment {

    private final int ID;
    private final int userID;
    private final int ideaID;
    private final String userName;
    private final String text;
    private final Timestamp dateCreated;
    private final boolean isByCitizen;

    public Comment(int ID, int userID, int ideaID, String userName, String text, Timestamp dateCreated, boolean isByCitizen) {
        this.ID = ID;
        this.userID = userID;
        this.ideaID = ideaID;
        this.userName = userName;
        this.text = text;
        this.dateCreated = dateCreated;
        this.isByCitizen = isByCitizen;
    }

    public int getID() {
        return ID;
    }

    public int getUserID() {
        return userID;
    }

    public int getIdeaID() {
        return ideaID;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public boolean isByCitizen() {
        return isByCitizen;
    }
}
