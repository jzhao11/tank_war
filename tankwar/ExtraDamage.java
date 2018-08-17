package tankwar;

/**
 *
 * @author Jianfei Zhao
 * @email jzhao11@mail.sfsu.edu
 */
public class ExtraDamage extends PowerUp {
    
    public ExtraDamage(int x, int y) {
        super(x, y);
        this.powerUpName = "ExtraDamage";
    }
    
    @Override
    public void powerUpTank(Tank tank) {
        tank.levelUpBullets();
    }
}
