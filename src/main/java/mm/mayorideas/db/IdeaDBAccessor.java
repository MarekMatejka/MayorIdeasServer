package mm.mayorideas.db;

import mm.mayorideas.model.Idea;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class IdeaDBAccessor extends DBAccessor {

    private static IdeaDBAccessor dbAccessor = null;

    public static IdeaDBAccessor getInstance() {
        if (dbAccessor == null) {
            dbAccessor = new IdeaDBAccessor();
        }
        return dbAccessor;
    }

    private IdeaDBAccessor() {
        super();
    }

    public int addIdea(
            String title,
            int categoryID,
            String description,
            String location,
            int userID,
            Timestamp dateCreated) throws SQLException {

        String QUERY = "insert into Idea values(default, ?, ?, ?, ?, ?, ?);";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, categoryID);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, location);
        preparedStatement.setInt(5, userID);
        preparedStatement.setTimestamp(6, dateCreated);
        preparedStatement.executeUpdate();

        ResultSet ids = preparedStatement.getGeneratedKeys();
        int result = ids.next() ? ids.getInt(1) : -1;

        connection.close();
        preparedStatement.close();

        return result;
    }

    public Idea getIdea(int ID, int userID) throws SQLException {
        String QUERY =
                "select " +
                        "Idea.ID, Idea.Title, Idea.CategoryID, Category.Name, Idea.Description, Idea.Location, " +
                        "Idea.UserID, User.Name, Idea.DateCreated, ifnull(sum(Vote.Voted), 0), count(Vote.Voted), " +
                        "ifnull(A.count, 0), ifnull(B.vote, 0), if(C.ID > 0, true, false) " +
                "from Idea " +
                        "join Category on Idea.CategoryID = Category.ID " +
                        "join User on Idea.UserID = User.ID " +
                        "left join Vote on Idea.ID = Vote.IdeaID " +
                        "left join (" +
                            "select Idea.ID, count(Comment.UserID) as count " +
                            "from Idea " +
                            "join Comment on Idea.ID = Comment.IdeaID " +
                            "group by Idea.ID) as A " +
                        "on A.ID = Idea.ID " +
                        "left join (" +
                            "select Idea.ID, Vote.Voted as vote " +
                            "from Idea join Vote on Idea.ID = Vote.IdeaID where Vote.UserID = ?) as B " +
                        "on B.ID = Idea.ID " +
                        "left join (" +
                            "select Idea.ID " +
                            "from Idea join Follows on Idea.ID = Follows.IdeaID where Follows.UserID = ?) as C " +
                        "on C.ID = Idea.ID " +
                "where Idea.ID = ?;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, userID);
        preparedStatement.setInt(3, ID);
        ResultSet resultSet = preparedStatement.executeQuery();

        Idea idea = null;
        if(resultSet.next()) {
            idea = new Idea(
                    ID,
                    resultSet.getString(1),     //title
                    resultSet.getInt(2),        //category id
                    resultSet.getString(3),     //category name
                    resultSet.getString(4),     //description
                    resultSet.getString(5),     //location
                    resultSet.getInt(6),        //user id
                    resultSet.getString(7),     //user name
                    resultSet.getTimestamp(8),  //date created
                    resultSet.getInt(9),        //score
                    resultSet.getInt(10),       //num of votes
                    resultSet.getInt(11),       //num of comments
                    resultSet.getInt(12),       //user vote
                    resultSet.getBoolean(13));  //is user following
        }

        connection.close();
        preparedStatement.close();

        return idea;
    }

    public List<Idea> getTop10Ideas(int userID) throws SQLException {
        String QUERY =
                "select " +
                        "Idea.ID, Idea.Title, Idea.CategoryID, Category.Name, Idea.Description, Idea.Location, " +
                        "Idea.UserID, User.Name, Idea.DateCreated, ifnull(sum(Vote.Voted), 0), count(Vote.Voted), " +
                        "ifnull(A.count, 0), ifnull(B.vote, 0), if(C.ID > 0, true, false) " +
                "from Idea " +
                        "join Category on Idea.CategoryID = Category.ID " +
                        "join User on Idea.UserID = User.ID " +
                        "left join Vote on Idea.ID = Vote.IdeaID " +
                        "left join (" +
                            "select Idea.ID, count(Comment.UserID) as count " +
                            "from Idea " +
                            "join Comment on Idea.ID = Comment.IdeaID " +
                            "group by Idea.ID) as A " +
                        "on A.ID = Idea.ID " +
                        "left join (" +
                            "select Idea.ID, Vote.Voted as vote " +
                            "from Idea join Vote on Idea.ID = Vote.IdeaID where Vote.UserID = ?) as B " +
                        "on B.ID = Idea.ID " +
                        "left join (" +
                            "select Idea.ID " +
                            "from Idea join Follows on Idea.ID = Follows.IdeaID where Follows.UserID = ?) as C " +
                        "on C.ID = Idea.ID " +
                "group by Idea.ID " +
                "limit 10;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, userID);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Idea> ideas = new LinkedList<>();
        while(resultSet.next()) {
            ideas.add(new Idea(
                    resultSet.getInt(1),         //id
                    resultSet.getString(2),      //title
                    resultSet.getInt(3),         //category id
                    resultSet.getString(4),      //category name
                    resultSet.getString(5),      //description
                    resultSet.getString(6),      //location
                    resultSet.getInt(7),         //user id
                    resultSet.getString(8),      //user name
                    resultSet.getTimestamp(9),   //date created
                    resultSet.getInt(10),        //score
                    resultSet.getInt(11),        //num of votes
                    resultSet.getInt(12),        //comment count
                    resultSet.getInt(13),        //user vote
                    resultSet.getBoolean(14)));  //is user following
        }

        connection.close();
        preparedStatement.close();

        return ideas;
    }
}
