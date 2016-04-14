package mm.mayorideas.db;

import mm.mayorideas.gson.OverallIdeaStats;
import mm.mayorideas.gson.WeeklyStats;
import org.junit.Test;

import static org.junit.Assert.*;

public class StatsDBAccessorTest {

    @Test
    public void testGetWeeklyStats() throws Exception {
        StatsDBAccessor db = StatsDBAccessor.getInstance();
        final WeeklyStats weeklyStats = db.getWeeklyStats();

        assertTrue(weeklyStats.getClosedIdeasPerWeek() >= 0);
        assertTrue(weeklyStats.getInProgressIdeasPerWeek() >= 0);
        assertTrue(weeklyStats.getNewIdeasPerWeek() >= 0);
        assertTrue(weeklyStats.getInteractionsPerWeek() >= 0);
    }

    @Test
    public void testGetOverallIdeaStats() throws Exception {
        StatsDBAccessor db = StatsDBAccessor.getInstance();
        final OverallIdeaStats overallIdeaStats = db.getOverallIdeaStats();

        assertTrue(overallIdeaStats.getTotalClosedIdeas() >= 0);
        assertTrue(overallIdeaStats.getTotalInProgressIdeas() >= 0);
        assertTrue(overallIdeaStats.getTotalOpenIdeas() >= 0);
    }
}