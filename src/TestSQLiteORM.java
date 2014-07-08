import java.io.IOException;
import java.sql.SQLException;

/**
 * 
 */

/**
 * @author lfhernandez
 *
 */
public class TestSQLiteORM {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SQLiteORM sqliteORM = new SQLiteORM();
		
		try {
			sqliteORM.init();
			sqliteORM.generateClassFiles();
		} catch (ClassNotFoundException | SQLException | IOException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
