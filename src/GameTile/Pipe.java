package GameTile;

import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class Pipe extends Tile {
    public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler , int facing) {
        super(x, y, width, height, solid, id, handler);
        this.facing=facing;
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.green);
        g.fillRect(x,y,width,height);

    }

    @Override
    public void tick()
    {

    }
}
