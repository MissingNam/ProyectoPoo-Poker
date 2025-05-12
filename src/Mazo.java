import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
    private ArrayList<Carta> cartas = new ArrayList<>();
    
    public Mazo()
    {
        String[] palos = new String[4];
        palos[0] = "Diamante";
        palos[1] = "Espada";
        palos[2] = "Corazon";
        palos[3] = "Trebol";

        for(int i = 1; i < 14;i++)
        {
            for(int j = 0; j < 4; j++)
            {
                cartas.add(new Carta(i,palos[j]));
            }
        }
        
    }


    public void shuffle()
    {
        Collections.shuffle(cartas);
    }

    public Carta darCarta()
    {
        return cartas.remove(0);
    }

    public ArrayList<Carta> darNCartas(int n)
    {
        ArrayList<Carta> cartasRetorno = new ArrayList<>();
        for(int i = 0; i<n ; i++)
        {
            cartasRetorno.add(cartas.remove(0));
        }

        return cartasRetorno;
    }

    public Carta getCartaI(int i)
    {
        return cartas.get(i);
    }

    public ArrayList<Carta> allCards()
    {
        return cartas;
    }

    /* Metodo para pruebas, no considerarlo
    public void reubicarCartas()
    {
        int index = 0;
        for(int i = 0; i < 13; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                cartas.get(index).setBounds((i*59),(j*89),59,89);
                index++;
            }
        }

    }
        */

}
