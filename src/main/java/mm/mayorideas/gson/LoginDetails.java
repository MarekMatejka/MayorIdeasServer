package mm.mayorideas.gson;

public class LoginDetails {

    private final String username;
    private final String password;
    private final boolean isCitizen;

    public LoginDetails(String username, String password, boolean isCitizen) {
        this.username = username;
        this.password = password;
        this.isCitizen = isCitizen;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isCitizen() {
        return isCitizen;
    }
}
