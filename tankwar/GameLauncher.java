package tankwar;

import javax.swing.JFrame;

/**
 *
 * @author Jianfei Zhao
 * @email jzhao11@mail.sfsu.edu
 */
public class GameLauncher extends JFrame {

    private GameBoard gameBoard;
    private Sound sound;

    public GameLauncher() {
        gameBoard = new GameBoard();
        sound = new Sound("Music.wav");
    }

    public void runGame() {
        Thread gameThread = new Thread(gameBoard);
        gameThread.start();
        this.add(gameBoard);
        this.addKeyListener(gameBoard);
        this.setTitle("Tank War");
        this.setSize(800, 670);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.sound.play(true);
    }

    public static void main(String[] args) {
        GameLauncher gameLauncher = new GameLauncher();
        gameLauncher.runGame();
    }
}
