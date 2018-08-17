package tankwar;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

/**
 *
 * @author Jianfei Zhao
 * @email jzhao11@mail.sfsu.edu
 */
public class Sound {

    private AudioInputStream soundStream;
    private Clip clip;

    public Sound(String fileName) {
        try {
            soundStream = AudioSystem.getAudioInputStream(Sound.class.getResource("Resources/" + fileName));
            clip = AudioSystem.getClip();
            clip.open(soundStream);
        } catch (Exception ex) {
            System.out.println("Sound Resource Not Found: " + ex.getMessage());
        }
    }

    public void play(boolean isLoopPlayBack) {
        clip.start();
        if (isLoopPlayBack) {
            clip.loop(clip.LOOP_CONTINUOUSLY);
        }
    }
}
