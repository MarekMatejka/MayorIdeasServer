package mm.mayorideas.db;

import mm.mayorideas.model.Idea;
import mm.mayorideas.model.IdeaState;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class IdeaDBAccessorTest {

    @Test
    public void testAddIdea() throws Exception {
        IdeaDBAccessor db = IdeaDBAccessor.getInstance();
        int newID = db.addIdea("Test title 2", 2, "test description 2", 1.0, 1.0, 2, new Timestamp(System.currentTimeMillis()));
        assertTrue(newID >= 1);
    }

    @Test
    public void testGetIdea() throws Exception {
        IdeaDBAccessor db = IdeaDBAccessor.getInstance();
        Idea idea = db.getIdea(1, 1);

        assertEquals("new idea 1", idea.getTitle());
        assertEquals(2, idea.getCategoryID());
        assertEquals("Culture", idea.getCategoryName());
        assertTrue(idea.getDescription().startsWith("cufflinks v ft"));
        assertEquals(1, idea.getAuthorID());
        assertEquals("ryYKA1XUPz2IW294LRVw6g==", idea.getAuthorName());
        assertEquals(IdeaState.IN_PROGRESS, idea.getIdeaState());
        assertEquals(1, idea.getCoverImageID());
        assertEquals(51.525020941381854, idea.getLatitude(), 0);
        assertEquals(-0.09773802012205124, idea.getLongitude(), 0);
    }

    @Test
    public void testGetTop10Ideas() throws Exception {
        IdeaDBAccessor db = IdeaDBAccessor.getInstance();
        final List<Idea> top10Ideas = db.getTop10Ideas(1);

        assertTrue(top10Ideas.size() <= 10 && top10Ideas.size() >= 0);
        for (Idea idea : top10Ideas) {
            testIdeaNotMissingData(idea);
        }
    }

    @Test
    public void testGetClosestIdeas() throws Exception {
        IdeaDBAccessor db = IdeaDBAccessor.getInstance();
        final List<Idea> closestIdeas = db.getClosestIdeas(0, 0, 1);

        assertTrue(closestIdeas.size() <= 10 && closestIdeas.size() >= 0);
        for (Idea idea : closestIdeas) {
            testIdeaNotMissingData(idea);
        }
    }

    @Test
    public void testGetMyIdeas() throws Exception {
        IdeaDBAccessor db = IdeaDBAccessor.getInstance();
        final List<Idea> myIdeas = db.getMyIdeas(1);

        for (Idea idea : myIdeas) {
            testIdeaNotMissingData(idea);
            assertEquals(1, idea.getAuthorID());
        }
    }

    @Test
    public void testGetFollowingIdeas() throws Exception {
        IdeaDBAccessor db = IdeaDBAccessor.getInstance();
        final List<Idea> followingIdeas = db.getFollowingIdeas(1);

        for (Idea idea : followingIdeas) {
            testIdeaNotMissingData(idea);
        }
    }

    @Test
    public void testGetTrendingIdeas() throws Exception {
        IdeaDBAccessor db = IdeaDBAccessor.getInstance();
        final List<Idea> trendingIdeas = db.getTrendingIdeas(1);

        assertTrue(trendingIdeas.size() <= 10 && trendingIdeas.size() >= 0);
        for (Idea idea : trendingIdeas) {
            testIdeaNotMissingData(idea);
        }
    }

    @Test
    public void testGetIdeasInCategory() throws Exception {
        IdeaDBAccessor db = IdeaDBAccessor.getInstance();
        final List<Idea> trendingIdeas = db.getIdeasInCategory(1, 1);

        assertTrue(trendingIdeas.size() <= 10 && trendingIdeas.size() >= 0);
        for (Idea idea : trendingIdeas) {
            testIdeaNotMissingData(idea);
            assertEquals(1, idea.getCategoryID());
        }
    }

    private void testIdeaNotMissingData(Idea idea) {
        assertTrue(!idea.getTitle().isEmpty());
        assertTrue(idea.getCategoryID() >= 1);
        assertTrue(!idea.getCategoryName().isEmpty());
        assertTrue(!idea.getDescription().isEmpty());
        assertTrue(idea.getAuthorID() >= 1);
        assertTrue(!idea.getAuthorName().isEmpty());
        assertTrue(idea.getIdeaState() == IdeaState.IN_PROGRESS || idea.getIdeaState() == IdeaState.OPEN || idea.getIdeaState() == IdeaState.RESOLVED);
        assertTrue(idea.getCoverImageID() >= 1);
    }
}