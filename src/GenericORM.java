import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.activation.UnsupportedDataTypeException;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;
import org.ini4j.BasicMultiMap;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;

/**
 * 
 */

/**
 * @author lfhernandez
 *
 */
public class GenericORM {
	
	private Connection conn;
	protected String jdbcClassName;
	protected String strConnection;
	private String configFileName;
	private Wini ini;
	
	private DatabaseMetaData databaseMetaData;
	
	protected Map<String, String> mapJavaTypes;
	private Map<String, String> mapFunctionTypes;
	
	private Logger logger;

	/**
	 * 
	 */
	public GenericORM() {
		
		// TODO Auto-generated constructor stub
	}
	
	public void OpenConnection() throws ClassNotFoundException, SQLException {
    	// abro conexion a la BD
    	Class.forName(jdbcClassName);
    	conn = DriverManager.getConnection(strConnection);
	}
	
	public void init() throws ClassNotFoundException, SQLException, InvalidFileFormatException, IOException {
		String    catalog;
		String    schemaPattern;
		String    tableNamePattern;
		String[]  types;
		ResultSet rs;
		
    	// leo archivo de configuracion
    	
    	ini = new Wini();
    	
    	configFileName = System.getProperty("config_file");
    	
    	File f = new File(configFileName);
    	
    	if (!f.exists()) {
    		throw new FileNotFoundException("Config file does not exists");
    	}
    	
    	ini.load(new File(configFileName));

		OpenConnection();
				
		mapJavaTypes = new HashMap<String, String>();
		mapFunctionTypes = new HashMap<String, String>();
		
		mapJavaTypes.put("BIGINT", "Long");
		mapJavaTypes.put("INT", "Integer");
		mapJavaTypes.put("SMALLINT", "Short");
		mapJavaTypes.put("TINYINT", "Byte");
		mapJavaTypes.put("CHAR", "String");
		mapJavaTypes.put("VARCHAR", "String");
		mapJavaTypes.put("LONGVARCHAR", "String");
		mapJavaTypes.put("TEXT", "String");
		mapJavaTypes.put("DATE", "String");
		mapJavaTypes.put("DATETIME", "String");
		mapJavaTypes.put("TIMESTAMP", "String");
		mapJavaTypes.put("BIT", "Boolean");
		mapJavaTypes.put("BOOLEAN", "Boolean");
		mapJavaTypes.put("DECIMAL", "Decimal");
		mapJavaTypes.put("DOUBLE", "Double");
		mapJavaTypes.put("FLOAT", "Float");
		
		// para RecordSet.get<type>()
		mapFunctionTypes.put("BIGINT", "Long");
		mapFunctionTypes.put("INT", "Int");
		mapFunctionTypes.put("SMALLINT", "Short");
		mapFunctionTypes.put("TINYINT", "Byte");
		mapFunctionTypes.put("CHAR", "String");
		mapFunctionTypes.put("VARCHAR", "String");
		mapFunctionTypes.put("LONGVARCHAR", "String");
		mapFunctionTypes.put("TEXT", "String");
		mapFunctionTypes.put("DATE", "String");
		mapFunctionTypes.put("DATETIME", "String");
		mapFunctionTypes.put("TIMESTAMP", "String");
		mapFunctionTypes.put("BIT", "Boolean");
		mapFunctionTypes.put("BOOLEAN", "Boolean");
		mapFunctionTypes.put("DECIMAL", "Decimal");
		mapFunctionTypes.put("DOUBLE", "Double");
		mapFunctionTypes.put("FLOAT", "Float");
		
		logger = Logger.getLogger(GenericORM.class.getName());
    	
	}
	
	public void generateClassFiles() throws SQLException, IOException {

		String    catalog;
		String    schemaPattern;
		String    tableNamePattern;
		String[]  types;
		ResultSet rs;
		
		Map<String, Column> mapColumns;
		Map<String, PrimaryKey> mapPrimaryKeys;
		Map<String, ForeignKey> mapForeignKeys;
		
		mapColumns = new HashMap<String, Column>();
		mapPrimaryKeys = new HashMap<String, PrimaryKey>();
		mapForeignKeys = new HashMap<String, ForeignKey>();
		
    	// obtengo las tablas del modelo
    	databaseMetaData = conn.getMetaData();
    	
		catalog          = null;
		schemaPattern    = null;
		tableNamePattern = null;
		types            = new String[1];
		
		types[0] = "TABLE";
		
    	rs = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);

