<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> origin/DeltaFiveCardDraw
import java.util.ArrayList;
import java.util.Random;



public class Prueba {
    
    public static void main(String[] args) {
        ArrayList<Carta> cartas = new ArrayList<>();
        Random rnd = new Random();
        rnd.setSeed(6234724);
        /* 
        String[] palos = {"Diamante","Pica","Corazon","Trebol"};
        for(int i = 0; i < rnd.nextInt(7); i++)
        {
            cartas.add(new Carta(rnd.nextInt(14),palos[rnd.nextInt(4)]));
        }
        for(int i = 0; i < cartas.size(); i++)
        {
            cartas.get(i).voltear();
            System.out.println(cartas.get(i));
        }
            */

            Jugador jugador = new Jugador(50);
            cartas.add(new Carta(2,"Pica"));
            cartas.add(new Carta(3,"Trebol"));
            cartas.add(new Carta(4,"Trebol"));
            cartas.add(new Carta(5,"Trebol"));
            cartas.add(new Carta(6,"Trebol"));
            cartas.add(new Carta(8,"Diamante"));
            jugador.añadirCartas(cartas);

        pp2(jugador);
        
        System.out.println(jugador.getJugadaId());
        System.out.println(jugador.getPuntaje());
    }

    public static void pp2(Jugador jugador)
    {
        Mano mano = new Mano(jugador.getCartas());
        int puntaje = mano.buscarEscalera();
        if(puntaje != 0)
        {
            jugador.setJugadaId(2);
            jugador.setPuntaje(puntaje);
        }

    }
    
}
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JFrame;


public class Prueba {
    int al = 0;
    JFrame frame = new JFrame();
    public void main(String[] args) {
        Mazo mazo = new Mazo();

        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //mazo.shuffle();
        mazo.reubicarCartas();
        
        Iterator<Carta> it = mazo.allCards().iterator();
        while(it.hasNext())
        {
            Carta carta = it.next();
            carta.addActionListener(a -> resetFrame());
            frame.add(carta);
        }
            
        

        
        /* 

        for(int i = 0; i<52; i++)
        {
            mazo.getCartaI(i).addActionListener(a -> resetFrame());
            frame.add(mazo.getCartaI(i)); 
        }
            */
    
        frame.setVisible(true);
    }

    public void resetFrame()
    {
        frame.setVisible(true);
    }
    
}
>>>>>>> origin/beta_FiveCardDraw
=======
>>>>>>> origin/DeltaFiveCardDraw
