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
 * @author Administrador
 */
public class DAOUsuario {
    private Connection conn;
    private Statement st;
    private String sql;

    public DAOUsuario(){
        try {
            conn = BD.dameInstancia().dameConexion();
            st = conn.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertar(Usuario usuario) {
        try {
            sql = "INSERT INTO usuarios (nombre, clave) VALUES ('" + usuario.verNombre() + "','" + usuario.verClave() + "')";
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizar(Usuario usuario) {
        try {
            sql = "UPDATE usuarios SET clave='" + usuario.verClave() + "' WHERE nombre='" + usuario.verNombre() + "'";
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrar(Usuario usuario) {
        try {
            sql = "DELETE FROM usuarios WHERE nombre='" + usuario.verNombre() + "'";
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Usuario buscarPorNombre(String nombre, String clave) {
        try {
            ResultSet rst;
            sql = "SELECT * FROM usuarios WHERE nombre='" + nombre + "' AND clave='" + clave + "'";
            rst = st.executeQuery(sql);
            if (rst.next()) {
                return new Usuario(nombre, clave);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void cerrar(){
        try {
            try {
                BD.dameInstancia().cerrar();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}