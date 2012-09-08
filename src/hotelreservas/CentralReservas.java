/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hotelreservas;

import java.util.*;

/** 
 *
 * @author Administrador
 */
public class CentralReservas {
    private DAOHotel hoteles;
    private DAOUsuario usuarios;



    public CentralReservas() {
        hoteles=new DAOHotel();
        usuarios=new DAOUsuario();
    }

    public void altaUsuario(String nombre, String clave) {
        usuarios.insertar(new Usuario(nombre, clave));
    }

    public void altaHotel(String nombre, String localidad, int numHab, float precio) {
        hoteles.insertar(new Hotel(nombre, localidad, numHab, precio));
    }

    public Usuario registro(String nombreUsuario, String claveUsuario) {
        return usuarios.buscarPorNombre(nombreUsuario, claveUsuario);
    }

    public Vector consultaDisponibilidad(String localidad, GregorianCalendar fecha, int numDias, int numHab) {
        Vector<Hotel> v=hoteles.buscarPorLocalidad(localidad);

        for(int i=0;i<v.size();i++){
            if(!v.get(i).disponible(fecha, numDias, numHab)){
                    v.get(i);
            }
        }

        return v;
    }

    public Hotel buscarHotel(String nombre, String localidad) throws HotelNoExiste{
        return hoteles.buscarPorNombreLocalidad(nombre, localidad);
    }

    public Vector buscarHotelLocalidad(String localidad){
        return hoteles.buscarPorLocalidad(localidad);
    }

    public Reserva reserva(Hotel hotel, GregorianCalendar fecha, int numDias, int numHab, String usuario) throws ReservaNoRealizada{
        Reserva r=null;

        try{
            r = hotel.reserva(fecha, numDias, numHab);
        }
        catch(ReservaNoRealizada ex){
            throw new ReservaNoRealizada();
        }
        hoteles.actualizar(hotel, usuario);
        return r;
    }

    public Vector consultaReserva(String usuario) {
        return hoteles.buscarReservas(usuario);
    }

    public void cerrar() {
        usuarios.cerrar();
    }
}
