package tankwar;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Jianfei Zhao
 * @email jzhao11@mail.sfsu.edu
 */
public class GameBoard extends JPanel implements KeyListener, Runnable {

    private static int windowWidth = 960;
    private static int windowHeight = 960;
    private static BufferedImage background = GameObject.getImg("Background");
    private static ArrayList<Wall> walls = new ArrayList<>();
    private static ArrayList<PowerUp> powerUps = new ArrayList<>();
    private static int powerUpDelay = 0;
    private static int powerUpRate = 1000;

    static {
        int wallWidth = Wall.getWidth();
        int wallHeight = Wall.getHeight();
        int column = windowWidth / wallWidth;
        int row = windowHeight / wallHeight;

        // unbreakable walls
        for (int i = 0; i < row; ++i) {
            walls.add(new Wall(0 * wallWidth, i * wallHeight, false));
            walls.add(new Wall((column - 1) * wallWidth, i * wallHeight, false));
            if (i > 0 && i < 5) {
                walls.add(new Wall((column - 9) * wallWidth, windowHeight - (i + 1) * wallHeight, false));
                walls.add(new Wall(8 * wallWidth, i * wallHeight, false));
            }
        }
        for (int j = 1; j < column - 1; ++j) {
            walls.add(new Wall(j * wallWidth, 0 * wallHeight, false));
            walls.add(new Wall(j * wallWidth, (row - 1) * wallHeight, false));
            if (j < 5) {
                walls.add(new Wall(j * wallHeight, (column - 9) * wallWidth, false));
                walls.add(new Wall(windowHeight - (j + 1) * wallHeight, 8 * wallWidth, false));
            }
        }

        // breakable walls
        for (int i = 1; i < row - 8; ++i) {
            walls.add(new Wall(5 * wallWidth, i * wallHeight, true));
            walls.add(new Wall((column - 6) * wallWidth, windowHeight - (i + 1) * wallHeight, true));
        }
        for (int j = 1; j < column - 8; ++j) {
            walls.add(new Wall(windowWidth - (j + 1) * wallWidth, 5 * wallHeight, true));
            walls.add(new Wall(j * wallWidth, (row - 6) * wallHeight, true));
        }
    }

    public static ArrayList<Wall> getWalls() {
        return walls;
    }

    private int screenX;
    private int screenY;
    private Tank tank1;
    private Tank tank2;

    public GameBoard() {
        tank1 = new Tank(60, 60, 0, 0, (short) 0, "Tank1");
        tank2 = new Tank(windowWidth - 100, windowHeight - 100, 0, 0, (short) 180, "Tank2");
    }

    // update the starting point (x, y) for left- and right-screen
    // should be called before loading left- and right-screen
    private void checkScreen(Tank tank) {
        if (tank.getX() < 100) {
            screenX = 0;
        } else if (tank.getX() > 550) {
            screenX = 450;
        } else {
            screenX = tank.getX() - 100;
        }

        if (tank.getY() < 100) {
            screenY = 0;
        } else if (tank.getY() > 300) {
            screenY = 200;
        } else {
            screenY = tank.getY() - 100;
        }
    }

    private void checkCollision(Graphics g, Tank tank1, Tank tank2) {
        // bullet-tank collision
        // bullet-wall collision
        Bullet bullet;
        for (int i = 0; i < tank1.getBullets().size(); ++i) {
            bullet = tank1.getBullets().get(i);
            if (tank2.getNumOfLives() > 0) {
                if (collide(tank2, bullet)) {
                    loadExplosion(g, bullet);
                }
            }
            for (int j = 0; j < walls.size(); ++j) {
                if (collide(walls.get(j), bullet)) {
                    loadExplosion(g, bullet);
                }
            }
        }

        // powerup-tank collision
        PowerUp powerUp;
        for (int i = 0; i < powerUps.size(); ++i) {
            powerUp = powerUps.get(i);
            if (collide(powerUp, tank1)) {
                powerUp.powerUpTank(tank1);
            }
        }
    }

    private boolean collide(Collidable obj1, Collidable obj2) {
        if (obj1.getBounds().intersects(obj2.getBounds())) {
            obj1.collide(obj2);
            obj2.collide(obj1);
            return true;
        } else {
            return false;
        }
    }

