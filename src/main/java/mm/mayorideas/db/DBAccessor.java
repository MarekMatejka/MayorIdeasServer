package mm.mayorideas.db;

import mm.mayorideas.properties.DBProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBAccessor {
    protected static Properties properties = null;

    protected DBAccessor() {
        properties = new DBProperties();
        try {
            Class.forName(properties.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {e.printStackTrace();}
    }

    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty("db.schema"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));
    }
}
