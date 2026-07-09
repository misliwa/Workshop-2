package pl.coderslab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DbCredentials {
    private static final String FILE_PATH = "database.properties";
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASS;

    public static void readDatabaseCredentials(){
        Properties properties = new Properties();

        try(FileInputStream fileInputStream = new FileInputStream(FILE_PATH)){
            properties.load(fileInputStream);
            DB_URL = properties.getProperty("db.url");
            DB_USER = properties.getProperty("db.user");
            DB_PASS = properties.getProperty("db.password");

        }catch (FileNotFoundException e){
            System.out.printf("File not found. Create file %s accordingly to its example%n", FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPass() {
        return DB_PASS;
    }
}
