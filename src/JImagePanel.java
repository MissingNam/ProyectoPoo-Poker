import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JImagePanel extends JPanel {

    private BufferedImage imagen;
    public JImagePanel(String rutaImagen)
    {
        try {
            imagen = ImageIO.read(new File(rutaImagen));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(imagen != null)
        {
            g.drawImage(imagen, 0, 0, getWidth(),getHeight(),this);
        }
    }

}
