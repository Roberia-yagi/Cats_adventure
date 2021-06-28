import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * 音を再生，停止する際に使うクラス
 */

public class MapGameSound {

    public void play_BGM() {
        Media m = new Media(new File("BGM.wav").toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);

        mp.setCycleCount(MediaPlayer.INDEFINITE);
        mp.play();
    }

    public void stop_BGM(){
        Media m = new Media(new File("BGM.wav").toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.stop();
    }

    public void play_BGM_Battle(){
        Media m = new Media(new File("BGM_Battle.wav").toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);

        mp.setCycleCount(MediaPlayer.INDEFINITE);
        mp.play();
    }

    public void stop_BGM_Battle(){
        Media m = new Media(new File("BGM_Battle.wav").toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.stop();
    }

    public void play_SE_item() {
        Media m = new Media(new File("SE_item.wav").toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);

        mp.setCycleCount(1);
        mp.play();
    }

    public void play_SE_goal() {
        Media m = new Media(new File("SE_goal.wav").toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);

        mp.setCycleCount(1);
        mp.play();
    }

    public void play_SE_attack() {
        Media m = new Media(new File("SE_attack.wav").toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);

        mp.setCycleCount(1);
        mp.play();
    }

    public void play_SE_spell() {
        Media m = new Media(new File("SE_spell.wav").toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);

        mp.setCycleCount(1);
        mp.play();
    }

    public void play_SE_levelup() {
        Media m = new Media(new File("SE_levelup.wav").toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);

        mp.setCycleCount(1);
        mp.play();
    }

    
    public void play_SE_heal() {
        Media m = new Media(new File("SE_heal.wav").toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);

        mp.setCycleCount(1);
        mp.play();
    }

}
