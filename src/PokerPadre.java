import java.util.ArrayList;


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

    public abstract void rondaRepartir();

    public abstract void showdown();
    
    public abstract Jugador mejorJugada();

    



}
