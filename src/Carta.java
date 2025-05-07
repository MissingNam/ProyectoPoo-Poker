
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.*;


public class Carta extends JButton {

    int categoria;
    String palo;
    ImageIcon imagen;
    ImageIcon reverso = new ImageIcon("imagenes\\fila-1-columna-1.png");
    String ubicacion;
    boolean visible = false;

    public Carta(int categoria, String palo)
    {
        this.categoria = categoria;
        this.palo = palo;
        // colocarle la categoria
        int columna = categoria;
        if(columna == 1)
        {
            columna = 14;
        }

        //colocarle el palo
        int fila = 1;
        switch(palo)
        {
            case "Trebol":
                fila = 1;
                break;
            case "Pica":
                fila = 2;
                break;
            case "Corazon":
                fila = 3;
                break;
            case "Diamante":
                fila = 4;
                break;
            default:
                fila = 1;
                break;
        }
               
        ubicacion = ("imagenes\\fila-"+fila+"-columna-"+columna+".png");
        imagen = new ImageIcon(ubicacion);

        this.setIcon(reverso);
        this.setSize(59,89);

        


    }
    
    @Override
        protected void processMouseEvent(MouseEvent e)
        {
            if(e.getID() == MouseEvent.MOUSE_PRESSED)
            {
                voltear();
            }
            super.processMouseEvent(e);
        }


    public String getPalo()
    {
        return palo;
    }

    public int getCategoria()
    {
        return categoria;
    }

    public void voltear()
    {
        visible = !visible;
        if(visible)
        {
            this.setIcon(imagen);
        } else {
            this.setIcon(reverso);
        }

    }

}
