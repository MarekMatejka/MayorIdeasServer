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
            double latitude,
            double longitude,
            int userID,
            Timestamp dateCreated) throws SQLException {

        String QUERY = "insert into Idea values(default, ?, ?, ?, ?, ?, ?, ?);";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, categoryID);
        preparedStatement.setString(3, description);
        preparedStatement.setInt(4, userID);
        preparedStatement.setTimestamp(5, dateCreated);
        preparedStatement.setDouble(6, latitude);
        preparedStatement.setDouble(7, longitude);
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
                        "Idea.ID, Idea.Title, Idea.CategoryID, Category.Name, Idea.Description, Idea.Latitude, Idea.Longitude, " +
                        "Idea.UserID, User.Name, Idea.DateCreated, ifnull(sum(Vote.Voted), 0), count(Vote.Voted), " +
                        "ifnull(A.count, 0), ifnull(B.vote, 0), if(C.ID > 0, true, false), ifnull(D.PID, 109) " +
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
                        "left join (" +
                            "select Idea.ID, Picture.ID as PID " +
                            "from Idea join Picture on Idea.ID = Picture.IdeaID) as D " +
                        "on D.ID = Idea.ID " +
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
                    resultSet.getDouble(5),     //latitude
                    resultSet.getDouble(6),     //longitude
                    resultSet.getInt(7),        //user id
                    resultSet.getString(8),     //user name
                    resultSet.getTimestamp(9),  //date created
                    resultSet.getInt(10),       //score
                    resultSet.getInt(11),       //num of votes
                    resultSet.getInt(12),       //num of comments
                    resultSet.getInt(13),       //user vote
                    resultSet.getBoolean(14),   //is user following
                    resultSet.getInt(15));      //cover image ID
        }

        connection.close();
        preparedStatement.close();

        return idea;
    }

    public List<Idea> getTop10Ideas(int userID) throws SQLException {
        String QUERY =
                "select " +
                        "Idea.ID, Idea.Title, Idea.CategoryID, Category.Name, Idea.Description, Idea.Latitude, Idea.Longitude, " +
                        "Idea.UserID, User.Name, Idea.DateCreated, ifnull(sum(Vote.Voted), 0), count(Vote.Voted), " +
                        "ifnull(A.count, 0), ifnull(B.vote, 0), if(C.ID > 0, true, false), ifnull(D.PID, 109) " +
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
                        "left join (" +
                            "select Idea.ID, Picture.ID as PID " +
                            "from Idea join Picture on Idea.ID = Picture.IdeaID) as D " +
                        "on D.ID = Idea.ID " +
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
                    resultSet.getDouble(6),      //latitude
                    resultSet.getDouble(7),      //longitude
                    resultSet.getInt(8),         //user id
                    resultSet.getString(9),      //user name
                    resultSet.getTimestamp(10),   //date created
                    resultSet.getInt(11),        //score
                    resultSet.getInt(12),        //num of votes
                    resultSet.getInt(13),        //comment count
                    resultSet.getInt(14),        //user vote
                    resultSet.getBoolean(15),    //is user following
                    resultSet.getInt(16)));      //cover image ID
        }

        connection.close();
        preparedStatement.close();

        return ideas;
    }

    public List<Idea> getClosestIdeas(double lat, double lang) throws SQLException {

        String QUERY =  "select * from (" +
                            "select A.*, sqrt(pow(?-a.lat, 2)+pow(?-a.lang, 2)) dst from LatLangTest A) t " +
                        "order by dst " +
                        "limit 10";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setDouble(1, lat);
        preparedStatement.setDouble(2, lang);
        ResultSet resultSet = preparedStatement.executeQuery();

        return null;
    }
}
