package maps;

import graphics.TileShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * this class represents a map or level for the game
 * can either be a house or a forest setting.
 */
public class Map {

    private int xTiles = 17;
    private int yTiles = 12;
    private TileShape[][] _map = new TileShape[xTiles][yTiles];
    private ArrayList<TileShape> _grass = new ArrayList<>();
    private ArrayList<TileShape> _obstacles = new ArrayList<>();

    private TileShape fportal;
    private TileShape bportal;
    private int tileSize = 60;

    private String houseGround = "floor.png";
    private String houseObs = "brick.png";

    private String forestGround = "grass.jpg";
    private String forestObs = "tree.png";


    public Map(int num_obs, Boolean isForest) {
        String ground;
        String obs;
        if (isForest) {
            ground = forestGround;
            obs = forestObs;
        } else {
            ground = houseGround;
            obs = houseObs;
        }

        for (int i = 0; i < xTiles; i++) {
            for (int j = 0; j < yTiles; j++) {
                if (i == 0 | i == xTiles - 1 | j == 0 | j == yTiles - 1) {
                    TileShape tree = new TileShape(i * tileSize, j * tileSize, tileSize, tileSize, obs, true);
                    _obstacles.add(tree);
                    _map[i][j] = tree;
                }
                TileShape grass = new TileShape(i * tileSize, j * tileSize, tileSize, tileSize, ground, true);
                _grass.add(grass);
                _map[i][j] = grass;
            }
        }

        for (int k = 0; k < num_obs; k++) {
            int x = new Random().nextInt(xTiles);
            int y = new Random().nextInt(yTiles);
            if ((x != (xTiles - 2) && y != yTiles / 2) || (x != 1 && y != yTiles / 2 )) {
                TileShape tile = new TileShape(x * tileSize, y * tileSize, tileSize, tileSize, obs, true);
                _obstacles.add(tile);
            } else {
                k--;
            }
        }
    }

    public void addForwardsPortal() {
        TileShape portal = new TileShape((xTiles - 2) * tileSize, yTiles / 2 * tileSize, tileSize, tileSize, "portal.png", true);
        fportal = portal;
    }

    public void addBackwardsPortal() {
        TileShape portal = new TileShape(tileSize, yTiles / 2 * tileSize, tileSize, tileSize, "portal.png", true);
        bportal = portal;
    }

    public void paint(Graphics g) {
        for (int i = 0; i < xTiles; i++) {
            for (int j = 0; j < yTiles; j++) {
                _map[i][j].renderShape(g);
            }
        }
        for (TileShape obs : _obstacles) {
            obs.renderShape(g);
        }
        if (fportal != null){
            fportal.renderShape(g);
        }
        if (bportal != null){
            bportal.renderShape(g);
        }

    }

    public ArrayList<TileShape> getObstacles() {
        return this._obstacles;
    }

    public TileShape getForwardPortals() {
        return fportal;
    }

    public TileShape getBackwardPortals() {
        return bportal;
    }
}
