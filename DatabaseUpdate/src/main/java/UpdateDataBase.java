import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDataBase {

	public static final String URL = "jdbc:mysql://localhost";
	public static final String USER = "root";
	public static final String PASSWORD = "rocet1990";

	public static void main(String[] agrs) {
		System.out.println("start");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			  connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(loadScript());
			System.out.println("statement");
			statement.execute();
			System.out.println("execute");
		} catch (SQLException e) {
			e.printStackTrace();
			 try {
				connection.rollback();
				 connection.setAutoCommit(true);
			} catch (SQLException e1) {
			}

		}
	}

	public static String loadScript() {
		System.out.println("start load");
		String scriptSQL;
		String result = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/DatabaseUpdate.sql"));
			while ((scriptSQL = br.readLine()) != null) {
				result += scriptSQL + "\n";
			}
		} catch (IOException e) {
		}
		System.out.println("load done");
		System.out.println(result);
		return result;
	}

}
