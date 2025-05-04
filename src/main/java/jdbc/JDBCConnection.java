package jdbc;

import java.sql.*;

public class JDBCConnection {
    Connection connection;
    public JDBCConnection() throws SQLException, ClassNotFoundException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/csulab6",
                "root",
                "root"
        );

//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("select * from `csulab6`.`users`");
//
//        while (resultSet.next()){
//            System.out.println("login");
//            System.out.println("password");
//        }
    }

    public ResultSet SQLRequest(String request) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(request);
    }

    public void SQLVoidRequest(String request) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(request); // для запросов, которые ничего не возвращают
    }

    public void Close() throws SQLException { connection.close(); }
}
