package tankwar;

import java.awt.Rectangle;

/**
 *
 * @author Jianfei Zhao
 * @email jzhao11@mail.sfsu.edu
 */
public class PowerUp extends GameObject {

    private static int width = 32;
    private static int height = 32;

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }

    protected String powerUpName;

    public PowerUp(int x, int y) {
        super(x, y);
        this.health = 5;
        this.damage = 0;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void powerUpTank(Tank tank) {
    }

    public String getPowerUpName() {
        return powerUpName;
    }
}
