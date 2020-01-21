package GameEntity;

import GameTile.Tile;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class Player extends Entity{

    public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
        velX=1;
        velY=-1;
    }



    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.fillRect(x,y,width,height);
    }

    @Override
    public void tick()
    {
        x+=velX;
//        y+=velY;

        if(x<=0)
            x=0;

//        if(y<=0)
//            y=0;

        if(x+width>=1080)
            x=1080-width;

        if(y+height>=775)
            y=775-height;

        //Ehtemalan shekaste shodan ro inja bayad begi dawsh
        for (Tile t :handler.getTile()){
            if(!t.getSolid())
                break;

            if(t.getId()==Id.wall){
                if(getBoundsTop().intersects(t.getBounds())){
                    setVelY(0);
//                    y=t.getY()+t.getHeight();
                }
            }

            if(getBoundsBottom().intersects((t.getBounds()))){
                setVelY(0);
                y=t.getY()-t.getHeight();
            }

            if(getBoundsLeft().intersects((t.getBounds()))){
               setVelX(0);
                x=t.getX()+t.getWidth();
            }

            if(getBoundsRight().intersects((t.getBounds()))){
                setVelX(0);
                x=t.getX()-t.getWidth();
            }

        }
    }
}
