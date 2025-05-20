
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Image;


public class Carta extends JButton implements Comparable<Carta> {

    private int categoria;
    private String palo;
    private ImageIcon imagen;
    private ImageIcon reverso = new ImageIcon("imagenes\\fila-1-columna-1.png");
    private String ubicacion;
    private boolean mirable = false;
    private boolean activa = false;

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
                fila = 2;
                break;
        }
               
        ubicacion = ("imagenes\\fila-"+fila+"-columna-"+columna+".png");
        imagen = new ImageIcon(ubicacion);
        Image resizedimagen = imagen.getImage().getScaledInstance(59*2, 89*2, Image.SCALE_SMOOTH);
        imagen = new ImageIcon(resizedimagen);
        resizedimagen = reverso.getImage().getScaledInstance(59*2,89*2,Image.SCALE_SMOOTH);
        reverso = new ImageIcon(resizedimagen);

        this.setIcon(reverso);
        this.setSize(59,89);

        


    }
    
    /*  Esto fue para hacer pruebas, no lo consideres ya que interferiria con 7-stud
    @Override
        protected void processMouseEvent(MouseEvent e)
        {
            if(e.getID() == MouseEvent.MOUSE_PRESSED)
            {
                voltear();
            }
            super.processMouseEvent(e);
        }
            */


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
        mirable = !mirable;
        if(mirable)
        {
            this.setIcon(imagen);
        } else {
            this.setIcon(reverso);
        }

    }

    public String toString()
    {
        if(mirable)
        {
            return ("["+categoria+"|"+palo+"]");
        } else {
            return ("*["+categoria+"|"+palo+"]");
        }
    }

    public void cambioActivacion()
    {
        activa = !activa;
    }

    public void activar()
    {
        activa = true;
    }
    public void desactivar()
    {
        activa = false;
    }

    public boolean estaActiva()
    {
        return activa;
    }

    public boolean esMirable()
    {
        return mirable;
    }

    public int compareTo(Carta otraCarta)
    {
        if(this.getCategoria() > otraCarta.getCategoria())
        {
            return 1;
        } else if(this.getCategoria() == otraCarta.getCategoria())
        {
            if(this.getPalo().charAt(0) > otraCarta.getPalo().charAt(0))
            {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }


}
