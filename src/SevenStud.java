import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;

import javax.swing.*;



public class SevenStud extends PokerPadre
{
    private int jugadorActual = 0;

    // cosas para lo grafico
    JFrame frame = new JFrame();
    JPanel panelInfo = new JPanel();
    JPanel panelJuego = new JPanel();
    JLabel rondaActual = new JLabel("Third-Street");
    JLabel dineroJugadorActual = new JLabel();
    JLabel dineroAcumulado = new JLabel();
    JLabel apuestaActual = new JLabel();
    int dineroBanca = 0;

    public SevenStud(int apuestaInicial, int numJugadores,int dineroInicial)
    {
        super(apuestaInicial,numJugadores,dineroInicial);
        // darle las cartas a los jugadores al inicio del juego
        mazo.shuffle();
        for(Jugador a : jugadores)
        {
            a.setDinero(a.getDinero()-apuesta);
            dineroBanca += apuesta;
            a.añadirCarta(mazo.darCarta());
            a.añadirCarta(mazo.darCarta());
            a.añadirCarta(mazo.darCarta());
            a.getCartaI(2).voltear();
        }

        for(int i = 0; i<jugadores.get(0).manoSize(); i++)
        {
            System.out.println(jugadores.get(0).getCartaI(i));
        }

        crearGrafico();

        thirdStreet();
    }

