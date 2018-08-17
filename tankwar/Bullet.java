package tankwar;

import java.awt.Rectangle;

/**
 *
 * @author Jianfei Zhao
 * @email jzhao11@mail.sfsu.edu
 */
public class Bullet extends GameObject implements Runnable {

    private static int width = 16;
    private static int height = 16;

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    private int vx;
    private int vy;
    private int angle;
    private int bulletLevel;
    private String bulletName;

    public Bullet(int x, int y, int vx, int vy, int angle, int bulletLevel) {
        super(x, y);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.health = 1;
        this.bulletLevel = bulletLevel;
        this.bulletName = "Bullet" + bulletLevel;
        this.damage = bulletLevel * 5;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public String getBulletName() {
        return bulletName;
    }

    public void moveForwards() {
        vx = (int) Math.round(6 * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(6 * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        bounds.setLocation(x, y);
        checkBorder();
    }

    public void checkBorder() {
        if (x < 30 || x > 950 || y < 30 || y > 950) {
            isAlive = false;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                System.out.println("Bullet Exception: " + ex.getMessage());
            }
            moveForwards();
        }
    }
}
