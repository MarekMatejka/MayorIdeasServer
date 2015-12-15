package mm.mayorideas.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class VoteDBAccessor extends DBAccessor {

    private static VoteDBAccessor dbAccessor = null;

    public static VoteDBAccessor getInstance() {
        if (dbAccessor == null) {
            dbAccessor = new VoteDBAccessor();
        }
        return dbAccessor;
    }

    private VoteDBAccessor() {
        super();
    }

    public void castVote(int ideaID, int userID, int voted) throws SQLException {
        String QUERY = "insert into Vote values(?, ?, ?, ?) on duplicate key update Voted = ?, DateVoted = ?;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, ideaID);
        preparedStatement.setInt(3, voted);
        preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(5, voted);
        preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        preparedStatement.executeUpdate();

        connection.close();
        preparedStatement.close();
    }

    public void deleteVote(int ideaID, int userID) throws SQLException {
        String QUERY = "delete from Vote where UserID = ? and IdeaID = ?;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, ideaID);
        preparedStatement.executeUpdate();

        connection.close();
        preparedStatement.close();
    }
}
