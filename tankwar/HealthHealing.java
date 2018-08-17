package tankwar;

/**
 *
 * @author Jianfei Zhao
 * @email jzhao11@mail.sfsu.edu
 */
public class HealthHealing extends PowerUp {
    
    public HealthHealing(int x, int y) {
        super(x, y);
        this.powerUpName = "HealthHealing";
    }
    
    @Override
    public void powerUpTank(Tank tank) {
        tank.addHealth(50);
    }
}
