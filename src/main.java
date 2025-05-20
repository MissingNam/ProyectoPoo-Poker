import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;

import javax.swing.*;

public class main {
    //cosas para usar
    private static JButton inicio = new JButton("Jugar");
    private static JFrame frame = new JFrame();
    private static JComboBox<String> modoJuego = new JComboBox<>();
    private static JTextField apuestaInicial = new JTextField("1");
    private static JTextField dineroInicial = new JTextField("200");

    public static void main(String[] args) {
        // grafico de inicio para solicitar todo

        ImageIcon fondoBoton = new ImageIcon("imagenes\\Fondos\\fondoBotones.png");

        ImageIcon fondoInicio=new ImageIcon("imagenes\\Fondos\\mesaPoker.jpg");
        Image fondoImagen = fondoInicio.getImage().getScaledInstance(500,500, Image.SCALE_SMOOTH);
        ImageIcon fondoRedimencionado = new ImageIcon(fondoImagen);
        JLabel fondo = new JLabel();
        fondo.setBounds(0, 0, 500, 250);
        fondo.setIcon(fondoRedimencionado);
        fondo.setVisible(true);
        frame.add(fondo);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500,250);
        frame.setLayout(null);

        JLabel titulo = new JLabel("POKER");
        titulo.setFont(new Font("Agency FB",Font.BOLD,48));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(250-titulo.getPreferredSize().width/2,0,200,50);
        fondo.add(titulo);

        // Combo box de modo de Juego
       // String[] combo = {"5 Card", "7 Card - Stud"};
       
        modoJuego.addItem("5 Card");
        modoJuego.addItem("7 Card - Stud");
        
        modoJuego.setBounds(250-modoJuego.getPreferredSize().width/2,100,100,25);
        fondo.add(modoJuego);

        // TextFields para apuesta y dinero inicial

        JLabel apuesta = new JLabel("Apuesta");
        apuesta.setForeground(Color.WHITE);
        apuesta.setFont(new Font("Agency FB",Font.BOLD,20));
        apuesta.setBounds(350,70,100,25);
        apuesta.setVisible(true);
        fondo.add(apuesta);
        
        apuestaInicial.setBounds(350,100,100,25);
        fondo.add(apuestaInicial);

        JLabel dinero = new JLabel("Dinero");
        dinero.setForeground(Color.WHITE);
        dinero.setFont(new Font("Agency FB",Font.BOLD,20));
        dinero.setBounds(50,70,100,25);
        dinero.setVisible(true);
        fondo.add(dinero);

        dineroInicial.setBounds(50,100,100,25);
        fondo.add(dineroInicial);

        Image fondoBotonImagen = fondoBoton.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon botonImageIcon = new ImageIcon(fondoBotonImagen);
        inicio.setBounds(250-50,150,100,25);
        inicio.addActionListener(a -> botonAccion());
        inicio.setIcon(botonImageIcon);
        inicio.setVerticalTextPosition(JButton.CENTER);
        inicio.setHorizontalTextPosition(JButton.CENTER);
        fondo.add(inicio);

        frame.setVisible(true);

    }

    public static void botonAccion()
    {
        Integer apuesta = Integer.parseInt(apuestaInicial.getText());
        Integer dinero = Integer.parseInt(dineroInicial.getText());

        if(apuesta != null && dinero != null && dinero > apuesta)
        {
            if(modoJuego.getSelectedItem() == "5 Card")
            {
                String nJugadoresPane = JOptionPane.showInputDialog("Ingrese la Cantidad de Jugadores 2-7");
                int nJugadores = Integer.parseInt(nJugadoresPane);
                if(nJugadores <= 7 && nJugadores >= 2)
                {
                    FiveCardDraw juego = new FiveCardDraw(apuesta, nJugadores,dinero); 
                } else {
                    JOptionPane.showMessageDialog(null, "Revise las Entradas", "Poker", JOptionPane.ERROR_MESSAGE);  
                }
            } else {

                iniciarSevenStud(apuesta, dinero);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Revise las Entradas", "Poker", JOptionPane.ERROR_MESSAGE);
        }
    }





    public static void iniciarSevenStud(int apuesta, int dinero)
    {
        File archive = new File("C:\\Games\\partida7Stud.txt");
        if(archive.exists())
        {
            int opcion = JOptionPane.showConfirmDialog(null,"Ya existe una partida, quieres Cargarla?","Poker",JOptionPane.YES_NO_OPTION);

            if(opcion == JOptionPane.NO_OPTION)
            {
                archive.delete();
                String nJugadoresPane = JOptionPane.showInputDialog("Ingrese la Cantidad de Jugadores 2-8");
                int nJugadores = Integer.parseInt(nJugadoresPane);
                if(nJugadores <= 8 && nJugadores >= 2)
                {
                    SevenStud juego = new SevenStud(apuesta, nJugadores,dinero); 
                } else {
                    JOptionPane.showMessageDialog(null, "Revise las Entradas", "Poker", JOptionPane.ERROR_MESSAGE);  
                }
            }  else {
                SevenStud juego = new SevenStud(0,0,0);
            }

        } else {

                String nJugadoresPane = JOptionPane.showInputDialog("Ingrese la Cantidad de Jugadores 2-8");
                int nJugadores = Integer.parseInt(nJugadoresPane);
                if(nJugadores <= 8 && nJugadores >= 2)
                {
                    SevenStud juego = new SevenStud(apuesta, nJugadores,dinero); 
                } else {
                    JOptionPane.showMessageDialog(null, "Revise las Entradas", "Poker", JOptionPane.ERROR_MESSAGE);  
                }
        }
    }

    
}
