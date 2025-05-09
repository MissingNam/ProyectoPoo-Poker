import java.awt.Font;

import javax.swing.*;

public class main {
    //cosas para usar
    private static JButton inicio = new JButton("Jugar");
    private static JFrame frame = new JFrame();
    private static JComboBox<String> modoJuego = new JComboBox<>();
    private static JTextField apuestaInicial = new JTextField("1");
    private static JTextField dineroInicial = new JTextField("20");

    public static void main(String[] args) {
        // grafico de inicio para solicitar todo

        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500,250);
        frame.setLayout(null);

        JLabel titulo = new JLabel("POKER");
        titulo.setFont(new Font("Agency FB",Font.BOLD,48));
        titulo.setBounds(175,0,200,50);
        frame.add(titulo);

        // Combo box de modo de Juego
       // String[] combo = {"5 Card", "7 Card - Stud"};
       
        modoJuego.addItem("5 Card");
        modoJuego.addItem("7 Card - Stud");
        
        modoJuego.setBounds(25,150,100,25);
        frame.add(modoJuego);

        // TextFields para apuesta y dinero inicial
        
        apuestaInicial.setBounds(300,150,150,25);
        frame.add(apuestaInicial);

        
        dineroInicial.setBounds(300,100,150,25);
        frame.add(dineroInicial);

        
        inicio.setBounds(150,150,100,25);
        inicio.addActionListener(a -> botonAccion());
        frame.add(inicio);

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
        } else {
            JOptionPane.showMessageDialog(null, "Revise las Entradas", "Poker", JOptionPane.ERROR_MESSAGE);
        }
    }

    
}
