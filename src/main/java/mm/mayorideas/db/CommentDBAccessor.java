package mm.mayorideas.db;

import java.sql.*;

public class CommentDBAccessor extends DBAccessor {

    private static CommentDBAccessor dbAccessor = null;

    public static CommentDBAccessor getInstance() {
        if (dbAccessor == null) {
            dbAccessor = new CommentDBAccessor();
        }
        return dbAccessor;
    }

    private CommentDBAccessor() {
        super();
    }

    public int addComment(int ideaID, int userID, String commentText) throws SQLException {
        String QUERY = "insert into Comment values(default, ?, ?, ?, ?);";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, ideaID);
        preparedStatement.setString(3, commentText);
        preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        preparedStatement.executeUpdate();

        ResultSet ids = preparedStatement.getGeneratedKeys();
        int result = ids.next() ? ids.getInt(1) : -1;

        connection.close();
        preparedStatement.close();

        return result;
    }
}
