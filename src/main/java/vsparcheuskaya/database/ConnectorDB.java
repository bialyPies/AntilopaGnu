package vsparcheuskaya.database;

import java.sql.Connection; //provides access to metadata
import java.sql.DriverManager; //singleton,
// contains info about all registered drivers
// provides tools for managing a set of database drivers
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectorDB {
    public static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("db", Locale.getDefault());
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        return DriverManager.getConnection(url, user, pass); //based on the URL parameter, it finds the corresponding database and calls its method connect
    }
}
