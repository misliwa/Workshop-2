package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private static final String DB_URL = DbCredentials.getDbUrl();
    private static final String DB_USER = DbCredentials.getDbUser();
    private static final String DB_PASS = DbCredentials.getDbPass();

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}


