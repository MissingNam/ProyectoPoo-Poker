import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;

import javax.swing.*;



public class SevenStud extends PokerPadre
{
    

    // cosas para lo grafico
    private JFrame frame = new JFrame();
    private JPanel panelInfo = new JPanel();
    private JPanel panelJuego = new JPanel();
    private JLabel rondaActual = new JLabel("Third-Street");
    private JLabel dineroJugadorActual = new JLabel();
    private JLabel dineroAcumulado = new JLabel();
    private JLabel apuestaActual = new JLabel();
    private JLabel jugadorTurno = new JLabel();

    // cosas donde se guarda informacion importante
    private int dineroBanca = 0;
    private int jugadorActual = 0;

    //banderas, 
    private int primeraVez = 0;
    private int todosEvaluados = 0;

    public SevenStud(int apuestaInicial, int numJugadores,int dineroInicial)
    {
        super(apuestaInicial,numJugadores,dineroInicial);
        // darle las cartas a los jugadores al inicio del juego
        mazo.shuffle();
        Set<Integer> llaves = jugadores.keySet();
        for(Integer b : llaves)
        {
            Jugador a = jugadores.get(b);
            a.setDinero(a.getDinero()-apuesta);
            dineroBanca += apuesta;
            a.añadirCarta(mazo.darCarta());
            a.añadirCarta(mazo.darCarta());
            a.añadirCarta(mazo.darCarta());
            a.getCartaI(2).voltear();
            // darle la chance a la carta 0 y 1 de voltearse
            Carta self = a.getCartaI(0);
            self.addActionListener(s -> self.voltear());
            Carta self2 = a.getCartaI(1);
            self2.addActionListener(s -> self2.voltear());
        }

        for(int i = 0; i<jugadores.get(0).manoSize(); i++)
        {
            System.out.println(jugadores.get(0).getCartaI(i));
        }

        crearGrafico();
        thirdStreet();
    }


