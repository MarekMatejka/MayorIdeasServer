package mm.mayorideas.gson;

public class OverallIdeaStats {

    private final int totalOpenIdeas;
    private final int totalInProgressIdeas;
    private final int totalClosedIdeas;

    public OverallIdeaStats(int totalOpenIdeas, int totalInProgressIdeas, int totalClosedIdeas) {
        this.totalOpenIdeas = totalOpenIdeas;
        this.totalInProgressIdeas = totalInProgressIdeas;
        this.totalClosedIdeas = totalClosedIdeas;
    }

    public int getTotalOpenIdeas() {
        return totalOpenIdeas;
    }

    public int getTotalInProgressIdeas() {
        return totalInProgressIdeas;
    }

    public int getTotalClosedIdeas() {
        return totalClosedIdeas;
    }
}
