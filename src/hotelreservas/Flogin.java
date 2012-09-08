/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hotelreservas;

import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Administrador
 */
public class Flogin extends JDialog implements ActionListener {
    JTextField tfu;
    JTextField tfp;
    JButton bl,bs;
    String nombre,clave;
    Usuario u;
    CentralReservas cr;
    boolean operar,salir;

    class FloginWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e){
            salir=true;
        }
    }


    public Flogin(JFrame f, CentralReservas c){
        super(f, "Login Usuario", true);
        cr=c;

        JPanel p=new JPanel();
        JLabel l1=new JLabel("Usuario");
        tfu=new JTextField(10);
        JLabel l2=new JLabel("Password");
        tfp=new JPasswordField(10);
        //p.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        p.add(l1);
        p.add(tfu);
        p.add(l2);
        p.add(tfp);

        JPanel pb=new JPanel();
        pb.add(bl=new JButton("Login"));
        pb.add(bs=new JButton("Salir"));

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(p);
        add(pb);
        pack();

        bl.addActionListener(this);
        bs.addActionListener(this);

        nombre=null;
        clave=null;
        u=null;
        operar=false;
        salir=false;

        addWindowListener(new FloginWindowListener());
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==bl){
            nombre=tfu.getText();
            clave=tfp.getText();
            u = cr.registro(nombre, clave);
            if(u==null){
                JOptionPane.showMessageDialog(this, "Usuario y password incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                tfu.setText("");
                tfp.setText("");
                setVisible(true);
            }else{
                hide();
                operar=true;
            }
        }

        if(e.getSource()==bs){
            salir=true;
            hide();
        }
    }

    public Usuario leerUsuario(){
        return u;
    }

    public boolean realizarOperacion() {
        return operar;
    }

    public boolean salirAplicacion() {
        return salir;
    }
}