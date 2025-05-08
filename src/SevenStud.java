

public class SevenStud extends PokerPadre
{

    public SevenStud(int apuestaInicial, int numJugadores,int dineroInicial)
    {
        super(apuestaInicial,numJugadores,dineroInicial);
        // darle las cartas a los jugadores al inicio del juego
        mazo.shuffle();
        for(Jugador a : jugadores)
        {
            a.setDinero(a.getDinero()-apuesta);
            a.añadirCarta(mazo.darCarta());
            a.añadirCarta(mazo.darCarta());
            a.añadirCarta(mazo.darCarta());
            a.getCartaI(2).voltear();
            a.getCartaI(0).voltear();
        }

        for(int i = 0; i<jugadores.get(0).manoSize(); i++)
        {
            System.out.println(jugadores.get(0).getCartaI(i));
        }
    }

    public void rondaApuestas()
    {

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





}