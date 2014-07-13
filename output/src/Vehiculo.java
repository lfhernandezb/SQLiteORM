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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Luis Hernandez
 *
 */
@Root
public class Vehiculo {
    @Element(name = "anio", required = false)
    private Integer _anio;
    @Element(name = "fechaModificacion")
    private String _fechaModificacion;
    @Element(name = "aireAcondicionado", required = false)
    private Boolean _aireAcondicionado;
    @Element(name = "alias")
    private String _alias;
    @Element(name = "idUsuario")
    private Long _idUsuario;
    @Element(name = "idVehiculo")
    private Long _idVehiculo;
    @Element(name = "borrado")
    private Boolean _borrado;
    @Element(name = "patente", required = false)
    private String _patente;
    @Element(name = "idModelo")
    private Long _idModelo;
    @Element(name = "idTraccion")
    private Byte _idTraccion;
    @Element(name = "km", required = false)
    private Integer _km;
    @Element(name = "idCombustible")
    private Byte _idCombustible;
    @Element(name = "idTipoTransmision")
    private Byte _idTipoTransmision;
    @Element(name = "alzaVidrios", required = false)
    private Boolean _alzaVidrios;

    private final static String _str_sql = 
        "    SELECT" +
        "    ve.anio AS anio," +
        "    strftime('%Y-%m-%d %H:%M:%S', ve.fecha_modificacion) AS fecha_modificacion," +
        "    ve.aire_acondicionado AS aire_acondicionado," +
        "    ve.alias AS alias," +
        "    ve.id_usuario AS id_usuario," +
        "    ve.id_vehiculo AS id_vehiculo," +
        "    ve.borrado AS borrado," +
        "    ve.patente AS patente," +
        "    ve.id_modelo AS id_modelo," +
        "    ve.id_traccion AS id_traccion," +
        "    ve.km AS km," +
        "    ve.id_combustible AS id_combustible," +
        "    ve.id_tipo_transmision AS id_tipo_transmision," +
        "    ve.alza_vidrios AS alza_vidrios" +
        "    FROM vehiculo ve";

