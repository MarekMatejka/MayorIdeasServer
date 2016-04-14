package mm.mayorideas.db;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class ImagesDBAccessorTest {

    @Test
    public void testGetImage() throws Exception {
        ImagesDBAccessor db = ImagesDBAccessor.getInstance();
        final File image = db.getImage(1);
        assertTrue(image.isFile());
        assertTrue(image.getTotalSpace() > 0);
        image.delete();
    }

    @Test
    public void testGetImageIdsForIdea() throws Exception {
        ImagesDBAccessor db = ImagesDBAccessor.getInstance();
        final List<Integer> imageIdsForIdea = db.getImageIdsForIdea(1);
        assertTrue(imageIdsForIdea.size() >= 1);
    }
}