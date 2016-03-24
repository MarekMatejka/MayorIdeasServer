package mm.mayorideas.db;

import com.sun.istack.internal.Nullable;
import mm.mayorideas.gson.LoginDetails;
import mm.mayorideas.gson.LoginDetailsResponse;
import mm.mayorideas.gson.NewUserDetails;
import mm.mayorideas.gson.UserStats;
import mm.mayorideas.security.AESEncryptor;

import java.sql.*;
import java.util.Collections;

public class UserDBAccessor extends DBAccessor {

    private static UserDBAccessor dbAccessor = null;

    public static UserDBAccessor getInstance() {
        if (dbAccessor == null) {
            dbAccessor = new UserDBAccessor();
        }
        return dbAccessor;
    }

    private UserDBAccessor() {
        super();
    }

    public @Nullable LoginDetailsResponse verifyLoginDetails(LoginDetails loginDetails) throws SQLException {
        String QUERY =  "select User.ID, User.Username, User.Name " +
                        "from User " +
                        "where User.Username = ? and User.Password = ? and User.isCitizen = ?;";

        AESEncryptor aes = new AESEncryptor();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setString(1, aes.encrypt(loginDetails.getUsername()));
        preparedStatement.setString(2, loginDetails.getPassword());
        preparedStatement.setBoolean(3, loginDetails.isCitizen());
        ResultSet resultSet = preparedStatement.executeQuery();

        LoginDetailsResponse response = null;
        if (resultSet.next()) {
            response = new LoginDetailsResponse(
                    resultSet.getInt(1),    //ID
                    resultSet.getString(2), //Username
                    resultSet.getString(3)  //Name
            );
        }

        connection.close();
        preparedStatement.close();

        return response;
    }

    public int addNewUser(NewUserDetails newUserDetails) throws SQLException {
        String QUERY = "insert into User values(default, ?, ?, ?, ?);";

        AESEncryptor aes = new AESEncryptor();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, aes.encrypt(newUserDetails.getUsername()));
        preparedStatement.setString(2, aes.encrypt(newUserDetails.getName()));
        preparedStatement.setString(3, newUserDetails.getPassword());
        preparedStatement.setBoolean(4, newUserDetails.isCitizen());
        preparedStatement.executeUpdate();

        ResultSet ids = preparedStatement.getGeneratedKeys();
        int result = -1;
        if (ids.next()) {
            result = ids.getInt(1);
        }

        connection.close();
        preparedStatement.close();

        return result;
    }

    public @Nullable UserStats getUserStats(int userID) throws SQLException {
        String QUERY =
                "select A.UserID, ideas, votes, comments, follows from (" +
                    "select count(*) ideas, UserID from Idea where UserID = ?) as A " +
                    "join (select count(*) votes, UserID from Vote where UserID = ?) as B on A.UserID = B.UserID " +
                    "join (select count(*) comments, UserID from Comment where UserID = ?) as C on A.UserID = B.UserID " +
                    "join (select count(*) follows, UserID from Follows where UserID = ?) as D on A.UserID = D.UserID;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, userID);
        preparedStatement.setInt(3, userID);
        preparedStatement.setInt(4, userID);
        ResultSet resultSet = preparedStatement.executeQuery();

        UserStats stats = null;
        if (resultSet.next()) {
            stats = new UserStats(
                    resultSet.getInt(1),    //userID
                    resultSet.getInt(2),    //# of ideas
                    resultSet.getInt(3),    //# of votes
                    resultSet.getInt(4),    //# of comments
                    resultSet.getInt(5)     //# of follows
            );
        }

        connection.close();
        preparedStatement.close();

        return stats;
    }
}