    public Vehiculo() {
        _anio = null;
        _fechaModificacion = null;
        _aireAcondicionado = null;
        _alias = null;
        _idUsuario = null;
        _idVehiculo = null;
        _borrado = null;
        _patente = null;
        _idModelo = null;
        _idTraccion = null;
        _km = null;
        _idCombustible = null;
        _idTipoTransmision = null;
        _alzaVidrios = null;

    }
    /**
     * @return the _anio
     */
    public Integer getAnio() {
        return _anio;
    }
    /**
     * @return the _fechaModificacion
     */
    public String getFechaModificacion() {
        return _fechaModificacion;
    }
    /**
     * @return the _aireAcondicionado
     */
    public Boolean getAireAcondicionado() {
        return _aireAcondicionado;
    }
    /**
     * @return the _alias
     */
    public String getAlias() {
        return _alias;
    }
    /**
     * @return the _idUsuario
     */
    public Long getIdUsuario() {
        return _idUsuario;
    }
    /**
     * @return the _idVehiculo
     */
    public Long getIdVehiculo() {
        return _idVehiculo;
    }
    /**
     * @return the _borrado
     */
    public Boolean getBorrado() {
        return _borrado;
    }
    /**
     * @return the _patente
     */
    public String getPatente() {
        return _patente;
    }
    /**
     * @return the _idModelo
     */
    public Long getIdModelo() {
        return _idModelo;
    }
    /**
     * @return the _idTraccion
     */
    public Byte getIdTraccion() {
        return _idTraccion;
    }
    /**
     * @return the _km
     */
    public Integer getKm() {
        return _km;
    }
    /**
     * @return the _idCombustible
     */
    public Byte getIdCombustible() {
        return _idCombustible;
    }
    /**
     * @return the _idTipoTransmision
     */
    public Byte getIdTipoTransmision() {
        return _idTipoTransmision;
    }
    /**
     * @return the _alzaVidrios
     */
    public Boolean getAlzaVidrios() {
        return _alzaVidrios;
    }
    /**
     * @param _anio the _anio to set
     */
    public void setAnio(Integer _anio) {
        this._anio = _anio;
    }
    /**
     * @param _fechaModificacion the _fechaModificacion to set
     */
    public void setFechaModificacion(String _fechaModificacion) {
        this._fechaModificacion = _fechaModificacion;
    }
    /**
     * @param _aireAcondicionado the _aireAcondicionado to set
     */
    public void setAireAcondicionado(Boolean _aireAcondicionado) {
        this._aireAcondicionado = _aireAcondicionado;
    }
    /**
     * @param _alias the _alias to set
     */
    public void setAlias(String _alias) {
        this._alias = _alias;
    }
    /**
     * @param _idUsuario the _idUsuario to set
     */
    public void setIdUsuario(Long _idUsuario) {
        this._idUsuario = _idUsuario;
    }
    /**
     * @param _idVehiculo the _idVehiculo to set
     */
    public void setIdVehiculo(Long _idVehiculo) {
        this._idVehiculo = _idVehiculo;
    }
    /**
     * @param _borrado the _borrado to set
     */
    public void setBorrado(Boolean _borrado) {
        this._borrado = _borrado;
    }
    /**
     * @param _patente the _patente to set
     */
    public void setPatente(String _patente) {
        this._patente = _patente;
    }
    /**
     * @param _idModelo the _idModelo to set
     */
    public void setIdModelo(Long _idModelo) {
        this._idModelo = _idModelo;
    }
    /**
     * @param _idTraccion the _idTraccion to set
     */
    public void setIdTraccion(Byte _idTraccion) {
        this._idTraccion = _idTraccion;
    }
    /**
     * @param _km the _km to set
     */
    public void setKm(Integer _km) {
        this._km = _km;
    }
    /**
     * @param _idCombustible the _idCombustible to set
     */
    public void setIdCombustible(Byte _idCombustible) {
        this._idCombustible = _idCombustible;
    }
    /**
     * @param _idTipoTransmision the _idTipoTransmision to set
     */
    public void setIdTipoTransmision(Byte _idTipoTransmision) {
        this._idTipoTransmision = _idTipoTransmision;
    }
    /**
     * @param _alzaVidrios the _alzaVidrios to set
     */
    public void setAlzaVidrios(Boolean _alzaVidrios) {
        this._alzaVidrios = _alzaVidrios;
    }

    public static Vehiculo fromRS(ResultSet p_rs) throws SQLException {
        Vehiculo ret = new Vehiculo();

        ret.setAnio(p_rs.getInt("anio"));
        ret.setFechaModificacion(p_rs.getString("fecha_modificacion"));
        ret.setAireAcondicionado(p_rs.getBoolean("aire_acondicionado"));
        ret.setAlias(p_rs.getString("alias"));
        ret.setIdUsuario(p_rs.getLong("id_usuario"));
        ret.setIdVehiculo(p_rs.getLong("id_vehiculo"));
        ret.setBorrado(p_rs.getBoolean("borrado"));
        ret.setPatente(p_rs.getString("patente"));
        ret.setIdModelo(p_rs.getLong("id_modelo"));
        ret.setIdTraccion(p_rs.getByte("id_traccion"));
        ret.setKm(p_rs.getInt("km"));
        ret.setIdCombustible(p_rs.getByte("id_combustible"));
        ret.setIdTipoTransmision(p_rs.getByte("id_tipo_transmision"));
        ret.setAlzaVidrios(p_rs.getBoolean("alza_vidrios"));

        return ret;
    }

