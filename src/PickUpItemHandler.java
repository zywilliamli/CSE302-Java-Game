import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class PickUpItemHandler {

    private final MapHandler maps;
    Timer velocity_timer;
    int levels = 4;
    int[] killedEnemies = {0,0,0,0};
    int[] numberOfEnemies = new int[4];
    int lastLevel = 1;
    private ArrayList<PickUpItem> itemsList;
    private ArrayList<Items> levelPickUps = new ArrayList<>(Arrays.asList(Items.PROJECTILE, Items.CUPIDBOW, Items.WOLFSKIN, Items.HEALTH));
    private int currentPickUps;
    private HashMap<Integer, ArrayList> pickupIndices = new HashMap<>();

    Protagonist player;

    // Constructor initialises array of bullets
    public PickUpItemHandler(MapHandler maps) {
        this.maps = maps;
        itemsList = new ArrayList<>();
        currentPickUps = 0;

        this.velocity_timer = new Timer(1000/300, (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkCollision();
            }
        }));
        velocity_timer.start();
    }

    /**
     * called by enemy handler when new enemies are created
     * @param level
     * @param number
     */
    public void addNumberOfEnemies(int level, int number){
        if (level > levels || number < 0){
            return;
        }
        numberOfEnemies[level] = number;
        assignPickUp(level);
    }

    public void assignPickUp(int level){
        if (level >= levels - 1 || levels < 0){
            return;
        }
        ArrayList indices = new ArrayList();
        if (level < 2){
            int index = ThreadLocalRandom.current().nextInt(numberOfEnemies[level]/6, numberOfEnemies[level]-4);
            indices.add(index);
            System.out.println("assigning pickup for level" + level + " with index " + index);
        } else if (level == 2){ //two items in level 2

            int index = ThreadLocalRandom.current().nextInt(numberOfEnemies[level]/3, numberOfEnemies[level]);
            int index_2 = index;
            while (index_2 == index){
                index_2 = ThreadLocalRandom.current().nextInt(numberOfEnemies[level]/3, numberOfEnemies[level]);
            }
            indices.add(index);
            indices.add(index_2);
            System.out.println("assigning pickup for level" + level + " with index " + index + " " + index_2);

        }
        pickupIndices.put(level, indices);
    }

    public void addPlayer(Protagonist player) {
        this.player = player;
    }

    public void paint(Graphics2D win){
        for (PickUpItem item: itemsList){
            item.paint(win);
        }
    }

    public void changePickupItem(){
    }

    //checks whether player collides with item.
    public void checkCollision(){
        if (player == null){
            return;
        }
        Rectangle playerRec = player.getBounds();
        for (int i = 0; i < itemsList.size(); i++){
            PickUpItem item = itemsList.get(i);
            Rectangle itemRec = item.getBounds();
            if (playerRec.intersects(itemRec)) {
                item.collectItem();
                itemsList.remove(i);
            }
        }
    }

    public void addEnemiesKilled(int level, int x, int y){ //to be called by enemy handler
        if (level > levels){
            return;
        }
        System.out.println("current level is " + level);
        lastLevel = level;
        killedEnemies[level]++;
        Items pickup = getPickupItem();
        if (pickup != null) {
            System.out.println("item recieved!");
            System.out.println(pickup);
            createItem(x, y, pickup);
        }
    }

    private void createItem(int x, int y, Items item){
        PickUpItem pickup = new PickUpItem(player, x, y, item, maps);
        itemsList.add(pickup);
    }

    private Items getPickupItem() {
        System.out.println("wolves killed per level is " + killedEnemies[lastLevel]);
        Items item = null;
        int indexKilled = killedEnemies[lastLevel];
        if (indexKilled == 1) {
            return Items.KEY;
        }
        ArrayList indices = pickupIndices.get(lastLevel);
        System.out.println("indices are " + indices);
        if(indices.contains(indexKilled) && currentPickUps < levelPickUps.size()){
            item = levelPickUps.get(currentPickUps);
            currentPickUps++;
            System.out.println("current pick up is " + currentPickUps);
        }
        return item;
    }

}