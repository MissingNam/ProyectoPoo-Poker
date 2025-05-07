import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;

public class Prueba {
    public static void main(String[] args) {
        ArrayList<Carta> cartas = new ArrayList<>();
        String[] palos = new String[4];
        palos[0] = "Diamante";
        palos[1] = "Espada";
        palos[2] = "Corazon";
        palos[3] = "Trebol";

        JFrame frame = new JFrame();

        for(int i = 1; i < 14;i++)
        {
            for(int j = 0; j < 4; j++)
            {
                cartas.add(new Carta(i,palos[j]));
            }
        }
        //Collections.shuffle(cartas);

        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int index = 0;
        for(int i = 0; i < 13; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                cartas.get(index).setBounds((i*59),(j*89),59,89);
                index++;
            }
        }


        for(int i = 0; i<52; i++)
        {
            
            frame.add(cartas.get(i));
        }
        frame.setVisible(true);
    }
}
