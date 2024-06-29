import java.sql.*;
import java.util.ArrayList;

public class DBHelper {
    private String userName;
    private String password;
    private int port;
    private String dataBase;
    private String host;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;


    public DBHelper(String userName, String password, int port, String dataBase, String host) {
        this.userName = userName;
        this.password = password;
        this.port = port;
        this.dataBase = dataBase;
        this.host = host;
    }

    public void connectDatabase() throws SQLException {
        String dbUrl = "jdbc:mysql://" + host + ":" + port + "/" + dataBase + "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(dbUrl, userName, password);
            System.out.println("<DEBUG> " + host + ":" + port + " adresi üzerinden bağlantı sağlandı.");
        } catch (SQLException exception) {
            errorMessage(exception, "connectDatabase");
        }
    }

    public void closeDatabase() throws SQLException {
        if (connection == null) {
            return;
        }
        if (statement != null) {
            statement.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }

    public void errorMessage(SQLException exception, String method) {
        System.out.println(method + " Hata : " + exception.getMessage());
        System.out.println(method + " Hata Kodu : " + exception.getErrorCode());
    }

    public ResultSet selectQuery(String query) throws SQLException {
        if (connection == null) {
            System.out.println("Veritabanı bağlantısı bulunamadı!");
            return null;
        }
        statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException exception) {
            errorMessage(exception, "selectQuery");
        }
        return resultSet;
    }

    public void insertQuery(String query, ArrayList values) throws SQLException {
        if (connection == null) {
            System.out.println("Veritabanı bağlantısı bulunamadı!");
            return;
        }
        preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < values.size(); i++) {
                ArrayList list = (ArrayList) values.get(i);
                for (int k = 1; k < list.size() + 1; k++) {
                    preparedStatement.setObject(k, list.get(k - 1));
                }
            }
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Insert işlemi başarıyla gerçekleştirildi.");
            }

        } catch (SQLException exception) {
            errorMessage(exception, "insertQuery");
        }
    }

    public void updateQuery(String query, ArrayList values) throws SQLException {
        if (connection == null) {
            System.out.println("Veritabanı bağlantısı bulunamadı!");
            return;
        }
        preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < values.size(); i++) {
                ArrayList list = (ArrayList) values.get(i);
                for (int k = 1; k < list.size() + 1; k++) {
                    preparedStatement.setObject(k, list.get(k - 1));
                }
            }
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Update işlemi başarıyla gerçekleştirildi.");
            }

        } catch (SQLException exception) {
            errorMessage(exception, "updateQuery");
        }
    }
}
