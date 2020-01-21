package Mario;

import GameEntity.Entity;
import GameTile.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Handler {
    private ArrayList<Entity> entity = new ArrayList<>();
    private ArrayList<Tile> tile = new ArrayList<>();

    public void render(Graphics g){
        for (Entity en:entity)
            en.render(g);

        for (Tile tl : tile)
            tl.render(g);
    }

    public void tick(){
        for (Entity en:entity)
            en.tick();

        for (Tile tl : tile)
            tl.tick();
    }

    public void addEntity(Entity e)
    {
        entity.add(e);
    }

    public void addTile(Tile t)
    {
        tile.add(t);
    }

    public void removeEntity(Entity e)
    {
        entity.remove(e);
    }

    public void removeTile(Tile t)
    {
        tile.remove(t);
    }




}
