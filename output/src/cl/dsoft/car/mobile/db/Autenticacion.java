/**
 * 
 */
package cl.dsoft.car.mobile.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap;
import java.util.ArrayList;
import cl.dsoft.UnsupportedParameterException;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Luis Hernandez
 *
 */
@Root
public class Autenticacion {
    @Element(name = "fechaModificacion")
    private String _fechaModificacion;
    @Element(name = "fecha", required = false)
    private String _fecha;
    @Element(name = "idRedSocial")
    private Long _idRedSocial;
    @Element(name = "token", required = false)
    private String _token;
    @Element(name = "idUsuario")
    private Long _idUsuario;
    @Element(name = "id")
    private Long _id;

    private final static String _str_sql = 
        "    SELECT" +
        "    strftime('%Y-%m-%d %H:%M:%S', au.fecha_modificacion) AS fecha_modificacion," +
        "    strftime('%Y-%m-%d %H:%M:%S', au.fecha) AS fecha," +
        "    au.id_red_social AS id_red_social," +
        "    au.token AS token," +
        "    au.id_usuario AS id_usuario," +
        "    au.id_autenticacion AS id" +
        "    FROM autenticacion au";

    public Autenticacion() {
        _fechaModificacion = null;
        _fecha = null;
        _idRedSocial = null;
        _token = null;
        _idUsuario = null;
        _id = null;

    }
    /**
     * @return the _fechaModificacion
     */
    public String getFechaModificacion() {
        return _fechaModificacion;
    }
    /**
     * @return the _fecha
     */
    public String getFecha() {
        return _fecha;
    }
    /**
     * @return the _idRedSocial
     */
    public Long getIdRedSocial() {
        return _idRedSocial;
    }
    /**
     * @return the _token
     */
    public String getToken() {
        return _token;
    }
    /**
     * @return the _idUsuario
     */
    public Long getIdUsuario() {
        return _idUsuario;
    }
    /**
     * @return the _id
     */
    public Long getId() {
        return _id;
    }
    /**
     * @param _fechaModificacion the _fechaModificacion to set
     */
    public void setFechaModificacion(String _fechaModificacion) {
        this._fechaModificacion = _fechaModificacion;
    }
    /**
     * @param _fecha the _fecha to set
     */
    public void setFecha(String _fecha) {
        this._fecha = _fecha;
    }
    /**
     * @param _idRedSocial the _idRedSocial to set
     */
    public void setIdRedSocial(Long _idRedSocial) {
        this._idRedSocial = _idRedSocial;
    }
    /**
     * @param _token the _token to set
     */
    public void setToken(String _token) {
        this._token = _token;
    }
    /**
     * @param _idUsuario the _idUsuario to set
     */
    public void setIdUsuario(Long _idUsuario) {
        this._idUsuario = _idUsuario;
    }
    /**
     * @param _id the _id to set
     */
    public void setId(Long _id) {
        this._id = _id;
    }

    public static Autenticacion fromRS(ResultSet p_rs) throws SQLException {
        Autenticacion ret = new Autenticacion();

        ret.setFechaModificacion(p_rs.getString("fecha_modificacion"));
        ret.setFecha(p_rs.getString("fecha"));
        ret.setIdRedSocial(p_rs.getLong("id_red_social"));
        ret.setToken(p_rs.getString("token"));
        ret.setIdUsuario(p_rs.getLong("id_usuario"));
        ret.setId(p_rs.getLong("id"));

        return ret;
    }