    public void rondaApuestas()
    {
        int fin = 0;
        // evaluar que no haya terminado la ronda de apuestas
        for(int i = 0; i<jugadores.size(); i++)
        {
            if(jugadores.get(i).seRindio() || jugadores.get(i).igualoApuesta())
            {
                fin++;
            }
        }

        // si este if se ejecuta, es porque el jugador no se ha rendido o 
        // algun jugador no ha alcanzado la apuesta
        if(fin < jugadores.size() && !jugadores.get(jugadorActual).seRindio() && !jugadores.get(jugadorActual).igualoApuesta())
        {
            JButton igualar = new JButton("Igualar");
            JButton aumentar = new JButton("Aumentar");
            JButton rendirse = new JButton("Rendirse");
            JButton bringIn = new JButton("Bring-In");

            actualizarPanelJuego();
            actualizarLabels();
            // poner las cosas
        
            if(rondaActual.getText().equals("Third Street") && jugadorActual == 0)
            {
                //boton bring-in
                bringIn.addActionListener( a -> {
                    apuesta = jugadores.get(jugadorActual).getCartaI(2).getCategoria();
                    jugadores.get(jugadorActual).setDinero(jugadores.get(jugadorActual).getDinero() - apuesta);
                    dineroBanca += apuesta;
                    igualar.setEnabled(true);
                    jugadores.get(jugadorActual).cambioAlcanzoApuesta();
                    jugadorActual++;
                    nextJugador();
                    rondaApuestas();
                    
                });
                bringIn.setBounds(450,75,100,25);
                igualar.setEnabled(false);
                panelJuego.add(bringIn);
            }  else {
                igualar.setEnabled(true);
            }

            // colocar boton Igualar y su funcionamiento
            igualar.addActionListener(a -> {
                if(jugadores.get(jugadorActual).getDinero() > apuesta)
                {
                    jugadores.get(jugadorActual).setDinero(jugadores.get(jugadorActual).getDinero() - apuesta);
                    dineroBanca += apuesta;
                    jugadores.get(jugadorActual).cambioAlcanzoApuesta();
                    jugadorActual ++;
                    nextJugador();
                    actualizarLabels();
                    rondaApuestas();
                } else {
                    jugadores.get(jugadorActual).cambioRendido();
                    JOptionPane.showMessageDialog(null, "El Jugador no tiene suficiente dinero", "Poker", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            igualar.setBounds(450,50,100,25);
            panelJuego.add(igualar);

            // boton para aumentar la apuesta
            aumentar.setBounds(450,100,100,25);
            aumentar.addActionListener(a -> {
                //crear el slider para el JOptionPane
                int aumento;
                JSlider slider = new JSlider(1,jugadores.get(jugadorActual).getDinero(),1);
                slider.setMajorTickSpacing(10);
                slider.setPaintTicks(true);
                slider.setPaintTrack(true);
                // obtener el valor del slider 
                int opcion = JOptionPane.showConfirmDialog(null, slider,"Seleccione un valor",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(opcion == JOptionPane.OK_OPTION)
                {
                    aumento = slider.getValue();
                } else {
                    aumento = 1;
                }


                // aumentar el valor a la apuesta
                apuesta = apuesta+aumento;
                jugadores.get(jugadorActual).setDinero(jugadores.get(jugadorActual).getDinero()-apuesta);
                jugadores.stream().forEach(jug -> jug.desIgualarApuesta()); 
                jugadores.get(jugadorActual).igualoApuesta();
                jugadorActual++;
                nextJugador();
                actualizarLabels();
                rondaApuestas();
            });
            panelJuego.add(aumentar);



            // boton de rendirse
            rendirse.setBounds(450,120,100,25);
            rendirse.addActionListener(a -> {
                jugadores.get(jugadorActual).cambioRendido();
                jugadorActual++;
                nextJugador();
                rondaApuestas();
            });

            panelJuego.add(rendirse);

        } else if(fin >= jugadores.size())
        {
            JOptionPane.showMessageDialog(null, "Se acabaron las apuestas", "Poker", JOptionPane.INFORMATION_MESSAGE);

        } else if(jugadores.get(jugadorActual).seRindio()) 
        {
            JOptionPane.showMessageDialog(null, "El jugador se rindio", "Poker", JOptionPane.INFORMATION_MESSAGE);
            jugadorActual ++;
            nextJugador();
            rondaApuestas();
        } else if (jugadores.get(jugadorActual).igualoApuesta())
        {
            JOptionPane.showMessageDialog(null, "El jugador ya igualo la apuesta", "Poker", JOptionPane.INFORMATION_MESSAGE);
            jugadorActual ++;
            nextJugador();
            rondaApuestas();
        }




        

    }

    public void rondaRepartir()
    {

    }

    public void showdown()
    {

    }
    
    public Jugador mejorJugada()
    {
        Jugador ganador = jugadores.get(0);

        return ganador;
    }


    public void crearGrafico()
    {
        // todo lo relacionado al frame y paneles
        frame.setSize(1000,500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        
        panelJuego.setBounds(0,0,750,500);
        panelJuego.setBackground(Color.GREEN);
        
        panelInfo.setBounds(750,0,250,500);
        panelInfo.setBackground(Color.GRAY);
        panelInfo.setLayout(null);
        mazo.getCartaI(0).setBounds(75,250,CARDLENGHT,CARDHEIGHT);
        panelInfo.add(mazo.getCartaI(0));

        rondaActual.setFont(new Font("Agency FB",Font.BOLD,48));
        rondaActual.setBounds(10,0,250,100);
        dineroJugadorActual.setText("Dinero restante: "+jugadores.get(jugadorActual).getDinero()+"$");
        dineroJugadorActual.setBounds(10,100,250,100);
        panelInfo.add(dineroJugadorActual);
        panelInfo.add(rondaActual);


        //Panel de Juego
        panelJuego.setLayout(null);
        dineroAcumulado.setText("Banca: "+ dineroBanca + " $");
        dineroAcumulado.setFont(new Font("Agency FB",Font.BOLD,48));
        dineroAcumulado.setBounds(200,0,250,100);
        apuestaActual.setText("Apuesta Actual: "+apuesta+" $");
        apuestaActual.setBounds(200,100,250,25);
        panelJuego.add(dineroAcumulado);
        panelJuego.add(apuestaActual);

        frame.add(panelJuego);
        frame.add(panelInfo);
        frame.setVisible(true);
    }


    public void thirdStreet()
    {
        rondaActual.setText("Third Street");
        ponerAlPeorAlInicio();
        rondaApuestas();

    }

    public void actualizarPanelJuego()
    {
        panelJuego.removeAll();

        panelJuego.add(dineroAcumulado);
        panelJuego.add(apuestaActual);

        for(int i = 0; i<jugadores.get(jugadorActual).manoSize(); i++)
        {
            Carta poner = jugadores.get(jugadorActual).getCartaI(i);
            poner.setBounds((i*CARDLENGHT)+10,200,CARDLENGHT,CARDHEIGHT);
            panelJuego.add(jugadores.get(jugadorActual).getCartaI(i));
        }
        frame.setVisible(true);
    }


    public void actualizarLabels()
    {
        dineroJugadorActual.setText("Dinero restante: "+jugadores.get(jugadorActual).getDinero()+"$");
        dineroAcumulado.setText("Banca: "+dineroBanca+" $");
        apuestaActual.setText("Apuesta Actual: "+apuesta+" $");
        frame.setVisible(true);

    }

    public void ponerAlPeorAlInicio()
    {
        Iterator<Jugador> iterador = jugadores.iterator();
        Carta menor = iterador.next().getCartaI(2);
        int indexPeor = 0;
        int repeticiones = -1;
        while(iterador.hasNext())
        {
            repeticiones ++;
            Jugador evaluar = iterador.next();
            if(menor.getCategoria() > evaluar.getCartaI(2).getCategoria())
            {
                menor = evaluar.getCartaI(2);
                indexPeor = repeticiones;
            } else if(menor.getCategoria() == evaluar.getCartaI(2).getCategoria()){
                // Trebol > Diamente > Corazon > Pica
                int a;
                int b;
                if(menor.getPalo() == "Trebol")
                {
                    a = 4;
                } else if(menor.getPalo() == "Diamante")
                {
                    a = 3;
                } else if(menor.getPalo() == "Corazon")
                {
                    a = 2;
                } else {
                    a = 1;
                } 

                if(evaluar.getCartaI(2).getPalo() == "Trebol")
                {
                    b = 4;
                } else if(evaluar.getCartaI(2).getPalo() == "Diamante")
                {
                    b = 3;
                } else if(evaluar.getCartaI(2).getPalo() == "Corazon")
                {
                    b = 2;
                } else {
                    b = 1;
                } 

                if(b < a)
                {
                    menor = evaluar.getCartaI(2);
                    indexPeor = repeticiones;
                }


            }
        }
        // colocar al inicio del arrayLits
        Jugador peor = jugadores.remove(indexPeor);
        jugadores.add(0,peor);


    }


    public void nextJugador()
    {
        if(jugadorActual >= jugadores.size())
        {
            jugadorActual = 0;
        }
    }

}