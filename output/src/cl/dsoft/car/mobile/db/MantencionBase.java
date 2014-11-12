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
public class MantencionBase {
    @Element(name = "nombre")
    private String _nombre;
    @Element(name = "descripcionItem", required = false)
    private String _descripcionItem;
    @Element(name = "traccion")
    private String _traccion;
    @Element(name = "fechaModificacion")
    private String _fechaModificacion;
    @Element(name = "combustible")
    private String _combustible;
    @Element(name = "id")
    private Long _id;
    @Element(name = "kmEntreMantenciones", required = false)
    private Integer _kmEntreMantenciones;
    @Element(name = "mesesEntreMantenciones", required = false)
    private Integer _mesesEntreMantenciones;
    @Element(name = "dependeKm", required = false)
    private Boolean _dependeKm;
    @Element(name = "accion", required = false)
    private String _accion;
    @Element(name = "url", required = false)
    private String _url;
    @Element(name = "beneficios", required = false)
    private String _beneficios;

    private final static String _str_sql = 
        "    SELECT" +
        "    ma.nombre AS nombre," +
        "    ma.descripcion_item AS descripcion_item," +
        "    ma.traccion AS traccion," +
        "    strftime('%Y-%m-%d %H:%M:%S', ma.fecha_modificacion) AS fecha_modificacion," +
        "    ma.combustible AS combustible," +
        "    ma.id_mantencion_base AS id," +
        "    ma.km_entre_mantenciones AS km_entre_mantenciones," +
        "    ma.meses_entre_mantenciones AS meses_entre_mantenciones," +
        "    ma.depende_km AS depende_km," +
        "    ma.accion AS accion," +
        "    ma.url AS url," +
        "    ma.beneficios AS beneficios" +
        "    FROM mantencion_base ma";