    	while(rs.next()) {
    		ResultSet rsColumns;
    		ResultSet rsPrimaryKeys;
    		ResultSet rsIndexInfo;
    		ResultSet rsImportedKeys;
    		String className;
    		String tableShortAlias;
    		String output;
    		
    		Boolean bFlag;

    		
    		mapColumns.clear();
    		mapPrimaryKeys.clear();
    		mapForeignKeys.clear();
    		
    	    String tableName = rs.getString(3);
    	    
    	    logger.debug("table: " + tableName);
    	    
    	    tableNamePattern = tableName;
    	    
    	    rsColumns = databaseMetaData.getColumns(null, null, tableNamePattern, null);

    	    rsPrimaryKeys = databaseMetaData.getPrimaryKeys(null, null, tableNamePattern);

    	    //rsIndexInfo = databaseMetaData.getIndexInfo(null, null, tableNamePattern, false, false);
    	    rsImportedKeys = databaseMetaData.getImportedKeys(null, null, tableNamePattern);
    	    
    	    //logger.debug("columns:");
    	    
    	    while(rsColumns.next()){
    	        String columnName = rsColumns.getString(4);
    	        
    	        mapColumns.put(columnName, Column.fromRS(rsColumns));
    	        /*
    	        if (tableName.equals("vehiculo")) {
    	        	logger.debug(Column.fromRS(rsColumns).toString());
    	        }
    	        */
    	    }
    	    
    	    while(rsPrimaryKeys.next()){
    	    	String columnName = rsPrimaryKeys.getString(4);
    	    	
    	    	mapPrimaryKeys.put(columnName, PrimaryKey.fromRS(rsPrimaryKeys));
    	    }
    	    
    	    while(rsImportedKeys.next()){
    	    	String columnName = rsImportedKeys.getString(8);
    	    	
    	    	mapForeignKeys.put(columnName, ForeignKey.fromRS(rsImportedKeys));
    	    }

    	    className = toJavaClassName(tableName);
    	    
    	    tableShortAlias = tableName.substring(0, 2);
    	    
    	    output = 
        	    "/**\n" +
           	    " * \n" +
                " */\n" +
           	    "package " + System.getProperty("package_name") + ";\n" +
           	    "\n" +
				"import java.sql.Connection;\n" +
				"import java.sql.ResultSet;\n" +
				"import java.sql.SQLException;\n" +
				"import java.sql.Statement;\n" +
				"import java.util.AbstractMap;\n" +
				"import java.util.ArrayList;\n" +
				"\n" +
				getCustomImports() +
				"\n" +
				"/**\n" +
				" * @author Luis Hernandez\n" +
				" *\n" +
				" */\n" +
				getClassModifier() +
				"public class " + className + " {\n";
    	    
    	    // declaraciones
    	    
    	    output += getDeclarations(mapColumns, mapPrimaryKeys);
    	        	    
    	    // sentencia SELECT
    	    
	        output += getSelectSentence(tableName, tableShortAlias, mapColumns, mapPrimaryKeys);
    	    
    	    // fin SELECT
    	    
    	    // constructor
    	    
	        output += getConstructor(className, mapColumns, mapPrimaryKeys);

			// fin constructor
    	    
    	    // getters
	        
	        output += getGetters(mapColumns, mapPrimaryKeys);
    	    
    	    // fin getters
    	    
    	    // setters
	        
	        output += getSetters(mapColumns, mapPrimaryKeys);
    	    
    	    // fin setters
    	    
    	    // fromRS
    	    
    	    output += getFromRS(className, mapColumns, mapPrimaryKeys);
    	           	    
    	    // fin fromRS
    	    
    	    // getByParameter
    	    
	        output += getGetByParameter(className, tableShortAlias);

	        // fin getByParameter
	        
	        // getById
	        
	        if (mapPrimaryKeys.size() == 1) {
    	        output += getGetById(className, tableName);
	        }

	        // fin getById
	        
	        // seek
    	    
	        output += getSeek(className, tableShortAlias, mapColumns, mapPrimaryKeys, mapForeignKeys);
    	    
    	    // fin seek
    	    
    	    // getNextId
    	    
    	    bFlag = false; 
    	    
	        if (mapPrimaryKeys.size() > 0) {
	        	
	        	for (Map.Entry<String, PrimaryKey> entry : mapPrimaryKeys.entrySet()) {
	        		if (mapColumns.get(entry.getKey()).getIsAutoincrement() == "YES") {
	        			bFlag = true;
	        		}
	        		else if (!mapForeignKeys.containsKey(entry.getKey())) {
	        			bFlag = false;
	        		}
	        	}
    	        
		        if (!bFlag) {
		    	    
		        	output += getGetNextId(tableName, mapColumns, mapPrimaryKeys, mapForeignKeys);
		        }
	        }
	        
    	    
    	    // fin getNextId
    	    
    	    // update
    	        
    	    output += getUpdate(tableName, mapColumns, mapPrimaryKeys, mapForeignKeys);
	        	
    	    // fin update
    	    
    	    // insert
	        	
    	    output += getInsert(tableName, mapColumns, mapPrimaryKeys, mapForeignKeys);
    	    
    	    // fin insert
    	    
    	    // delete
	        
	        output += getDelete(tableName, mapColumns, mapPrimaryKeys);

	       // fin delete 
	        
    	    // load
    	    
	        output += getLoad(className, mapColumns, mapPrimaryKeys);

	        // fin load
	        
    	    // save
    	    
	        output += getSave(mapColumns, mapPrimaryKeys);

	        // fin save
	        
    	    // toString
    	    
	        output += getToString(className, mapColumns, mapPrimaryKeys);

	        // fin toString
	        
    	    // toJSON
    	    /*
	        output +=
	        	"\n" +
	            "    public String toJSON() {\n" +
	            "        return \"{\\\"" + className + "\\\" : {\" +";
	        
	        bFirst = false;
	        
    	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
    	    	
    	        String columnName = entry.getKey();
    	        Column column = entry.getValue();
    	        
    	        if (!bFirst) {
    	        	bFirst = true;
    	        }
    	        else {
    	        	output += " + \",\" +";
    	        }
    	        
    	        output += "\n	           \"    \\\"_";
    	        
    	        switch(column.getBaseType()) {
    	        	case "BIGINT":
    	        	case "INT":
    	        	case "SMALLINT":
    	        	case "TINYINT":
    	        	case "DECIMAL":
    	        	case "DOUBLE":
    	        	case "FLOAT":
    	        	case "BIT":
    	        	case "BOOLEAN":

            	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
            	        	output += "id";
            	        }
            	        else {
            	        	output += column.getMemberName();
            	        }
            	        
            	        output += "\\\" : \" + (_";
            	        
            	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
            	        	output += "id";
            	        }
            	        else {
            	        	output += column.getMemberName();
            	        }
            	        
            	        output += " != null ? _";
            	        
            	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
            	        	output += "id";
            	        }
            	        else {
            	        	output += column.getMemberName();
            	        }
            	        
            	        output += " : \"null\")";
            	        
    	        		break;
    	        	case "CHAR":
    	        	case "VARCHAR":
    	        	case "LONGVARCHAR":
    	        	case "TEXT":
    	        	case "DATE":
    	        	case "DATETIME":
    	        	case "TIMESTAMP":
    	        		output += columnName + "\\\" : \" + (_" + column.getMemberName() + " != null ? \"\\\"\" + _" + column.getMemberName() + " + \"\\\"\" : \"null\")";	    	        		
    	        		break;
    	        	default:
    	        		throw new Exception("Tipo no soportado: " + column.getTypeName() + " columna: " + columnName);
    	        } // end switch
    	        
    	    }
	        
	        output +=
	        	" +" +
	            "\n			   \"}}\";" +
	            "\n" +
	            "    }\n" +
	            "\n";
			*/
	        // fin toJSON

    	    // toXML
    	    /*
	        output +=
	        	"\n" +
	            "    public String toXML() {\n" +
	            "        return \"<" + className + ">\" +";
	        
    	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
    	    	
    	        String columnName = entry.getKey();
    	        Column column = entry.getValue();
    	        
    	        output += "\n	           \"    <";
    	        
    	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
    	        	output += "id";
    	        }
    	        else {
    	        	output += column.getMemberName();
    	        }
    	        
    	        output += "\" + (_";
    	        
    	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
    	        	output += "id";
    	        }
    	        else {
    	        	output += column.getMemberName();
    	        }
    	        
    	        output += " != null ? \">\" + _";
    	        
    	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
    	        	output += "id";
    	        }
    	        else {
    	        	output += column.getMemberName();
    	        }
    	        
    	        output += " + \"</";
    	        		
    	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
    	        	output += "id";
    	        }
    	        else {
    	        	output += column.getMemberName();
    	        }
    	        		
    	        output += ">\" : \" xsi:nil=\\\"true\\\" xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\"/>\")" + " +";
            	        
    	        
    	    }
	        
	        output +=
	            "\n			   \"</" + className + ">\";" +
	            "\n" +
	            "    }\n" +
	            "\n";
			*/
	        // fin toXML
	        
    	    // fromXMLNode
    	    
    	    output +=
    	    	"\n" +
       	    	"/*\n" +
				"    public static " + className + " fromXMLNode(Node xmlNode) {\n" +
	            "        " + className + " ret = new " + className + "();\n\n" +
				"        Element element = (Element) xmlNode;\n\n";

    	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
    	    	
    	        String columnName = entry.getKey();
    	        Column column = entry.getValue();
    	        
    	        output += 
    	        	"        ret.set"; 
    	        
    	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
    	        	output += "Id";
    	        }
    	        else {
    	        	output += WordUtils.capitalize(column.getMemberName());
    	        }
    	        
    	        output += "(";
    	        
    	        if (mapFunctionTypes.get(column.getBaseType()).equals("String") ) {
    	        	output += "element.getElementsByTagName(\"" + columnName + "\").item(0).getTextContent()";
    	        }
    	        else {
    	        	output += mapJavaTypes.get(column.getBaseType()) + ".decode(element.getElementsByTagName(\"" + columnName + "\").item(0).getTextContent())";
    	        }

