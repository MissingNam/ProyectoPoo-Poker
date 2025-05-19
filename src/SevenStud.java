import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class SevenStud extends PokerPadre
{
    

    // cosas para lo grafico
    private JFrame frame = new JFrame();
    private JImagePanel panelInfo = new JImagePanel("imagenes/Fondos/mesaMadera.jpg");
    private JImagePanel panelJuego = new JImagePanel("imagenes/Fondos/mesaPoker.jpg");
    private JLabel rondaActual = new JLabel("Third-Street");
    private JLabel dineroJugadorActual = new JLabel();
    private JLabel dineroAcumulado = new JLabel();
    private JLabel apuestaActual = new JLabel();
    private JLabel jugadorTurno = new JLabel();
    private JButton botonGuardar = new JButton("Guardar");

    // cosas donde se guarda informacion importante
    private int dineroBanca = 0;
    private int jugadorActual = 0;

    //banderas, 
    private int primeraVez = 0;
    private int todosEvaluados = 0;

    public SevenStud(int apuestaInicial, int numJugadores,int dineroInicial)
    {
        super(apuestaInicial,numJugadores,dineroInicial);
        File archivo = new File("C:\\Games\\partida7Stud.txt");
        if(archivo.exists())
        {
            cargarPartida();
        } else {
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
                    botonGuardar.setEnabled(false);
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
                if(jugadores.get(jugadorActual).getDinero() >= apuesta)
                {
                    jugadores.get(jugadorActual).setDinero(jugadores.get(jugadorActual).getDinero() - apuesta);
                    dineroBanca += apuesta;
                    jugadores.get(jugadorActual).cambioAlcanzoApuesta();
                    jugadorActual ++;
                    nextJugador();
                    botonGuardar.setEnabled(false);
                    actualizarLabels();
                    rondaApuestas();
                } else {
                    jugadores.get(jugadorActual).cambioRendido();
                    JOptionPane.showMessageDialog(null, "El Jugador no tiene suficiente dinero", "Poker", JOptionPane.INFORMATION_MESSAGE);
                    jugadorActual ++;
                    nextJugador();
                    botonGuardar.setEnabled(false);
                    actualizarLabels();
                    rondaApuestas();
                }
            });

            igualar.setBounds(450,50,100,25);
            panelJuego.add(igualar);

            // boton para aumentar la apuesta
            aumentar.setBounds(450,100,100,25);
            aumentar.addActionListener(a -> {
                //crear el slider para el JOptionPane
                int aumento;
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                JSlider slider = new JSlider(1,jugadores.get(jugadorActual).getDinero()/2,1);
                JLabel label = new JLabel("Aumento: "+slider.getValue());

                // esto es extraño, me lo encontre en reddit
                slider.addChangeListener(new ChangeListener(){
                    @Override
                    public void stateChanged(ChangeEvent e)
                    {
                        label.setText("Aumento: "+slider.getValue());
                    }
                });
                panel.add(slider);
                panel.add(label);

                slider.setMajorTickSpacing(10);
                slider.setPaintTicks(true);
                slider.setPaintTrack(true);
                // obtener el valor del slider 
                int opcion = JOptionPane.showConfirmDialog(null, panel,"Seleccione un valor",JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE);
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
                botonGuardar.setEnabled(false);
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
                botonGuardar.setEnabled(false);
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
            JOptionPane.showMessageDialog(null, "EL Jugador "+(jugadorActual+1)+" Gana!!","Poker",JOptionPane.INFORMATION_MESSAGE);
            apuesta = 1;
            Jugador ganador = jugadores.get(jugadorActual);
            ganador.setDinero(dineroBanca+ganador.getDinero());

            reiniciarJuego();
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
            panelJuego.setVisible(false);
            panelJuego.setVisible(true);
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
        //panelJuego.setBackground(Color.GREEN);
        
        panelInfo.setBounds(1000,0,250,500);
        //panelInfo.setBackground(Color.GRAY);
        panelInfo.setLayout(null);
        mazo.getCartaI(0).setBounds(75,250,CARDLENGHT,CARDHEIGHT);
        panelInfo.add(mazo.getCartaI(0));

       
        botonGuardar.setBounds(10,165,100,25);
        botonGuardar.addActionListener(a -> guardarPartida());
        rondaActual.setFont(new Font("Agency FB",Font.BOLD,48));
        rondaActual.setForeground(Color.WHITE);
        rondaActual.setBounds(10,0,250,100);
        dineroJugadorActual.setText("Dinero restante: "+jugadores.get(jugadorActual).getDinero()+"$");
        dineroJugadorActual.setBounds(10,100,250,100);
        dineroJugadorActual.setForeground(Color.WHITE);
        jugadorTurno.setText("Jugador Actual: "+(jugadorActual+1));
        jugadorTurno.setBounds(10,150,250,100);
        jugadorTurno.setForeground(Color.WHITE);
        panelInfo.add(dineroJugadorActual);
        panelInfo.add(rondaActual);
        panelInfo.add(jugadorTurno);
        panelInfo.add(botonGuardar);

        
        //Panel de Juego
        panelJuego.setLayout(null);
        dineroAcumulado.setText("Banca: "+ dineroBanca + " $");
        dineroAcumulado.setFont(new Font("Agency FB",Font.BOLD,48));
        dineroAcumulado.setBounds(600,50,250,100);
        dineroAcumulado.setForeground(Color.WHITE);
        apuestaActual.setText("Apuesta Actual: "+apuesta+" $");
        apuestaActual.setBounds(200,100,250,25);
        apuestaActual.setForeground(Color.WHITE);
        panelJuego.add(dineroAcumulado);
        panelJuego.add(apuestaActual);

        frame.add(panelJuego);
        frame.add(panelInfo);
        frame.setVisible(true);
    }

    // calles, estas casi no hacen nada, solo llaman a ronda repartir y a ronda apuestas, segun lo necesario
    public void thirdStreet()
    {
        botonGuardar.setEnabled(true);
        rondaActual.setText("Third Street");
        ponerAlPeorAlInicio();
        rondaApuestas();
    }

    public void fourthStreet()
    {
        botonGuardar.setEnabled(true);
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
        botonGuardar.setEnabled(true);
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
        botonGuardar.setEnabled(true);
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
        botonGuardar.setEnabled(true);
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
        while(iterador.hasNext())
        {
            int llave = iterador.next();
            Jugador evaluar = jugadores.get(llave);
            int comparacion = menor.compareTo(evaluar.getCartaI(2));
                if(comparacion == 1)
                {
                    menor = evaluar.getCartaI(2);
                    indexPeor = llave;
                }
        }
        // empezar con la index del pero
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
        todosEvaluados = 0;
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


    public void guardarPartida()
    {
        try {
            FileWriter writer = new FileWriter("C:\\Games\\partida7Stud.txt");
            writer.write(Integer.toString(numJugadores)+"\n");
            writer.write(rondaActual.getText()+"\n");
            writer.write(apuesta+"\n");
            writer.write(dineroBanca+"\n");
    
            for(int i = 0; i<numJugadores; i++)
            {
                writer.write(Integer.toString(i)+"\n");
                writer.write(jugadores.get(i).getDinero()+"\n");
                for(int j = 0; j < jugadores.get(i).getCartas().size(); j++)
                {
                    writer.write(jugadores.get(i).getCartaI(j)+"\n");
                }
            }
            writer.write("mazo \n");
            for(int i = 0; i<mazo.size(); i++)
            {
                writer.write(mazo.getCartaI(i)+"\n");
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear el archivo.");
            e.printStackTrace();
        }
    }

    public void cargarPartida()
    {
        File archivo = new File("C:\\Games\\partida7Stud.txt");
        try (Scanner lector = new Scanner(archivo)) {
        int numJugadores = Integer.parseInt(lector.nextLine());
        String nombreRonda = lector.nextLine();
        int apuestaActual = Integer.parseInt(lector.nextLine());
        int pozo = Integer.parseInt(lector.nextLine());

        HashMap<Integer, Jugador> jugadores = new HashMap<>();

        // Leer todos los jugadores
        for (int i = 0; i < numJugadores; i++) {
            int id = Integer.parseInt(lector.nextLine());
            int dinero = Integer.parseInt(lector.nextLine());

            Jugador jugador = new Jugador(dinero);
            int limit = 0;
            switch(nombreRonda)
         {
            case "Third Street":
                limit = 3;
                break;
            case "Fourth Street":
                limit = 4;
                break;
            case "Fifth Street":
                limit = 5;
                break;
            case "Sixth Street":
                limit = 6;
                break;
            case "Seventh Street":
                limit = 7;
                break;
            case "ShowDown":
                limit = 7;
                break;
        }

            for (int j = 0; j < limit; j++) {
                if (!lector.hasNextLine()) break;
                String lineaCarta = lector.nextLine();
                Carta carta = parsearCarta(lineaCarta);
                jugador.añadirCarta(carta);
            }
            jugadores.put(id, jugador);
        }

        // Leer "mazo"
        if (lector.hasNextLine() && lector.nextLine().equalsIgnoreCase("mazo")) {
    
            while (lector.hasNextLine()) {
                String cartaStr = lector.nextLine();
                Carta carta = parsearCarta(cartaStr);
                mazo.añadirAMazo(carta);
            }
        }

        // Guardar info del juego
        this.numJugadores = numJugadores;
        this.apuesta = apuestaActual;
        this.dineroBanca = pozo;
        this.jugadores = jugadores;

        for(int i = 0; i<jugadores.size(); i++)
        {
            for(int j = 0; j <jugadores.get(i).manoSize(); j++)
            {
                Carta self = jugadores.get(i).getCartaI(j);
                if(!self.esMirable())
                {
                    self.addActionListener(a -> self.voltear());
                }
            }
        }

        switch(nombreRonda)
        {
            case "Third Street":
                thirdStreet();
                break;
            case "Fourth Street":
                fourthStreet();
                break;
            case "Fifth Street":
                fifthStreet();
                break;
            case "Sixth Street":
                sixthStreet();
                break;
            case "Seventh Street":
                sevenStreth();
                break;
            case "ShowDown":
                showdown();
                break;
        }

        crearGrafico();

        System.out.println("Partida cargada con éxito.");
    } catch (Exception e) {
        System.out.println("Error al cargar la partida:");
        e.printStackTrace();
    }
    }


    private Carta parsearCarta(String texto) {
            boolean revelada = true;

            if (texto.startsWith("*")) {
                texto = texto.substring(1);
            } else {
                revelada = false;
            }

            texto = texto.replace("[", "");
            texto = texto.replace("]", "");
            String[] partes = texto.split("\\|");
            int valor = Integer.parseInt(partes[0]);
            String palo = partes[1];

            Carta carta = new Carta(valor, palo);
            if (!revelada) {
                carta.voltear();
            }

            return carta;
        }



}