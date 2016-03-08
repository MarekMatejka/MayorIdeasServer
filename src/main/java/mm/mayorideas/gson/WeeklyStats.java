package mm.mayorideas.gson;

public class WeeklyStats {

    private final int newIdeasPerWeek;
    private final int newIdeasTrend;
    private final int inProgressIdeasPerWeek;
    private final int inProgressTrend;
    private final int closedIdeasPerWeek;
    private final int closedIdeasTrend;
    private final int interactionsPerWeek;
    private final int interactionsTrend;

    public WeeklyStats(
            int newIdeasPerWeek,
            int newIdeasTrend,
            int inProgressIdeasPerWeek,
            int inProgressTrend,
            int closedIdeasPerWeek,
            int closedIdeasTrend,
            int interactionsPerWeek,
            int interactionsTrend) {
        this.newIdeasPerWeek = newIdeasPerWeek;
        this.newIdeasTrend = newIdeasTrend;
        this.inProgressIdeasPerWeek = inProgressIdeasPerWeek;
        this.inProgressTrend = inProgressTrend;
        this.closedIdeasPerWeek = closedIdeasPerWeek;
        this.closedIdeasTrend = closedIdeasTrend;
        this.interactionsPerWeek = interactionsPerWeek;
        this.interactionsTrend = interactionsTrend;
    }
}
