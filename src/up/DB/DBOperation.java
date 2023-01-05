package up.DB;

import com.mysql.cj.MysqlType;

import java.sql.*;
import java.util.Scanner;

public class DBOperation {
    private Connection conn;

    public DBOperation(Connection c) {
        conn = c;
    }

    public void insertPerson(String name, String lastName, int age){
        PreparedStatement preper = null;
        try {
            preper = conn.prepareStatement(
                    "INSERT INTO Person (Name, LastName, Age) VALUES (?, ?, ?)"
            );
            preper.setString(2, lastName);
            preper.setString(1, name);
            preper.setInt(3, age);

            int result = preper.executeUpdate();
            System.out.println("Wstawiono " + result + " wierszy");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectPerson(){
        try {
            PreparedStatement preper = conn.prepareStatement(
                    "SELECT * FROM Person",
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);

            ResultSet resultSet = preper.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String lastName = resultSet.getString("LastName");
                int age = resultSet.getInt("Age");

                System.out.println(id + " " + name + " " + lastName + " " + age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePerson() throws SQLException {
        System.out.println("Podaj ID osoby, której dane chcesz zaktualizować");
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Podaj nowe imię (wciśnij Enter aby pominąć)");
        String newName = scanner.nextLine();
        System.out.println("Podaj nowe nazwisko (wciśnij Enter aby pominąć)");
        String newLastName = scanner.nextLine();
        System.out.println("Podaj nowy wiek (wciśnij Enter aby pominąć)");
        String newAge = scanner.nextLine();
        scanner.close();

        PreparedStatement getNumberOfRecords = conn.prepareStatement("SELECT COUNT(*) FROM Person");
        ResultSet queryNumber = getNumberOfRecords.executeQuery();
        int numberOfRecords = 0;
        while (queryNumber.next()) {
            numberOfRecords++;
        }
//        System.out.println(numberOfRecords);
        System.out.println("ID:" + id + " Imie:" + newName + " Nazwisko:" + newLastName + " Wiek:" + newAge);
        if(id > 0 && id <= numberOfRecords) {
            if(!newName.equals("") && !newName.equals(" ")) {
                PreparedStatement updateName = conn.prepareStatement("UPDATE Person SET Name = ? WHERE ID=?");
                updateName.setString(1, newName);
                updateName.setInt(2, id);
                updateName.execute();
            }
            if(!newLastName.equals("") && !newLastName.equals(" ")) {
                PreparedStatement updateLastName = conn.prepareStatement("UPDATE Person SET LastName = ? WHERE ID=?");
                updateLastName.setString(1, newLastName);
                updateLastName.setInt(2, id);
                updateLastName.execute();
            }

            if(!newAge.equals("") && !newAge.equals(" ")) {
                PreparedStatement updateAge = conn.prepareStatement("UPDATE Person SET Age = ? WHERE ID=?");
                updateAge.setInt(1, Integer.parseInt(newAge));
                updateAge.setInt(2, id);
                updateAge.execute();
            }
        } else {
            System.out.println("Wybrane ID nie znajduje sie w bazie");
        }
    }

    public void deleteRecord() throws SQLException {
        System.out.println("Podaj ID osoby, którą chcesz usunąć");
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        scanner.close();

        PreparedStatement getNumberOfRecords = conn.prepareStatement("SELECT COUNT(*) FROM Person");
        ResultSet queryNumber = getNumberOfRecords.executeQuery();
        int numberOfRecords = 0;
        while (queryNumber.next()) {
            numberOfRecords++;
        }
        if(id > 0 && id <= numberOfRecords) {
            PreparedStatement deletePerson = conn.prepareStatement("DELETE FROM Person WHERE ID = ?");
            deletePerson.setInt(1, id);
            deletePerson.execute();
        } else {
            System.out.println("Wybrane ID nie znajduje sie w bazie");
        }
    }

    public void getReversedRecordSet() throws SQLException {
        CallableStatement callableStatement = conn.prepareCall("{call GetAllPersons()}", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = callableStatement.executeQuery();

        resultSet.afterLast();

        while (resultSet.previous()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            int age = resultSet.getInt(4);

            System.out.println(String.format("%d %s %s %d", id, name, lastName, age));
        }
    }

    public void getCountPerson(){
        try {
            CallableStatement callableStatement = conn.prepareCall( "{call GetCountPerson(?) }" );
            callableStatement.registerOutParameter(1, MysqlType.INT);
            callableStatement.executeUpdate();

            int number = callableStatement.getInt(1);
            System.out.println("Liczba rekordów w tabeli " + number);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getPersons(int id){
        try {
            CallableStatement callableStatement = conn.prepareCall("{call GetPersons(?,?,?,?)}");
            callableStatement.setInt(1, id);
            callableStatement.registerOutParameter(2, MysqlType.FIELD_TYPE_VARCHAR);
            callableStatement.registerOutParameter(3, MysqlType.FIELD_TYPE_VARCHAR);
            callableStatement.registerOutParameter(4, MysqlType.INT);

            callableStatement.executeUpdate();

            String name = callableStatement.getString(2);
            String lastName = callableStatement.getString(3);
            int age = callableStatement.getInt(4);

            System.out.println(String.format("%s %s %d", name, lastName, age));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllPersons(){
        try {
            CallableStatement callableStatement = conn.prepareCall("{call GetAllPersons()}");
            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int age = resultSet.getInt(4);

                System.out.println(String.format("%d %s %s %d", id, name, lastName, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