    public static Autenticacion getByParameter(Connection p_conn, String p_key, String p_value) throws SQLException {
        Autenticacion ret = null;
        
        String str_sql = _str_sql +
            "  WHERE au." + p_key + " = " + p_value +
            "  LIMIT 0, 1";
        
        //System.out.println(str_sql);
        
        // assume that conn is an already created JDBC connection (see previous examples)
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = p_conn.createStatement();
            //System.out.println("stmt = p_conn.createStatement() ok");
            rs = stmt.executeQuery(str_sql);
            //System.out.println("rs = stmt.executeQuery(str_sql) ok");

            // Now do something with the ResultSet ....
            
            if (rs.next()) {
                //System.out.println("rs.next() ok");
                ret = fromRS(rs);
                //System.out.println("fromRS(rs) ok");
            }
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            
            throw ex;
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { 
                    
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    
                } // ignore
                stmt = null;
            }
        }        
        
        return ret;        
    }

    public static Autenticacion getById(Connection p_conn, String p_id) throws SQLException {
        return getByParameter(p_conn, "id_autenticacion", p_id);
    }
    
    public static ArrayList<Autenticacion> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_order, String p_direction, int p_offset, int p_limit) throws UnsupportedParameterException, SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        String str_sql;
        ArrayList<Autenticacion> ret;
        
        str_sql = "";
        
        try {
            ArrayList<String> array_clauses = new ArrayList<String>();
            
            ret = new ArrayList<Autenticacion>();
            
            str_sql = _str_sql;
            
            for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {
                if (p.getKey().equals("id_autenticacion")) {
                    array_clauses.add("au.id_autenticacion = " + p.getValue());
                }
                else if (p.getKey().equals("id_red_social")) {
                    array_clauses.add("au.id_red_social = " + p.getValue());
                }
                else if (p.getKey().equals("id_usuario")) {
                    array_clauses.add("au.id_usuario = " + p.getValue());
                }
                else if (p.getKey().equals("mas reciente")) {
                    array_clauses.add("au.fecha_modificacion > datetime('" + p.getValue() + "', 'localtime')");
                }
                else {
                    throw new UnsupportedParameterException("Parametro no soportado: " + p.getKey());
                }
            }
                                
            boolean bFirstTime = false;
            
            for(String clause : array_clauses) {
                if (!bFirstTime) {
                     bFirstTime = true;
                     str_sql += " WHERE ";
                }
                else {
                     str_sql += " AND ";
                }
                str_sql += clause;
            }
            
            if (p_order != null && p_direction != null) {
                str_sql += " ORDER BY " + p_order + " " + p_direction;
            }
            
            if (p_offset != -1 && p_limit != -1) {
                str_sql += "  LIMIT " +  Integer.toString(p_offset) + ", " + Integer.toString(p_limit);
            }
            
            //echo "<br>" . str_sql . "<br>";
        
            stmt = p_conn.createStatement();
            
            rs = stmt.executeQuery(str_sql);
            
            while (rs.next()) {
                ret.add(fromRS(rs));
            }
            /*
            if (ret.size() == 0) {
                ret = null;
            }
            */
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            
            throw ex;
        }
        catch (UnsupportedParameterException ex) {
            throw ex;
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { 
                    
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    
                } // ignore
                stmt = null;
            }
        }        

        return ret;
    }


    public static Long getNextId(Connection p_conn) throws SQLException {
        Long ret = null;
        
        String str_sql = 
            "  SELECT COALESCE(MAX(id_autenticacion), 0) + 1 AS next_id FROM autenticacion";
        
        //System.out.println(str_sql);
        
        // assume that conn is an already created JDBC connection (see previous examples)
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = p_conn.createStatement();
            //System.out.println("stmt = p_conn.createStatement() ok");
            rs = stmt.executeQuery(str_sql);
            //System.out.println("rs = stmt.executeQuery(str_sql) ok");

            // Now do something with the ResultSet ....
            
            if (rs.next()) {
                //System.out.println("rs.next() ok");
                ret = rs.getLong("next_id");
                //System.out.println("fromRS(rs) ok");
            }
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            
            throw ex;
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { 
                    
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    
                } // ignore
                stmt = null;
            }
        }        
        
        return ret;        
    }

    public int update(Connection p_conn) throws SQLException {

        int ret = -1;
        Statement stmt = null;

        String str_sql =
            "    UPDATE autenticacion" +
            "    SET" +
            "    fecha_modificacion = " + (_fechaModificacion != null ? "datetime('" + _fechaModificacion + "', 'localtime')" : "datetime('now', 'localtime')") + "," +
            "    fecha = " + (_fecha != null ? "date('" + _fecha + "', 'localtime')" : "null") + "," +
            "    token = " + (_token != null ? "'" + _token + "'" : "null") +
            "    WHERE" +
            "    id_autenticacion = " + Long.toString(this._id);

        try {
            stmt = p_conn.createStatement();

            ret = stmt.executeUpdate(str_sql);

            load(p_conn);

            /*
            if (stmt.executeUpdate(str_sql) < 1) {
                throw new Exception("No hubo filas afectadas");
            }
            */
            
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            
            throw ex;
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    
                } // ignore
                stmt = null;
            }
        }
        
        return ret;
    }
    
    public int insert(Connection p_conn) throws SQLException {
        
        int ret = -1;
        Statement stmt = null;
        ResultSet rs = null;

        if (_id == null) {
            _id = getNextId(p_conn);
        }

        String str_sql =
            "    INSERT INTO autenticacion" +
            "    (" +
            "    fecha_modificacion, " +
            "    fecha, " +
            "    id_red_social, " +
            "    token, " +
            "    id_usuario, " +
            "    id_autenticacion)" +
            "    VALUES" +
            "    (" +
            "    " + (_fechaModificacion != null ? "datetime('" + _fechaModificacion + "', 'localtime')" : "datetime('now', 'localtime')") + "," +
            "    " + (_fecha != null ? "date('" + _fecha + "', 'localtime')" : "null") + "," +
            "    " + (_idRedSocial != null ? "'" + _idRedSocial + "'" : "null") + "," +
            "    " + (_token != null ? "'" + _token + "'" : "null") + "," +
            "    " + (_idUsuario != null ? "'" + _idUsuario + "'" : "null") + "," +
            "    " + (_id != null ? "'" + _id + "'" : "null") +
            "    )";
        
        try {
            stmt = p_conn.createStatement();
            
            ret = stmt.executeUpdate(str_sql);

            load(p_conn);

        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            
            throw ex;
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { 
                    
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    
                } // ignore
                stmt = null;
            }
        }
        
        return ret;
    }

    public int delete(Connection p_conn) throws SQLException {

        int ret = -1;
        Statement stmt = null;

        String str_sql =
            "    DELETE FROM autenticacion" +
            "    WHERE" +
            "    id_autenticacion = " + Long.toString(this._id);

        try {
            stmt = p_conn.createStatement();
            
            ret = stmt.executeUpdate(str_sql);
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            
            throw ex;
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    
                } // ignore
                stmt = null;
            }
        }
        
        return ret;
    }

    public void load(Connection p_conn) throws SQLException {
        Autenticacion obj = null;
        
        String str_sql = _str_sql +
            "    WHERE" +
            "    id_autenticacion = " + Long.toString(this._id) +
            "    LIMIT 0, 1";
        
        //System.out.println(str_sql);
        
        // assume that conn is an already created JDBC connection (see previous examples)
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = p_conn.createStatement();
            //System.out.println("stmt = p_conn.createStatement() ok");
            rs = stmt.executeQuery(str_sql);
            //System.out.println("rs = stmt.executeQuery(str_sql) ok");

            // Now do something with the ResultSet ....
            
            if (rs.next()) {
                //System.out.println("rs.next() ok");
                obj = fromRS(rs);
                //System.out.println("fromRS(rs) ok");

                _fechaModificacion = obj.getFechaModificacion();
                _fecha = obj.getFecha();
                _idRedSocial = obj.getIdRedSocial();
                _token = obj.getToken();
                _idUsuario = obj.getIdUsuario();
            }
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            
            throw ex;
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { 
                    
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    
                } // ignore
                stmt = null;
            }
        }        
        
    }

    public void save(Connection p_conn) throws SQLException {
        
        String str_sql = _str_sql +
            "    WHERE" +
            "    id_autenticacion = " + Long.toString(this._id) +
            "    LIMIT 0, 1";
        
        //System.out.println(str_sql);
        
        // assume that conn is an already created JDBC connection (see previous examples)
        Statement stmt = null;
        ResultSet rs = null;
        Boolean exists = false;
        
        try {
            stmt = p_conn.createStatement();
            //System.out.println("stmt = p_conn.createStatement() ok");
            rs = stmt.executeQuery(str_sql);
            //System.out.println("rs = stmt.executeQuery(str_sql) ok");

            // Now do something with the ResultSet ....

            if (rs.next()) {
                // registro existe
                exists = true;
            }

            rs.close();
            stmt.close();

            if (exists) {
            	// update
            	update(p_conn);
            }
            else {
            	// insert
            	insert(p_conn);
            }
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage() + " sentencia: " + str_sql);
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            
            throw ex;
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { 
                    
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    
                } // ignore
                stmt = null;
            }
        }        
        
    }

