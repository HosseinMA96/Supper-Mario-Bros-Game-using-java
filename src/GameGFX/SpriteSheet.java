package GameGFX;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;

public class SpriteSheet {
    private BufferedImage sheet;

    public SpriteSheet(String path)
    {
        try {
          //  sheet = ImageIO.read(getClass().getResource(path));
            sheet = ImageIO.read(new File("C:\\Users\\erfan\\Desktop\\dummy\\res\\spritesheet.png"));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite(int x,int y)
    {
        return sheet.getSubimage(x*32-32,y*32-32,32,32);
    }
}
