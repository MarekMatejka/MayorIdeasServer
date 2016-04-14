package mm.mayorideas.db;

import mm.mayorideas.gson.LoginDetails;
import mm.mayorideas.gson.LoginDetailsResponse;
import mm.mayorideas.gson.UserStats;
import mm.mayorideas.security.AESEncryptor;
import mm.mayorideas.security.SHA256Encryptor;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDBAccessorTest {

    @Test
    public void testVerifyLoginDetailsSuccess() throws Exception {
        UserDBAccessor db = UserDBAccessor.getInstance();
        final LoginDetails loginDetails = new LoginDetails(
                                            "C7AH/tIACY+YDyWhgz/YDA864TAMfGDVKFKvzsBVGvY=",
                                            "0698fb9b13c4487a2c9cd2976e1370da56d11b4dcccdcdeed32dcbc763706154",
                                            true);
        final LoginDetailsResponse response = db.verifyLoginDetails(loginDetails);

        assertEquals(1, response.getID());
        assertEquals("ryYKA1XUPz2IW294LRVw6g==", response.getName());
        assertEquals("C7AH/tIACY+YDyWhgz/YDA864TAMfGDVKFKvzsBVGvY=", response.getUsername());
    }

    @Test
    public void testVerifyLoginDetailsFailure() throws Exception {
        UserDBAccessor db = UserDBAccessor.getInstance();
        final LoginDetails loginDetails = new LoginDetails(
                "usernameFailure",
                "passwordFailure",
                true);
        final LoginDetailsResponse response = db.verifyLoginDetails(loginDetails);
        assertNull(response);
    }

    @Test
    public void testGetUserStatsSuccess() throws Exception {
        UserDBAccessor db = UserDBAccessor.getInstance();
        final UserStats userStats = db.getUserStats(1);

        assertTrue(userStats.getComments() >= 0);
        assertTrue(userStats.getFollows() >= 0);
        assertTrue(userStats.getIdeas() >= 0);
        assertTrue(userStats.getVotes() >= 0);
    }

    @Test
    public void testGetUserStatsFailure() throws Exception {
        UserDBAccessor db = UserDBAccessor.getInstance();
        final UserStats userStats = db.getUserStats(-1);
        assertNull(userStats);
    }
}