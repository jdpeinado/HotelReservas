/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hotelreservas;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class HotelNoExiste extends Exception {}
/**
 *
 * @author Administrador
 */
public class DAOHotel {
    private Connection conn;
    private Statement st;
    private String sql;

    public DAOHotel(){
        try {
            conn = BD.dameInstancia().dameConexion();
            st = conn.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertar(Hotel hotel) {
        try {
            GregorianCalendar cal = new GregorianCalendar();
            sql = "INSERT INTO hoteles VALUES ('" + hotel.verNombre().toLowerCase() + "','" + hotel.verLocalidad().toLowerCase() + "'," + hotel.verNumHab() + "," + hotel.verPrecio() + ")";
            st.executeUpdate(sql);            
        } catch (SQLException ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizar(Hotel hotel,String usuario) {
        try {
            GregorianCalendar cal = null;
            Vector<Reserva> r = hotel.verReservas();
            if(r.size()!=0){
                cal = r.lastElement().verFecha();
                int id=r.lastElement().verId();
                String hot=hotel.verNombre().toLowerCase();
                int numHab=r.lastElement().verNumHab();
                int numDias=r.lastElement().verNumDias();
                sql = "INSERT INTO reservas VALUES (" + id + ",'" + hot + "','" + usuario + "','" + cal.getTime() + "'," + numDias + "," + numHab + ")";
                st.executeUpdate(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void borrar(Hotel hotel) {
        try {
            sql = "DELETE FROM hoteles WHERE nombre='" + hotel.verNombre().toLowerCase() + "'";
            st.executeUpdate(sql);
            sql = "DELETE FROM reservas WHERE hotel='" + hotel.verNombre().toLowerCase() + "'";
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Hotel buscarPorNombreLocalidad(String nombre, String localidad) throws HotelNoExiste {
        try {
            ResultSet rst1;
            ResultSet rst2;
            String f;
            GregorianCalendar fecha = new GregorianCalendar();
            java.util.Date fech;
            sql = "SELECT * FROM hoteles WHERE nombre='" + nombre.toLowerCase() + "' AND localidad='" + localidad.toLowerCase() + "'";
            rst1 = st.executeQuery(sql);
            if (rst1.next()) {
                Hotel h = new Hotel(nombre, localidad, rst1.getInt(3), rst1.getFloat(4));
                sql = "SELECT * FROM reservas WHERE nombre='" + nombre.toLowerCase() + "'";
                rst2 = st.executeQuery(sql);
                while (rst2.next()) {
                    f = rst2.getString(4);
                    SimpleDateFormat formateador = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    fech = formateador.parse(f);
                    fecha.setTime(fech);
                    h.reserva(fecha, rst2.getInt(5), rst2.getInt(6));
                }
                return h;
            }
            throw new HotelNoExiste();
        } catch (ReservaNoRealizada ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new HotelNoExiste();
    }

    public Vector buscarPorLocalidad(String localidad) {
        Vector<Hotel> v = new Vector();
        try {

            java.util.Date fech;
            ResultSet rst1;
            ResultSet rst2;
            Hotel h;
            GregorianCalendar fecha = new GregorianCalendar();
            String f;
            rst1 = st.executeQuery("SELECT * FROM hoteles WHERE localidad='" + localidad.toLowerCase() + "'");
            while (rst1.next()) {
                h = new Hotel(rst1.getString(1), localidad, rst1.getInt(3), rst1.getFloat(4));
                sql = "SELECT * FROM reservas WHERE nombre='" + h.verNombre().toLowerCase() + "'";
                rst2 = st.executeQuery(sql);
                while (rst2.next()) {
                    f = rst2.getString(4);
                    SimpleDateFormat formateador = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    fech = formateador.parse(f);
                    fecha.setTime(fech);
                    h.reserva(fecha, rst2.getInt(5), rst2.getInt(6));
                }
                v.add(h);
            }
            return v;
        } catch (ReservaNoRealizada ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }

    public Vector buscarReservas(String usuario){
        Vector<Reserva> v = new Vector();
        try {

            java.util.Date fech;
            ResultSet rst1;
            ResultSet rst2;
            Hotel h;
            Reserva r;
            GregorianCalendar fecha = new GregorianCalendar();
            String f;
            rst1 = st.executeQuery("SELECT * FROM hoteles");
            while (rst1.next()) {
                h = new Hotel(rst1.getString(1), rst1.getString(2), rst1.getInt(3), rst1.getFloat(4));
                sql = "SELECT * FROM reservas WHERE nombre='" + h.verNombre().toLowerCase() + "' AND usuario='" + usuario + "'";
                rst2 = st.executeQuery(sql);
                while (rst2.next()) {
                    f = rst2.getString(4);
                    SimpleDateFormat formateador = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    fech = formateador.parse(f);
                    fecha.setTime(fech);
                    r=new Reserva(rst2.getInt(1), fecha, rst2.getInt(5), rst2.getInt(6), h.verNombre());
                    v.add(r);
                }
            }
            return v;
        } catch (ParseException ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }

}