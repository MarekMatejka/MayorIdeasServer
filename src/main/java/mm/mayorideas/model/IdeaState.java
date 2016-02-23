package mm.mayorideas.model;


public enum IdeaState {

    OPEN(1),
    IN_PROGRESS(2),
    RESOLVED(3);

    private final int id;

    IdeaState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static IdeaState valueOf(int ID) {
        switch (ID) {
            case 1: return OPEN;
            case 2: return IN_PROGRESS;
            case 3: return RESOLVED;
            default: return OPEN;
        }
    }
}
