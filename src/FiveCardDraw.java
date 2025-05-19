import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class FiveCardDraw extends PokerPadre{

    private JFrame ventanaDeJuego;
    private JLabel tablero;
    private JLabel menuDeAccion;
    private JLabel dineroJugador;
    private JLabel apuestaActual;
    private JLabel turnoJugador;
    private JLabel bote;

    
    private ArrayList<Carta> descartes;

    private int jugadorActual=0;
    private int dineroDelBote=0;
    private int evaluarApuestas=0;
    private int evaluarDescartes=0;
    private int jugadoresNoRendidos=numJugadores;

    private boolean primeraVez=true;
    private boolean primeraRonda=true;
    private boolean rondaDescartesB=false;
    private boolean segundaRondaApuesta=false;
    private boolean showdownB=false;

    private String fondoBoton="imagenes\\Fondos\\fondoBotones.PNG";

    public FiveCardDraw(int apuestaInicial, int numJugadores,int dineroInicial){
        super(apuestaInicial,numJugadores,dineroInicial);
        descartes = new ArrayList<>();
        mazo.shuffle();
        sistemaDeJuego();
    }

    public void reiniciarJuego(){
        dineroDelBote = 0;
        jugadorActual =0;
        primeraVez = true;
        evaluarApuestas =0;
    }

    public void sistemaDeJuego(){
        rondaRepartir(5, false);
        creacionDeJuego();
        rondaApuestas();
        actualizarCartas();
    }

    public void creacionDeJuego(){
        ImageIcon fondoBotonOriginal = new ImageIcon(fondoBoton);

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

        
        JButton regresar = new JButton("REGRESAR");
        regresar.setFont(new Font("Agency FB", Font.BOLD, 20));
        regresar.setBounds(20,20,
                        regresar.getPreferredSize().width, regresar.getPreferredSize().height);
        Image imagenRedimencionada = fondoBotonOriginal.getImage().getScaledInstance(
                            regresar.getPreferredSize().width, regresar.getPreferredSize().height, 
                            Image.SCALE_SMOOTH);
        ImageIcon fondoBotonRedimencionado= new ImageIcon(imagenRedimencionada);
        regresar.setForeground(Color.WHITE);
        regresar.setIcon(fondoBotonRedimencionado);
        regresar.setHorizontalTextPosition(JButton.CENTER);
        regresar.setVerticalTextPosition(JButton.CENTER);
        regresar.setVisible(true);
        ventanaDeJuego.add(regresar);

        regresar.addActionListener(e -> {
            ventanaDeJuego.dispose();;
        });

        turnoJugador = new JLabel("Turno del jugador "+(jugadorActual+1));
        turnoJugador.setFont(new Font("Agency FB", Font.BOLD, 40));
        turnoJugador.setBounds(ventanaDeJuego.getWidth()/3-turnoJugador.getPreferredSize().width/3, 
                        ventanaDeJuego.getHeight()/20, 
                        turnoJugador.getPreferredSize().width*2, 
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


        apuestaActual = new JLabel("Apuesta actual:"+apuesta);
        apuestaActual.setFont(new Font("Agency FB", Font.BOLD, 30));
        apuestaActual.setForeground(Color.WHITE);
        apuestaActual.setBounds(ventanaDeJuego.getWidth()/40,ventanaDeJuego.getHeight()/6,
                        apuestaActual.getPreferredSize().width*2,
                        apuestaActual.getPreferredSize().height);
        apuestaActual.setVisible(true);
        ventanaDeJuego.add(apuestaActual);

        dineroJugador = new JLabel("Fichas: "+jugadores.get(jugadorActual).getDinero());
        dineroJugador.setFont(new Font("Agency FB", Font.BOLD, 30));
        dineroJugador.setForeground(Color.WHITE);
        dineroJugador.setBounds(menuDeAccion.getWidth()/2-dineroJugador.getPreferredSize().width/2,
                        ventanaDeJuego.getHeight()/6,
                        dineroJugador.getPreferredSize().width,
                        dineroJugador.getPreferredSize().height);
        dineroJugador.setVisible(true);
        menuDeAccion.add(dineroJugador);

        ventanaDeJuego.add(menuDeAccion);

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
        pasar.setVisible(false);
        menuDeAccion.add(pasar);
        

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
        subir.setVisible(false);
        menuDeAccion.add(subir);
        subir.addActionListener(e->{
            String fichas = JOptionPane.showInputDialog("Subo la apuesta a");
            int fichasSubidas = Integer.parseInt(fichas);
            if(jugadores.get(jugadorActual).getDinero() >= fichasSubidas && fichasSubidas>apuesta){
                dineroDelBote+=fichasSubidas-jugadores.get(jugadorActual).getDineroApostado();
                jugadores.get(jugadorActual).setDinero(
                                        jugadores.get(jugadorActual).getDinero()
                                        -(fichasSubidas-jugadores.get(jugadorActual).getDineroApostado()));
                jugadores.get(jugadorActual).agregarDineroApostado(
                        fichasSubidas-jugadores.get(jugadorActual).getDineroApostado());
                apuesta=fichasSubidas;
                evaluarApuestas=0;
                pasarJugador();
            }else if(jugadores.get(jugadorActual).getDinero() < fichasSubidas){
                JOptionPane.showMessageDialog(null, "Ingreso mas dinero de lo que tiene", 
                                        "Poco dinero", JOptionPane.ERROR_MESSAGE); 
            }else if(apuesta > fichasSubidas){
                JOptionPane.showMessageDialog(null, "Deja de hacer trampa, ingrese las fichas de la apuesta", 
                                        "Trampa", JOptionPane.ERROR_MESSAGE); 
            }
        });

        JButton voltearCartas = new JButton("Voltear");
        voltearCartas.setForeground(Color.WHITE);
        voltearCartas.setFont(new Font("Agency FB", Font.BOLD, 30));
        voltearCartas.setBounds(208, 450, 
                        voltearCartas.getPreferredSize().width, 
                        voltearCartas.getPreferredSize().height);
        Image voltearCartasFondoImagen = fondoBotonOriginal.getImage().getScaledInstance(
                                            voltearCartas.getPreferredSize().width, 
                                            voltearCartas.getPreferredSize().height, 
                                            Image.SCALE_SMOOTH);           
        ImageIcon voltearCartasFondo = new ImageIcon(voltearCartasFondoImagen);
        voltearCartas.setIcon(voltearCartasFondo);
        voltearCartas.setVerticalTextPosition(JButton.CENTER);
        voltearCartas.setHorizontalTextPosition(JButton.CENTER);
        voltearCartas.setVisible(!primeraRonda);
        ventanaDeJuego.add(voltearCartas);
        voltearCartas.addActionListener(e->{
            JOptionPane.showMessageDialog(null, 
                "Al darle a descartar, las cartas boca arriba se descartaran", 
                "Tip", JOptionPane.INFORMATION_MESSAGE); 
            for(int i = 0; i<jugadores.get(jugadorActual).getCartas().size();i++)
            {
                Carta self = jugadores.get(jugadorActual).getCartaI(i);
                for (ActionListener al : self.getActionListeners()) {
                    self.removeActionListener(al);
                }
                self.addActionListener(a -> self.voltear());
            }
        });

        JButton descartar = new JButton("Descartar");
        descartar.setForeground(Color.WHITE);
        descartar.setFont(new Font("Agency FB", Font.BOLD, 30));
        descartar.setBounds(425, 450,
                        descartar.getPreferredSize().width, 
                        descartar.getPreferredSize().height);
        Image descartarFondoImagen = fondoBotonOriginal.getImage().getScaledInstance(
                                            descartar.getPreferredSize().width, 
                                            descartar.getPreferredSize().height, 
                                            Image.SCALE_SMOOTH);           
        ImageIcon descartarCartasFondo = new ImageIcon(descartarFondoImagen);
        descartar.setIcon(descartarCartasFondo);
        descartar.setVerticalTextPosition(JButton.CENTER);
        descartar.setHorizontalTextPosition(JButton.CENTER);
        ventanaDeJuego.add(descartar);
        descartar.setVisible(false);

        

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
        igualar.setVisible(false);
        menuDeAccion.add(igualar);

        descartar.addActionListener(e->{
            ArrayList<Carta> cartasDelJugador = jugadores.get(jugadorActual).getCartas();
            Iterator<Carta> it = cartasDelJugador.iterator();
            int cartasRemovidas=0;
            while (it.hasNext()) {
                Carta c = it.next();
                if (c.esMirable()) {
                    it.remove();
                    cartasRemovidas++;
                    descartes.add(c);
                }
            }
            jugadores.get(jugadorActual).añadirCartas(mazo.darNCartas(cartasRemovidas));
            pasarJugador();
            evaluarDescartes++;
            if(evaluarDescartes == jugadoresNoRendidos){
                descartar.setVisible(false);
                subir.setVisible(true);
                igualar.setVisible(true);
                segundaRondaApuesta=true;
                rondaDescartesB=false;
                primeraRonda=false;
            }
        });

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
        rendirse.setVisible(segundaRondaApuesta);
        menuDeAccion.add(rendirse);
        rendirse.addActionListener(e->{
            if(!primeraVez){
                jugadores.get(jugadorActual).cambioRendido();
                jugadoresNoRendidos--;
                buscarRendidos();
                pasarJugador();
            }
        });

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
        apostar.setVisible(primeraVez);
        menuDeAccion.add(apostar);
        apostar.addActionListener(e ->{
            String fichas = JOptionPane.showInputDialog("Ingrese la cantidad que quiere apostar");
            int fichasSubidas = Integer.parseInt(fichas);
            if(fichasSubidas<= jugadores.get(jugadorActual).getDinero() && fichasSubidas>0){
                jugadores.get(jugadorActual).setDinero(jugadores.get(jugadorActual).getDinero()-fichasSubidas);
                jugadores.get(jugadorActual).setDineroApostado(fichasSubidas);
                dineroDelBote+=fichasSubidas;
                apuesta=0;
                apuesta+=fichasSubidas;
                primeraVez=false;
                evaluarApuestas++;
                apostar.setVisible(false);
                pasar.setVisible(true);
                igualar.setVisible(true);
                subir.setVisible(true);

                pasarJugador();
            }else{
                JOptionPane.showMessageDialog(null, "Ingreso de cantidad equivocada", 
                                        "Cantidad equivocada", JOptionPane.ERROR_MESSAGE); 
            }
        });

        pasar.addActionListener(e ->{
            if(jugadores.get(jugadorActual).getDineroApostado() == apuesta || rondaDescartesB){
                System.out.println(segundaRondaApuesta);;
                if(segundaRondaApuesta){
                    evaluarApuestas++;
                    if(evaluarApuestas==jugadoresNoRendidos){
                       showdown();
                    }
                }
                if(rondaDescartesB){
                    evaluarDescartes++;
                    if(evaluarDescartes==jugadoresNoRendidos){
                        descartar.setVisible(false);
                        subir.setVisible(true);
                        igualar.setVisible(true);
                        segundaRondaApuesta=true;
                        rondaDescartesB=false;
                        primeraRonda=false;
                    }
                }
                pasarJugador();
            }else{
                JOptionPane.showMessageDialog(null, "NO haga trampa,"+ 
                                            "primero iguale la apuesta actual", 
                                        "Trampa", JOptionPane.ERROR_MESSAGE); 
            }
        });

        igualar.addActionListener(e ->{
            if(jugadores.get(jugadorActual).getDineroApostado()<apuesta){
                dineroDelBote+=apuesta-jugadores.get(jugadorActual).getDineroApostado();
                jugadores.get(jugadorActual).setDinero(
                                        jugadores.get(jugadorActual).getDinero()
                                        -(apuesta-jugadores.get(jugadorActual).getDineroApostado()));
                jugadores.get(jugadorActual).agregarDineroApostado(
                        apuesta-jugadores.get(jugadorActual).getDineroApostado());
                evaluarApuestas++;
                pasarJugador();
                if(evaluarApuestas==jugadoresNoRendidos){
                    for(int i =0; i<numJugadores;i++){
                        jugadores.get(i).setDineroApostado(0);
                    }
                    if(primeraRonda){
                        apuesta=0;
                        evaluarApuestas=0;
                        voltearCartas.setVisible(true);
                        rondaDescartesB=true;
                        descartar.setVisible(true);
                        igualar.setVisible(false);
                        subir.setVisible(false);
                        rendirse.setVisible(true);
                        apuestaActual.setText("Apuesta actual:"+apuesta);
                    }
                    if(segundaRondaApuesta){
                        showdown();
                    }
                }
            }
        });
        
        ventanaDeJuego.add(tablero);
        ventanaDeJuego.setResizable(false);
        ventanaDeJuego.setVisible(true);
    }

    @Override
    public void rondaApuestas(){
        
    }

    public void buscarRendidos(){
        Jugador ganador = null;
        if(jugadoresNoRendidos == 1){
            for(int i =0;i<numJugadores;i++){
                ganador=jugadores.get(i);
            }
        }
        if(ganador != null){
            JOptionPane.showMessageDialog(null, "El jugador: "+(jugadorActual+1)+" Es el ganador", 
                                    "Ganador", JOptionPane.ERROR_MESSAGE); 

            ventanaDeJuego.dispose();
        }
    }

    private void pasarJugador(){
        jugadorActual++;
        if(jugadorActual>=numJugadores){
            jugadorActual=0;
        }
        if(jugadores.get(jugadorActual).seRindio()){
            jugadorActual++;
        }
        bote.setText("Bote:"+dineroDelBote);
        apuestaActual.setText("Apuesta actual:"+apuesta);
        turnoJugador.setText("Turno del jugador: "+(jugadorActual+1));
        dineroJugador.setText("Fichas:"+jugadores.get(jugadorActual).getDinero());
        actualizarCartas();
    }

    public void actualizarCartas()
    {
        tablero.removeAll();
        for(int i = 0; i<jugadores.get(jugadorActual).manoSize(); i++)
        {
            Carta poner = jugadores.get(jugadorActual).getCartaI(i);
            poner.setBounds((i*CARDLENGHT)+100,250,CARDLENGHT,CARDHEIGHT);
            tablero.add(jugadores.get(jugadorActual).getCartaI(i));
        }
        tablero.revalidate();
        tablero.repaint();
    }

    @Override
    public void showdown(){
        for(int i = 0; i<jugadores.size(); i++)
        {
           
             mejorJugada(jugadores.get(jugadorActual));
            jugadorActual ++;
            nextJugador();
        }

        JOptionPane.showMessageDialog(null,"El ganador es el jugador "+(jugadorActual+1)+" ", "Ganador", JOptionPane.INFORMATION_MESSAGE);



    }
}
