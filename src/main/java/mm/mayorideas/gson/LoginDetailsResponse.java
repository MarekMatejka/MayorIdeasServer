package mm.mayorideas.gson;

public class LoginDetailsResponse {

    private final int ID;
    private final String username;
    private final String name;

    public LoginDetailsResponse(int ID, String username, String name) {
        this.ID = ID;
        this.username = username;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
}
