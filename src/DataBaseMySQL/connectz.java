package DataBaseMySQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectz {
	public static Connection getJDBCConnection() throws SQLException {
        final String url = "jdbc:mysql://localhost:3306/flappybird?autoReconnect=true&useSSL=false";
        final String user = "root";
        final String password = "12345";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
        public static void main(String[] args) throws SQLException {
        Connection connection = getJDBCConnection();
        
        if (connection != null) {
            System.out.println("Data connection successful");
        } else {
            System.out.println("Data connection failed");
        }
    }

}

