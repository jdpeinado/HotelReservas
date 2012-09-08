/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hotelreservas;

import java.util.*;

class ReservaNoRealizada extends Exception{}

/**
 *
 * @author Administrador
 */
public class Hotel {
    private String nombre;
    private String localidad;
    private int numHab;
    private float precio;
    private Vector<Reserva> reservas;

    public Hotel(String anombre, String alocalidad, int anumHab, float aprecio){
        nombre=anombre;
        localidad=alocalidad;
        numHab=anumHab;
        precio=aprecio;
        reservas=new Vector();
    }

    public boolean disponible(GregorianCalendar fecha, int anumDias, int anumHab){
        Reserva r;
        int habLibres=numHab;
        GregorianCalendar fSalidaU=new GregorianCalendar();
        GregorianCalendar fSalidaR=new GregorianCalendar();

        fSalidaU=(GregorianCalendar)fecha.clone();
        fSalidaU.add(Calendar.DATE, anumDias);
        for(int i=0;i<reservas.size();i++){
           r=reservas.get(i);
           fSalidaR=(GregorianCalendar)(r.verFecha()).clone();
           fSalidaR.add(Calendar.DATE, r.verNumDias());
           if(!(fecha.getTime().compareTo(fSalidaR.getTime())>0 || fSalidaU.getTime().compareTo(r.verFecha().getTime())<0)){
               habLibres=habLibres-r.verNumHab();
           }
        }
        if(habLibres>=anumHab) return true;
        return false;
    }

    public Reserva reserva(GregorianCalendar fecha,int anumDias, int anumHab) throws ReservaNoRealizada{
        if(disponible(fecha, anumDias, anumHab)){
            GregorianCalendar hoy=new GregorianCalendar();
            Reserva r=new Reserva(reservas.size(), fecha, anumDias, anumHab, nombre);
            reservas.add(r);
            return r;
        }
        throw new ReservaNoRealizada();
    }

    public float importeReserva(Reserva r){
        float p;

        p=(float) r.verNumDias()*precio;
        reservas.remove(reservas.indexOf(r));

        return p;
    }

    public int habitacionesLibres(GregorianCalendar fecha){
        Reserva r;
        int habOcu=0;
        GregorianCalendar f;

        for(int i=0;i<reservas.size();i++){
           r=reservas.get(i);
           f=(GregorianCalendar) fecha.clone();
           f.add(Calendar.DATE, r.verNumDias());
           if(fecha.compareTo(r.verFecha())>=0 && fecha.compareTo(f)<0){
               habOcu=habOcu+r.verNumHab();
           }
        }

        return numHab-habOcu;
    }

    public String verLocalidad(){
        return localidad;
    }

    public String verNombre(){
        return nombre;
    }

    public int verNumHab(){
        return numHab;
    }

    public float verPrecio(){
        return precio;
    }

    public Vector<Reserva> verReservas(){
        return reservas;
    }

}