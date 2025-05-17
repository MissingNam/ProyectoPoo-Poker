import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FiveCardDraw extends PokerPadre{

    private JFrame ventanaDeJuego;
    private JLabel tablero;
    private JLabel menuDeAccion;
    private JLabel dineroJugador;
    private JLabel ronda;
    private JLabel apuestaActual;
    private JLabel turnoJugador;
    private JLabel bote;

    private int jugadorActual=0;
    private int dineroDelBote=0;
    private int evaluarApuestas=0;

    private boolean primeraVez=true;

    public FiveCardDraw(int apuestaInicial, int numJugadores,int dineroInicial){
        super(apuestaInicial,numJugadores,dineroInicial);
        creacionDeJuego();
    }

    public void creacionDeJuego(){
        ventanaDeJuego = new JFrame("Five Card Draw");
        ventanaDeJuego.setSize(1080, 600);
        ventanaDeJuego.setLayout(null);
        

        ImageIcon fondoTablero = new ImageIcon("imagenes\\Fondos\\mesaPoker.jpg");
        tablero = new JLabel(fondoTablero);
        tablero.setBounds(0, 0, 790, 600);
        tablero.setLayout(null);
        ventanaDeJuego.add(tablero);


        ImageIcon fondoMesa = new ImageIcon("imagenes\\Fondos\\mesaMadera.jpg");
        menuDeAccion = new JLabel(fondoMesa);
        menuDeAccion.setBounds(790, 0, 290, 600);
        menuDeAccion.setLayout(null);




        ventanaDeJuego.add(tablero);
        ventanaDeJuego.add(menuDeAccion);

        
        ventanaDeJuego.setVisible(true);
    }

    @Override
    public void rondaApuestas(){

    }

    @Override
    public void showdown(){

    }
}