    	        output += 
    	        	");\n";

    	    }
    	    
    	    output +=
                "\n" +			
	            "        return ret;\n" +
	            "    }\n" +
                "    */\n";

    	          	    
    	    // fin fromXMLNode
	        
	        
	        
	        // fin clase
	        
	        output +=  
	        	"}\n";    	        	

	        
	        //System.out.println(output);
	        
	        writeToFile(System.getProperty("output_dir") + "/" + className + ".java", output);
    	} // end while rs (tabla)
		
	}
	
	private String getGetById(String className, String tableName) {
		// TODO Auto-generated method stub
		String output;
		
		output =
	    	"    public static " + className + " getById(Connection p_conn, String p_id) throws Exception {\n" +
	    	"        return getByParameter(p_conn, \"id_" + tableName + "\", p_id);\n" +
	    	"    }\n";
		
		return output;
	}

	private String getToString(String className, Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys) throws UnsupportedDataTypeException {
		// TODO Auto-generated method stub
		String output;
		Boolean bFirst;
		
		output =
	    	"\n" +
	        "@Override\n" +
	        "    public String toString() {\n" +
	        "        return \"" + className + " [\" +";
	    
	    bFirst = false;
	    
	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        Column column = entry.getValue();
	        
	        if (!bFirst) {
	        	bFirst = true;
	        }
	        else {
	        	output += " + \",\" +";
	        }
	        
	        output += "\n	           \"    _";
	        
	        switch(column.getBaseType()) {
	        	case "BIGINT":
	        	case "INT":
	        	case "SMALLINT":
	        	case "TINYINT":
	        	case "DECIMAL":
	        	case "DOUBLE":
	        	case "FLOAT":
	        	case "BIT":
	        	case "BOOLEAN":
	
	    	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	    	        	output += "id";
	    	        }
	    	        else {
	    	        	output += column.getMemberName();
	    	        }
	    	        
	    	        output += " = \" + (_";
	    	        
	    	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	    	        	output += "id";
	    	        }
	    	        else {
	    	        	output += column.getMemberName();
	    	        }
	    	        
	    	        output += " != null ? _";
	    	        
	    	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	    	        	output += "id";
	    	        }
	    	        else {
	    	        	output += column.getMemberName();
	    	        }
	    	        
	    	        output += " : \"null\")";
	    	        
	        		break;
	        	case "CHAR":
	        	case "VARCHAR":
	        	case "LONGVARCHAR":
	        	case "TEXT":
	        	case "DATE":
	        	case "DATETIME":
	        	case "TIMESTAMP":
	        		output += column.getMemberName() + " = \" + (_" + column.getMemberName() + " != null ? \"'\" + _" + column.getMemberName() + " + \"'\" : \"null\")";
	        		break;
	        	default:
	        		throw new UnsupportedDataTypeException("Tipo no soportado: " + column.getTypeName() + " columna: " + columnName);
	        } // end switch
	        
	    }
	    
	    output +=
	    	" +" +
	        "\n			   \"]\";" +
	        "\n" +
	        "    }\n" +
	        "\n";
		
		return output;
	}

	private String getSave(Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys) {
		// TODO Auto-generated method stub
		String output;
		
		output =
	    	"\n" +
	        "    public void save(Connection p_conn) throws SQLException {\n" +
	        "        \n" +
	        "        String str_sql = _str_sql +\n" +
	        "            \"    WHERE\" +\n";
	    
	    output += buildWhereSentence(mapColumns, mapPrimaryKeys, mapJavaTypes);
	    
	    output += 
	    	" +\n" +
	        "            \"    LIMIT 0, 1\";\n" +
	        "        \n" +
	        "        //System.out.println(str_sql);\n" +
	        "        \n" +
	        "        // assume that conn is an already created JDBC connection (see previous examples)\n" +
	        "        Statement stmt = null;\n" +
	        "        ResultSet rs = null;\n" +
	        "        Boolean exists = false;\n" +
	        "        \n" +
	        "        try {\n" +
	        "            stmt = p_conn.createStatement();\n" +
	        "            //System.out.println(\"stmt = p_conn.createStatement() ok\");\n" +
	        "            rs = stmt.executeQuery(str_sql);\n" +
	        "            //System.out.println(\"rs = stmt.executeQuery(str_sql) ok\");\n" +
	        "\n" +
	        "            // Now do something with the ResultSet ....\n" +
	        "\n" +
	        "            if (rs.next()) {\n" +
	        "                // registro existe\n" +
	        "                exists = true;\n" +
	        "            }\n" +
	        "\n" +
	        "            rs.close();" +
	        "\n" +
	        "            stmt.close();\n" +
	        "\n" +
	        "            if (exists) {\n" +
	        "            	// update\n" +
	        "            	update(p_conn);\n" +
	        "            }\n" +
	        "            else {\n" +
	        "            	// insert\n" +
	        "            	insert(p_conn);\n" +
	        "            }\n" +
	        "        }\n" +
	        "        catch (SQLException ex){\n" +
	        "            // handle any errors\n" +
	        "            System.out.println(\"SQLException: \" + ex.getMessage() + \" sentencia: \" + str_sql);\n" +
	        "            System.out.println(\"SQLState: \" + ex.getSQLState());\n" +
	        "            System.out.println(\"VendorError: \" + ex.getErrorCode());\n" +
	        "            \n" +
	        "            throw ex;\n" +
	        "        }\n" +
	        "        finally {\n" +
	        "            // it is a good idea to release\n" +
	        "            // resources in a finally{} block\n" +
	        "            // in reverse-order of their creation\n" +
	        "            // if they are no-longer needed\n" +
	        "            if (rs != null) {\n" +
	        "                try {\n" +
	        "                    rs.close();\n" +
	        "                } catch (SQLException sqlEx) { \n" +
	        "                    \n" +
	        "                } // ignore\n" +
	        "                rs = null;\n" +
	        "            }\n" +
	        "            if (stmt != null) {\n" +
	        "                try {\n" +
	        "                    stmt.close();\n" +
	        "                } catch (SQLException sqlEx) {\n" +
	        "                    \n" +
	        "                } // ignore\n" +
	        "                stmt = null;\n" +
	        "            }\n" +
	        "        }        \n" +
	        "        \n" +
	        "    }\n";
		
		return output;
	}

	private String getLoad(String className, Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys) {
		// TODO Auto-generated method stub
		String output;
		
		output =
	    	"\n" +
	        "    public void load(Connection p_conn) throws SQLException {\n" +
	        "        " + className + " obj = null;\n" +
	        "        \n" +
	        "        String str_sql = _str_sql +\n" +
	        "            \"    WHERE\" +\n";
	    
	    output += buildWhereSentence(mapColumns, mapPrimaryKeys, mapJavaTypes);
	    
	    output += 
	    	" +\n" +
	        "            \"    LIMIT 0, 1\";\n" +
	        "        \n" +
	        "        //System.out.println(str_sql);\n" +
	        "        \n" +
	        "        // assume that conn is an already created JDBC connection (see previous examples)\n" +
	        "        Statement stmt = null;\n" +
	        "        ResultSet rs = null;\n" +
	        "        \n" +
	        "        try {\n" +
	        "            stmt = p_conn.createStatement();\n" +
	        "            //System.out.println(\"stmt = p_conn.createStatement() ok\");\n" +
	        "            rs = stmt.executeQuery(str_sql);\n" +
	        "            //System.out.println(\"rs = stmt.executeQuery(str_sql) ok\");\n" +
	        "\n" +
	        "            // Now do something with the ResultSet ....\n" +
	        "            \n" +
	        "            if (rs.next()) {\n" +
	        "                //System.out.println(\"rs.next() ok\");\n" +
	        "                obj = fromRS(rs);\n" +
	        "                //System.out.println(\"fromRS(rs) ok\");\n\n";
	    
	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        Column column = entry.getValue();
	        
	        // no se cargan las llaves primarias
	        if (mapPrimaryKeys.containsKey(columnName)) {
	        	continue;
	        }
	        
	        output += 
	        	"                _"; 
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += column.getMemberName();
	        }
	        
	        output += " = obj.get";
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "Id";
	        }
	        else {
	        	output += WordUtils.capitalize(column.getMemberName());
	        }
	
	        output += 
	        	"();\n";
	
	    }
	    
	    output +=
	        "            }\n" +
	        "        }\n" +
	        "        catch (SQLException ex){\n" +
	        "            // handle any errors\n" +
	        "            System.out.println(\"SQLException: \" + ex.getMessage() + \" sentencia: \" + str_sql);\n" +
	        "            System.out.println(\"SQLState: \" + ex.getSQLState());\n" +
	        "            System.out.println(\"VendorError: \" + ex.getErrorCode());\n" +
	        "            \n" +
	        "            throw ex;\n" +
	        "        }\n" +
	        "        finally {\n" +
	        "            // it is a good idea to release\n" +
	        "            // resources in a finally{} block\n" +
	        "            // in reverse-order of their creation\n" +
	        "            // if they are no-longer needed\n" +
	        "            if (rs != null) {\n" +
	        "                try {\n" +
	        "                    rs.close();\n" +
	        "                } catch (SQLException sqlEx) { \n" +
	        "                    \n" +
	        "                } // ignore\n" +
	        "                rs = null;\n" +
	        "            }\n" +
	        "            if (stmt != null) {\n" +
	        "                try {\n" +
	        "                    stmt.close();\n" +
	        "                } catch (SQLException sqlEx) {\n" +
	        "                    \n" +
	        "                } // ignore\n" +
	        "                stmt = null;\n" +
	        "            }\n" +
	        "        }        \n" +
	        "        \n" +
	        "    }\n";
		
		return output;
	}

	private String getDelete(String tableName, Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys) {
		// TODO Auto-generated method stub
		String output;
		
		output =
        	"    public int delete(Connection p_conn) throws SQLException {\n" +
        	"\n" +
        	"        int ret = -1;\n" +
        	"        Statement stmt = null;\n" +
        	"\n" +
        	"        String str_sql =\n" +
        	"            \"    DELETE FROM " + tableName + "\" +\n" +
        	"            \"    WHERE\" +\n";
        
        output += buildWhereSentence(
        		mapColumns,
        		mapPrimaryKeys,
        		mapJavaTypes
        	);
        
        output +=
        	";\n" +
        	"\n" +
        	"        try {\n" +
        	"            stmt = p_conn.createStatement();\n" +
        	"            \n" +
        	"            ret = stmt.executeUpdate(str_sql);\n" +
        	"        }\n" +
        	"        catch (SQLException ex){\n" +
        	"            // handle any errors\n" +
        	"            System.out.println(\"SQLException: \" + ex.getMessage() + \" sentencia: \" + str_sql);\n" +
        	"            System.out.println(\"SQLState: \" + ex.getSQLState());\n" +
        	"            System.out.println(\"VendorError: \" + ex.getErrorCode());\n" +
        	"            \n" +
        	"            throw ex;\n" +
        	"        }\n" +
        	"        finally {\n" +
        	"            // it is a good idea to release\n" +
        	"            // resources in a finally{} block\n" +
        	"            // in reverse-order of their creation\n" +
        	"            // if they are no-longer needed\n" +
        	"            if (stmt != null) {\n" +
        	"                try {\n" +
        	"                    stmt.close();\n" +
        	"                } catch (SQLException sqlEx) {\n" +
        	"                    \n" +
        	"                } // ignore\n" +
        	"                stmt = null;\n" +
        	"            }\n" +
        	"        }\n" +
        	"        \n" +
        	"        return ret;\n" +
        	"    }\n";
				
		return output;
	}

	private String getInsert(String tableName, Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys, Map<String, ForeignKey> mapForeignKeys) throws UnsupportedDataTypeException {
		// TODO Auto-generated method stub
		String output;
		String columnId;
		Boolean bFirst;
		Boolean bFlag;
		
		columnId = "";
		
		output =
	    	"    public int insert(Connection p_conn) throws SQLException {\n" +
	    	"        \n" +
	    	"        int ret = -1;\n" +
	    	"        Statement stmt = null;\n" +
	    	"        ResultSet rs = null;\n" +
	    	"\n";
		
	    bFlag = false; 
	    
        if (mapPrimaryKeys.size() > 0) {
        	
        	for (Map.Entry<String, PrimaryKey> entry : mapPrimaryKeys.entrySet()) {
        		if (mapColumns.get(entry.getKey()).getIsAutoincrement() == "YES") {
        			bFlag = true;
        		}
        		else if (!mapForeignKeys.containsKey(entry.getKey())) {
        			bFlag = false;
        			
        			if (mapPrimaryKeys.size() == 1) {
        				columnId = "id";
        	        }
        	        else {
        	        	columnId = mapColumns.get(entry.getKey()).getMemberName();
        	        }
        		}
        	}
	        
	        if (!bFlag && !columnId.equals("")) {
	    	    
	        	output += 
	        		"        if (_" + columnId + " == null) {\n" +
	        		"            _" + columnId + " = getNextId(p_conn);\n" +
	        		"        }\n\n";
	        }
        }
		
		output +=
	    	"        String str_sql =\n" +
	    	"            \"    INSERT INTO " + tableName + "\" +\n" +
	    	"            \"    (\" +\n";
	    
	    bFirst =  false;
	    
	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        Column column = entry.getValue();
	        
	        // no se insertan las llaves primarias autoincrementales
	        if (mapPrimaryKeys.containsKey(columnName) && column.getIsAutoincrement() == "YES") {
	        	continue;
	        }
	        /*
	        // no se insertan columnas con valor por defecto
	        if (column.getColumnDef() != null) {
	        	continue;
	        }
	        */
	        if (!bFirst) {
	        	bFirst = true;
	        }
	        else {
	        	output += ", \" +\n";
	        }
	        
	        output += "            \"    " + columnName;
	        
	    }
	    
	    output +=
	    	")\" +\n" +
	    	"            \"    VALUES\" +\n" +
	    	"            \"    (\" +\n";
	
	       bFirst =  false;
	    
	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
	    	
	        String value = "null";
	    	String columnName = entry.getKey();
	        Column column = entry.getValue();        	        
	        
	        // no se insertan las llaves primarias autoincrementales
	        if (mapPrimaryKeys.containsKey(columnName) && column.getIsAutoincrement() == "YES") {
	        	continue;
	        }
	        /*
	        // no se insertan columnas con valor por defecto
	        if (column.getColumnDef() != null) {
	        	continue;
	        }
	        */
	        
	        if (column.getColumnDef() != null && column.getIsNullable().equals("NO")) {
	        	value = column.getColumnDef();
	        }
	        
	        if (!bFirst) {
	        	bFirst = true;
	        }
	        else {
	        	output += " + \",\" +\n";
	        }
	        
	        output += "            \"    ";
	        
	        switch(column.getBaseType()) {
	        	case "BIGINT":
	        	case "INT":
	        	case "SMALLINT":
	        	case "TINYINT":
	        	case "DECIMAL":
	        	case "DOUBLE":
	        	case "FLOAT":
	        	case "BIT":
	        	case "BOOLEAN":
	        		output += "\" + (_";
	        		
	    	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	    	        	output += "id";
	    	        }
	    	        else {
	    	        	output += column.getMemberName();
	    	        }
	    	        
	    	        output += " != null ? \"'\" + _";
	
	    	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	    	        	output += "id";
	    	        }
	    	        else {
	    	        	output += column.getMemberName();
	    	        }
	    	        
	    	        output += " + \"'\" : \"" + value + "\")";
	    	        
	        		break;
	        	case "CHAR":
	        	case "VARCHAR":
	        	case "LONGVARCHAR":
	        	case "TEXT":
	        	case "DATE":
	        	case "DATETIME":
	        	case "TIMESTAMP":
	        		output += "\" + (_" + column.getMemberName() + " != null ? \"'\" + _" + column.getMemberName() + " + \"'\" : \"" + value + "\")";
	        		break;
	        	default:
	        		throw new UnsupportedDataTypeException("Tipo no soportado: " + column.getTypeName() + " columna: " + columnName);
	        } // end switch
	        
	    }
	    
	    output +=
	    	" +\n            \"    )\";\n" +
	    	"        \n" +
	    	"        try {\n" +
	    	"            stmt = p_conn.createStatement();\n" +
	    	"            \n" +
	    	"            ret = stmt.executeUpdate(str_sql";
	    
	    bFlag = false; 
	    
	    if (mapPrimaryKeys.size() == 1) {
	    	
	    	for (Map.Entry<String, PrimaryKey> entry : mapPrimaryKeys.entrySet()) {
	    		if (mapColumns.get(entry.getKey()).getIsAutoincrement() == "YES") {
	    			bFlag = true;
	    		}
	    	}
	        
	    }
	    
	    if (bFlag) {
	    	
		    output +=
		        ", Statement.RETURN_GENERATED_KEYS);\n" +
	        	"\n" +
	        	"            rs = stmt.getGeneratedKeys();\n" +
	        	"\n" +
	        	"            if (rs.next()) {\n" +
	        	"                _id = rs.getInt(1);\n" +
	        	"            } else {\n" +
	        	"                // throw an exception from here\n" +
	        	"                // throw new Exception(\"Error al obtener id\");\n" +
	        	"            }\n" +
	        	"\n" +
	        	"            rs.close();\n" +
	        	"            rs = null;\n" +
	        	"            //System.out.println(\"Key returned from getGeneratedKeys():\" + _id.toString());\n";
		    
	    }
	    else {
	    	
		    output +=
		        ");\n";
	    }
	
	    output +=
	    	"\n" +
	    	"            load(p_conn);\n" +
	    	"\n" +
	    	"        }\n" +
	    	"        catch (SQLException ex){\n" +
	    	"            // handle any errors\n" +
	    	"            System.out.println(\"SQLException: \" + ex.getMessage() + \" sentencia: \" + str_sql);\n" +
	    	"            System.out.println(\"SQLState: \" + ex.getSQLState());\n" +
	    	"            System.out.println(\"VendorError: \" + ex.getErrorCode());\n" +
	    	"            \n" +
	    	"            throw ex;\n" +
	    	"        }\n" +
	    	"        finally {\n" +
	    	"            // it is a good idea to release\n" +
	    	"            // resources in a finally{} block\n" +
	    	"            // in reverse-order of their creation\n" +
	    	"            // if they are no-longer needed\n" +
	    	"            if (rs != null) {\n" +
	    	"                try {\n" +
	    	"                    rs.close();\n" +
	    	"                } catch (SQLException sqlEx) { \n" +
	    	"                    \n" +
	    	"                } // ignore\n" +
	    	"                rs = null;\n" +
	    	"            }\n" +
	    	"            if (stmt != null) {\n" +
	    	"                try {\n" +
	    	"                    stmt.close();\n" +
	    	"                } catch (SQLException sqlEx) {\n" +
	    	"                    \n" +
	    	"                } // ignore\n" +
	    	"                stmt = null;\n" +
	    	"            }\n" +
	    	"        }\n" +
	    	"        \n" +
	    	"        return ret;\n" +
	    	"    }\n" +
	    	"\n";
		
		return output;
	}

	private String getUpdate(String tableName, Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys, Map<String, ForeignKey> mapForeignKeys) throws UnsupportedDataTypeException {
		// TODO Auto-generated method stub
		String output;
		Boolean bFirst;
		
		output =
	    	"    public int update(Connection p_conn) throws SQLException {\n" +
	    	"\n" +
	    	"        int ret = -1;\n" +
	    	"        Statement stmt = null;\n" +
	    	"\n" +
	    	"        String str_sql =\n" +
	    	"            \"    UPDATE " + tableName + "\" +\n" +
	    	"            \"    SET\" +";
	        
	    bFirst =  false;
	        
	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
	    	
	        String value = "null";
	    	String columnName = entry.getKey();
	        Column column = entry.getValue();
	        
	        // no se actualizan las llaves primarias
	        if (mapPrimaryKeys.containsKey(columnName)) {
	        	continue;
	        }
	        
	        // no se actualizan las llaves foraneas
	        if (mapForeignKeys.containsKey(columnName)) {
	        	continue;
	        }
	        
	        if (!bFirst) {
	        	bFirst = true;
	        }
	        else {
	        	output += " + \",\" +";
	        }
	        
	        output += "\n            \"    ";
	        
	        if (column.getColumnDef() != null && column.getIsNullable().equals("NO")) {
	        	value = column.getColumnDef();
	        }
	        
	        switch(column.getBaseType()) {
	        	case "BIGINT":
	        	case "INT":
	        	case "SMALLINT":
	        	case "TINYINT":
	        	case "DECIMAL":
	        	case "DOUBLE":
	        	case "FLOAT":
	        	case "BIT":
	        	case "BOOLEAN":
	        		output += columnName + " = \" + (_" + column.getMemberName() + " != null ? \"'\" + _" + column.getMemberName() + " + \"'\" : \"" + value + "\")";
	        		break;
	        	case "CHAR":
	        	case "VARCHAR":
	        	case "LONGVARCHAR":
	        	case "TEXT":
	        	case "DATE":
	        	case "DATETIME":
	        	case "TIMESTAMP":
	        		output += columnName + " = \" + (_" + column.getMemberName() + " != null ? \"'\" + _" + column.getMemberName() + " + \"'\" : \"" + value + "\")";	    	        		break;
	        	default:
	        		throw new UnsupportedDataTypeException("Tipo no soportado: " + column.getTypeName() + " columna: " + columnName);
	        } // end switch
	        
	    }
	
	    output +=
	    		" +\n            \"    WHERE\" +\n";
	    
	    output += buildWhereSentence(
	    		mapColumns,
	    		mapPrimaryKeys,
	    		mapJavaTypes
	    	);
	    
	    output +=
	    	";\n" +
	    	"\n" +
	    	"        try {\n" +
	    	"            stmt = p_conn.createStatement();\n" +
	    	"\n" +
	    	"            ret = stmt.executeUpdate(str_sql);\n" +
	    	"\n" +
	    	"            load(p_conn);\n" +
	    	"\n" +
	    	"            /*\n" +
	    	"            if (stmt.executeUpdate(str_sql) < 1) {\n" +
	    	"                throw new Exception(\"No hubo filas afectadas\");\n" +
	    	"            }\n" +
	    	"            */\n" +
	    	"            \n" +
	    	"        }\n" +
	    	"        catch (SQLException ex){\n" +
	    	"            // handle any errors\n" +
	    	"            System.out.println(\"SQLException: \" + ex.getMessage() + \" sentencia: \" + str_sql);\n" +
	    	"            System.out.println(\"SQLState: \" + ex.getSQLState());\n" +
	    	"            System.out.println(\"VendorError: \" + ex.getErrorCode());\n" +
	    	"            \n" +
	    	"            throw ex;\n" +
	    	"        }\n" +
	    	"        finally {\n" +
	    	"            // it is a good idea to release\n" +
	    	"            // resources in a finally{} block\n" +
	    	"            // in reverse-order of their creation\n" +
	    	"            // if they are no-longer needed\n" +
	    	"            if (stmt != null) {\n" +
	    	"                try {\n" +
	    	"                    stmt.close();\n" +
	    	"                } catch (SQLException sqlEx) {\n" +
	    	"                    \n" +
	    	"                } // ignore\n" +
	    	"                stmt = null;\n" +
	    	"            }\n" +
	    	"        }\n" +
	    	"        \n" +
	    	"        return ret;\n" +
	    	"    }\n" +
	    	"    \n";
		
		return output;
	}

	private String getGetNextId(String tableName, Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys, Map<String, ForeignKey> mapForeignKeys) {
		// TODO Auto-generated method stub
		String output;
		String type1, type2;
		
		output = "";
		type1 = "";
		type2 = "";
		
		// la idea es obtener el proximo valor de id + tableName; esta columna
		// es parte de la llave primaria y no es llave foranea
		
		for (Map.Entry<String, PrimaryKey> entry : mapPrimaryKeys.entrySet()) {
			
	        String columnName = entry.getKey();
	        PrimaryKey pk = entry.getValue();
	        
	        if (!mapForeignKeys.containsKey(columnName)) {
	        	type1 = mapJavaTypes.get(mapColumns.get(columnName).getBaseType());
	        	type2 = mapFunctionTypes.get(mapColumns.get(columnName).getBaseType());
	        }
		}
		
		if (!type1.equals("")) {
		
	        output =
	        	"\n" +
	            "    public static " + type1 + " getNextId(Connection p_conn) throws SQLException {\n" +
	            "        " + type1 + " ret = null;\n" +
	            "        \n" +
	            "        String str_sql = \n" +
	            "            \"  SELECT COALESCE(MAX(id_" + tableName + "), 0) + 1 AS next_id FROM " + tableName + "\";\n" +
	            "        \n" +
	            "        //System.out.println(str_sql);\n" +
	            "        \n" +
	            "        // assume that conn is an already created JDBC connection (see previous examples)\n" +
	            "        Statement stmt = null;\n" +
	            "        ResultSet rs = null;\n" +
	            "        \n" +
	            "        try {\n" +
	            "            stmt = p_conn.createStatement();\n" +
	            "            //System.out.println(\"stmt = p_conn.createStatement() ok\");\n" +
	            "            rs = stmt.executeQuery(str_sql);\n" +
	            "            //System.out.println(\"rs = stmt.executeQuery(str_sql) ok\");\n" +
	            "\n" +
	            "            // Now do something with the ResultSet ....\n" +
	            "            \n" +
	            "            if (rs.next()) {\n" +
	            "                //System.out.println(\"rs.next() ok\");\n" +
	            "                ret = rs.get" + type2 + "(\"next_id\");\n" +
	            "                //System.out.println(\"fromRS(rs) ok\");\n" +
	            "            }\n" +
	            "        }\n" +
	            "        catch (SQLException ex){\n" +
	            "            // handle any errors\n" +
	            "            System.out.println(\"SQLException: \" + ex.getMessage() + \" sentencia: \" + str_sql);\n" +
	            "            System.out.println(\"SQLState: \" + ex.getSQLState());\n" +
	            "            System.out.println(\"VendorError: \" + ex.getErrorCode());\n" +
	            "            \n" +
	            "            throw ex;\n" +
	            "        }\n" +
	            "        finally {\n" +
	            "            // it is a good idea to release\n" +
	            "            // resources in a finally{} block\n" +
	            "            // in reverse-order of their creation\n" +
	            "            // if they are no-longer needed\n" +
	            "            if (rs != null) {\n" +
	            "                try {\n" +
	            "                    rs.close();\n" +
	            "                } catch (SQLException sqlEx) { \n" +
	            "                    \n" +
	            "                } // ignore\n" +
	            "                rs = null;\n" +
	            "            }\n" +
	            "            if (stmt != null) {\n" +
	            "                try {\n" +
	            "                    stmt.close();\n" +
	            "                } catch (SQLException sqlEx) {\n" +
	            "                    \n" +
	            "                } // ignore\n" +
	            "                stmt = null;\n" +
	            "            }\n" +
	            "        }        \n" +
	            "        \n" +
	            "        return ret;        \n" +
	            "    }\n" +
	            "\n";
		}
		
        return output;

	}

	private String getSeek(String className, String tableShortAlias, Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys, Map<String, ForeignKey> mapForeignKeys) {
		// TODO Auto-generated method stub
		Boolean bFirst;
		String output =
        	"    \n" +
        	"    public static ArrayList<" + className + "> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_order, String p_direction, int p_offset, int p_limit) throws Exception {\n" +
        	"        Statement stmt = null;\n" +
        	"        ResultSet rs = null;\n" +
        	"        String str_sql;\n" +
        	"        ArrayList<" + className + "> ret;\n" +
        	"        \n" +
        	"        str_sql = \"\";\n" +
        	"        \n" +
        	"        try {\n" +
        	"            ArrayList<String> array_clauses = new ArrayList<String>();\n" +
        	"            \n" +
        	"            ret = new ArrayList<" + className + ">();\n" +
        	"            \n" +
        	"            str_sql = _str_sql;\n" +
        	"            \n" +
        	"            for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {\n";
        
        bFirst = false;
        
	    for (Map.Entry<String, PrimaryKey> entry : mapPrimaryKeys.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        PrimaryKey pk = entry.getValue();
	        
	        output += "                ";
	        
	        if (!bFirst) {
	        	bFirst = true;
	        }
	        else {
	        	output += "else ";
	        }
	        
	        output += 
	        	"if (p.getKey().equals(\"" + pk.getColumnName() + "\")) {\n" +
        	    "                    array_clauses.add(\"" + tableShortAlias + "." + pk.getColumnName() + " = \" + p.getValue());\n" +
        	    "                }\n";

	    }
        
	    for (Map.Entry<String, ForeignKey> entry : mapForeignKeys.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        ForeignKey fk = entry.getValue();
	        
	        output += "                ";
	        
	        if (!bFirst) {
	        	bFirst = true;
	        }
	        else {
	        	output += "else ";
	        }
	        
	        output += 
    	        	"if (p.getKey().equals(\"" + fk.getColumnName() + "\")) {\n" +
            	    "                    array_clauses.add(\"" + tableShortAlias + "." + fk.getColumnName() + " = \" + p.getValue());\n" +
            	    "                }\n";

	    }
        
	    if (mapColumns.containsKey("fecha_modificacion")) {
	    	
	        output += "                ";
	        
	        if (!bFirst) {
	        	bFirst = true;
	        }
	        else {
	        	output += "else ";
	        }
	        
	        output += 
    	        	"if (p.getKey().equals(\"mas reciente\")) {\n" +
            	    "                    array_clauses.add(\"" + tableShortAlias + ".fecha_modificacion > \" + p.getValue());\n" +
            	    "                }\n";

	    }

	    if (mapColumns.containsKey("borrado")) {
	    	
	        output += "                ";
	        
	        if (!bFirst) {
	        	bFirst = true;
	        }
	        else {
	        	output += "else ";
	        }
	        
	        output += 
	        	"if (p.getKey().equals(\"no borrado\")) {\n" +
	    	    "                    array_clauses.add(\"" + tableShortAlias + ".borrado = 'false'\");\n" +
	    	    "                }\n";

	        output += "                ";
	        
	        output += "else ";
	        
	        output += 
	        	"if (p.getKey().equals(\"borrado\")) {\n" +
        	    "                    array_clauses.add(\"" + tableShortAlias + ".borrado = 'true'\");\n" +
        	    "                }\n";
	    }

	    output +=
        	"                else {\n" +
        	"                    throw new Exception(\"Parametro no soportado: \" + p.getKey());\n" +
	        "                }\n" +
        	"            }\n" +
        	"                                \n" +
        	"            boolean bFirstTime = false;\n" +
        	"            \n" +
        	"            for(String clause : array_clauses) {\n" +
        	"                if (!bFirstTime) {\n" +
        	"                     bFirstTime = true;\n" +
        	"                     str_sql += \" WHERE \";\n" +
        	"                }\n" +
        	"                else {\n" +
        	"                     str_sql += \" AND \";\n" +
        	"                }\n" +
        	"                str_sql += clause;\n" +
        	"            }\n" +
        	"            \n" +
        	"            if (p_order != null && p_direction != null) {\n" +
        	"                str_sql += \" ORDER BY \" + p_order + \" \" + p_direction;\n" +
        	"            }\n" +
        	"            \n" +
        	"            if (p_offset != -1 && p_limit != -1) {\n" +
        	"                str_sql += \"  LIMIT \" +  Integer.toString(p_offset) + \", \" + Integer.toString(p_limit);\n" +
        	"            }\n" +
        	"            \n" +
        	"            //echo \"<br>\" . str_sql . \"<br>\";\n" +
        	"        \n" +
        	"            stmt = p_conn.createStatement();\n" +
        	"            \n" +
        	"            rs = stmt.executeQuery(str_sql);\n" +
        	"            \n" +
        	"            while (rs.next()) {\n" +
        	"                ret.add(fromRS(rs));\n" +
        	"            }\n" +
        	"            /*\n" +
        	"            if (ret.size() == 0) {\n" +
        	"                ret = null;\n" +
        	"            }\n" +
        	"            */\n" +
        	"        }\n" +
        	"        catch (SQLException ex){\n" +
        	"            // handle any errors\n" +
        	"            System.out.println(\"SQLException: \" + ex.getMessage() + \" sentencia: \" + str_sql);\n" +
        	"            System.out.println(\"SQLState: \" + ex.getSQLState());\n" +
        	"            System.out.println(\"VendorError: \" + ex.getErrorCode());\n" +
        	"            \n" +
        	"            throw ex;\n" +
        	"        }\n" +
        	"        catch (Exception ex) {\n" +
        	"            throw ex;\n" +
        	"        }\n" +
        	"        finally {\n" +
        	"            // it is a good idea to release\n" +
        	"            // resources in a finally{} block\n" +
        	"            // in reverse-order of their creation\n" +
        	"            // if they are no-longer needed\n" +
        	"            if (rs != null) {\n" +
        	"                try {\n" +
        	"                    rs.close();\n" +
        	"                } catch (SQLException sqlEx) { \n" +
        	"                    \n" +
        	"                } // ignore\n" +
        	"                rs = null;\n" +
        	"            }\n" +
        	"            if (stmt != null) {\n" +
        	"                try {\n" +
        	"                    stmt.close();\n" +
        	"                } catch (SQLException sqlEx) {\n" +
        	"                    \n" +
        	"                } // ignore\n" +
        	"                stmt = null;\n" +
        	"            }\n" +
        	"        }        \n" +
        	"\n" +
        	"        return ret;\n" +
        	"    }\n" +
        	"\n";

		return output;
	}

	private String getGetByParameter(String className, String tableShortAlias) {
		// TODO Auto-generated method stub
		String output =		
	    	"\n" +
	        "    public static " + className + " getByParameter(Connection p_conn, String p_key, String p_value) throws SQLException {\n" +
	        "        " + className + " ret = null;\n" +
	        "        \n" +
	        "        String str_sql = _str_sql +\n" +
	        "            \"  WHERE " + tableShortAlias + ".\" + p_key + \" = \" + p_value +\n" +
	        "            \"  LIMIT 0, 1\";\n" +
	        "        \n" +
	        "        //System.out.println(str_sql);\n" +
	        "        \n" +
	        "        // assume that conn is an already created JDBC connection (see previous examples)\n" +
	        "        Statement stmt = null;\n" +
	        "        ResultSet rs = null;\n" +
	        "        \n" +
	        "        try {\n" +
	        "            stmt = p_conn.createStatement();\n" +
	        "            //System.out.println(\"stmt = p_conn.createStatement() ok\");\n" +
	        "            rs = stmt.executeQuery(str_sql);\n" +
	        "            //System.out.println(\"rs = stmt.executeQuery(str_sql) ok\");\n" +
	        "\n" +
	        "            // Now do something with the ResultSet ....\n" +
	        "            \n" +
	        "            if (rs.next()) {\n" +
	        "                //System.out.println(\"rs.next() ok\");\n" +
	        "                ret = fromRS(rs);\n" +
	        "                //System.out.println(\"fromRS(rs) ok\");\n" +
	        "            }\n" +
	        "        }\n" +
	        "        catch (SQLException ex){\n" +
	        "            // handle any errors\n" +
	        "            System.out.println(\"SQLException: \" + ex.getMessage() + \" sentencia: \" + str_sql);\n" +
	        "            System.out.println(\"SQLState: \" + ex.getSQLState());\n" +
	        "            System.out.println(\"VendorError: \" + ex.getErrorCode());\n" +
	        "            \n" +
	        "            throw ex;\n" +
	        "        }\n" +
	        "        finally {\n" +
	        "            // it is a good idea to release\n" +
	        "            // resources in a finally{} block\n" +
	        "            // in reverse-order of their creation\n" +
	        "            // if they are no-longer needed\n" +
	        "            if (rs != null) {\n" +
	        "                try {\n" +
	        "                    rs.close();\n" +
	        "                } catch (SQLException sqlEx) { \n" +
	        "                    \n" +
	        "                } // ignore\n" +
	        "                rs = null;\n" +
	        "            }\n" +
	        "            if (stmt != null) {\n" +
	        "                try {\n" +
	        "                    stmt.close();\n" +
	        "                } catch (SQLException sqlEx) {\n" +
	        "                    \n" +
	        "                } // ignore\n" +
	        "                stmt = null;\n" +
	        "            }\n" +
	        "        }        \n" +
	        "        \n" +
	        "        return ret;        \n" +
	        "    }\n" +
	        "\n";
		
		return output;
	}

	private String getFromRS(String className, Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys) {
		// TODO Auto-generated method stub
		String output =
	    	"\n" +
			"    public static " + className + " fromRS(ResultSet p_rs) throws SQLException {\n" +
	        "        " + className + " ret = new " + className + "();\n\n";		
	
	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        Column column = entry.getValue();
	        
	        output += 
	        	"        ret.set"; 
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "Id";
	        }
	        else {
	        	output += WordUtils.capitalize(column.getMemberName());
	        }
	        
	        output += "(p_rs.get" + mapFunctionTypes.get(column.getBaseType()) + "(\"";
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += columnName;
	        }
	
	        output += 
	        	"\"));\n";
	
	    }
	    
	    output +=
	        "\n" +			
	        "        return ret;\n" +
	        "    }\n";		
		
		return output;
	}

	private String getSetters(Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys) {
		// TODO Auto-generated method stub
		String output = "";
		
	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        Column column = entry.getValue();
	        
	        output +=
    	        	"    /**\n" +
    	    	        	"     * @param _";

	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += column.getMemberName();
	        }
	        
	        output +=
	            " the _";
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += column.getMemberName();
	        }
	        
	        output +=
	            " to set\n" +
	        	"     */\n" +
	        	"    public void set";
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "Id";
	        }
	        else {
	        	output += WordUtils.capitalize(column.getMemberName());
	        }
	        
	        output +=
	            "(" + mapJavaTypes.get(column.getBaseType()) + " _";
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += column.getMemberName();
	        }
	        
	        output +=
	            ") {\n" +
        	"        this._";

	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += column.getMemberName();
	        }
	        
	        output +=
	            " = _";
	    
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += column.getMemberName();
	        }
	        
	        output +=
	            ";\n" +
        	    "    }\n";
	           	    
	    }
		
		return output;
	}
	
	private String getGetters(Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys) {
		// TODO Auto-generated method stub
		String output = "";
		
	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        Column column = entry.getValue();
	        
	        output +=
	        	"    /**\n" +
	        	"     * @return the _";

	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += column.getMemberName();
	        }
	        
	        output +=
	            "\n" +
	        	"     */\n" +
	        	"    public " + mapJavaTypes.get(column.getBaseType()) + " get";
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "Id";
	        }
	        else {
	        	output += WordUtils.capitalize(column.getMemberName());
	        }
	        
	        output +=
	            "() {\n" +
	        	"        return _";
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += column.getMemberName();
	        }
	        
	        output +=
	            ";\n" +
	        	"    }\n";
	        
	    }
		
		return output;
	}

	private String getConstructor(String className, Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys) {
		// TODO Auto-generated method stub
		String output =
        	"\n" +
        	"    public " + className + "() {\n";
        
	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        Column column = entry.getValue();
	        
	        output += "        _";
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += column.getMemberName();
	        }
	        
	        output += " = null;\n";
	    }

	    output +=
        		"\n    }\n";
				
		return output;
	}

	private String getSelectSentence(String tableName, String tableShortAlias, Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys) throws UnsupportedDataTypeException {
		// TODO Auto-generated method stub
		String output =
        	"\n" +
        	"    private final static String _str_sql = \n" +
            "        \"    SELECT\" +";
        
        Boolean bFirst = false;
        
	    for (Map.Entry<String, Column> entry : mapColumns.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        Column column = entry.getValue();
	        
	        if (!bFirst) {
	        	bFirst = true;
	        }
	        else {
	        	output += ",\" +";
	        }

	        output += "\n        \"    ";
	        
	        switch(column.getBaseType()) {
	        	case "BIGINT":
	        	case "INT":
	        	case "SMALLINT":
	        	case "TINYINT":
	        	case "CHAR":
	        	case "VARCHAR":
	        	case "LONGVARCHAR":
	        	case "TEXT":
	        	case "DECIMAL":
	        	case "DOUBLE":
	        	case "FLOAT":
	        	case "BIT":
	        	case "BOOLEAN":
	        		output += tableShortAlias + "." + columnName;
	        		break;
	        	case "DATE":
	        	case "DATETIME":
	        	case "TIMESTAMP":
	        		output += "strftime('%Y-%m-%d %H:%M:%S', " + tableShortAlias + "." + columnName + ")";
	        		break;
	        	default:
	        		throw new UnsupportedDataTypeException("Tipo no soportado: " + column.getTypeName() + " columna: " + columnName);
	        } // end switch
	        
	        output += " AS ";
	        
	        if (mapPrimaryKeys.size() == 1 && mapPrimaryKeys.containsKey(columnName)) {
	        	output += "id";
	        }
	        else {
	        	output += columnName;
	        }
	        
	    }
	    
	    output +=
    		"\" +\n        \"    FROM " + tableName + " " + tableShortAlias + "\";\n";
				
		return output;
	}
	
	private String buildWhereSentence(
			Map<String, Column> p_mapColumns,
			Map<String, PrimaryKey> p_mapPrimaryKeys,
			Map<String, String> p_mapJavaTypes
	) 
	{
		String res = "";
		Boolean bFirst = false;
		
	    for (Map.Entry<String, PrimaryKey> entry : p_mapPrimaryKeys.entrySet()) {
	    	
	        String columnName = entry.getKey();
	        PrimaryKey pk = entry.getValue();
	        
	        if (!bFirst) {
	        	bFirst = true;
	        }
	        else {
	        	res += " + \" AND\" +\n";
	        }

	        res += "            \"    " + columnName + " = \" + " + p_mapJavaTypes.get(p_mapColumns.get(columnName).getBaseType()) + ".toString(this._";
	        
	        if (p_mapPrimaryKeys.size() == 1) {
	        	res += "id";
	        }
	        else {
	        	res += Util.toJavaFieldName(columnName);
	        }
	        
	        res += ")";

	    }
		
	    return res;
	}

	protected String getDeclarations(Map<String, Column> mapColumns, Map<String, PrimaryKey> mapPrimaryKeys) {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getClassModifier() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getCustomImports() {
		// TODO Auto-generated method stub
		return null;
	}

	private String toJavaClassName(String name) { // "MY_TABLE"
	    String name0 = name.replace("_", " "); // to "MY TABLE"
	    name0 = WordUtils.capitalizeFully(name0); // to "My Table"
	    name0 = name0.replace(" ", ""); // to "MyTable"
	    return name0;
	}
	
	private void writeToFile(String p_fileName, String p_content) throws IOException {
		 
		File file = new File(p_fileName);

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(p_content);
		bw.close();

		System.out.println("Done");
	}


}
