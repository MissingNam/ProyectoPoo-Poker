import java.util.ArrayList;

public class Jugador {
    ArrayList<Carta> mano = new ArrayList<>();
    int dinero;
    boolean rendido;
    boolean alcanzoApuesta;

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

    public void CambioAlcanzoApuesta()
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



}
