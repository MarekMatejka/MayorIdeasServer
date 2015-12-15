package mm.mayorideas.db;

import java.sql.*;

public class FollowsDBAccessor extends DBAccessor {

    private static FollowsDBAccessor dbAccessor = null;

    public static FollowsDBAccessor getInstance() {
        if (dbAccessor == null) {
            dbAccessor = new FollowsDBAccessor();
        }
        return dbAccessor;
    }

    private FollowsDBAccessor() {
        super();
    }

    public void addFollows(int ideaID, int userID) throws SQLException {
        String QUERY = "insert into Follows values(?, ?);";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, ideaID);
        preparedStatement.executeUpdate();

        connection.close();
        preparedStatement.close();
    }

    public void deleteFollows(int ideaID, int userID) throws SQLException {
        String QUERY = "delete from Follows where UserID = ? and IdeaID = ?;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, ideaID);
        preparedStatement.executeUpdate();

        connection.close();
        preparedStatement.close();
    }
}
