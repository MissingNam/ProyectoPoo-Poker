import java.util.ArrayList;

public class Jugador {
    private ArrayList<Carta> mano = new ArrayList<>();
    private int dinero;
    private boolean rendido;
    private boolean alcanzoApuesta;
    private int puntaje;
    private int jugadaID;

    public Jugador(int dineroInicial)
    {
        dinero = dineroInicial;
    }

    public void añadirCartas(ArrayList<Carta> nuevasCartas)
    {
        mano.addAll(nuevasCartas);
    }

    public void añadirCarta(Carta nuevaCarta)
    {
        mano.add(nuevaCarta);
    }

    public int getDinero()
    {
        return dinero;
    }

    public void setDinero(int nuevoDinero)
    {
        dinero = nuevoDinero;
    }

    // intercambia entre rendido y recuperado
    public void cambioRendido()
    {
        rendido = !rendido;
    }

    public void cambioAlcanzoApuesta()
    {
        alcanzoApuesta = !alcanzoApuesta;
    }

    public ArrayList<Carta> getCartas()
    {
        return mano;
    }

    public String toString()
    {
        return (""+dinero);
    }

    public Carta getCartaI(int i)
    {
        return mano.get(i);
    }

    public int manoSize()
    {
        return mano.size();
    }

    public String getDineroString()
    {
        return Integer.toString(dinero);
    }

    public boolean seRindio()
    {
        return rendido;
    }

    public boolean igualoApuesta()
    {
        return alcanzoApuesta;
    }

    public void desIgualarApuesta()
    {
        alcanzoApuesta = false;
    }

    public void setPuntaje(int newPuntaje)
    {
        puntaje = newPuntaje;
    }

    public int getPuntaje()
    {
        return puntaje;
    }


    public void setJugadaId(int newId)
    {
        jugadaID = newId;
    }

    public int getJugadaId()
    {
        return jugadaID;
    }

    public void vaciarMano()
    {
        mano.clear();
    }


}
