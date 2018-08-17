package tankwar;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Jianfei Zhao
 * @email jzhao11@mail.sfsu.edu
 */
public class GameObject implements Collidable {

    protected static HashMap<String, BufferedImage> imgSrcs = new HashMap<>(); // store image resources

    static {
        try {
            imgSrcs.put("Background", ImageIO.read(GameObject.class.getResource("Resources/Background.bmp")));
            imgSrcs.put("Tank", ImageIO.read(GameObject.class.getResource("Resources/Tank.jpg")));
            imgSrcs.put("UnbreakableWall", ImageIO.read(GameObject.class.getResource("Resources/Wall0.gif")));
            imgSrcs.put("BreakableWall", ImageIO.read(GameObject.class.getResource("Resources/Wall1.gif")));
            imgSrcs.put("Bullet1", ImageIO.read(GameObject.class.getResource("Resources/Bullet1.png")));
            imgSrcs.put("Bullet2", ImageIO.read(GameObject.class.getResource("Resources/Bullet2.png")));
            imgSrcs.put("Bullet3", ImageIO.read(GameObject.class.getResource("Resources/Bullet3.png")));
            imgSrcs.put("Bullet4", ImageIO.read(GameObject.class.getResource("Resources/Bullet4.png")));
            imgSrcs.put("Bullet5", ImageIO.read(GameObject.class.getResource("Resources/Bullet5.png")));
            imgSrcs.put("Bullet6", ImageIO.read(GameObject.class.getResource("Resources/Bullet6.png")));
            imgSrcs.put("ExtraDamage", ImageIO.read(GameObject.class.getResource("Resources/Damage.png")));
            imgSrcs.put("ExtraLife", ImageIO.read(GameObject.class.getResource("Resources/Life.png")));
            imgSrcs.put("HealthHealing", ImageIO.read(GameObject.class.getResource("Resources/Health.png")));
            imgSrcs.put("Explosion0", ImageIO.read(GameObject.class.getResource("Resources/Explosion0.png")));
            imgSrcs.put("Explosion1", ImageIO.read(GameObject.class.getResource("Resources/Explosion1.png")));
            imgSrcs.put("Explosion2", ImageIO.read(GameObject.class.getResource("Resources/Explosion2.png")));
            imgSrcs.put("Explosion3", ImageIO.read(GameObject.class.getResource("Resources/Explosion3.png")));
            imgSrcs.put("Explosion4", ImageIO.read(GameObject.class.getResource("Resources/Explosion4.png")));
            imgSrcs.put("Explosion5", ImageIO.read(GameObject.class.getResource("Resources/Explosion5.png")));
        } catch (Exception ex) {
            System.out.println("GameObject Resource Not Found: " + ex.getMessage());
        }
    }

    public static BufferedImage getImg(String gameObjectName) {
        return imgSrcs.get(gameObjectName);
    }

    protected Rectangle bounds;
    protected int health;
    protected int damage;
    protected boolean isAlive;
    protected int x;
    protected int y;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.isAlive = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void collide(Collidable obj) {
        isAlive = false;
    }
}
