import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws SQLException {

        // DB Bağlantı Başlangıç
        DBHelper dbHelper = new DBHelper("root", "123456", 3306, "sakila", "localhost");
        dbHelper.connectDatabase();
        // DB Select Query
        ResultSet resultSet = dbHelper.selectQuery("select * from actor");
        while (resultSet.next()) {
            System.out.println("İsim : " + resultSet.getString("first_name") + " - Soyisim : " + resultSet.getString("last_name"));
        }
        // DB Insert Query
        ArrayList insertMainList = new ArrayList();
        ArrayList insertValues = new ArrayList();
        insertValues.add("İsmail");
        insertValues.add("Bozan");
        insertMainList.add(insertValues);
        dbHelper.insertQuery("insert into actor (first_name, last_name) values(?,?)", insertMainList);
        // DB Update Query
        ArrayList updateList = new ArrayList();
        ArrayList updateValues = new ArrayList();
        updateValues.add("bozan");
        updateList.add(updateValues);
        dbHelper.updateQuery("update actor set last_name = ? where actor_id = 201", updateList);
        // DB Bağlantı Bitiş
        dbHelper.closeDatabase();

    }
}
