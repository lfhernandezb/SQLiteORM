import java.util.Map;

/**
 * 
 */

/**
 * @author lfhernandez
 *
 */
public class SQLiteORM extends GenericORM {

	public SQLiteORM() {
		super();
		// TODO Auto-generated constructor stub
		jdbcClassName = "org.sqlite.JDBC";
		strConnection = "jdbc:sqlite:" + System.getProperty("data_file");
	}
	
	/* (non-Javadoc)
	 * @see GenericORM#getCustomImports()
	 */
	@Override
	protected String getCustomImports() {
		// TODO Auto-generated method stub
		return
			"import org.simpleframework.xml.Element;\n" +
			"import org.simpleframework.xml.Root;\n";

	}

	/* (non-Javadoc)
	 * @see GenericORM#getClassModifier()
	 */
	@Override
	protected String getClassModifier() {
		// TODO Auto-generated method stub
		return 
			"@Root\n";
	}

	/* (non-Javadoc)
	 * @see GenericORM#getDeclarations()
	 */
	@Override
	protected String getDeclarations(Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys) {
		// TODO Auto-generated method stub
		String output = "";
		
	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        Column column = entry.getValue();
	        
	        output += "    @Element(name = \"";
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += column.getMemberName();
	        }
	        
	        output += "\"";
	        
	        if (column.getIsNullable().equals("YES")) {
	        	output += ", required = false";
	        }
	        
	        output += ")\n";

	        output += "    private " + mapJavaTypes.get(column.getBaseType()) + " _";

	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += column.getMemberName();
	        }
	        
	        output += ";\n";
	    }
		
		return output;
	}
	
	

}
