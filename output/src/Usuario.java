/**
 * 
 */
package cl.dsoft.mobile.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap;
import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Luis Hernandez
 *
 */
@Root
public class Usuario {
    @Element(name = "nombre", required = false)
    private String _nombre;
    @Element(name = "fechaModificacion")
    private String _fechaModificacion;
    @Element(name = "fechaVencimientoLicencia", required = false)
    private String _fechaVencimientoLicencia;
    @Element(name = "id")
    private Long _id;
    @Element(name = "hombre", required = false)
    private Boolean _hombre;
    @Element(name = "idComuna")
    private Long _idComuna;
    @Element(name = "borrado")
    private Boolean _borrado;
    @Element(name = "telefono", required = false)
    private String _telefono;
    @Element(name = "correo", required = false)
    private String _correo;
    @Element(name = "fechaNacimiento", required = false)
    private String _fechaNacimiento;

    private final static String _str_sql = 
        "    SELECT" +
        "    us.nombre AS nombre," +
        "    strftime('%Y-%m-%d %H:%M:%S', us.fecha_modificacion) AS fecha_modificacion," +
        "    strftime('%Y-%m-%d %H:%M:%S', us.fecha_vencimiento_licencia) AS fecha_vencimiento_licencia," +
        "    us.id_usuario AS id," +
        "    us.hombre AS hombre," +
        "    us.id_comuna AS id_comuna," +
        "    us.borrado AS borrado," +
        "    us.telefono AS telefono," +
        "    us.correo AS correo," +
        "    strftime('%Y-%m-%d %H:%M:%S', us.fecha_nacimiento) AS fecha_nacimiento" +
        "    FROM usuario us";

