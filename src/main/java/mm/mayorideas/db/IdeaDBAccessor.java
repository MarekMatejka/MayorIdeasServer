package mm.mayorideas.db;

import mm.mayorideas.model.Idea;
import mm.mayorideas.model.IdeaState;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class IdeaDBAccessor extends DBAccessor {

    private static final String SELECT_IDEA = "select " +
            "Idea.ID, Idea.Title, Idea.CategoryID, Category.Name cat_name, Idea.Description, Idea.Latitude, Idea.Longitude, " +
            "Idea.UserID, User.Name, Idea.DateCreated, ifnull(sum(Vote.Voted), 0) score, count(Vote.Voted) votes, " +
            "ifnull(A.count, 0) comments, ifnull(B.vote, 0) user_vote, if(C.ID > 0, true, false) isFollowing, " +
            "ifnull(D.PID, 109) cover_image_ID, Idea.State ";

    private static final String FROM_IDEA = "from Idea " +
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
            "on D.ID = Idea.ID ";

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

        String QUERY = "insert into Idea values(default, ?, ?, ?, ?, ?, ?, ?, ?);";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, categoryID);
        preparedStatement.setString(3, description);
        preparedStatement.setInt(4, userID);
        preparedStatement.setTimestamp(5, dateCreated);
        preparedStatement.setDouble(6, latitude);
        preparedStatement.setDouble(7, longitude);
        preparedStatement.setInt(8, IdeaState.OPEN.getId());
        preparedStatement.executeUpdate();

        ResultSet ids = preparedStatement.getGeneratedKeys();
        int result = ids.next() ? ids.getInt(1) : -1;

        connection.close();
        preparedStatement.close();

        return result;
    }

    public Idea getIdea(int ID, int userID) throws SQLException {
        String QUERY =
                SELECT_IDEA +
                FROM_IDEA +
                "where Idea.ID = ?;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, userID);
        preparedStatement.setInt(3, ID);
        ResultSet resultSet = preparedStatement.executeQuery();

        Idea idea = getIdeasFromResultSet(resultSet).get(0);

        connection.close();
        preparedStatement.close();

        return idea;
    }

    public List<Idea> getTop10Ideas(int userID) throws SQLException {
        String QUERY =
                SELECT_IDEA +
                FROM_IDEA +
                "group by Idea.ID " +
                "order by score desc " +
                "limit 10;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, userID);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Idea> ideas = getIdeasFromResultSet(resultSet);

        connection.close();
        preparedStatement.close();

        return ideas;
    }

    public List<Idea> getClosestIdeas(double lat, double lang, int userID) throws SQLException {
        String QUERY =
                SELECT_IDEA + ", sqrt(pow(?-Idea.Latitude, 2)+pow(?-Idea.Longitude, 2)) dst " +
                FROM_IDEA +
                "group by Idea.ID " +
                "order by dst asc " +
                "limit 10;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setDouble(1, lat);
        preparedStatement.setDouble(2, lang);
        preparedStatement.setInt(3, userID);
        preparedStatement.setInt(4, userID);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Idea> ideas = getIdeasFromResultSet(resultSet);

        connection.close();
        preparedStatement.close();

        return ideas;
    }

    private List<Idea> getIdeasFromResultSet(ResultSet resultSet) throws SQLException {
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
                    resultSet.getTimestamp(10),  //date created
                    resultSet.getInt(11),        //score
                    resultSet.getInt(12),        //num of votes
                    resultSet.getInt(13),        //comment count
                    resultSet.getInt(14),        //user vote
                    resultSet.getBoolean(15),    //is user following
                    resultSet.getInt(16),        //cover image ID
                    resultSet.getInt(17)));      //state
        }
        return ideas;
    }

    public List<Idea> getMyIdeas(int userID) throws SQLException {
        String QUERY =
                SELECT_IDEA +
                FROM_IDEA +
                "where Idea.UserID = ? " +
                "group by Idea.ID " +
                "order by Idea.ID desc;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, userID);
        preparedStatement.setInt(3, userID);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Idea> ideas = getIdeasFromResultSet(resultSet);

        connection.close();
        preparedStatement.close();

        return ideas;
    }

    public List<Idea> getFollowingIdeas(int userID) throws SQLException {
        String QUERY =
                SELECT_IDEA +
                        FROM_IDEA +
                        "group by Idea.ID " +
                        "having isFollowing = true " +
                        "order by Idea.ID desc;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, userID);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Idea> ideas = getIdeasFromResultSet(resultSet);

        connection.close();
        preparedStatement.close();

        return ideas;
    }

    public List<Idea> getTrendingIdeas(int userID) throws SQLException {
        String QUERY =
            "select *, (votes2+comments2) interactions " +
            "from ( " +
                SELECT_IDEA + ", count(Vote2.Voted) votes2, ifnull(A2.count, 0) comments2 " +
                FROM_IDEA +
                    "left join (" +
                        "select Vote.IdeaID, Vote.Voted " +
                        "from Vote " +
                        "where cast(Vote.DateVoted as Date) >= date_sub(current_date, interval 7 day)) as Vote2 " +
                    "on Idea.ID = Vote2.IdeaID " +
                    "left join (" +
                        "select Idea.ID, count(Comment.UserID) as count " +
                        "from Idea " +
                        "join Comment on Idea.ID = Comment.IdeaID " +
                        "where cast(Comment.DateCreated as Date) >= date_sub(current_date, interval 7 day) " +
                        "group by Idea.ID) as A2 " +
                    "on A2.ID = Idea.ID " +
                "group by Idea.ID ) as X "+
            "order by interactions desc "+
            "limit 10;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, userID);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Idea> ideas = getIdeasFromResultSet(resultSet);

        connection.close();
        preparedStatement.close();

        return ideas;
    }

    public List<Idea> getIdeasInCategory(int categoryID, int userID) throws SQLException {
        String QUERY =
                SELECT_IDEA +
                        FROM_IDEA +
                        "where Idea.CategoryID = ? " +
                        "group by Idea.ID " +
                        "order by Idea.DateCreated desc " +
                        "limit 10;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, userID);
        preparedStatement.setInt(3, categoryID);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Idea> ideas = getIdeasFromResultSet(resultSet);

        connection.close();
        preparedStatement.close();

        return ideas;
    }
}
