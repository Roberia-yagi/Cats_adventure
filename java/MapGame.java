import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;


public class MapGame extends Application {
    Stage stage;
    Pane primaryPane;
    Scene primaryScene;
    public static MapGame singleton;
    public MapGameSound sound = new MapGameSound();

    @Override
    public void start(Stage primaryStage) throws Exception {
        sound.play_BGM();
        singleton = this;
    	stage = primaryStage;
    	primaryStage.setTitle("MAP GAME");
    	primaryPane = (Pane)FXMLLoader.load(getClass().getResource("MapGame.fxml"));
    	primaryScene = new Scene(primaryPane);
    	primaryStage.setScene(primaryScene);
    	primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public  static MapGame getInstance(){
        return singleton;
    }

    public void battle() {
        try {
            Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Battle.fxml"));
            Scene myScene = new Scene(myPane);
            stage.setScene(myScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bossBattle(){
        try {
            Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("BossBattle.fxml"));
            Scene myScene = new Scene(myPane);
            stage.setScene(myScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        try {
            Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Clear.fxml"));
            Scene myScene = new Scene(myPane);
            stage.setScene(myScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lose(){
        try {
            Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Lose.fxml"));
            Scene myScene = new Scene(myPane);
            stage.setScene(myScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Map(){
            Scene myScene = primaryScene;
            stage.setScene(myScene);
            stage.show();
    }
}
