/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hotelreservas;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class BD {
    private static BD instancia=null;
    private Connection conn;

    private BD() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:file:./bd/hotel");
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static BD dameInstancia() throws ClassNotFoundException, SQLException{
        if(instancia==null)
            instancia=new BD();
        return instancia;
    }

    public Connection dameConexion(){
        return conn;
    }

    public void cerrar() throws SQLException{
        Statement st;

        st = conn.createStatement();
        st.executeUpdate("SHUTDOWN");
        st.close();
        conn.close();
    }
}
