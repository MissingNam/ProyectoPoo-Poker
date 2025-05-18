import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FiveCardDraw extends PokerPadre{

    private JFrame ventanaDeJuego;
    private JLabel tablero;
    private JLabel menuDeAccion;
    private JLabel dineroJugador;
    private JLabel apuestaActual;
    private JLabel turnoJugador;
    private JLabel bote;

    private int jugadorActual=0;
    private int dineroDelBote=0;
    private int evaluarApuestas=0;

    private boolean primeraVez=true;

    private String fondoBoton="imagenes\\Fondos\\fondoBotones.PNG";

    public FiveCardDraw(int apuestaInicial, int numJugadores,int dineroInicial){
        super(apuestaInicial,numJugadores,dineroInicial);
        mazo.shuffle();
        rondaRepartir(5, true);
        creacionDeJuego();
        rondaApuestas();
        actualizarPanelJuego();
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

        ImageIcon fondoBoton = new ImageIcon(this.fondoBoton);
        
        JButton regresar = new JButton("REGRESAR");
        regresar.setFont(new Font("Agency FB", Font.BOLD, 20));
        regresar.setBounds(20,20,
                        regresar.getPreferredSize().width, regresar.getPreferredSize().height);
        Image imagenRedimencionada = fondoBoton.getImage().getScaledInstance(
                            regresar.getPreferredSize().width, regresar.getPreferredSize().height, 
                            Image.SCALE_SMOOTH);
        ImageIcon fondoBotonRedimencionado= new ImageIcon(imagenRedimencionada);
        regresar.setForeground(Color.WHITE);
        regresar.setIcon(fondoBotonRedimencionado);
        regresar.setHorizontalTextPosition(JButton.CENTER);
        regresar.setVerticalTextPosition(JButton.CENTER);
        regresar.setVisible(true);
        ventanaDeJuego.add(regresar);

        turnoJugador = new JLabel("Turno del jugador "+(jugadorActual+1));
        turnoJugador.setFont(new Font("Agency FB", Font.BOLD, 40));
        turnoJugador.setBounds(ventanaDeJuego.getWidth()/3-turnoJugador.getPreferredSize().width/3, 
                        ventanaDeJuego.getHeight()/20, 
                        turnoJugador.getPreferredSize().width, 
                        turnoJugador.getPreferredSize().height);
        turnoJugador.setForeground(Color.WHITE);
        turnoJugador.setVisible(true);
        ventanaDeJuego.add(turnoJugador);

        ImageIcon boteOriginal = new ImageIcon("imagenes\\Fondos\\fondoBote.png");
        bote = new JLabel("Bote: "+ dineroDelBote);
        bote.setFont(new Font("Agency FB", Font.BOLD, 30));
        bote.setBounds(menuDeAccion.getWidth()/2-bote.getPreferredSize().width/2-30,
                ventanaDeJuego.getHeight()/20,
                bote.getPreferredSize().width*3,
                bote.getPreferredSize().height);  
        bote.setForeground(Color.WHITE); 
        Image boteImagen = boteOriginal.getImage().getScaledInstance(
            50, 
            bote.getPreferredSize().height, Image.SCALE_SMOOTH);
        ImageIcon boteRedimencionado = new ImageIcon(boteImagen);
        bote.setIcon(boteRedimencionado);
        bote.setVerticalTextPosition(JLabel.CENTER);
        bote.setHorizontalTextPosition(JLabel.RIGHT);
        bote.setVisible(true);
        menuDeAccion.add(bote);


        apuestaActual = new JLabel("Apuesta actual:"+evaluarApuestas);
        apuestaActual.setFont(new Font("Agency FB", Font.BOLD, 30));
        apuestaActual.setForeground(Color.WHITE);
        apuestaActual.setBounds(ventanaDeJuego.getWidth()/40,ventanaDeJuego.getHeight()/6,
                        apuestaActual.getPreferredSize().width,
                        apuestaActual.getPreferredSize().height);
        apuestaActual.setVisible(true);
        ventanaDeJuego.add(apuestaActual);

        dineroJugador = new JLabel("Fichas: 0");
        dineroJugador.setFont(new Font("Agency FB", Font.BOLD, 30));
        dineroJugador.setForeground(Color.WHITE);
        dineroJugador.setBounds(menuDeAccion.getWidth()/2-dineroJugador.getPreferredSize().width/2,
                        ventanaDeJuego.getHeight()/6,
                        dineroJugador.getPreferredSize().width,
                        dineroJugador.getPreferredSize().height);
        dineroJugador.setVisible(true);
        menuDeAccion.add(dineroJugador);

        ventanaDeJuego.add(tablero);
        ventanaDeJuego.add(menuDeAccion);
        
        ventanaDeJuego.setResizable(false);
        ventanaDeJuego.setVisible(true);
    }

    @Override
    public void rondaApuestas(){
        ImageIcon fondoBotonOriginal = new ImageIcon("imagenes\\Fondos\\fondoBotones.png");
        JButton pasar = new JButton("Pasar");
        pasar.setForeground(Color.WHITE);
        pasar.setFont(new Font("Agency FB", Font.BOLD, 30));
        pasar.setBounds(menuDeAccion.getWidth()/2-pasar.getPreferredSize().width/2, 
                        ventanaDeJuego.getHeight()*2/7-pasar.getPreferredSize().height/2, 
                        pasar.getPreferredSize().width, 
                        pasar.getPreferredSize().height);
        Image pasarFondoImagen = fondoBotonOriginal.getImage().getScaledInstance(
                                            pasar.getPreferredSize().width, 
                                            pasar.getPreferredSize().height, 
                                            Image.SCALE_SMOOTH); 
        ImageIcon pasarFondo = new ImageIcon(pasarFondoImagen);
        pasar.setIcon(pasarFondo);
        pasar.setVerticalTextPosition(JButton.CENTER);
        pasar.setHorizontalTextPosition(JButton.CENTER);
        menuDeAccion.add(pasar);
        pasar.setVisible(true);

        JButton subir = new JButton("Subir");
        subir.setForeground(Color.WHITE);
        subir.setFont(new Font("Agency FB", Font.BOLD, 30));
        subir.setBounds(menuDeAccion.getWidth()/2-subir.getPreferredSize().width/2,
                        ventanaDeJuego.getHeight()*3/7-subir.getPreferredSize().height/2, 
                        subir.getPreferredSize().width, 
                        subir.getPreferredSize().height);
        Image subirFondoImagen = fondoBotonOriginal.getImage().getScaledInstance(
                                            subir.getPreferredSize().width, 
                                            subir.getPreferredSize().height, 
                                            Image.SCALE_SMOOTH);
        ImageIcon subirFondo = new ImageIcon(subirFondoImagen);
        subir.setIcon(subirFondo);
        subir.setVerticalTextPosition(JButton.CENTER);
        subir.setHorizontalTextPosition(JButton.CENTER);
        subir.setVisible(true);
        menuDeAccion.add(subir);


        JButton igualar = new JButton("Igualar");
        igualar.setForeground(Color.WHITE);
        igualar.setFont(new Font("Agency FB", Font.BOLD, 30));
        igualar.setBounds(menuDeAccion.getWidth()/2-igualar.getPreferredSize().width/2, 
                        ventanaDeJuego.getHeight()*4/7-subir.getPreferredSize().height/2, 
                        igualar.getPreferredSize().width, 
                        igualar.getPreferredSize().height);
        Image igualarFondoImagen = fondoBotonOriginal.getImage().getScaledInstance(
                                                igualar.getPreferredSize().width, 
                                                igualar.getPreferredSize().height, 
                                                Image.SCALE_SMOOTH);
        ImageIcon igualarFondo = new ImageIcon(igualarFondoImagen);
        igualar.setIcon(igualarFondo);
        igualar.setVerticalTextPosition(JButton.CENTER);
        igualar.setHorizontalTextPosition(JButton.CENTER);
        igualar.setVisible(true);
        menuDeAccion.add(igualar);



        JButton apostar = new JButton("Apostar");
        apostar.setForeground(Color.WHITE);
        apostar.setFont(new Font("Agency FB", Font.BOLD, 30));
        apostar.setBounds(menuDeAccion.getWidth()/2-apostar.getPreferredSize().width/2, 
                        ventanaDeJuego.getHeight()*5/7-apostar.getPreferredSize().height/2, 
                        apostar.getPreferredSize().width, 
                        apostar.getPreferredSize().height);
        Image apostarFondoImagen = fondoBotonOriginal.getImage().getScaledInstance(
                                            apostar.getPreferredSize().width, 
                                            apostar.getPreferredSize().height, 
                                            Image.SCALE_SMOOTH);           
        ImageIcon apostarFondo = new ImageIcon(apostarFondoImagen);
        apostar.setIcon(apostarFondo);
        apostar.setVerticalTextPosition(JButton.CENTER);
        apostar.setHorizontalTextPosition(JButton.CENTER);
        apostar.setVisible(true);
        menuDeAccion.add(apostar);


        JButton rendirse = new JButton("Rendirse");
        rendirse.setForeground(Color.WHITE);
        rendirse.setFont(new Font("Agency FB", Font.BOLD, 30));
        rendirse.setBounds(menuDeAccion.getWidth()/2-rendirse.getPreferredSize().width/2, 
                            ventanaDeJuego.getHeight()*6/7-rendirse.getPreferredSize().height/2, 
                            rendirse.getPreferredSize().width, 
                            rendirse.getPreferredSize().height);
        Image rendirseFondoImagen = fondoBotonOriginal.getImage().getScaledInstance(
                                                    rendirse.getPreferredSize().width, 
                                                    rendirse.getPreferredSize().height, 
                                                    Image.SCALE_SMOOTH);
        ImageIcon rendirseFondo = new ImageIcon(rendirseFondoImagen);
        rendirse.setIcon(rendirseFondo);
        rendirse.setVerticalTextPosition(JButton.CENTER);
        rendirse.setHorizontalTextPosition(JButton.CENTER);
        rendirse.setVisible(true);
        menuDeAccion.add(rendirse);

        menuDeAccion.revalidate();
        menuDeAccion.repaint();
    }

    public void actualizarPanelJuego()
    {
        for(int i = 0; i<jugadores.get(jugadorActual).manoSize(); i++)
        {
            Carta poner = jugadores.get(jugadorActual).getCartaI(i);
            poner.setBounds((i*CARDLENGHT)+100,250,CARDLENGHT,CARDHEIGHT);
            tablero.add(jugadores.get(jugadorActual).getCartaI(i));
        }

    }

    @Override
    public void showdown(){

    }
}
