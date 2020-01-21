package Mario.entity;

import java.awt.*;

public class Entity {
    private int x,y,width,height,velX,velY;
    private boolean solid;


    public Entity(int x, int y, int width, int height,boolean solid) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid=solid;
    }

    /**
     * Instead of creating bufferStrategies we use grapgics so
     * the game will not lag due to many objects displayed in the screen
     * @param g
     */
    public void render(Graphics g)
    {

    }

    /**
     * Update this entity
     */
    public void tick()
    {
        x+=velX;
        y+=velY;
    }

    public boolean getSolid(){
        return solid;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }
}
