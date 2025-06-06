import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JFrame;


public class Prueba {
    int al = 0;
    JFrame frame = new JFrame();
    public void main(String[] args) {
        Mazo mazo = new Mazo();

        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //mazo.shuffle();
        mazo.reubicarCartas();
        
        Iterator<Carta> it = mazo.allCards().iterator();
        while(it.hasNext())
        {
            Carta carta = it.next();
            carta.addActionListener(a -> resetFrame());
            frame.add(carta);
        }
            
        

        
        /* 

        for(int i = 0; i<52; i++)
        {
            mazo.getCartaI(i).addActionListener(a -> resetFrame());
            frame.add(mazo.getCartaI(i)); 
        }
            */
    
        frame.setVisible(true);
    }

    public void resetFrame()
    {
        frame.setVisible(true);
    }
    
}
