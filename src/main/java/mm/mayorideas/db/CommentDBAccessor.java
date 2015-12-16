package mm.mayorideas.db;

import mm.mayorideas.model.Comment;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

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

    public List<Comment> getAllCommentsForIdea(int ideaID) throws SQLException {
        String QUERY = "select Comment.ID, Comment.UserID, Comment.IdeaID, User.Name, Comment.Text, Comment.DateCreated " +
                       "from Comment join User on Comment.UserID = User.ID " +
                       "where Comment.IdeaID = ? " +
                       "order by Comment.DateCreated DESC;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, ideaID);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Comment> comments = new LinkedList<>();
        while (resultSet.next()) {
            comments.add(new Comment(
                            resultSet.getInt(1),         //comment ID
                            resultSet.getInt(2),         //user ID
                            resultSet.getInt(3),         //idea ID
                            resultSet.getString(4),      //user name
                            resultSet.getString(5),      //comment text
                            resultSet.getTimestamp(6))); //date created
        }

        connection.close();
        preparedStatement.close();

        return comments;
    }

    public List<Comment> getLast2CommentsForIdea(int ideaID) throws SQLException {
        String QUERY =  "select Comment.ID, Comment.UserID, Comment.IdeaID, User.Name, Comment.Text, Comment.DateCreated " +
                        "from Comment join User on Comment.UserID = User.ID " +
                        "where Comment.IdeaID = ? " +
                        "order by Comment.DateCreated DESC " +
                        "limit 2;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, ideaID);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Comment> comments = new LinkedList<>();
        while (resultSet.next()) {
            comments.add(new Comment(
                    resultSet.getInt(1),         //comment ID
                    resultSet.getInt(2),         //user ID
                    resultSet.getInt(3),         //idea ID
                    resultSet.getString(4),      //user name
                    resultSet.getString(5),      //comment text
                    resultSet.getTimestamp(6))); //date created
        }

        connection.close();
        preparedStatement.close();

        return comments;
    }

    public int getCommentCountForIdea(int ideaID) throws SQLException {
        String QUERY =  "select count(*) from Comment where IdeaID = ?;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, ideaID);
        ResultSet resultSet = preparedStatement.executeQuery();

        int result = resultSet.next() ? resultSet.getInt(1) : 0;

        connection.close();
        preparedStatement.close();

        return result;

    }
}
