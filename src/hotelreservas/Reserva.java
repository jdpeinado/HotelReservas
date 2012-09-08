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
public class Reserva {
    private int id;
    private GregorianCalendar fechaEntrada;
    private int numDias;
    private int numHab;
    private String nombreHotel;

    public Reserva(){

    }

    public Reserva(Reserva r){
        id=r.id;
        fechaEntrada=r.fechaEntrada;
        numDias=r.numDias;
        numHab=r.numHab;
        nombreHotel=r.nombreHotel;
    }

    public Reserva(int aid, GregorianCalendar fecha, int anumDias, int anumHab, String anombreHotel){
        id = aid;
        fechaEntrada=(GregorianCalendar) fecha.clone();
        numDias=anumDias;
        numHab=anumHab;
        nombreHotel=anombreHotel;
    }

    int verId(){ return id;}

    GregorianCalendar verFecha(){ return fechaEntrada;}

    int verNumDias(){ return numDias;}

    int verNumHab(){ return numHab;}

    String verNombreHotel() { return nombreHotel;}

     public GregorianCalendar fechaSalida() {
        GregorianCalendar cal=(GregorianCalendar) fechaEntrada.clone();
        cal.add(Calendar.DATE, numDias);

        return cal;
    }
}