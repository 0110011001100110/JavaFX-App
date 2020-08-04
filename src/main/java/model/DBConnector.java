package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    //DB settings for postgresql
    private final static String db_driver = "org.postgresql.Driver";
    protected static String db_url = "jdbc:postgresql://127.0.0.1:5432/";
    private final static String db_owner = "postgres";
    private final static String db_pass = "ropa0110";

    private static Connection connection = null;

    public static void setDb_url(String db_url) {
        DBConnector.db_url = db_url;
    }

    public static Connection getConnection(){

        // Connect to postgresql database
        try {
            connection = DriverManager.getConnection(db_url, db_owner, db_pass);

            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return connection;

    }
}
