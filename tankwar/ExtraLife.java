package tankwar;

/**
 *
 * @author Jianfei Zhao
 * @email jzhao11@mail.sfsu.edu
 */
public class ExtraLife extends PowerUp {

    public ExtraLife(int x, int y) {
        super(x, y);
        this.powerUpName = "ExtraLife";
    }

    @Override
    public void powerUpTank(Tank tank) {
        tank.addNumOfLives();
    }
}
