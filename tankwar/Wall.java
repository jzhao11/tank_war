package tankwar;

import java.awt.Rectangle;

/**
 *
 * @author Jianfei Zhao
 * @email jzhao11@mail.sfsu.edu
 */
public class Wall extends GameObject {

    private static int width = 40;
    private static int height = 40;

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    private boolean isBreakable;
    private String wallName;

    public Wall(int x, int y, boolean isBreakable) {
        super(x, y);
        this.isBreakable = isBreakable;
        this.wallName = isBreakable ? "BreakableWall" : "UnbreakableWall";
        this.health = 10;
        this.damage = 0;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public String getWallName() {
        return wallName;
    }

    @Override
    public void collide(Collidable obj) {
        if (isBreakable) {
            health = (health > obj.getDamage()) ? health - obj.getDamage() : 0;
            if (health == 0) {
                isAlive = false;
            }
        }
    }
}