@Override
    public String toString() {
        return "Autenticacion [" +
	           "    _fechaModificacion = " + (_fechaModificacion != null ? "'" + _fechaModificacion + "'" : "null") + "," +
	           "    _fecha = " + (_fecha != null ? "'" + _fecha + "'" : "null") + "," +
	           "    _idRedSocial = " + (_idRedSocial != null ? _idRedSocial : "null") + "," +
	           "    _token = " + (_token != null ? "'" + _token + "'" : "null") + "," +
	           "    _idUsuario = " + (_idUsuario != null ? _idUsuario : "null") + "," +
	           "    _id = " + (_id != null ? _id : "null") +
			   "]";
    }


/*
    public static Autenticacion fromXMLNode(Node xmlNode) {
        Autenticacion ret = new Autenticacion();

        Element element = (Element) xmlNode;

        ret.setFechaModificacion(element.getElementsByTagName("fecha_modificacion").item(0).getTextContent());
        ret.setFecha(element.getElementsByTagName("fecha").item(0).getTextContent());
        ret.setIdRedSocial(Long.decode(element.getElementsByTagName("id_red_social").item(0).getTextContent()));
        ret.setToken(element.getElementsByTagName("token").item(0).getTextContent());
        ret.setIdUsuario(Long.decode(element.getElementsByTagName("id_usuario").item(0).getTextContent()));
        ret.setId(Long.decode(element.getElementsByTagName("id_autenticacion").item(0).getTextContent()));

        return ret;
    }
    */
}
