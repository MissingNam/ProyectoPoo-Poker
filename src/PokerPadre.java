import java.util.ArrayList;
import java.util.Iterator;


public abstract class PokerPadre {
    public final int CARDLENGHT = 59*2;
    public final int CARDHEIGHT = 89*2;
    
    protected ArrayList<Jugador> jugadores = new ArrayList<>();
    protected int apuesta;
    protected int numJugadores;
    protected Mazo mazo = new Mazo();

    public PokerPadre(int apuestaInicial, int numJugadores,int dineroInicial)
    {
        this.numJugadores = numJugadores;
        for(int i = 0; i < numJugadores; i++)
        {
            jugadores.add(new Jugador(dineroInicial));
        }

        apuesta = apuestaInicial;

    }
    
    public abstract void rondaApuestas();

    public void rondaRepartir(int numero,boolean visible)
    {
        // recorre todos los jugadores y les da una carta
        Iterator<Jugador> iterador = jugadores.iterator();
        while(iterador.hasNext())
        {
            Jugador aDar = iterador.next();
            // evitar darle cartas a los rendidos (temas de optimizacion)
            if(visible)
            {
                for(int i = 0; i<numero; i++)
                {
                    Carta añadirCa = mazo.darCarta();
                    añadirCa.voltear();
                    aDar.añadirCarta(añadirCa);
                }
            } else {
                for(int i = 0; i<numero; i++)
                {
                    Carta añadirCa = mazo.darCarta();
                    aDar.añadirCarta(añadirCa);
                } 
            }
            
        }
    }

    public abstract void showdown();
    
    public void mejorJugada(Jugador jugador)
    {
        Mano mano = new Mano(jugador.getCartas());

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

        puntaje = mano.buscarEscaleraReal();
        if(puntaje != 0)
        {
            jugador.setJugadaId(10);
            jugador.setPuntaje(puntaje);
        }
        

    }

    



}
