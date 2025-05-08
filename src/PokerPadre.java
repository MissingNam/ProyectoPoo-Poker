import java.util.ArrayList;

public abstract class PokerPadre {
    
    ArrayList<Jugador> jugadores = new ArrayList<>();
    int apuesta;
    int numJugadores;
    Mazo mazo = new Mazo();

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