    public MantencionBase() {
        _nombre = null;
        _descripcionItem = null;
        _traccion = null;
        _fechaModificacion = null;
        _combustible = null;
        _id = null;
        _kmEntreMantenciones = null;
        _mesesEntreMantenciones = null;
        _dependeKm = null;
        _accion = null;
        _url = null;
        _beneficios = null;

    }
    /**
     * @return the _nombre
     */
    public String getNombre() {
        return _nombre;
    }
    /**
     * @return the _descripcionItem
     */
    public String getDescripcionItem() {
        return _descripcionItem;
    }
    /**
     * @return the _traccion
     */
    public String getTraccion() {
        return _traccion;
    }
    /**
     * @return the _fechaModificacion
     */
    public String getFechaModificacion() {
        return _fechaModificacion;
    }
    /**
     * @return the _combustible
     */
    public String getCombustible() {
        return _combustible;
    }
    /**
     * @return the _id
     */
    public Long getId() {
        return _id;
    }
    /**
     * @return the _kmEntreMantenciones
     */
    public Integer getKmEntreMantenciones() {
        return _kmEntreMantenciones;
    }
    /**
     * @return the _mesesEntreMantenciones
     */
    public Integer getMesesEntreMantenciones() {
        return _mesesEntreMantenciones;
    }
    /**
     * @return the _dependeKm
     */
    public Boolean getDependeKm() {
        return _dependeKm;
    }
    /**
     * @return the _accion
     */
    public String getAccion() {
        return _accion;
    }
    /**
     * @return the _url
     */
    public String getUrl() {
        return _url;
    }
    /**
     * @return the _beneficios
     */
    public String getBeneficios() {
        return _beneficios;
    }
    /**
     * @param _nombre the _nombre to set
     */
    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }
    /**
     * @param _descripcionItem the _descripcionItem to set
     */
    public void setDescripcionItem(String _descripcionItem) {
        this._descripcionItem = _descripcionItem;
    }
    /**
     * @param _traccion the _traccion to set
     */
    public void setTraccion(String _traccion) {
        this._traccion = _traccion;
    }
    /**
     * @param _fechaModificacion the _fechaModificacion to set
     */
    public void setFechaModificacion(String _fechaModificacion) {
        this._fechaModificacion = _fechaModificacion;
    }
    /**
     * @param _combustible the _combustible to set
     */
    public void setCombustible(String _combustible) {
        this._combustible = _combustible;
    }
    /**
     * @param _id the _id to set
     */
    public void setId(Long _id) {
        this._id = _id;
    }
    /**
     * @param _kmEntreMantenciones the _kmEntreMantenciones to set
     */
    public void setKmEntreMantenciones(Integer _kmEntreMantenciones) {
        this._kmEntreMantenciones = _kmEntreMantenciones;
    }
    /**
     * @param _mesesEntreMantenciones the _mesesEntreMantenciones to set
     */
    public void setMesesEntreMantenciones(Integer _mesesEntreMantenciones) {
        this._mesesEntreMantenciones = _mesesEntreMantenciones;
    }
    /**
     * @param _dependeKm the _dependeKm to set
     */
    public void setDependeKm(Boolean _dependeKm) {
        this._dependeKm = _dependeKm;
    }
    /**
     * @param _accion the _accion to set
     */
    public void setAccion(String _accion) {
        this._accion = _accion;
    }
    /**
     * @param _url the _url to set
     */
    public void setUrl(String _url) {
        this._url = _url;
    }
    /**
     * @param _beneficios the _beneficios to set
     */
    public void setBeneficios(String _beneficios) {
        this._beneficios = _beneficios;
    }

    public static MantencionBase fromRS(ResultSet p_rs) throws SQLException {
        MantencionBase ret = new MantencionBase();

        ret.setNombre(p_rs.getString("nombre"));
        ret.setDescripcionItem(p_rs.getString("descripcion_item"));
        ret.setTraccion(p_rs.getString("traccion"));
        ret.setFechaModificacion(p_rs.getString("fecha_modificacion"));
        ret.setCombustible(p_rs.getString("combustible"));
        ret.setId(p_rs.getLong("id"));
        ret.setKmEntreMantenciones(p_rs.getInt("km_entre_mantenciones"));
        ret.setMesesEntreMantenciones(p_rs.getInt("meses_entre_mantenciones"));
        ret.setDependeKm(p_rs.getString("depende_km") != null ? p_rs.getString("depende_km").equals("true") : null);
        ret.setAccion(p_rs.getString("accion"));
        ret.setUrl(p_rs.getString("url"));
        ret.setBeneficios(p_rs.getString("beneficios"));

        return ret;
    }

    public static MantencionBase getByParameter(Connection p_conn, String p_key, String p_value) throws SQLException {
        MantencionBase ret = null;
        
        String str_sql = _str_sql +
            "  WHERE ma." + p_key + " = " + p_value +
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

    public static MantencionBase getById(Connection p_conn, String p_id) throws SQLException {
        return getByParameter(p_conn, "id_mantencion_base", p_id);
    }
    
    public static ArrayList<MantencionBase> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_order, String p_direction, int p_offset, int p_limit) throws UnsupportedParameterException, SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        String str_sql;
        ArrayList<MantencionBase> ret;
        
        str_sql = "";
        
        try {
            ArrayList<String> array_clauses = new ArrayList<String>();
            
            ret = new ArrayList<MantencionBase>();
            
            str_sql = _str_sql;
            
            for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {
                if (p.getKey().equals("id_mantencion_base")) {
                    array_clauses.add("ma.id_mantencion_base = " + p.getValue());
                }
                else if (p.getKey().equals("mas reciente")) {
                    array_clauses.add("ma.fecha_modificacion > datetime('" + p.getValue() + "', 'localtime')");
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
            "  SELECT COALESCE(MAX(id_mantencion_base), 0) + 1 AS next_id FROM mantencion_base";
        
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
            "    UPDATE mantencion_base" +
            "    SET" +
            "    nombre = " + (_nombre != null ? "'" + _nombre + "'" : "null") + "," +
            "    descripcion_item = " + (_descripcionItem != null ? "'" + _descripcionItem + "'" : "null") + "," +
            "    traccion = " + (_traccion != null ? "'" + _traccion + "'" : "null") + "," +
            "    fecha_modificacion = " + (_fechaModificacion != null ? "datetime('" + _fechaModificacion + "', 'localtime')" : "datetime('now', 'localtime')") + "," +
            "    combustible = " + (_combustible != null ? "'" + _combustible + "'" : "null") + "," +
            "    km_entre_mantenciones = " + (_kmEntreMantenciones != null ? "'" + _kmEntreMantenciones + "'" : "null") + "," +
            "    meses_entre_mantenciones = " + (_mesesEntreMantenciones != null ? "'" + _mesesEntreMantenciones + "'" : "null") + "," +
            "    depende_km = " + (_dependeKm != null ? "'" + _dependeKm + "'" : "null") + "," +
            "    accion = " + (_accion != null ? "'" + _accion + "'" : "null") + "," +
            "    url = " + (_url != null ? "'" + _url + "'" : "null") + "," +
            "    beneficios = " + (_beneficios != null ? "'" + _beneficios + "'" : "null") +
            "    WHERE" +
            "    id_mantencion_base = " + Long.toString(this._id);

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
            "    INSERT INTO mantencion_base" +
            "    (" +
            "    nombre, " +
            "    descripcion_item, " +
            "    traccion, " +
            "    fecha_modificacion, " +
            "    combustible, " +
            "    id_mantencion_base, " +
            "    km_entre_mantenciones, " +
            "    meses_entre_mantenciones, " +
            "    depende_km, " +
            "    accion, " +
            "    url, " +
            "    beneficios)" +
            "    VALUES" +
            "    (" +
            "    " + (_nombre != null ? "'" + _nombre + "'" : "null") + "," +
            "    " + (_descripcionItem != null ? "'" + _descripcionItem + "'" : "null") + "," +
            "    " + (_traccion != null ? "'" + _traccion + "'" : "null") + "," +
            "    " + (_fechaModificacion != null ? "datetime('" + _fechaModificacion + "', 'localtime')" : "datetime('now', 'localtime')") + "," +
            "    " + (_combustible != null ? "'" + _combustible + "'" : "null") + "," +
            "    " + (_id != null ? "'" + _id + "'" : "null") + "," +
            "    " + (_kmEntreMantenciones != null ? "'" + _kmEntreMantenciones + "'" : "null") + "," +
            "    " + (_mesesEntreMantenciones != null ? "'" + _mesesEntreMantenciones + "'" : "null") + "," +
            "    " + (_dependeKm != null ? "'" + _dependeKm + "'" : "null") + "," +
            "    " + (_accion != null ? "'" + _accion + "'" : "null") + "," +
            "    " + (_url != null ? "'" + _url + "'" : "null") + "," +
            "    " + (_beneficios != null ? "'" + _beneficios + "'" : "null") +
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
            "    DELETE FROM mantencion_base" +
            "    WHERE" +
            "    id_mantencion_base = " + Long.toString(this._id);

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
        MantencionBase obj = null;
        
        String str_sql = _str_sql +
            "    WHERE" +
            "    id_mantencion_base = " + Long.toString(this._id) +
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
                _descripcionItem = obj.getDescripcionItem();
                _traccion = obj.getTraccion();
                _fechaModificacion = obj.getFechaModificacion();
                _combustible = obj.getCombustible();
                _kmEntreMantenciones = obj.getKmEntreMantenciones();
                _mesesEntreMantenciones = obj.getMesesEntreMantenciones();
                _dependeKm = obj.getDependeKm();
                _accion = obj.getAccion();
                _url = obj.getUrl();
                _beneficios = obj.getBeneficios();
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
            "    id_mantencion_base = " + Long.toString(this._id) +
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
        return "MantencionBase [" +
	           "    _nombre = " + (_nombre != null ? "'" + _nombre + "'" : "null") + "," +
	           "    _descripcionItem = " + (_descripcionItem != null ? "'" + _descripcionItem + "'" : "null") + "," +
	           "    _traccion = " + (_traccion != null ? "'" + _traccion + "'" : "null") + "," +
	           "    _fechaModificacion = " + (_fechaModificacion != null ? "'" + _fechaModificacion + "'" : "null") + "," +
	           "    _combustible = " + (_combustible != null ? "'" + _combustible + "'" : "null") + "," +
	           "    _id = " + (_id != null ? _id : "null") + "," +
	           "    _kmEntreMantenciones = " + (_kmEntreMantenciones != null ? _kmEntreMantenciones : "null") + "," +
	           "    _mesesEntreMantenciones = " + (_mesesEntreMantenciones != null ? _mesesEntreMantenciones : "null") + "," +
	           "    _dependeKm = " + (_dependeKm != null ? _dependeKm : "null") + "," +
	           "    _accion = " + (_accion != null ? "'" + _accion + "'" : "null") + "," +
	           "    _url = " + (_url != null ? "'" + _url + "'" : "null") + "," +
	           "    _beneficios = " + (_beneficios != null ? "'" + _beneficios + "'" : "null") +
			   "]";
    }


/*
    public static MantencionBase fromXMLNode(Node xmlNode) {
        MantencionBase ret = new MantencionBase();

        Element element = (Element) xmlNode;

        ret.setNombre(element.getElementsByTagName("nombre").item(0).getTextContent());
        ret.setDescripcionItem(element.getElementsByTagName("descripcion_item").item(0).getTextContent());
        ret.setTraccion(element.getElementsByTagName("traccion").item(0).getTextContent());
        ret.setFechaModificacion(element.getElementsByTagName("fecha_modificacion").item(0).getTextContent());
        ret.setCombustible(element.getElementsByTagName("combustible").item(0).getTextContent());
        ret.setId(Long.decode(element.getElementsByTagName("id_mantencion_base").item(0).getTextContent()));
        ret.setKmEntreMantenciones(Integer.decode(element.getElementsByTagName("km_entre_mantenciones").item(0).getTextContent()));
        ret.setMesesEntreMantenciones(Integer.decode(element.getElementsByTagName("meses_entre_mantenciones").item(0).getTextContent()));
        ret.setDependeKm(element.getElementsByTagName("depende_km").item(0).getTextContent());
        ret.setAccion(element.getElementsByTagName("accion").item(0).getTextContent());
        ret.setUrl(element.getElementsByTagName("url").item(0).getTextContent());
        ret.setBeneficios(element.getElementsByTagName("beneficios").item(0).getTextContent());

        return ret;
    }
    */
}
