/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hotelreservas;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrador
 */
public class FHotelApp extends JFrame implements ActionListener{
    JTable tr;
    InfoReservas ir;
    JLabel ln,ll,lfl,lfh,lfd,lf;
    JButton bh,bl,bf,blf,act;
    JTextField tn,tl,tfl,tfh,tfd,tf;
    JList lh,lhf;
    CentralReservas cr;
    Usuario u;
    Vector v=new Vector();
    Vector<Hotel> hoteles=new Vector(),hotelesf=new Vector();



    class FHotelAppWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e){
            terminarAplicacion();
        }
    }

    public void terminarAplicacion(){
        cr.cerrar();
        System.exit(0);
    }

    public FHotelApp()throws ClassNotFoundException, SQLException, ParseException, ReservaNoRealizada{
        super("Gestión reservas de hoteles");
        cr=new CentralReservas();
        u=null;

        loginUsuario();

        JTabbedPane tabpane=new JTabbedPane();
        
        tabpane.addTab("Reservas", pestañaReservas());
        tabpane.addTab("Búsqueda Hoteles", pestañaHoteles());
        tabpane.addTab("Buscar hoteles por fecha", pestañaHotelFecha());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(tabpane);
        pack();

        addWindowListener(new FHotelAppWindowListener());
        setVisible(true);
    }

    private void loginUsuario() throws ParseException, ReservaNoRealizada{
        Flogin login=new Flogin(this,cr);
        login.setVisible(true);

        do{
            if(login.realizarOperacion())
                u=login.leerUsuario();
            if(login.salirAplicacion())
                terminarAplicacion();
        }while(!login.realizarOperacion());
        v=cr.consultaReserva(u.verNombre());
    }

    private JPanel pestañaReservas(){
        JPanel pr = new JPanel(); // Crear panel de la tabla
        pr.setLayout(new BoxLayout(pr, BoxLayout.Y_AXIS));

        // Crear la tabla pasando un objeto de la clase InfoReservas
        tr = new JTable(ir = new InfoReservas());
        JScrollPane sp = new JScrollPane(tr);
        sp.setPreferredSize(new Dimension(400,100));

        act=new JButton("Actualizar tabla");

        pr.add(sp, BorderLayout.CENTER);
        pr.add(act);
        pack();

        act.addActionListener(this);

        return pr;
    }

    private JPanel pestañaHoteles(){
        JPanel ph=new JPanel();
        ph.setLayout(new FlowLayout());
        ph.add(ln=new JLabel("Nombre hotel"));
        ph.add(tn=new JTextField(20));
        ph.add(ll=new JLabel("Localidad"));
        ph.add(tl=new JTextField(10));
        ph.add(bh=new JButton("Buscar"));

        JPanel panelLista=new JPanel();
        lh=new JList();
        panelLista.add(lh, BorderLayout.CENTER);

        JPanel pl=new JPanel();
        pl.setLayout(new FlowLayout());
        pl.add(bl=new JButton("Limpiar"),BorderLayout.SOUTH);

        JPanel panelprincipal=new JPanel();
        panelprincipal.setLayout(new BoxLayout(panelprincipal, BoxLayout.Y_AXIS));
        panelprincipal.add(ph);
        panelprincipal.add(panelLista);
        panelprincipal.add(pl, BorderLayout.CENTER);

        bh.addActionListener(this);
        bl.addActionListener(this);

        return panelprincipal;
    }

    public JPanel pestañaHotelFecha(){
        JPanel pf=new JPanel();
        pf.setLayout(new BoxLayout(pf, BoxLayout.Y_AXIS));

        JPanel pfl=new JPanel();
        pfl.setLayout(new FlowLayout(FlowLayout.LEFT));
        pfl.add(lfl=new JLabel("Localidad"));
        pfl.add(tfl=new JTextField(20));

        JPanel pff=new JPanel();
        pff.setLayout(new FlowLayout(FlowLayout.LEFT));
        pff.add(lf=new JLabel("Fecha(dd/MM/yyyy)"));
        pff.add(tf=new JTextField(10));

        JPanel pfh=new JPanel();
        pfh.setLayout(new FlowLayout(FlowLayout.LEFT));
        pfh.add(lfh=new JLabel("Nº habitaciones"));
        pfh.add(tfh=new JTextField(5));
        pfh.add(lfd=new JLabel("Nº dias"));
        pfh.add(tfd=new JTextField(5));

        JPanel patr=new JPanel();
        patr.setLayout(new BoxLayout(patr, BoxLayout.Y_AXIS));
        patr.add(pfl);
        patr.add(pff);
        patr.add(pfh);

        JPanel panelLista=new JPanel();
        lhf=new JList();
        panelLista.add(lhf, BorderLayout.CENTER);

        JPanel pal=new JPanel();
        pal.setLayout(new BoxLayout(pal, BoxLayout.X_AXIS));
        pal.add(patr);
        pal.add(panelLista);

        JPanel pb=new JPanel();
        pb.setLayout(new BoxLayout(pb, BoxLayout.X_AXIS));
        pb.add(bf=new JButton("Buscar"));
        pb.add(blf=new JButton("Limpiar"));

        pf.add(pal);
        pf.add(pb);

        bf.addActionListener(this);
        blf.addActionListener(this);
        pack();

        return pf;
    }

    private void fichaHotel(Hotel h){
        FichaH fh=new FichaH(this,h,false);

        fh.setVisible(true);
    }

    private void fichaHotelf(Hotel h,GregorianCalendar fecha, int numDias, int numHab){
        FichaH fh=new FichaH(this, h, cr, fecha, numDias, numHab, u.verNombre());

        fh.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==bh){
            String hotel=tn.getText();
            String localidad=tl.getText();
            
            if(hotel.equals("") && localidad.equals(""))
                hotel="";//lanzar mensaje
            else{
                if(hotel.equals("")){
                    hoteles = cr.buscarHotelLocalidad(localidad);
                    if(hoteles.size()==0)
                        JOptionPane.showMessageDialog(this, "No existe ningun hotel en la localidad","Atención!", JOptionPane.ERROR_MESSAGE);
                    else{
                        DefaultListModel modelo = new DefaultListModel();
                        for(int i=0;i<hoteles.size();i++)
                            modelo.addElement( hoteles.get(i).verNombre() );

                        lh.setModel(modelo);
                        pack();
                        MouseListener mouseListener = new MouseAdapter()
                        {
                            @Override
                            public void mouseClicked(MouseEvent e)
                            {
                                if (e.getClickCount() == 1) // Se mira si es doble click
                                {
                                    int posicion = lh.locationToIndex(e.getPoint());
                                    Hotel h=hoteles.get(posicion);
                                    fichaHotel(h);
                                 }
                            }
                        };
                        lh.addMouseListener(mouseListener);
                    }
                }else
                    if(localidad.equals(""))
                        localidad="";//lanzar mensaje
                    else{
                        Hotel h = null;
                        try {
                            h = cr.buscarHotel(hotel, localidad);
                        } catch (HotelNoExiste ex) {
                            JOptionPane.showMessageDialog(this, "El hotel no existe","Atención!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        fichaHotel(h);
                    }
            }
        }
         if(e.getSource()==bl){
             DefaultListModel modelo = new DefaultListModel();
             lh.setModel(modelo);

             tn.setText("");
             tl.setText("");
         }
        if(e.getSource()==blf){
             DefaultListModel modelo = new DefaultListModel();
             lhf.setModel(modelo);

             tfl.setText("");
             tf.setText("");
             tfh.setText("");
             tfd.setText("");
         }
        if(e.getSource()==bf){
            if(!(tfl.getText().equals("") || tf.getText().equals("") || tfh.getText().equals("") || tfd.getText().equals(""))){
                try {
                    String localidad = tfl.getText();
                    String fecha = tf.getText();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date f = sdf.parse(fecha);
                    final GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(f);
                    final int nhab = Integer.parseInt(tfh.getText());
                    final int ndias = Integer.parseInt(tfd.getText());

                    hotelesf = cr.buscarHotelLocalidad(localidad);
                    if(hotelesf.size()==0)
                        JOptionPane.showMessageDialog(this, "No existe ningun hotel en la localidad","Atención!", JOptionPane.ERROR_MESSAGE);
                    else{
                        GregorianCalendar cals;
                        int menor;
                        Vector color=new Vector();
                        for(int i=0;i<hotelesf.size();i++){
                            menor=hotelesf.get(i).verNumHab();
                            if(hotelesf.get(i).disponible(cal, ndias, nhab)){
                                cals=(GregorianCalendar) cal.clone();
                                for(int j=0;j<ndias;j++){
                                    if(hotelesf.get(i).habitacionesLibres(cals)<menor){
                                        menor=hotelesf.get(i).habitacionesLibres(cals);
                                    }
                                }
                                if((menor+nhab)==hotelesf.get(i).verNumHab())
                                    color.add("r");
                                else if((menor+nhab)>=(hotelesf.get(i).verNumHab()*(90/100)))
                                        color.add("n");
                                else color.add("b");
                            }
                        }

                        DefaultListModel modelo = new DefaultListModel();
                        for(int i=0;i<hotelesf.size();i++)
                            modelo.addElement( hotelesf.get(i).verNombre() );

                        lhf.setModel(modelo);
                        pack();
                        MouseListener mouseListener = new MouseAdapter()
                        {
                            @Override
                            public void mouseClicked(MouseEvent e)
                            {
                                if (e.getClickCount() == 1) // Se mira si es doble click
                                {
                                    int posicion = lhf.locationToIndex(e.getPoint());
                                    Hotel h=hotelesf.get(posicion);
                                    fichaHotelf(h,cal,ndias,nhab);
                                 }
                            }
                        };
                        lhf.addMouseListener(mouseListener);
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto","Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        if(e.getSource()==act){
            v=cr.consultaReserva(u.verNombre());
            ir.fireTableRowsInserted(0, v.size()-1);
        }
    }

    class InfoReservas extends AbstractTableModel{
        private final String[] nombreCols={"Identificador" , "Fecha Entrada", "Numero Dias", "Habitaciones", "Hotel", "Estado"};
        SimpleDateFormat sdf;

        public InfoReservas(){
            super();

            sdf=new SimpleDateFormat("dd.MM.yyyy");
        }

        @Override
        public String getColumnName(int column){
            return nombreCols[column];
        }

        public int getRowCount() {
            if(v.size()==0)
                return 0;
            else
                return v.size();
        }

        public int getColumnCount() {
            return 6;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Reserva r=(Reserva) v.get(rowIndex);
            switch(columnIndex){
                case 0: return r.verId();
                case 1: return sdf.format(r.verFecha().getTime());
                case 2: return r.verNumDias();
                case 3: return r.verNumHab();
                case 4: return r.verNombreHotel();
                case 5: GregorianCalendar cal=new GregorianCalendar();

                        if(r.fechaSalida().before(cal))
                            return "Finalizada";
                        else
                            return "Vigente";
            }
            return "";
        }
    }
}