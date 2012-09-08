/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hotelreservas;

/**
 *
 * @author Administrador
 */
public class Usuario {
    private String nombre;
    private String clave;

    public Usuario(){

    }

    public Usuario(String anombre, String aclave){
        nombre=anombre;
        clave=aclave;
    }

    public Usuario(Usuario u){
        nombre=u.nombre;
        clave=u.clave;
    }

    public String verClave(){ return clave;}

    public String verNombre(){ return nombre;}

}