    // aqui se realizan las apuestas, se repasa a todos los jugadores y se les pregunta que quieren hacer
    public void rondaApuestas()
    {
        int fin = 0;
        // evaluar que no haya terminado la ronda de apuestas
        for(int i = 0; i<numJugadores; i++)
        {
            if(jugadores.get(i).seRindio() || jugadores.get(i).igualoApuesta())
            {
                fin++;
            }
        }
        

        // si este if se ejecuta, es porque el jugador no se ha rendido o 
        // algun jugador no ha alcanzado la apuesta
        if(fin < numJugadores && !jugadores.get(jugadorActual).seRindio() && !jugadores.get(jugadorActual).igualoApuesta())
        {
            JButton igualar = new JButton("Igualar");
            JButton aumentar = new JButton("Aumentar");
            JButton rendirse = new JButton("Rendirse");
            JButton bringIn = new JButton("Bring-In");

            actualizarPanelJuego();
            actualizarLabels();
            // poner las cosas
        
            if(rondaActual.getText().equals("Third Street") && primeraVez == 0)
            {
                primeraVez = 1;
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
                Set<Integer> llaves = jugadores.keySet();
                llaves.stream().forEach(i -> jugadores.get(i).desIgualarApuesta());
                //jugadores.stream().forEach(jug -> jug.desIgualarApuesta()); 
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

        } else if(fin >= numJugadores)
        {
            JOptionPane.showMessageDialog(null, "Se acabaron las apuestas", "Poker", JOptionPane.INFORMATION_MESSAGE);
            //jugadores.stream().forEach(a -> a.cambioAlcanzoApuesta());
            Set<Integer> llaves = jugadores.keySet();
            llaves.stream().forEach(i -> jugadores.get(i).cambioAlcanzoApuesta());
            // si es third street, lo mandamos a fourthStreet una vez se acaban las apuestas
            if(rondaActual.getText().equals("Third Street"))
            {
                fourthStreet();
            } else
            if(rondaActual.getText().equals("Fourth Street"))
            {
                fifthStreet();
            } else
            if(rondaActual.getText().equals("Fifth Street"))
            {
                sixthStreet();
            } else
            if(rondaActual.getText().equals("Sixth Street"))
            {
                sevenStreth();
            } else
            if(rondaActual.getText().equals("Seventh Street"))
            {
                showdown();
            }

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


        // terminar el juego si todos se rindieron
        int rendidos = 0;
        for(int i = 0; i< numJugadores; i++)
        {
            if(jugadores.get(i).seRindio() == true){rendidos ++;}
        }
        if(rendidos == numJugadores-1)
        {
            showdown();
        }


    }

    // aqui esta todo lo que se va a hacer una vez se alcanze showdown
    // se dictara al ganador y se reiniciara el juego
    public void showdown()
    {
        rondaActual.setText("Showdown");
        actualizarPanelJuego();
        actualizarLabels();

        if(todosEvaluados < jugadores.size())
        {
            JOptionPane.showMessageDialog(null,"Voltee las 5 cartas que desea jugar","Poker",JOptionPane.INFORMATION_MESSAGE);
            // aqui se empieza a poner los action listener a las cartas
            for(int i = 0; i<jugadores.get(jugadorActual).getCartas().size();i++)
            {
                Carta self = jugadores.get(jugadorActual).getCartaI(i);
                 // Remover todos los ActionListeners existentes
                for (ActionListener al : self.getActionListeners()) {
                    self.removeActionListener(al);
                }
                self.addActionListener(a -> self.voltear());
            }

            // aqui se crea el boton y la lambda que lo controla
            JButton ofrecer = new JButton("Ofrecer");
            ofrecer.setBounds(450,75,100,25);
            ofrecer.addActionListener(b -> {
                int activas = 0;
                for(int i = 0; i<jugadores.get(jugadorActual).getCartas().size();i++)
                {
                    if(jugadores.get(jugadorActual).getCartaI(i).esMirable())
                    {
                        
                        activas ++;
                    }
                }

                if(activas <= 5)
                {
                    mejorJugada(jugadores.get(jugadorActual));
                    jugadorActual ++;
                    todosEvaluados ++;
                    nextJugador();
                    showdown();
                } else {
                    showdown();
                }

            });
            panelJuego.add(ofrecer);
        } else {


            JOptionPane.showMessageDialog(null, "EL Jugador "+(jugadorActual+1)+" Gana!!","Poker",JOptionPane.INFORMATION_MESSAGE);
            apuesta = 1;
            Jugador ganador = jugadores.get(jugadorActual);
            ganador.setDinero(dineroBanca+ganador.getDinero());

            reiniciarJuego();
        }

    }

    public void crearGrafico()
    {
        // todo lo relacionado al frame y paneles
        frame.setSize(1250,500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        
        panelJuego.setBounds(0,0,1000,500);
        panelJuego.setBackground(Color.GREEN);
        
        panelInfo.setBounds(1000,0,250,500);
        panelInfo.setBackground(Color.GRAY);
        panelInfo.setLayout(null);
        mazo.getCartaI(0).setBounds(75,250,CARDLENGHT,CARDHEIGHT);
        panelInfo.add(mazo.getCartaI(0));

        rondaActual.setFont(new Font("Agency FB",Font.BOLD,48));
        rondaActual.setBounds(10,0,250,100);
        dineroJugadorActual.setText("Dinero restante: "+jugadores.get(jugadorActual).getDinero()+"$");
        dineroJugadorActual.setBounds(10,100,250,100);
        jugadorTurno.setText("Jugador Actual: "+(jugadorActual+1));
        jugadorTurno.setBounds(10,150,250,100);
        panelInfo.add(dineroJugadorActual);
        panelInfo.add(rondaActual);
        panelInfo.add(jugadorTurno);

        
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

    // calles, estas casi no hacen nada, solo llaman a ronda repartir y a ronda apuestas, segun lo necesario
    public void thirdStreet()
    {
        rondaActual.setText("Third Street");
        ponerAlPeorAlInicio();
        rondaApuestas();
    }

    public void fourthStreet()
    {
        rondaActual.setText("Fourth Street");
        rondaRepartir(1,true);
        // aqui añadir el organizar a los jugadores
        for(int i = 0; i<numJugadores;i++)
        {
            super.mejorJugada(jugadores.get(i));
        }
        empezarConMejor();
        rondaApuestas();
    }

    public void fifthStreet()
    {
        rondaActual.setText("Fifth Street");
        actualizarLabels();

        rondaRepartir(1,true);
        for(int i = 0; i<numJugadores;i++)
        {
            super.mejorJugada(jugadores.get(i));
            
        }
        empezarConMejor();
        rondaApuestas();
    }

    public void sixthStreet()
    {
        rondaActual.setText("Sixth Street");
        actualizarLabels();

        rondaRepartir(1,true);
        for(int i = 0; i<numJugadores;i++)
        {
            
            super.mejorJugada(jugadores.get(i));
            
        }
        empezarConMejor();
        rondaApuestas();
    }

    //tralaleor tralala
    public void sevenStreth()
    {
        rondaActual.setText("Seventh Street");
        rondaRepartir(1,false);
        Set<Integer> llaves = jugadores.keySet();
        for(Integer index : llaves)
        {
            
            Jugador jugador = jugadores.get(index);
            jugador.getCartaI(6).addActionListener(b -> jugador.getCartaI(6).voltear());
        }
        rondaApuestas();
    }



    public void actualizarPanelJuego()
    {
        panelJuego.removeAll();
        panelJuego.setVisible(false);
        panelJuego.setVisible(true);

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
        jugadorTurno.setText("Jugador Actual: "+(jugadorActual+1));
        frame.setVisible(true);

    }

    public void ponerAlPeorAlInicio()
    {
        Set<Integer> llaves = jugadores.keySet();
        Iterator<Integer> iterador = llaves.iterator();
        Carta menor = jugadores.get(iterador.next()).getCartaI(2);
        int indexPeor = 0;
        int repeticiones = -1;
        while(iterador.hasNext())
        {
            repeticiones ++;
            Jugador evaluar = jugadores.get(iterador.next());
            if(menor.getCategoria() > evaluar.getCartaI(2).getCategoria())
            {
                menor = evaluar.getCartaI(2);
                indexPeor = repeticiones;
            } else if(menor.getCategoria() == evaluar.getCartaI(2).getCategoria()){
                // Trebol > Diamente > Corazon > Pica
                int a;
                int b;
                if(menor.getPalo().equals("Trebol"))
                {
                    a = 4;
                } else if(menor.getPalo().equals("Diamante"))
                {
                    a = 3;
                } else if(menor.getPalo().equals("Corazon"))
                {
                    a = 2;
                } else {
                    a = 1;
                } 

                if(evaluar.getCartaI(2).getPalo().equals("Trebol"))
                {
                    b = 4;
                } else if(evaluar.getCartaI(2).getPalo().equals("Diamante"))
                {
                    b = 3;
                } else if(evaluar.getCartaI(2).getPalo().equals("Corazon"))
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
        jugadorActual = indexPeor;
        actualizarPanelJuego();
        actualizarLabels();

    }


    public void nextJugador()
    {
        if(jugadorActual >= numJugadores)
        {
            jugadorActual = 0;
        }
    }

    public void empezarConMejor()
    {
        for(int i = 0; i< numJugadores; i++)
        {
            super.mejorJugada(jugadores.get(i));
        }


        int index = 0;
        Jugador jugador1 = jugadores.get(0);
        while(jugador1.seRindio() == true)
        {
            index ++;
            jugador1 = jugadores.get(index);
        }

        for(int i = 0; i<numJugadores; i++)
        {
            Jugador jugador2 = jugadores.get(i);
            if(jugador2.getJugadaId() > jugador1.getJugadaId())
            {
                jugador1 = jugador2;
                index = i;
            } else if(jugador2.getJugadaId() == jugador1.getJugadaId())
            {
                if(jugador2.getPuntaje() > jugador1.getPuntaje())
                {
                    jugador1 = jugador2;
                    index = i;
                }

            }
        }

        jugadorActual = index;
        /*  usado para debug
        for(int i = 0; i<numJugadores; i++)
        {
            System.out.println("Id"+jugadores.get(i).getJugadaId());
            System.out.println("Puntos"+jugadores.get(i).getPuntaje());
            
        }
        System.out.println("#############");
        */
        actualizarPanelJuego();
        actualizarLabels();
    }


    public void reiniciarJuego()
    {
        actualizarLabels();
        actualizarPanelJuego();
        mazo.vaciar();
        mazo.llenarMazo();
        mazo.shuffle();
        
        int rotos = 0;
        primeraVez = 0;
        dineroBanca = 0;
        for(int i = 0; i<numJugadores; i++)
        {
            jugadores.get(i).vaciarMano();
            if(jugadores.get(i).seRindio() == true){ jugadores.get(i).cambioRendido();}
            if(jugadores.get(i).igualoApuesta() == true){jugadores.get(i).cambioAlcanzoApuesta();}

            if(jugadores.get(i).getDinero() < apuesta)
            {
                rotos ++;
                jugadores.get(i).cambioRendido();
            }
        }

        Set<Integer> llaves = jugadores.keySet();
        for(Integer b : llaves)
        {
            Jugador a = jugadores.get(b);
            a.setDinero(a.getDinero()-apuesta);
            dineroBanca += apuesta;
            a.añadirCarta(mazo.darCarta());
            a.añadirCarta(mazo.darCarta());
            a.añadirCarta(mazo.darCarta());
            a.getCartaI(2).voltear();
            // darle la chance a la carta 0 y 1 de voltearse
            Carta self = a.getCartaI(0);
            self.addActionListener(s -> self.voltear());
            Carta self2 = a.getCartaI(1);
            self2.addActionListener(s -> self2.voltear());
        }


        actualizarPanelJuego();
        if(rotos >= numJugadores-1)
        {
            JOptionPane.showMessageDialog(null, "EL Jugador "+(jugadorActual+1)+" Gana la Final!!","Poker",JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        } else {
            thirdStreet();
        }
        
    }

    public void cambiarActivacionCartasVisibles(Jugador jugador)
    {
        for(int j = 0; j<jugador.getCartas().size(); j++)
        {
            if(jugador.getCartaI(j).esMirable())
            {
                jugador.getCartaI(j).cambioActivacion();
            }
        }
    }
    


}