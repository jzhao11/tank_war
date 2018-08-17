package tankwar;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Anthony Souza, modified by Jianfei Zhao
 */
public class Tank extends GameObject implements Observer {

    private int fireRate = 17; // work with fireDelay to prevent "laserbeam" shooting
    private int fireDelay = 0; // work with fireRate to prevent "laserbeam" shooting
    private int r = 6;
    private int vx;
    private int vy;
    private short angle;
    private String tankName;
    private int numOfLives;
    private boolean isUpPressed;
    private boolean isDownPressed;
    private boolean isRightPressed;
    private boolean isLeftPressed;
    private boolean isFirePressed;
    private LinkedList<Bullet> bullets;
    private int bulletLevel;

    public Tank(int x, int y, int vx, int vy, short angle, String tankName) {
        super(x, y);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.tankName = tankName;
        this.numOfLives = 3;
        this.bullets = new LinkedList<>();
        this.health = 100;
        this.damage = 0;
        this.bounds = new Rectangle(x, y, 40, 40);
        this.bulletLevel = 1;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth(int healingPoints) {
        health = (health + healingPoints > 100) ? 100 : health + healingPoints;
    }

    public void addNumOfLives() {
        if (numOfLives < 3) {
            numOfLives++;
        }
    }

    public void levelUpBullets() {
        if (bulletLevel < 6) {
            bulletLevel++;
        }
    }

    public int getAngle() {
        return angle;
    }

    public String getTankName() {
        return tankName;
    }

    public int getNumOfLives() {
        return numOfLives;
    }

    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    public void toggleUpPressed() {
        isUpPressed = true;
    }

    public void toggleDownPressed() {
        isDownPressed = true;
    }

    public void toggleRightPressed() {
        isRightPressed = true;
    }

    public void toggleLeftPressed() {
        isLeftPressed = true;
    }

    public void toggleFirePressed() {
        isFirePressed = true;
    }

    public void unToggleFirePressed() {
        isFirePressed = false;
    }

    public void unToggleUpPressed() {
        isUpPressed = false;
    }

    public void unToggleDownPressed() {
        isDownPressed = false;
    }

    public void unToggleRightPressed() {
        isRightPressed = false;
    }

    public void unToggleLeftPressed() {
        isLeftPressed = false;
    }

    @Override
    public void update(Observable o, Object o1) {
        if (isUpPressed) {
            moveForwards();
        }
        if (isDownPressed) {
            moveBackwards();
        }
        if (isLeftPressed) {
            rotateLeft();
        }
        if (isRightPressed) {
            rotateRight();
        }
        if (isFirePressed) {
            openFire();
        }
    }

    // shoot a new bullet based on a time-delay manner
    private void openFire() {
        if (fireDelay++ % fireRate == 0) {
            Bullet bullet = new Bullet(x + 20, y + 20, vx, vy, angle, bulletLevel);
            bullets.add(bullet);
            Thread bulletThread = new Thread(bullet);
            bulletThread.start();
        }
    }

    private void rotateLeft() {
        angle -= 3;
    }

    private void rotateRight() {
        angle += 3;
    }

    private void moveBackwards() {
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        bounds.setLocation(x, y);
        if (checkBorder()) {
            x += vx;
            y += vy;
        }
    }

    private void moveForwards() {
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        bounds.setLocation(x, y);
        if (checkBorder()) {
            x -= vx;
            y -= vy;
        }
    }

    private boolean checkBorder() {
        ArrayList<Wall> walls = GameBoard.getWalls();
        for (int i = 0; i < walls.size(); ++i) {
            if (this.bounds.intersects(walls.get(i).getBounds())) {
                return true;
            }
        }
        return false;
    }

    public void revive() {
        if (numOfLives > 0) {
            numOfLives--;
            isAlive = true;
            health = 100;
        }
    }

    @Override
    public void collide(Collidable obj) {
        health = (health > obj.getDamage()) ? health - obj.getDamage() : 0;
        if (health == 0) {
            isAlive = false;
            revive();
        }
    }
}
