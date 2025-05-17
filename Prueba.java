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
