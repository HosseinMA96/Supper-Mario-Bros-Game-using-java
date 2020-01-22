package Mario;

import GameEntity.Entity;
import GameTile.Tile;
import GameTile.Wall;

import java.awt.*;
import java.util.ArrayList;

public class Handler {
    private ArrayList<Entity> entity = new ArrayList<>();
    private ArrayList<Tile> tile = new ArrayList<>();

    public Handler() {
        createLevel();
    }

    public void render(Graphics g) {
        for (Entity en : entity)
            en.render(g);

        for (Tile tl : tile)
            tl.render(g);
    }

    public void tick() {
        for (Entity en : entity)
            en.tick();

        for (Tile tl : tile)
            tl.tick();
    }

    public void createLevel() {
        //64 * 64 pixels for walls is asumptions
        for (int i = 0; i < Game.WITDH * Game.SCALE / 64 + 10; i++) {
            addTile(new Wall(i * 64, Game.HEIGHT * Game.SCALE - 64, 64, 64, true, Id.wall, this));

            if(i!=0 && i!=1 && i!=16 && i!=17 && i!=15)
                addTile(new Wall(i * 64, 300, 64, 64, true, Id.wall, this));
        }
    }

    public void addEntity(Entity e) {
        entity.add(e);
    }

    public void addTile(Tile t) {
        tile.add(t);
    }

    public void removeEntity(Entity e) {
        entity.remove(e);
    }

    public void removeTile(Tile t) {
        tile.remove(t);
    }

    public ArrayList<Entity> getEntity() {
        return entity;
    }

    public ArrayList<Tile> getTile() {
        return tile;
    }
}
