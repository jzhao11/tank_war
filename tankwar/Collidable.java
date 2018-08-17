package tankwar;

import java.awt.Rectangle;

/**
 *
 * @author Jianfei Zhao
 * @email jzhao11@mail.sfsu.edu
 */
public interface Collidable {

    public Rectangle getBounds();

    public int getDamage();

    public void collide(Collidable obj);
}
