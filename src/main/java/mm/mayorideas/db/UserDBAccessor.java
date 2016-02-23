package mm.mayorideas.db;

import com.sun.istack.internal.Nullable;
import mm.mayorideas.gson.LoginDetails;
import mm.mayorideas.gson.LoginDetailsResponse;
import mm.mayorideas.gson.NewUserDetails;

import java.sql.*;

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
                        "where User.Username = ? and User.Password = ?;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setString(1, loginDetails.getUsername());
        preparedStatement.setString(2, loginDetails.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();

        LoginDetailsResponse response = null;
        if (resultSet.next()) {
            response = new LoginDetailsResponse(
                    resultSet.getInt(1), //ID
                    resultSet.getString(2), //Username
                    resultSet.getString(3) //Name
            );
        }

        connection.close();
        preparedStatement.close();

        return response;
    }

    public int addNewUser(NewUserDetails newUserDetails) throws SQLException {
        String QUERY = "insert into User values(default, ?, ?, ?, ?);";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, newUserDetails.getUsername());
        preparedStatement.setString(2, newUserDetails.getName());
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
}
