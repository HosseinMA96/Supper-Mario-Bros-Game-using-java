package GameEntity;

import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public abstract class Entity {
    protected int x,y,width,height,velX,velY;
//    protected boolean solid;
    protected Id id;
    protected Handler handler;
    protected boolean jumping=false,goingDownPipe;
    protected boolean falling=true;
    protected double gravity=0.0;
    protected int facing=0; // facing left =0 , facing right =1


    public Entity(int x, int y, int width, int height,Id id,Handler handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
//        this.solid=solid;
        this.id=id;
        this.handler=handler;
    }

    /**
     * Instead of creating bufferStrategies we use grapgics so
     * the game will not lag due to many objects displayed in the screen
     * @param g
     */
    public abstract void render(Graphics g);


    /**
     * Update this entity
     */
    public abstract void tick();


    public void die()
    {
        handler.removeEntity(this);

        if(id==Id.player1)
        {
            Game.lives--;
            Game.showDeathScreen=true;


            if(Game.lives==0)
                Game.gameOver=true;
        }

    }

//    public boolean getSolid(){
//        return solid;
//    }

    public void setFacing(int f)
    {

    }

    public void setGoingDownPipe(boolean goingDownPipe) {
        this.goingDownPipe = goingDownPipe;
    }

    public boolean getGoingDownPipe()
    {
        return goingDownPipe;
    }
    public int getX() {
        return x;
    }

    public Id getId() {
        return id;
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

    public void setGravity(double gravity) {
        this.gravity = gravity;
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

    public Rectangle getBounds()
    {
        return new Rectangle(getX(),getY(),width,height);
    }

    public Rectangle getBoundsTop()
    {
        return new Rectangle(getX()+10,getY(),width-20,5);
    }

    public Rectangle getBoundsBottom()
    {
        return new Rectangle(getX()+10,getY()+height-5,width-20,5);
    }

    public Rectangle getBoundsLeft()
    {
        return new Rectangle(getX(),getY()+10,5,height-20);
    }

    public Rectangle getBoundsRight()
    {
        return new Rectangle(getX()+width-5,getY()+10,5,height-20);
    }

    public double getGravity() {
        return gravity;
    }

    public boolean getJumping()
    {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
}