    public static Vehiculo getByParameter(Connection p_conn, String p_key, String p_value) throws SQLException {
        Vehiculo ret = null;
        
        String str_sql = _str_sql +
            "  WHERE ve." + p_key + " = " + p_value +
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

    
    public static ArrayList<Vehiculo> seek(Connection p_conn, ArrayList<AbstractMap.SimpleEntry<String, String>> p_parameters, String p_order, String p_direction, int p_offset, int p_limit) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        String str_sql;
        ArrayList<Vehiculo> ret;
        
        str_sql = "";
        
        try {
            ArrayList<String> array_clauses = new ArrayList<String>();
            
            ret = new ArrayList<Vehiculo>();
            
            str_sql = _str_sql;
            
            for (AbstractMap.SimpleEntry<String, String> p : p_parameters) {
                if (p.getKey().equals("id_usuario")) {
                    array_clauses.add("ve.id_usuario = " + p.getValue());
                }
                else if (p.getKey().equals("id_vehiculo")) {
                    array_clauses.add("ve.id_vehiculo = " + p.getValue());
                }
                else if (p.getKey().equals("id_usuario")) {
                    array_clauses.add("ve.id_usuario = " + p.getValue());
                }
                else if (p.getKey().equals("id_modelo")) {
                    array_clauses.add("ve.id_modelo = " + p.getValue());
                }
                else if (p.getKey().equals("id_traccion")) {
                    array_clauses.add("ve.id_traccion = " + p.getValue());
                }
                else if (p.getKey().equals("id_tipo_transmision")) {
                    array_clauses.add("ve.id_tipo_transmision = " + p.getValue());
                }
                else if (p.getKey().equals("id_combustible")) {
                    array_clauses.add("ve.id_combustible = " + p.getValue());
                }
                else if (p.getKey().equals("mas reciente")) {
                    array_clauses.add("ve.fecha_modificacion > " + p.getValue());
                }
                else if (p.getKey().equals("no borrado")) {
                    array_clauses.add("ve.borrado = 'false'");
                }
                else if (p.getKey().equals("borrado")) {
                    array_clauses.add("ve.borrado = 'true'");
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
            "  SELECT COALESCE(MAX(id_vehiculo), 0) + 1 AS next_id FROM vehiculo";
        
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
            "    UPDATE vehiculo" +
            "    SET" +
            "    anio = " + (_anio != null ? "'" + _anio + "'" : "null") + "," +
            "    fecha_modificacion = " + (_fechaModificacion != null ? "datetime('" + _fechaModificacion + "', 'localtime')" : "datetime('now', 'localtime')") + "," +
            "    aire_acondicionado = " + (_aireAcondicionado != null ? "'" + _aireAcondicionado + "'" : "null") + "," +
            "    alias = " + (_alias != null ? "'" + _alias + "'" : "null") + "," +
            "    borrado = " + (_borrado != null ? "'" + _borrado + "'" : "'false'") + "," +
            "    patente = " + (_patente != null ? "'" + _patente + "'" : "null") + "," +
            "    km = " + (_km != null ? "'" + _km + "'" : "null") + "," +
            "    alza_vidrios = " + (_alzaVidrios != null ? "'" + _alzaVidrios + "'" : "null") +
            "    WHERE" +
            "    id_usuario = " + Long.toString(this._idUsuario) + " AND" +
            "    id_vehiculo = " + Long.toString(this._idVehiculo);

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

        if (_idVehiculo == null) {
            _idVehiculo = getNextId(p_conn);
        }

        String str_sql =
            "    INSERT INTO vehiculo" +
            "    (" +
            "    anio, " +
            "    fecha_modificacion, " +
            "    aire_acondicionado, " +
            "    alias, " +
            "    id_usuario, " +
            "    id_vehiculo, " +
            "    borrado, " +
            "    patente, " +
            "    id_modelo, " +
            "    id_traccion, " +
            "    km, " +
            "    id_combustible, " +
            "    id_tipo_transmision, " +
            "    alza_vidrios)" +
            "    VALUES" +
            "    (" +
            "    " + (_anio != null ? "'" + _anio + "'" : "null") + "," +
            "    " + (_fechaModificacion != null ? "datetime('" + _fechaModificacion + "', 'localtime')" : "datetime('now', 'localtime')") + "," +
            "    " + (_aireAcondicionado != null ? "'" + _aireAcondicionado + "'" : "null") + "," +
            "    " + (_alias != null ? "'" + _alias + "'" : "null") + "," +
            "    " + (_idUsuario != null ? "'" + _idUsuario + "'" : "null") + "," +
            "    " + (_idVehiculo != null ? "'" + _idVehiculo + "'" : "null") + "," +
            "    " + (_borrado != null ? "'" + _borrado + "'" : "'false'") + "," +
            "    " + (_patente != null ? "'" + _patente + "'" : "null") + "," +
            "    " + (_idModelo != null ? "'" + _idModelo + "'" : "null") + "," +
            "    " + (_idTraccion != null ? "'" + _idTraccion + "'" : "null") + "," +
            "    " + (_km != null ? "'" + _km + "'" : "null") + "," +
            "    " + (_idCombustible != null ? "'" + _idCombustible + "'" : "null") + "," +
            "    " + (_idTipoTransmision != null ? "'" + _idTipoTransmision + "'" : "null") + "," +
            "    " + (_alzaVidrios != null ? "'" + _alzaVidrios + "'" : "null") +
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
            "    DELETE FROM vehiculo" +
            "    WHERE" +
            "    id_usuario = " + Long.toString(this._idUsuario) + " AND" +
            "    id_vehiculo = " + Long.toString(this._idVehiculo);

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
        Vehiculo obj = null;
        
        String str_sql = _str_sql +
            "    WHERE" +
            "    id_usuario = " + Long.toString(this._idUsuario) + " AND" +
            "    id_vehiculo = " + Long.toString(this._idVehiculo) +
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

                _anio = obj.getAnio();
                _fechaModificacion = obj.getFechaModificacion();
                _aireAcondicionado = obj.getAireAcondicionado();
                _alias = obj.getAlias();
                _borrado = obj.getBorrado();
                _patente = obj.getPatente();
                _idModelo = obj.getIdModelo();
                _idTraccion = obj.getIdTraccion();
                _km = obj.getKm();
                _idCombustible = obj.getIdCombustible();
                _idTipoTransmision = obj.getIdTipoTransmision();
                _alzaVidrios = obj.getAlzaVidrios();
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
            "    id_usuario = " + Long.toString(this._idUsuario) + " AND" +
            "    id_vehiculo = " + Long.toString(this._idVehiculo) +
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
        return "Vehiculo [" +
	           "    _anio = " + (_anio != null ? _anio : "null") + "," +
	           "    _fechaModificacion = " + (_fechaModificacion != null ? "'" + _fechaModificacion + "'" : "null") + "," +
	           "    _aireAcondicionado = " + (_aireAcondicionado != null ? _aireAcondicionado : "null") + "," +
	           "    _alias = " + (_alias != null ? "'" + _alias + "'" : "null") + "," +
	           "    _idUsuario = " + (_idUsuario != null ? _idUsuario : "null") + "," +
	           "    _idVehiculo = " + (_idVehiculo != null ? _idVehiculo : "null") + "," +
	           "    _borrado = " + (_borrado != null ? _borrado : "null") + "," +
	           "    _patente = " + (_patente != null ? "'" + _patente + "'" : "null") + "," +
	           "    _idModelo = " + (_idModelo != null ? _idModelo : "null") + "," +
	           "    _idTraccion = " + (_idTraccion != null ? _idTraccion : "null") + "," +
	           "    _km = " + (_km != null ? _km : "null") + "," +
	           "    _idCombustible = " + (_idCombustible != null ? _idCombustible : "null") + "," +
	           "    _idTipoTransmision = " + (_idTipoTransmision != null ? _idTipoTransmision : "null") + "," +
	           "    _alzaVidrios = " + (_alzaVidrios != null ? _alzaVidrios : "null") +
			   "]";
    }


/*
    public static Vehiculo fromXMLNode(Node xmlNode) {
        Vehiculo ret = new Vehiculo();

        Element element = (Element) xmlNode;

        ret.setAnio(Integer.decode(element.getElementsByTagName("anio").item(0).getTextContent()));
        ret.setFechaModificacion(element.getElementsByTagName("fecha_modificacion").item(0).getTextContent());
        ret.setAireAcondicionado(Boolean.decode(element.getElementsByTagName("aire_acondicionado").item(0).getTextContent()));
        ret.setAlias(element.getElementsByTagName("alias").item(0).getTextContent());
        ret.setIdUsuario(Long.decode(element.getElementsByTagName("id_usuario").item(0).getTextContent()));
        ret.setIdVehiculo(Long.decode(element.getElementsByTagName("id_vehiculo").item(0).getTextContent()));
        ret.setBorrado(Boolean.decode(element.getElementsByTagName("borrado").item(0).getTextContent()));
        ret.setPatente(element.getElementsByTagName("patente").item(0).getTextContent());
        ret.setIdModelo(Long.decode(element.getElementsByTagName("id_modelo").item(0).getTextContent()));
        ret.setIdTraccion(Byte.decode(element.getElementsByTagName("id_traccion").item(0).getTextContent()));
        ret.setKm(Integer.decode(element.getElementsByTagName("km").item(0).getTextContent()));
        ret.setIdCombustible(Byte.decode(element.getElementsByTagName("id_combustible").item(0).getTextContent()));
        ret.setIdTipoTransmision(Byte.decode(element.getElementsByTagName("id_tipo_transmision").item(0).getTextContent()));
        ret.setAlzaVidrios(Boolean.decode(element.getElementsByTagName("alza_vidrios").item(0).getTextContent()));

        return ret;
    }
    */

    public ArrayList<MantencionBaseHecha> getMantencionesPendientes(Connection p_conn) throws Exception {
    	
    	ArrayList<MantencionBaseHecha> ret;
    	ArrayList<MantencionBase> list_mb;
    	ArrayList<AbstractMap.SimpleEntry<String, String>> parametros;
    	Integer kmInicial = 0;
    	String traccion, combustible;
    	
    	traccion = Traccion.getById(p_conn, this.getIdTraccion().toString()).getDescripcion();
    	combustible = Combustible.getById(p_conn, this.getIdCombustible().toString()).getDescripcion();
    	
    	parametros = new ArrayList<AbstractMap.SimpleEntry<String, String>>();
    	
    	ret = new ArrayList<MantencionBaseHecha>();
    	
    	parametros.add(new AbstractMap.SimpleEntry<String, String>("traccion", traccion));
    	parametros.add(new AbstractMap.SimpleEntry<String, String>("combustible", combustible));
    	
    	list_mb = MantencionBase.seek(p_conn, parametros, null, null, 0, 10000);
    	
    	for (MantencionBase mb : list_mb) {
    		
    		ArrayList<MantencionBaseHecha> list_mbh;
    		MantencionBaseHecha mbh; // = new MantencionBaseHecha();
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		Integer n;
    		
    		if (mb.getDependeKm() && mb.getDiasEntreMantenciones() != null) {
    		
	    		// encuentro ultima mantencion hecha de este tipo
	    		parametros.clear();
	    		
	    		parametros.add(new AbstractMap.SimpleEntry<String, String>("id_mantencion_base", mb.getId().toString()));
	    		
	    		list_mbh = MantencionBaseHecha.seek(p_conn, parametros, "fecha", "DESC", 0, 1);
	    		
	    		if (list_mbh.size() > 0) {
	    			kmInicial = list_mbh.get(0).getKm();
	    		}
	    		
	    		n = (this.getKm() - kmInicial) / mb.getKmEntreMantenciones();
	    		
	    		if (n > 0) {
	    			// hay mantencion pendiente, o bien no la ha informado
	    			mbh = new MantencionBaseHecha();
	    			
	    			mbh.setIdMantencionBase(mb.getId());
	    			mbh.setVehiculoIdVehiculo(this.getIdVehiculo());
	    			mbh.setVehiculoIdUsuario(this.getIdUsuario());
	    			mbh.setKm(n * mb.getKmEntreMantenciones());
	    			mbh.setFecha(formatter.format(new Date()));
	    			
	    			ret.add(mbh);
	    		}
    		}
    		
    	}
    	
		return ret;
    	
    }

}
