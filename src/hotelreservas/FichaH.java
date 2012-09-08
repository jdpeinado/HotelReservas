/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hotelreservas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrador
 */
public class FichaH extends JDialog implements ActionListener{
    JLabel ln,ll,lh,lp;
    JButton ba,br,bav,bre;
    JTable tf;
    InfoFecha ife; 
    GregorianCalendar cal;
    Vector<GregorianCalendar> vfechas;
    Hotel h;
    CentralReservas cr;
    GregorianCalendar fecha;
    int numDias, numHab;
    String usuario;


    public FichaH(JFrame f, Hotel h, boolean re){
        super(f, "Hotel "+h.verNombre(), true);
        cal=new GregorianCalendar();
        vfechas=new Vector();
        
        for(int i=0;i<14;i++){
            vfechas.add((GregorianCalendar) cal.clone());
            cal.add(Calendar.DATE, 1);
        }
        
        JPanel pf = new JPanel(); // Crear panel de la tabla
        pf.setLayout(new BorderLayout());

        // Crear la tabla pasando un objeto de la clase InfoReservas
        tf = new JTable(ife = new InfoFecha());
        JScrollPane sp = new JScrollPane(tf);
        sp.setPreferredSize(new Dimension(400,100));
        pf.add(sp, BorderLayout.CENTER);

        JPanel pl=new JPanel();
        pl.setLayout(new BoxLayout(pl, BoxLayout.Y_AXIS));
        pl.add(ln=new JLabel("Nombre: "+h.verNombre()));
        pl.add(ll=new JLabel("Localidad: "+h.verLocalidad()));
        pl.add(lh=new JLabel("Nº Habitaciones: "+h.verNumHab()));
        pl.add(lp=new JLabel("Precio: "+h.verPrecio()));

        JPanel pb=new JPanel();
        pb.setLayout(new BoxLayout(pb, BoxLayout.X_AXIS));
        pb.add(ba=new JButton("Aceptar"));
        pb.add(br=new JButton("Reservar"));
        br.setEnabled(false);
        pb.add(bav=new JButton("Avanzar"));
        pb.add(bre=new JButton("Retroceder"));

      
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(pl);
        add(pf);
        add(pb);
        pack();

        ba.addActionListener(this);
        br.addActionListener(this);
        bav.addActionListener(this);
        bre.addActionListener(this);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public FichaH(JFrame f, Hotel ah, CentralReservas acr, GregorianCalendar afecha, int anumDias, int anumHab, String ausuario){
        super(f, "Hotel "+ah.verNombre(), true);
        cal=new GregorianCalendar();
        vfechas=new Vector();
        h=ah;
        cr=acr;
        usuario=ausuario;
        numDias=anumDias;
        numHab=anumHab;
        fecha=afecha;

        for(int i=0;i<14;i++){
            vfechas.add((GregorianCalendar) cal.clone());
            cal.add(Calendar.DATE, 1);
        }

        JPanel pf = new JPanel(); // Crear panel de la tabla
        pf.setLayout(new BorderLayout());

        // Crear la tabla pasando un objeto de la clase InfoReservas
        tf = new JTable(ife = new InfoFecha());
        JScrollPane sp = new JScrollPane(tf);
        sp.setPreferredSize(new Dimension(400,100));
        pf.add(sp, BorderLayout.CENTER);

        JPanel pl=new JPanel();
        pl.setLayout(new BoxLayout(pl, BoxLayout.Y_AXIS));
        pl.add(ln=new JLabel("Nombre: "+h.verNombre()));
        pl.add(ll=new JLabel("Localidad: "+h.verLocalidad()));
        pl.add(lh=new JLabel("Nº Habitaciones: "+h.verNumHab()));
        pl.add(lp=new JLabel("Precio: "+h.verPrecio()));

        JPanel pb=new JPanel();
        pb.setLayout(new BoxLayout(pb, BoxLayout.X_AXIS));
        pb.add(ba=new JButton("Aceptar"));
        pb.add(br=new JButton("Reservar"));
        pb.add(bav=new JButton("Avanzar"));
        pb.add(bre=new JButton("Retroceder"));


        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(pl);
        add(pf);
        add(pb);
        pack();

        ba.addActionListener(this);
        br.addActionListener(this);
        bav.addActionListener(this);
        bre.addActionListener(this);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ba){
            hide();
        }
        if(e.getSource()==bav){
            vfechas.remove(0);
            GregorianCalendar aux=(GregorianCalendar) vfechas.lastElement().clone();
            aux.add(Calendar.DATE,1);
            vfechas.add(aux);
            ife.fireTableDataChanged ();
        }
        if(e.getSource()==bre){
            vfechas.remove(vfechas.size()-1);
            GregorianCalendar aux=(GregorianCalendar) vfechas.get(0).clone();
            aux.add(Calendar.DATE,-1);
            vfechas.add(0,aux);
            ife.fireTableDataChanged ();
        }
        if(e.getSource()==br){
            try {
                 cr.reserva(h, fecha, numDias, numHab, usuario);
            } catch (ReservaNoRealizada ex) {
                 JOptionPane.showMessageDialog(this, "La reserva no se pudo reallzar","Error!", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(this, "La reserva se realizó con éxito","Información", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    class InfoFecha extends AbstractTableModel{
        private final String[] nombreCols={"Dia"};
        SimpleDateFormat sdf;

        public InfoFecha(){
            super();

            sdf=new SimpleDateFormat("dd.MM.yyyy");
        }

        @Override
        public String getColumnName(int column){
            return nombreCols[column];
        }

        public int getRowCount() {
            if(vfechas.size()==0)
                return 0;
            else
                return vfechas.size();
        }

        public int getColumnCount() {
            return 1;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            GregorianCalendar fecha=(GregorianCalendar) vfechas.get(rowIndex);
            
            switch(columnIndex){
                case 0: return sdf.format(fecha.getTime());
            }
            return "";
        }
    }
}