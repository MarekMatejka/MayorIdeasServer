package mm.mayorideas.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class ImagesDBAccessor extends DBAccessor {

    private static ImagesDBAccessor dbAccessor = null;

    public static ImagesDBAccessor getInstance() {
        if (dbAccessor == null) {
            dbAccessor = new ImagesDBAccessor();
        }
        return dbAccessor;
    }

    private ImagesDBAccessor() {
        super();
    }

    public String addImage(InputStream image, int ideaID) throws SQLException {
        Connection connection = getConnection();

        String INSERT_PICTURE = "insert into Picture values (default, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PICTURE, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setBlob(1, image);
        preparedStatement.setInt(2, ideaID);
        preparedStatement.executeUpdate();

        ResultSet ids = preparedStatement.getGeneratedKeys();
        String result  = ids.next() ? String.valueOf(ids.getLong(1)) : "-1";

        connection.close();
        preparedStatement.close();

        return result;
    }

    public File getImage(int imageID) throws SQLException {
        File image = new File(".\\"+imageID+".png");
        if (image.exists() && !image.isDirectory()) {
            return image;
        }

        Connection connection = getConnection();

        String sql = "SELECT id, picture FROM Picture WHERE id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, imageID);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            try {
                FileOutputStream fos = new FileOutputStream(image);

                InputStream is = resultSet.getBlob(2).getBinaryStream();
                byte[] buffer = new byte[is.available()];

                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }

                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                connection.close();
                return null;
            }
            connection.close();
            return image;
        }

        connection.close();
        return null;
    }
}
