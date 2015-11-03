package mm.mayorideas.db;

import mm.mayorideas.model.Idea;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

public class IdeaDBAccessorTest {

    @Test
    public void testAddIdea() throws Exception {
        IdeaDBAccessor db = (IdeaDBAccessor) DBAccessor.getInstance(DBAccessor.Type.IDEA);
        db.addIdea("Test title", 1, "test description", "test location", 1, new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testGetIdea() throws Exception {
        IdeaDBAccessor db = (IdeaDBAccessor) DBAccessor.getInstance(DBAccessor.Type.IDEA);
        Idea idea = db.getIdea(1);

        assertEquals("Test title", idea.getTitle());
        assertEquals(1, idea.getCategoryID());
        assertEquals("Nature", idea.getCategoryName());
        assertEquals("test description", idea.getDescription());
        assertEquals("test location", idea.getLocation());
        assertEquals(1, idea.getAuthorID());
        assertEquals("Marek", idea.getAuthorName());
    }
}