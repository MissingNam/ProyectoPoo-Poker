import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;



public abstract class PokerPadre {
    public final int CARDLENGHT = 59*2;
    public final int CARDHEIGHT = 89*2;
    
    protected HashMap<Integer,Jugador> jugadores = new HashMap<>();
    protected int apuesta;
    protected int numJugadores;
    protected Mazo mazo = new Mazo();
    protected int jugadorActual = 0;

    public PokerPadre(int apuestaInicial, int numJugadores,int dineroInicial)
    {
        this.numJugadores = numJugadores;
        for(int i = 0; i < numJugadores; i++)
        {
            Jugador a = new Jugador(dineroInicial);
            jugadores.put(i, a);
        }

        apuesta = apuestaInicial;

    }
    

    public void rondaRepartir(int numero,boolean visible)
    {
        // recorre todos los jugadores y les da una carta
        Set<Integer> llaves = jugadores.keySet();
        Iterator<Integer> iterador = llaves.iterator();
        while(iterador.hasNext())
        {
            Integer aDar = iterador.next();
            // evitar darle cartas a los rendidos (temas de optimizacion)
            if(visible)
            {
                for(int i = 0; i<numero; i++)
                {
                    Carta añadirCa = mazo.darCarta();
                    añadirCa.voltear();
                    jugadores.get(aDar).añadirCarta(añadirCa);
                }
            } else {
                for(int i = 0; i<numero; i++)
                {
                    Carta añadirCa = mazo.darCarta();
                    jugadores.get(aDar).añadirCarta(añadirCa);
                } 
            }
            
        }
    }

    public abstract void showdown();

    
    public void mejorJugada(Jugador jugador)
    {
        ArrayList<Carta> aEvaluar = new ArrayList<>();
        /* 
        aEvaluar.addAll(jugador.getCartas().stream().
            filter(a -> a.estaActiva() == true).collect(Collectors.toList()));

        Mano mano = new Mano(aEvaluar);
        */
        for(int i = 0; i<jugador.getCartas().size();i++)
        {
            if(jugador.getCartaI(i).esMirable())
            {
                aEvaluar.add(jugador.getCartaI(i));
            }
        }
        Mano mano = new Mano(aEvaluar);

        int puntaje = mano.cartaAlta();
        if(puntaje != 0)
        {
            jugador.setJugadaId(1);
            jugador.setPuntaje(puntaje);
        }

        puntaje = mano.buscarPar();
        if(puntaje != 0)
        {
            jugador.setJugadaId(2);
            jugador.setPuntaje(puntaje);
        }

        puntaje = mano.buscarDoblePar();
        if(puntaje != 0)
        {
            jugador.setJugadaId(3);
            jugador.setPuntaje(puntaje);
        }

        puntaje = mano.buscarTrio();
        if(puntaje != 0)
        {
            jugador.setJugadaId(4);
            jugador.setPuntaje(puntaje);
        }

        puntaje = mano.buscarEscalera();
        if(puntaje != 0)
        {
            jugador.setJugadaId(5);
            jugador.setPuntaje(puntaje);
        }

        puntaje = mano.buscarColor();
        if(puntaje != 0)
        {
            jugador.setJugadaId(6);
            jugador.setPuntaje(puntaje);
        }

        puntaje = mano.buscarFull();
        if(puntaje != 0)
        {
            jugador.setJugadaId(7);
            jugador.setPuntaje(puntaje);
        }

        puntaje = mano.buscarPoker();
        if(puntaje != 0)
        {
            jugador.setJugadaId(8);
            jugador.setPuntaje(puntaje);
        }

        puntaje = mano.buscarEscaleraDeColor();
        if(puntaje != 0)
        {
            jugador.setJugadaId(9);
            jugador.setPuntaje(puntaje);
        }
        /* 
        puntaje = mano.buscarEscaleraReal();
        if(puntaje != 0)
        {
            jugador.setJugadaId(10);
            jugador.setPuntaje(puntaje);
        }
            */
        

    }

    public void nextJugador()
    {
        if(jugadorActual >= numJugadores)
        {
            jugadorActual = 0;
        }
    }

    



}