    private void loadMap(Graphics g) {
        loadBackground(g);
        loadWall(g);
        loadTank(g, tank1);
        loadTank(g, tank2);
        loadPowerUp(g);
        checkCollision(g, tank1, tank2);
        checkCollision(g, tank2, tank1);
    }

    private void loadWall(Graphics g) {
        Wall wall;
        for (int i = 0; i < walls.size(); ++i) {
            wall = walls.get(i);
            if (wall.isAlive()) {
                g.drawImage(Wall.getImg(wall.getWallName()), wall.getX(), wall.getY(), Wall.getWidth(), Wall.getHeight(), null);
            } else {
                walls.remove(i);
            }
        }
    }

    private void loadBackground(Graphics g) {
        int backgroundWidth = background.getWidth();
        int backgroundHeight = background.getHeight();
        int column = (int) Math.ceil((double) windowWidth / backgroundWidth);
        int row = (int) Math.ceil((double) windowHeight / backgroundHeight);
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                g.drawImage(background, j * backgroundWidth, i * backgroundHeight, backgroundWidth, backgroundHeight, this);
            }
        }
    }

    private void loadPowerUp(Graphics g) {
        generateRandPowerUp();
        PowerUp powerUp;
        for (int i = 0; i < powerUps.size(); ++i) {
            powerUp = powerUps.get(i);
            if (powerUp.isAlive()) {
                g.drawImage(PowerUp.getImg(powerUp.getPowerUpName()), powerUp.getX(), powerUp.getY(), PowerUp.getWidth(), PowerUp.getHeight(), null);
            } else {
                powerUps.remove(i);
            }
        }
    }

    // generate a new powerup based on a time-delay manner
    // powerup with random type and position
    private void generateRandPowerUp() {
        if (powerUpDelay++ % powerUpRate == 0) {
            int random = Math.abs((int) System.currentTimeMillis() % 640);
            int x = 160 + random;
            int y = 800 - random;
            switch (random % 3) {
                case 0:
                    powerUps.add(new ExtraLife(x, y));
                    powerUps.add(new ExtraLife(y, x));
                    break;
                case 1:
                    powerUps.add(new HealthHealing(x, y));
                    powerUps.add(new HealthHealing(y, x));
                    break;
                case 2:
                    powerUps.add(new ExtraDamage(x, y));
                    powerUps.add(new ExtraDamage(y, x));
                    break;
                default:
                    break;
            }
        }
    }

    // load tank together with its health bar, lives count, and bullets
    private void loadTank(Graphics g, Tank tank) {
        if (tank.getNumOfLives() > 0) {
            BufferedImage img = Tank.getImg("Tank");
            AffineTransform rotation = AffineTransform.getTranslateInstance(tank.getX(), tank.getY());
            rotation.rotate(Math.toRadians(tank.getAngle()), img.getWidth() / 2, img.getHeight() / 2);
            ((Graphics2D) g).drawImage(img, rotation, null);

            loadHealthBar(g, tank);
            loadLivesCount(g, tank);
            tank.update(null, tank);
            loadBullet(g, tank);
        }
    }

    private void loadBullet(Graphics g, Tank tank) {
        Bullet bullet;
        Iterator<Bullet> itr = tank.getBullets().iterator();
        while (itr.hasNext()) {
            bullet = itr.next();
            if (bullet.isAlive()) {
                g.drawImage(Bullet.getImg(bullet.getBulletName()), bullet.getX(), bullet.getY(), Bullet.getWidth(), Bullet.getWidth(), null);
            } else {
                itr.remove();
            }
        }
    }

    private void loadHealthBar(Graphics g, Tank tank) {
        int health = tank.getHealth();
        if (health > 0) {
            Color color;
            if (health > 75) {
                color = Color.GREEN;
            } else if (health > 50) {
                color = Color.YELLOW;
            } else if (health > 25) {
                color = Color.ORANGE;
            } else {
                color = Color.RED;
            }
            g.setColor(color);
            g.fill3DRect(tank.getX() - 20, tank.getY() - 30, health, 10, false);
        }
    }

    private void loadLivesCount(Graphics g, Tank tank) {
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(tank.getTankName(), tank.getX() - 30, tank.getY() + 75);
        int livesCount = tank.getNumOfLives();
        for (int i = 0; i < livesCount; ++i) {
            g.fillOval(tank.getX() + (i + 1) * 20, tank.getY() + 60, 15, 15);
        }
    }

    private void loadExplosion(Graphics g, Bullet bullet) {
        Sound sound = new Sound("ExplosionLarge.wav");
        sound.play(false);
        for (int i = 0; i < 30; ++i) {
            g.drawImage(GameObject.getImg("Explosion" + i % 6), bullet.getX(), bullet.getY(), 64, 64, this);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                tank1.toggleUpPressed();
                break;
            case KeyEvent.VK_S:
                tank1.toggleDownPressed();
                break;
            case KeyEvent.VK_A:
                tank1.toggleLeftPressed();
                break;
            case KeyEvent.VK_D:
                tank1.toggleRightPressed();
                break;
            case KeyEvent.VK_SPACE:
                tank1.toggleFirePressed();
                break;
            case KeyEvent.VK_UP:
                tank2.toggleUpPressed();
                break;
            case KeyEvent.VK_DOWN:
                tank2.toggleDownPressed();
                break;
            case KeyEvent.VK_LEFT:
                tank2.toggleLeftPressed();
                break;
            case KeyEvent.VK_RIGHT:
                tank2.toggleRightPressed();
                break;
            case KeyEvent.VK_ENTER:
                tank2.toggleFirePressed();
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                tank1.unToggleUpPressed();
                break;
            case KeyEvent.VK_S:
                tank1.unToggleDownPressed();
                break;
            case KeyEvent.VK_A:
                tank1.unToggleLeftPressed();
                break;
            case KeyEvent.VK_D:
                tank1.unToggleRightPressed();
                break;
            case KeyEvent.VK_SPACE:
                tank1.unToggleFirePressed();
                break;
            case KeyEvent.VK_UP:
                tank2.unToggleUpPressed();
                break;
            case KeyEvent.VK_DOWN:
                tank2.unToggleDownPressed();
                break;
            case KeyEvent.VK_LEFT:
                tank2.unToggleLeftPressed();
                break;
            case KeyEvent.VK_RIGHT:
                tank2.unToggleRightPressed();
                break;
            case KeyEvent.VK_ENTER:
                tank2.unToggleFirePressed();
                break;
            default:
                break;
        }
    }

    // check if there is a winner
    // if a tank has no more life, it is the loser and the other tank is the winner
    // remove loser tank's bullets to stop damage calculation
    private String checkWinner() {
        if (tank1.getNumOfLives() == 0) {
            tank1.getBullets().clear();
            return "Tank2 Wins !";
        } else if (tank2.getNumOfLives() == 0) {
            tank2.getBullets().clear();
            return "Tank1 Wins !";
        } else {
            return null;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage board = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
        Graphics rawMap = board.createGraphics();

        // check winning conditions
        // if no winner, then loading map; otherwise, loading background with the winner
        String winnerInfo = checkWinner();
        if (winnerInfo != null) {
            loadBackground(g);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(winnerInfo, 320, 320);
        } else {
            loadMap(rawMap);

            // acquire mini-map, left-screen, and right-screen
            BufferedImage miniMap = board.getSubimage(0, 0, windowWidth, windowHeight);
            checkScreen(tank1);
            BufferedImage leftScreen = board.getSubimage(screenX, screenY, 510, 760);
            checkScreen(tank2);
            BufferedImage rightScreen = board.getSubimage(screenX, screenY, 510, 760);

            // load map
            g.drawImage(leftScreen, 0, 0, 400, 640, null);
            g.drawImage(rightScreen, 400, 0, 400, 640, null);
            g.drawLine(400, 0, 400, 640);
            g.drawImage(miniMap, 300, 440, 200, 200, null);
            g.dispose();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                System.out.println("GameBoard Exception: " + ex.getMessage());
            }
            repaint();
        }
    }
}
