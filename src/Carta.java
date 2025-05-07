
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Carta extends JButton {

    int Categoria;
    String Palo;
    public Carta(int Categoria, String Palo)
    {
        this.Categoria = Categoria;
        this.Palo = Palo;
        // colocarle la categoria
        int columna = Categoria;
        if(columna == 1)
        {
            columna = 14;
        }

        //colocarle el palo
        int fila = 1;
        switch(Palo)
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
               
        String ubicacion = ("imagenes\\fila-"+fila+"-columna-"+columna+".png");
        ImageIcon imagen = new ImageIcon(ubicacion);

        this.setIcon(imagen);
        this.setSize(59,89);
            

        

    }




}