    public Usuario() {
        _nombre = null;
        _fechaModificacion = null;
        _fechaVencimientoLicencia = null;
        _id = null;
        _hombre = null;
        _idComuna = null;
        _borrado = null;
        _telefono = null;
        _correo = null;
        _fechaNacimiento = null;

    }
    /**
     * @return the _nombre
     */
    public String getNombre() {
        return _nombre;
    }
    /**
     * @return the _fechaModificacion
     */
    public String getFechaModificacion() {
        return _fechaModificacion;
    }
    /**
     * @return the _fechaVencimientoLicencia
     */
    public String getFechaVencimientoLicencia() {
        return _fechaVencimientoLicencia;
    }
    /**
     * @return the _id
     */
    public Long getId() {
        return _id;
    }
    /**
     * @return the _hombre
     */
    public Boolean getHombre() {
        return _hombre;
    }
    /**
     * @return the _idComuna
     */
    public Long getIdComuna() {
        return _idComuna;
    }
    /**
     * @return the _borrado
     */
    public Boolean getBorrado() {
        return _borrado;
    }
    /**
     * @return the _telefono
     */
    public String getTelefono() {
        return _telefono;
    }
    /**
     * @return the _correo
     */
    public String getCorreo() {
        return _correo;
    }
    /**
     * @return the _fechaNacimiento
     */
    public String getFechaNacimiento() {
        return _fechaNacimiento;
    }
    /**
     * @param _nombre the _nombre to set
     */
    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }
    /**
     * @param _fechaModificacion the _fechaModificacion to set
     */
    public void setFechaModificacion(String _fechaModificacion) {
        this._fechaModificacion = _fechaModificacion;
    }
    /**
     * @param _fechaVencimientoLicencia the _fechaVencimientoLicencia to set
     */
    public void setFechaVencimientoLicencia(String _fechaVencimientoLicencia) {
        this._fechaVencimientoLicencia = _fechaVencimientoLicencia;
    }
    /**
     * @param _id the _id to set
     */
    public void setId(Long _id) {
        this._id = _id;
    }
    /**
     * @param _hombre the _hombre to set
     */
    public void setHombre(Boolean _hombre) {
        this._hombre = _hombre;
    }
    /**
     * @param _idComuna the _idComuna to set
     */
    public void setIdComuna(Long _idComuna) {
        this._idComuna = _idComuna;
    }
    /**
     * @param _borrado the _borrado to set
     */
    public void setBorrado(Boolean _borrado) {
        this._borrado = _borrado;
    }
    /**
     * @param _telefono the _telefono to set
     */
    public void setTelefono(String _telefono) {
        this._telefono = _telefono;
    }
    /**
     * @param _correo the _correo to set
     */
    public void setCorreo(String _correo) {
        this._correo = _correo;
    }
    /**
     * @param _fechaNacimiento the _fechaNacimiento to set
     */
    public void setFechaNacimiento(String _fechaNacimiento) {
        this._fechaNacimiento = _fechaNacimiento;
    }

    public static Usuario fromRS(ResultSet p_rs) throws SQLException {
        Usuario ret = new Usuario();

        ret.setNombre(p_rs.getString("nombre"));
        ret.setFechaModificacion(p_rs.getString("fecha_modificacion"));
        ret.setFechaVencimientoLicencia(p_rs.getString("fecha_vencimiento_licencia"));
        ret.setId(p_rs.getLong("id"));
        ret.setHombre(p_rs.getBoolean("hombre"));
        ret.setIdComuna(p_rs.getLong("id_comuna"));
        ret.setBorrado(p_rs.getBoolean("borrado"));
        ret.setTelefono(p_rs.getString("telefono"));
        ret.setCorreo(p_rs.getString("correo"));
        ret.setFechaNacimiento(p_rs.getString("fecha_nacimiento"));

        return ret;
    }

    public static Usuario getByParameter(Connection p_conn, String p_key, String p_value) throws SQLException {
        Usuario ret = null;
        
        String str_sql = _str_sql +
            "  WHERE us." + p_key + " = " + p_value +
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

    public static Usuario getById(Connection p_conn, String p_id) throws Exception {
        return getByParameter(p_conn, "id_usuario", p_id);
    }
    
    public static ArrayList<Usuario> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_order, String p_direction, int p_offset, int p_limit) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        String str_sql;
        ArrayList<Usuario> ret;
        
        str_sql = "";
        
        try {
            ArrayList<String> array_clauses = new ArrayList<String>();
            
            ret = new ArrayList<Usuario>();
            
            str_sql = _str_sql;
            
            for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {
                if (p.getKey().equals("id_usuario")) {
                    array_clauses.add("us.id_usuario = " + p.getValue());
                }
                else if (p.getKey().equals("id_comuna")) {
                    array_clauses.add("us.id_comuna = " + p.getValue());
                }
                else if (p.getKey().equals("mas reciente")) {
                    array_clauses.add("us.fecha_modificacion > " + p.getValue());
                }
                else if (p.getKey().equals("no borrado")) {
                    array_clauses.add("us.borrado = 'false'");
                }
                else if (p.getKey().equals("borrado")) {
                    array_clauses.add("us.borrado = 'true'");
                }
                else {
                    throw new Exception("Parametro no soportado: " + p.getKey());
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
        catch (Exception ex) {
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
            "  SELECT COALESCE(MAX(id_usuario), 0) + 1 AS next_id FROM usuario";
        
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
            "    UPDATE usuario" +
            "    SET" +
            "    nombre = " + (_nombre != null ? "'" + _nombre + "'" : "null") + "," +
            "    fecha_modificacion = " + (_fechaModificacion != null ? "datetime('" + _fechaModificacion + "', 'localtime')" : "datetime('now', 'localtime')") + "," +
            "    fecha_vencimiento_licencia = " + (_fechaVencimientoLicencia != null ? "date('" + _fechaVencimientoLicencia + "', 'localtime')" : "null") + "," +
            "    hombre = " + (_hombre != null ? "'" + _hombre + "'" : "null") + "," +
            "    borrado = " + (_borrado != null ? "'" + _borrado + "'" : "'false'") + "," +
            "    telefono = " + (_telefono != null ? "'" + _telefono + "'" : "null") + "," +
            "    correo = " + (_correo != null ? "'" + _correo + "'" : "null") + "," +
            "    fecha_nacimiento = " + (_fechaNacimiento != null ? "date('" + _fechaNacimiento + "', 'localtime')" : "null") +
            "    WHERE" +
            "    id_usuario = " + Long.toString(this._id);

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
            "    INSERT INTO usuario" +
            "    (" +
            "    nombre, " +
            "    fecha_modificacion, " +
            "    fecha_vencimiento_licencia, " +
            "    id_usuario, " +
            "    hombre, " +
            "    id_comuna, " +
            "    borrado, " +
            "    telefono, " +
            "    correo, " +
            "    fecha_nacimiento)" +
            "    VALUES" +
            "    (" +
            "    " + (_nombre != null ? "'" + _nombre + "'" : "null") + "," +
            "    " + (_fechaModificacion != null ? "datetime('" + _fechaModificacion + "', 'localtime')" : "datetime('now', 'localtime')") + "," +
            "    " + (_fechaVencimientoLicencia != null ? "date('" + _fechaVencimientoLicencia + "', 'localtime')" : "null") + "," +
            "    " + (_id != null ? "'" + _id + "'" : "null") + "," +
            "    " + (_hombre != null ? "'" + _hombre + "'" : "null") + "," +
            "    " + (_idComuna != null ? "'" + _idComuna + "'" : "null") + "," +
            "    " + (_borrado != null ? "'" + _borrado + "'" : "'false'") + "," +
            "    " + (_telefono != null ? "'" + _telefono + "'" : "null") + "," +
            "    " + (_correo != null ? "'" + _correo + "'" : "null") + "," +
            "    " + (_fechaNacimiento != null ? "date('" + _fechaNacimiento + "', 'localtime')" : "null") +
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
            "    DELETE FROM usuario" +
            "    WHERE" +
            "    id_usuario = " + Long.toString(this._id);

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
        Usuario obj = null;
        
        String str_sql = _str_sql +
            "    WHERE" +
            "    id_usuario = " + Long.toString(this._id) +
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

                _nombre = obj.getNombre();
                _fechaModificacion = obj.getFechaModificacion();
                _fechaVencimientoLicencia = obj.getFechaVencimientoLicencia();
                _hombre = obj.getHombre();
                _idComuna = obj.getIdComuna();
                _borrado = obj.getBorrado();
                _telefono = obj.getTelefono();
                _correo = obj.getCorreo();
                _fechaNacimiento = obj.getFechaNacimiento();
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
            "    id_usuario = " + Long.toString(this._id) +
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
        return "Usuario [" +
	           "    _nombre = " + (_nombre != null ? "'" + _nombre + "'" : "null") + "," +
	           "    _fechaModificacion = " + (_fechaModificacion != null ? "'" + _fechaModificacion + "'" : "null") + "," +
	           "    _fechaVencimientoLicencia = " + (_fechaVencimientoLicencia != null ? "'" + _fechaVencimientoLicencia + "'" : "null") + "," +
	           "    _id = " + (_id != null ? _id : "null") + "," +
	           "    _hombre = " + (_hombre != null ? _hombre : "null") + "," +
	           "    _idComuna = " + (_idComuna != null ? _idComuna : "null") + "," +
	           "    _borrado = " + (_borrado != null ? _borrado : "null") + "," +
	           "    _telefono = " + (_telefono != null ? "'" + _telefono + "'" : "null") + "," +
	           "    _correo = " + (_correo != null ? "'" + _correo + "'" : "null") + "," +
	           "    _fechaNacimiento = " + (_fechaNacimiento != null ? "'" + _fechaNacimiento + "'" : "null") +
			   "]";
    }


/*
    public static Usuario fromXMLNode(Node xmlNode) {
        Usuario ret = new Usuario();

        Element element = (Element) xmlNode;

        ret.setNombre(element.getElementsByTagName("nombre").item(0).getTextContent());
        ret.setFechaModificacion(element.getElementsByTagName("fecha_modificacion").item(0).getTextContent());
        ret.setFechaVencimientoLicencia(element.getElementsByTagName("fecha_vencimiento_licencia").item(0).getTextContent());
        ret.setId(Long.decode(element.getElementsByTagName("id_usuario").item(0).getTextContent()));
        ret.setHombre(Boolean.decode(element.getElementsByTagName("hombre").item(0).getTextContent()));
        ret.setIdComuna(Long.decode(element.getElementsByTagName("id_comuna").item(0).getTextContent()));
        ret.setBorrado(Boolean.decode(element.getElementsByTagName("borrado").item(0).getTextContent()));
        ret.setTelefono(element.getElementsByTagName("telefono").item(0).getTextContent());
        ret.setCorreo(element.getElementsByTagName("correo").item(0).getTextContent());
        ret.setFechaNacimiento(element.getElementsByTagName("fecha_nacimiento").item(0).getTextContent());

        return ret;
    }
    */
}
