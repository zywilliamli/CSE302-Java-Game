import java.awt.*;

public interface Weapon {

    public void attack();
    public void paint(Graphics2D g);
    public void checkCollision();
}