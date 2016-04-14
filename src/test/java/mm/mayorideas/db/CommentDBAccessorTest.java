package mm.mayorideas.db;

import mm.mayorideas.model.Comment;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CommentDBAccessorTest {

    @Test
    public void testGetAllCommentsForIdea() throws Exception {
        CommentDBAccessor db = CommentDBAccessor.getInstance();
        final List<Comment> allCommentsForIdea = db.getAllCommentsForIdea(1);

        for (Comment commment : allCommentsForIdea) {
            assertEquals(1, commment.getIdeaID());
            assertTrue(!commment.getText().isEmpty());
            assertTrue(!commment.getUserName().isEmpty());
            assertTrue(commment.getUserID() >= 1);
        }
    }

    @Test
    public void testGetLast2CommentsForIdea() throws Exception {
        CommentDBAccessor db = CommentDBAccessor.getInstance();
        final List<Comment> last2CommentsForIdea = db.getLast2CommentsForIdea(1);

        assertTrue(last2CommentsForIdea.size() <= 2);
        for (Comment commment : last2CommentsForIdea) {
            assertEquals(1, commment.getIdeaID());
            assertTrue(!commment.getText().isEmpty());
            assertTrue(!commment.getUserName().isEmpty());
            assertTrue(commment.getUserID() >= 1);
        }
    }

    @Test
    public void testGetCommentCountForIdea() throws Exception {
        CommentDBAccessor db = CommentDBAccessor.getInstance();
        final int commentCountForIdea = db.getCommentCountForIdea(1);
        assertTrue(commentCountForIdea >= 0);
    }
}