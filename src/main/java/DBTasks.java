import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTasks {
    public static void run(Connection connect) throws SQLException
    {
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT Country, SUM(Networth) AS SumOfNetworth FROM ForbesDB GROUP BY Country ORDER BY SumOfNetworth;");
        while (resultSet.next())
        {
            System.out.println(resultSet.getString("Country") + " = " + resultSet.getString("SumOfNetworth").replace('.', ','));
        }

        System.out.println(statement.executeQuery("SELECT * FROM ForbesDB WHERE Country = 'France' AND Networth > 10 ORDER BY Age").getString("Name"));

        resultSet = statement.executeQuery("SELECT * FROM ForbesDB WHERE Country = 'United States' AND Industry = 'Energy' ORDER BY Networth DESC;");
        System.out.println(resultSet.getString("Name") + " " + resultSet.getString("Source"));
        statement.close();
    }
}
