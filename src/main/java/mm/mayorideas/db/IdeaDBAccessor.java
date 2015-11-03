package mm.mayorideas.db;

import mm.mayorideas.model.Idea;

import java.sql.*;

public class IdeaDBAccessor extends DBAccessor {

    public IdeaDBAccessor() {
        super();
    }

    public int addIdea(
            String title,
            int categoryID,
            String description,
            String location,
            int authorID,
            Timestamp dateCreated) throws SQLException {

        String QUERY = "insert into Idea values(default, ?, ?, ?, ?, ?, ?);";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, categoryID);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, location);
        preparedStatement.setInt(5, authorID);
        preparedStatement.setTimestamp(6, dateCreated);
        preparedStatement.executeUpdate();

        ResultSet ids = preparedStatement.getGeneratedKeys();
        int result = ids.next() ? ids.getInt(1) : -1;

        connection.close();
        preparedStatement.close();

        return result;
    }

    public Idea getIdea(int ID) throws SQLException {
        String QUERY =
                "select " +
                    "Idea.Title, Idea.CategoryID, Category.Name, Idea.Description, Idea.Location, Idea.AuthorID, " +
                    "Author.Name, Idea.DateCreated " +
                "from Idea " +
                    "join Category on Idea.CategoryID = Category.ID " +
                    "join Author on Idea.AuthorID = Author.ID " +
                "where Idea.ID = ?;";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        preparedStatement.setInt(1, ID);
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
                    resultSet.getInt(6),        //author id
                    resultSet.getString(7),     //author name
                    resultSet.getTimestamp(8)); //date created
        }

        connection.close();
        preparedStatement.close();

        return idea;
    }
}
