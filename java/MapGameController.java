import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.text.*;


/**
 * ゲームの入力対する処理を司るクラス
 * @author Group19
 * @Version 4.0
 */

public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public static MoveEnemy[][] enemy;
    public Boss boss;
    public static CharaList charaStats;
    public static EnemyList[][] enemyStats;
    public static int enemy_id; //バトル時に参照する敵のid
    public ItemList itemStats;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;
    public static int floor = 1;
    public int enemySize;
    public static int Score = 0;
    public int[] eachItemCount = new int[5];
    @FXML Label itemCountShow;
    @FXML Label CharaHpShow;
    @FXML Label FloorShow;
    @FXML Label ScoreShow;
    @FXML Label Item0Show;
    @FXML Label Item1Show;
    @FXML Label Item2Show;
    @FXML Label Item3Show;
    @FXML Label Item4Show;
    @FXML Label LevelShow;

    public Group[] mapGroups;
    public MapGameSound sound = new MapGameSound();


    /**
     * 初期化するメソッド
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Font.loadFont(getClass().getResourceAsStream("/font/DragonQuestFC.ttf"), 14);
        mapData = new MapData(41,23);
        chara = new MoveChara(1,1,mapData);
        charaStats = new CharaList();
        enemySize = 10;
        enemyStats = new EnemyList[4][enemySize];
        enemy = new MoveEnemy[4][enemySize];
        itemStats = new ItemList();
        for(int i = 0; i < enemySize; i++){
                enemyStats[floor][i] = new EnemyList();
                enemy[floor][i] = new MoveEnemy(i,mapData, enemyStats[floor]);
        }
        changeStatus();
        floorUp();
        mapImageViews = new ImageView[mapData.getHeight()*mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x,y);
            }
        }
        mapPrint(chara, enemy[floor], boss, mapData);
    }

    /**
     * ゴールした際に新しいマップを作成するメソッド
     */
    public void createMap(){
        mapData = new MapData(41,23);
        chara = new MoveChara(1,1,mapData);
        if(floor==3){
            boss = new Boss(mapData);
        }
        for(int i = 0; i < enemySize; i++){
            enemyStats[floor][i] = new EnemyList();
            enemy[floor][i] = new MoveEnemy(i,mapData, enemyStats[floor]);
        }
        floorUp();
        mapGroups = new Group[mapData.getHeight()*mapData.getWidth()];
        mapImageViews = new ImageView[mapData.getHeight()*mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x,y);
            }
        }
        mapPrint(chara, enemy[floor], boss, mapData);
    }
    
    /**
     * 移動や新しいマップを作成した際にマップの表示を変えるメソッド
     */
    public void mapPrint(MoveChara c, MoveEnemy[] e, Boss b, MapData m){
        itemCountShow.setText("0");
        ScoreShow.setText("0");
        String tmp  = Integer.toString(charaStats.getChara().getLevel().getCur());
        LevelShow.setText(tmp);
        
        int cx = MoveChara.getPosX();
        int cy = MoveChara.getPosY();
        int[] ex;
        int[] ey;
        ex = new int[enemySize];
        ey = new int[enemySize];
        for(int i = 0; i < enemySize; i++){
            ex[i] = e[i].getPosX();
            ey[i] = e[i].getPosY();
        }
        mapGrid.getChildren().clear();
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                if (x==cx && y==cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                }
                else if(x==ex[0] && y==ey[0]){
                    mapGrid.add(e[0].getEnemyImageView(), x, y);
                }
                else if(x==ex[1] && y==ey[1]){
                    mapGrid.add(e[1].getEnemyImageView(), x, y);
                }
                else if(x==ex[2] && y==ey[2]){
                    mapGrid.add(e[2].getEnemyImageView(), x, y);
                }
                else if(x==ex[3] && y==ey[3]){
                    mapGrid.add(e[3].getEnemyImageView(), x, y);
                }
                else if(x==ex[4] && y==ey[4]){
                    mapGrid.add(e[4].getEnemyImageView(), x, y);
                }
                else if(x==ex[5] && y==ey[5]){
                    mapGrid.add(e[5].getEnemyImageView(), x, y);
                }
                else if(x==ex[6] && y==ey[6]){
                    mapGrid.add(e[6].getEnemyImageView(), x, y);
                }
                else if(x==ex[7] && y==ey[7]){
                    mapGrid.add(e[7].getEnemyImageView(), x, y);
                }
                else if(x==ex[8] && y==ey[8]){
                    mapGrid.add(e[8].getEnemyImageView(), x, y);
                }
                else if(x==ex[9] && y==ey[9]){
                    mapGrid.add(e[9].getEnemyImageView(), x, y);
                }
                else if(floor == 3 && x == 39 && y == 21){
                    mapGrid.add(b.getEnemyImageView(), x, y);
                }
                else {
                    mapImageViews[index] = mapData.getImageView(x,y);
                    mapGrid.add(mapImageViews[index], x, y);
                }
            }
        }
    }

    /**
移動したタイルにアイテムがあった場合itemCountの表示とキャラのステータスを変えるメソッド     */
    public void pickItem(){
        int r;
        r = (int)(Math.random() * eachItemCount.length);
        charaStats.getChara().getItemEffect(itemStats.getItem(r));
        eachItemCount[r]++;
        changeScore(100);
        return;
    }

    /**
     * 画面のステータスの表示を変更するメソッド
     */
    public void changeStatus(){
        String CharaHp;
        String CharaMoney;
        String CurHP = Integer.toString(charaStats.getChara().getHp().getCur());
        CharaHpShow.setText(CurHP);
        
        String CurMoney = Integer.toString(charaStats.getChara().getMoney().getCur());

        String itemCount;
        itemCount = Integer.toString(chara.getItemCount());
        itemCountShow.setText(itemCount);
        Item0Show.setText(Integer.toString(eachItemCount[0]));
        Item1Show.setText(Integer.toString(eachItemCount[1]));
        Item2Show.setText(Integer.toString(eachItemCount[2]));
        Item3Show.setText(Integer.toString(eachItemCount[3]));
        Item4Show.setText(Integer.toString(eachItemCount[4]));

        String Score_s;
        Score_s = Integer.toString(Score);
        ScoreShow.setText(Score_s);
    }

    public static EnemyList getEnemy(int i){
        return enemyStats[floor][i];
    }

    /**
     * 階層の表示を変更するメソッド
     */
    public void floorUp(){
        String floor_s;
        floor_s = Integer.toString(floor);
        FloorShow.setText(floor_s);
    }

        /**
    * 得点を変化（下限０）させ、表示を変えるメゾット
    */
    public void changeScore(int i){
        String Score_s;
        Score += i;
        if(Score<0)Score = 0;
        Score_s = Integer.toString(Score);
        ScoreShow.setText(Score_s);
    }

    public void func1ButtonAction(ActionEvent event) { }
    public void func2ButtonAction(ActionEvent event) { }
    public void func3ButtonAction(ActionEvent event) { }
    public void func4ButtonAction(ActionEvent event) { }

    public void keyAction(KeyEvent event){
        KeyCode key = event.getCode();
        if (key == KeyCode.DOWN){
            downButtonAction();
        }else if (key == KeyCode.RIGHT){
            rightButtonAction();
        }else if (key == KeyCode.UP){
            upButtonAction();
        }else if (key == KeyCode.LEFT){
            leftButtonAction();
        }
    }

    public void outputAction(String actionString) {
        System.out.println("Select Action: " + actionString);
    }

    public void goalChecker(){
        if(chara.goalchecker()){
            sound.play_SE_goal();
            floor++;
            createMap();
            changeScore(200);
        }
    }

    public void battleChecker(){
        for(int i = 0; i < enemySize; i++){
            int x = enemy[floor][i].getPosX();
            int y = enemy[floor][i].getPosY();
            if(chara.battleChecker(x, y)){
                enemy_id = i;
                MapGame.getInstance().battle();
            }
        }
        if(floor == 3 && MoveChara.getPosX() == 39 && MoveChara.getPosY() == 21){
            MapGame.getInstance().bossBattle();
        }
    }

    public void upButtonAction(){
        outputAction("UP");
        chara.setCharaDir(MoveChara.TYPE_UP);
        if(chara.pickItem(0, -1)){
            sound.play_SE_item();
            pickItem();
        }
        for(int i = 0; i < enemySize; i++){
            enemy[floor][i].move();
        }
        chara.move(0, -1);

        mapPrint(chara, enemy[floor], boss, mapData);
        changeStatus();
        battleChecker();
        goalChecker();
    }

    public void downButtonAction(){
        outputAction("DOWN");
        chara.setCharaDir(MoveChara.TYPE_DOWN);
        if(chara.pickItem(0, 1)){
            sound.play_SE_item();
            pickItem();
        }
        for(int i = 0; i < enemySize; i++){
            enemy[floor][i].move();
        }
        chara.move(0, 1);
        mapPrint(chara, enemy[floor], boss, mapData);
        changeStatus();
        battleChecker();
        goalChecker();
    }

    public void leftButtonAction(){
        outputAction("LEFT");
        chara.setCharaDir(MoveChara.TYPE_LEFT);
        if(chara.pickItem(-1, 0)){
            sound.play_SE_item();
            pickItem();
        }
        for(int i = 0; i < enemySize; i++){
            enemy[floor][i].move();
        }
        chara.move(-1, -0);
        mapPrint(chara, enemy[floor], boss, mapData);
        changeStatus();
        battleChecker();
        goalChecker();
    }

    public void rightButtonAction(){
        outputAction("RIGHT");
        chara.setCharaDir(MoveChara.TYPE_RIGHT);
        if(chara.pickItem(1, 0)){
            sound.play_SE_item();
            pickItem();
        }
        for(int i = 0; i < enemySize; i++){
            enemy[floor][i].move();
        }
        chara.move(1, 0);
        mapPrint(chara, enemy[floor], boss, mapData);
        changeStatus();
        battleChecker();
        goalChecker();
    }
}
