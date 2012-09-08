/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hotelreservas;

import java.sql.SQLException;
import java.text.ParseException;
import java.io.*;

class UsuarioInvalido extends Exception{}
class NoHayHotelesLibres extends Exception{}
/**
 *
 * @author Administrador
 */
public class HotelApp {

    public static void main(String[] args) throws UsuarioInvalido, NoHayHotelesLibres, IOException, ClassNotFoundException, SQLException, ParseException, ReservaNoRealizada{
        FHotelApp fhotel=new FHotelApp();
    }

}