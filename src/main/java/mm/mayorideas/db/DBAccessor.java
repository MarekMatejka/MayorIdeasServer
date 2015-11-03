package mm.mayorideas.db;

import mm.mayorideas.properties.DBProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBAccessor {
    protected static Map<Type, DBAccessor> accessorMap = new HashMap<>();
    protected static Properties properties = null;

    public enum Type {
        IDEA, CATEGORY, PICTURE, COMMENT, AUTHOR
    }

    public static DBAccessor getInstance(Type type) {
        if (!accessorMap.containsKey(type)) {
            switch (type) {
                case IDEA: accessorMap.put(Type.IDEA, new IdeaDBAccessor()); break;
            }
        }
        return accessorMap.get(type);
    }

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
