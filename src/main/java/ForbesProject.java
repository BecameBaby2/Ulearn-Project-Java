import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ForbesProject {
    public static void main(String[] args) throws SQLException, IOException, CsvException, ClassNotFoundException
    {
        ArrayList<Forbes> participant = getParticipant();
        Connection connect = DriverManager.getConnection("jdbc:sqlite:ForbesDB.db");
        Statement statementDB = connect.createStatement();

        Class.forName("org.sqlite.JDBC");
        statementDB.execute("drop table 'ForbesDB';");
        statementDB.execute("CREATE TABLE 'ForbesDB' ('Rank' int, 'Name' varchar, 'Networth' real,'Age' int, 'Country' varchar, 'Source' varchar, 'Industry' varchar); ");
        var result = connect.prepareStatement("INSERT INTO 'ForbesDB' ('Rank', 'Name', 'Networth', 'Age', 'Country', 'Source', 'Industry') VALUES (?,?,?,?,?,?,?);");
        for (Forbes i: participant) {
            result.setInt(1, i.getRank());
            result.setString(2, i.getName());
            result.setDouble(3, i.getNetworth());
            result.setDouble(4, i.getAge());
            result.setString(5, i.getCountry());
            result.setString(6, i.getSource());
            result.setString(7, i.getIndustry().trim());
            result.executeUpdate();
        }
        DBTasks.run(connect);
    }
    private static ArrayList<Forbes> getParticipant() throws IOException, CsvException
    {
        ArrayList<Forbes> participant = new ArrayList<Forbes>();
        CSVReader reader = new CSVReader(new FileReader("Forbes.csv"));
        reader.readNext();
        for (String[] j: reader.readAll()){
            Forbes resultSet = new Forbes();
            resultSet.setRank(Integer.parseInt(j[0]));
            resultSet.setName(j[1]);
            resultSet.setNetworth(Double.parseDouble(j[2]));
            resultSet.setAge(Double.parseDouble(j[3]));
            resultSet.setCountry(j[4]);
            resultSet.setSource(j[5]);
            resultSet.setIndustry(j[6]);
            participant.add(resultSet);
        }
        return participant;
    }
}
