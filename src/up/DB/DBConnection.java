package up.DB;

import java.sql.*;

public class DBConnection {
    private Connection conn;

    public DBConnection(){
        conn = null;
    }

    public Connection connectToSqlite(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
            System.out.println("Nawiązano połączenie z bazą");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection connectToMySql(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            System.out.println("Nawiązano połączenie z bazą");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createTable(){
        PreparedStatement preper = null;
        try {
            preper = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Person" +
                            "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "Name TEXT," +
                            "LastName TEXT," +
                            "Age INTEGER)"
            );
            preper.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void disconnect(){
        try {
            if (!conn.isClosed()){
                System.out.println("Zakończono połączenie");
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Savepoint getPoint(){
        try {
            Savepoint s = conn.setSavepoint();
            conn.setAutoCommit(false);
            System.out.println("Uwtorzono punkt przywracania");
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getRollback(Savepoint point){
        try {
            conn.rollback(point);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
