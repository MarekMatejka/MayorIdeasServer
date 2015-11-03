package mm.mayorideas.properties;

import java.io.IOException;
import java.util.Properties;

public class DBProperties extends Properties {

    public DBProperties() {
        try {
            this.load(DBProperties.class.getClassLoader().getResourceAsStream("properties/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
