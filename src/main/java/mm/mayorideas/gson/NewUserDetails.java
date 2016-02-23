package mm.mayorideas.gson;

public class NewUserDetails {

    private final String username;
    private final String password;
    private final String name;
    private final boolean isCitizen;

    public NewUserDetails(String username, String password, String name, boolean isCitizen) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.isCitizen = isCitizen;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isCitizen() {
        return isCitizen;
    }
}
