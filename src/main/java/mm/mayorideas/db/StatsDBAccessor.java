package mm.mayorideas.db;

import mm.mayorideas.gson.OverallIdeaStats;
import mm.mayorideas.gson.WeeklyStats;

import java.sql.*;

public class StatsDBAccessor extends DBAccessor {

    private static StatsDBAccessor dbAccessor = null;

    public static StatsDBAccessor getInstance() {
        if (dbAccessor == null) {
            dbAccessor = new StatsDBAccessor();
        }
        return dbAccessor;
    }

    private StatsDBAccessor() {
        super();
    }

    public WeeklyStats getWeeklyStats() throws SQLException {
        String QUERY = "select a, b, c, d, e, f, g, h, i, j from ( " +
                "select count(ID) a from Idea " +
                "where cast(DateCreated as Date) >= date_sub(current_date, interval 7 day) and state = 1) A join " +
                "(select count(ID) b from Idea " +
                "where cast(DateCreated as Date) >= date_sub(current_date, interval 14 day) and state = 1) B join " +
                "(select count(*) c from Vote " +
                "where cast(DateVoted as Date) >= date_sub(current_date, interval 7 day)) C join " +
                "(select count(*) d from Vote " +
                "where cast(DateVoted as Date) >= date_sub(current_date, interval 14 day)) D join " +
                "(select count(*) e from Comment " +
                "where cast(DateCreated as Date) >= date_sub(current_date, interval 7 day)) E join " +
                "(select count(*) f from Comment " +
                "where cast(DateCreated as Date) >= date_sub(current_date, interval 14 day)) F join " +
                "(select count(ID) g from Idea " +
                "where cast(DateCreated as Date) >= date_sub(current_date, interval 7 day) and state = 2) G join " +
                "(select count(ID) h from Idea " +
                "where cast(DateCreated as Date) >= date_sub(current_date, interval 14 day) and state = 2) H join " +
                "(select count(ID) i from Idea " +
                "where cast(DateCreated as Date) >= date_sub(current_date, interval 7 day) and state = 3) I join " +
                "(select count(ID) j from Idea " +
                "where cast(DateCreated as Date) >= date_sub(current_date, interval 14 day) and state = 3) J;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        final ResultSet resultSet = preparedStatement.executeQuery();

        WeeklyStats result = null;
        if (resultSet.next()) {
            result = new WeeklyStats(
                    resultSet.getInt(1),
                    resultSet.getInt(1)-resultSet.getInt(2),
                    resultSet.getInt(7),
                    resultSet.getInt(7)-resultSet.getInt(8),
                    resultSet.getInt(9),
                    resultSet.getInt(9)-resultSet.getInt(10),
                    resultSet.getInt(3)+resultSet.getInt(5),
                    (resultSet.getInt(3)+resultSet.getInt(5))-(resultSet.getInt(4)+resultSet.getInt(6)));
        }

        connection.close();
        preparedStatement.close();

        return result;
    }

    public OverallIdeaStats getOverallIdeaStats() throws SQLException {
        String QUERY = "select a, b, c from ( " +
                "select count(ID) a from Idea " +
                "where state = 1) A join " +
                "(select count(ID) b from Idea " +
                "where state = 2) B join " +
                "(select count(ID) c from Idea " +
                "where state = 3) C;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        final ResultSet resultSet = preparedStatement.executeQuery();

        OverallIdeaStats result = null;
        if (resultSet.next()) {
            result = new OverallIdeaStats(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3));
        }

        connection.close();
        preparedStatement.close();

        return result;
    }
}
