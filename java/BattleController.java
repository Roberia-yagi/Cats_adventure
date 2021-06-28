import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.Initializable;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;
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
import javafx.scene.shape.Rectangle;
import javafx.application.Platform;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.EventHandler;

/**
 * バトルの入力対する処理を司るクラス
 * 
 * @author Group19
 * @Version 4.0
 */

public class BattleController implements Initializable {
  private int id;
  private int Chara_Level;
  private int Chara_HP;
  private int Chara_MP;
  private int Chara_STR;
  private int Chara_DEF;
  private int Chara_LUK;
  private int Chara_MONEY;
  private int Chara_EXP;
  private int Enemy_Level;
  private int Enemy_HP;
  private int Enemy_MP;
  private int Enemy_STR;
  private int Enemy_DEF;
  private int Enemy_LUK;
  private int Enemy_MONEY;
  private int Enemy_EXP;
  private final String[] dirStrings = { "d", "l", "r", "u" };
  private final String[] kindStrings = { "1", "2", "3" };
  private final String[] enemyKindStrings = { "1", "2", "3", "4" };
  private final String[] attackKindStrings = { "1", "2", "3", "4", "5", "6" };
  private final String[] healKindStrings = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" };
  private final String enemyPngPathBefore = "png/battle/enemy";
  private final String pngPathAfter = ".png";
  private final String attackPngPathBefore = "png/effects/attack";
  private final String healPngPathBefore = "png/effects/heal";
  private int enemyType;
  private Image[][] enemyImages;
  private ImageView[] enemyImageViews;
  private ImageEnemyAnimation[] enemyImageAnimations;
  private Image[][][] attackImages;
  private ImageView[][] attackImageViews;
  private ImageAttackAnimation[][] attackImageAnimations;
  private Image[][] healImages;
  private ImageView[] healImageViews;
  private ImageHealAnimation[] healImageAnimations;
  @FXML
  Pane enemyPane;
  @FXML
  Label battleText1;
  @FXML
  Label battleText2;
  @FXML
  Label hp;
  @FXML
  Label mp;
  @FXML
  Label level;
  private String oldTxt;
  private Timeline timeline;
  private boolean finish = false;
  private String tmp = "";
  private int floor;
  public MapGameSound sound = new MapGameSound();

  /**
   * 初期化
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    sound.stop_BGM();
    sound.play_BGM_Battle();
    Font.loadFont(getClass().getResourceAsStream("/font/DragonQuestFC.ttf"), 14);
    floor = MapGameController.floor;
    initializeStates();
    initializeEnemyAnimation();
    initializeAttackAnimation();
    initializeHealAnimation();
    oldTxt = "たたかいがはじまった!";
    hp.setText(changeStatus(Chara_HP));
    mp.setText(changeStatus(Chara_MP));
    level.setText(changeStatus(Chara_Level));
  }

  /**
   * 初期化時にステータスを取得するメソッド
   */

  public void initializeStates() {
    id = MapGameController.enemy_id;
    enemyType = MapGameController.enemyStats[floor][id].getEnemyType();
    Chara_Level = MapGameController.charaStats.getChara().getLevel().getCur();
    Chara_HP = MapGameController.charaStats.getChara().getHp().getCur();
    Chara_MP = MapGameController.charaStats.getChara().getMp().getCur();
    Chara_STR = MapGameController.charaStats.getChara().getStr().getCur();
    Chara_DEF = MapGameController.charaStats.getChara().getDef().getCur();
    Chara_LUK = MapGameController.charaStats.getChara().getLuk().getCur();
    Chara_MONEY = MapGameController.charaStats.getChara().getMoney().getCur();
    Chara_EXP = MapGameController.charaStats.getChara().getExp().getCur();

    Enemy_Level = MapGameController.enemyStats[floor][id].getEnemyStats().getLevel().getCur();
    Enemy_HP = MapGameController.enemyStats[floor][id].getEnemyStats().getHp().getCur();
    Enemy_STR = MapGameController.enemyStats[floor][id].getEnemyStats().getStr().getCur();
    Enemy_DEF = MapGameController.enemyStats[floor][id].getEnemyStats().getDef().getCur();
    Enemy_LUK = MapGameController.enemyStats[floor][id].getEnemyStats().getLuk().getCur();
    Enemy_MONEY = MapGameController.enemyStats[floor][id].getEnemyStats().getMoney().getCur();
    Enemy_EXP = MapGameController.enemyStats[floor][id].getEnemyStats().getExp().getCur();
  }

  /**
   * 敵のアニメーション
   */

  public void initializeEnemyAnimation() {
    enemyImages = new Image[4][6];
    enemyImageViews = new ImageView[6];
    enemyImageAnimations = new ImageEnemyAnimation[4];
    for (int i = 0; i < 4; i++) {
      enemyImages[i] = new Image[3];
      for (int j = 0; j < 3; j++) {
        enemyImages[i][j] = new Image(
            enemyPngPathBefore + enemyKindStrings[enemyType] + dirStrings[i] + kindStrings[j] + pngPathAfter, 100, 0,
            true, false);
      }
      enemyImageViews[i] = new ImageView(enemyImages[i][0]);
      enemyImageAnimations[i] = new ImageEnemyAnimation(enemyImageViews[i], enemyImages[i]);
    }
    enemyImageAnimations[0].start();
    enemyPane.getChildren().clear();
    enemyPane.getChildren().add(getEnemyImageView());
  }

  /**
   * 攻撃時のアニメーション
   */

  public void initializeAttackAnimation() {
    attackImages = new Image[4][4][6];
    attackImageViews = new ImageView[4][6];
    attackImageAnimations = new ImageAttackAnimation[4][6];
    for (int i = 0; i < 2; i++) {
      attackImages[i][0] = new Image[6];
      for (int j = 0; j < 6; j++) {
        attackImages[i][0][j] = new Image(
            attackPngPathBefore + attackKindStrings[i] + attackKindStrings[j] + pngPathAfter, 100, 0, true, false);
      }
      attackImageViews[i][0] = new ImageView(attackImages[i][0][0]);
      attackImageAnimations[i][0] = new ImageAttackAnimation(attackImageViews[i][0], attackImages[i][0]);
    }
  }

  /**
   * 回復のアニメーション
   */

  public void initializeHealAnimation() {
    healImages = new Image[4][6];
    healImageViews = new ImageView[6];
    healImageAnimations = new ImageHealAnimation[13];
    for (int i = 0; i < 1; i++) {
      healImages[i] = new Image[13];
      for (int j = 0; j < 13; j++) {
        healImages[i][j] = new Image(healPngPathBefore + 1 + healKindStrings[j] + pngPathAfter, 100, 0, true, false);
      }
      healImageViews[i] = new ImageView(healImages[i][0]);
      healImageAnimations[i] = new ImageHealAnimation(healImageViews[i], healImages[i]);
    }
  }

  /**
   * 半角を全角に
   */

  public String FullWidth(String str) {
    if (str == null) {
      throw new IllegalArgumentException();
    }
    StringBuffer sb = new StringBuffer(str);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if ('0' <= c && c <= '9') {
        sb.setCharAt(i, (char) (c - '0' + '０'));
      }
    }
    return sb.toString();
  }

  /**
   * テキストを変更
   */

  public void changeText(String newTxt) {
    battleText2.setText(oldTxt);
    oldTxt = newTxt;
    battleText1.setText(oldTxt);
  }

  /**
   * ステータス表示を変更
   */

  public String changeStatus(int value) {
    return FullWidth(Integer.toString(value));
  }

  /**
   * ステータスをMapGameControllerへ
   */

  public void changeStatus() {
    MapGameController.charaStats.getChara().getLevel().setCur(Chara_Level);
    MapGameController.charaStats.getChara().getHp().setCur(Chara_HP);
    MapGameController.charaStats.getChara().getMp().setCur(Chara_MP);
    MapGameController.charaStats.getChara().getStr().setCur(Chara_STR);
    MapGameController.charaStats.getChara().getDef().setCur(Chara_DEF);
    MapGameController.charaStats.getChara().getLuk().setCur(Chara_LUK);
    MapGameController.charaStats.getChara().getMoney().setCur(Chara_MONEY);
    MapGameController.charaStats.getChara().getExp().setCur(Chara_EXP);
    MapGameController.enemyStats[floor][id].getEnemyStats().getHp().setCur(Enemy_HP);
  }

  /**
   * レベルアップした際のステータスアップ
   */

  public void levelUp() {
    Chara_HP = MapGameController.charaStats.getChara().getHp().getMax();
    Chara_HP += 100;
    MapGameController.charaStats.getChara().getHp().setMax(Chara_HP);
    Chara_MP = MapGameController.charaStats.getChara().getMp().getMax();
    Chara_MP += 30;
    MapGameController.charaStats.getChara().getMp().setMax(Chara_MP);
    Chara_STR += 50;
    Chara_DEF += 50;
    Chara_LUK += 1;
  }

  /**
   * レベルが上ったかどうかを判定
   */

  public boolean levelUpChecker() {
    if (Chara_Level == 1 && 100 <= Chara_EXP && Chara_EXP <= 700) {
      return true;
    } else if (Chara_Level == 2 && 700 <= Chara_EXP && Chara_EXP <= 2000) {
      return true;
    } else if (Chara_Level == 3 && 2000 <= Chara_EXP && Chara_EXP <= 5000) {
      return true;
    } else if (Chara_Level == 4 && 5000 <= Chara_EXP && Chara_EXP <= 8000) {
      return true;
    }
    return false;
  }

  /**
   * 負けた場合の処理
   */

  public void lose() {
    MapGame.getInstance().lose();
  }

  /**
   * 勝った場合の処理
   */

  public void win() {
    if (!finish) {
      finish = true;
      changeText("たたかいにしょうりした");
      Chara_EXP += Enemy_EXP;
      changeText("あなたは" + changeStatus(Enemy_EXP) + "のけいけんちをてにいれた");
      if (levelUpChecker()) {
        changeText("レベルがあがった!");
        Chara_Level++;
        sound.play_SE_levelup();
        levelUp();
      }
    }
  }

  /**
   * 攻撃時のアニメーション
   */

  public void attackAnimation() {
    attackImageAnimations[0][0].start();
    sound.play_SE_attack();
    enemyPane.getChildren().add(getAttackImageView(0));
    timeline = new Timeline(new KeyFrame(new Duration(600), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        attackImageAnimations[0][0].stop();
        enemyPane.getChildren().remove(getAttackImageView(0));
        if (Enemy_HP <= 0) {
          enemyPane.getChildren().clear();
        }
        timeline.stop();
      }
    }));
    timeline.play();
  }
  
  /**
   * 魔法時のアニメーション
   */

  public void spellAnimation() {
    attackImageAnimations[1][0].start();
    sound.play_SE_spell();
    enemyPane.getChildren().add(getAttackImageView(1));
    timeline = new Timeline(new KeyFrame(new Duration(600), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        attackImageAnimations[1][0].stop();
        enemyPane.getChildren().remove(getAttackImageView(1));
        if (Enemy_HP <= 0) {
          enemyPane.getChildren().clear();
        }
        timeline.stop();
      }
    }));
    timeline.play();
  }

  /**
   * 回復時のアニメーション
   */

  public void healAnimation() {
    healImageAnimations[0].start();
    sound.play_SE_heal();
    enemyPane.getChildren().add(getHealImageView());
    timeline = new Timeline(new KeyFrame(new Duration(1400), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        healImageAnimations[0].stop();
        enemyPane.getChildren().remove(getHealImageView());
        if (Enemy_HP <= 0) {
          enemyPane.getChildren().clear();
        }
        timeline.stop();
      }
    }));
    timeline.play();
  }

  /**
   * 逃げれるかの判定
   */

  public boolean canRetreat() {
    if (Chara_Level > Enemy_Level) {
      return true;
    } else if (Chara_Level == Enemy_Level) {
      if (Math.random() >= 0.5) {
        return true;
      }
    } else {
      if (Math.random() <= 0.1) {
        return true;
      }
    }

    return false;
  }

  /**
   * ダメージを受けた際の処理
   */

  public void damaged() {
    int damage = Enemy_STR / 10;
    Chara_HP -= damage;
    changeText("あいてから" + changeStatus(damage) + "のダメージをうけた");
    hp.setText(changeStatus(Chara_HP));
    if (Chara_HP <= 0) {
      lose();
    }
  }

  /**
   * 攻撃時の処理
   */

  public void attack(ActionEvent event) {
    if (Enemy_HP > 0) {
      attackAnimation();
      int damage = 0;
      damage = Chara_STR / 10;
      damage -= Enemy_DEF / 100;
      changeText("あいてに" + changeStatus(damage) + "のダメージをあたえた");

      Enemy_HP -= damage;
      if (Enemy_HP <= 0) {
        win();
      } else {
        damaged();
      }
    }
  }

  /**
   * 魔法時の処理
   */

  public void spell(ActionEvent event) {
    if (Enemy_HP > 0) {
      if (Chara_MP >= 10) {
        spellAnimation();
        Chara_MP -= 10;
        mp.setText(changeStatus(Chara_MP));

        int damage = 0;
        damage = Chara_STR / 5;
        changeText("あいてに" + changeStatus(damage) + "のダメージをあたえた");

        Enemy_HP -= damage;
        if (Enemy_HP <= 0) {
          win();
        } else {
          damaged();
        }
      } else {
        changeText("ＭＰがたりない！");
      }
    }
  }

  /**
   * 回復時の処理
   */

  public void heal(ActionEvent event) {
    if (Enemy_HP > 0) {
      if (Chara_MP >= 30) {
        healAnimation();
        Chara_MP -= 30;
        mp.setText(changeStatus(Chara_MP));
        int heal = MapGameController.charaStats.getChara().getHp().getMax() / 3;
        if (Chara_HP > heal) {
          heal = heal * 3 - Chara_HP;
        }
        changeText("じぶんは" + changeStatus(heal) + "かいふくした");

        Chara_HP += heal;

        damaged();
      } else {
        changeText("ＭＰがたりない！");
      }
    }
  }

  /**
   * 逃げた際の処理
   */

  public void retreat(ActionEvent event) {
    if (canRetreat() || Enemy_HP <= 0) {
      changeStatus();
      if (Enemy_HP <= 0) {
        MapGameController.enemy[floor][id].vanishEnemy();
        MapGameController.Score += 100;
        sound.stop_BGM_Battle();
        sound.play_BGM();

      }
      MapGame.getInstance().Map();
    } else {
      changeText("にげられなかった");
      damaged();
    }
  }

  ///////////////// アニメーション///////////

  public ImageView getEnemyImageView() {
    return enemyImageViews[0];
  }

  public ImageView getAttackImageView(int i) {
    return attackImageViews[i][0];
  }

  public ImageView getHealImageView() {
    return healImageViews[0];
  }

  private class ImageEnemyAnimation extends AnimationTimer {
    // アニメーション対象ノード
    private ImageView enemyView = null;
    private Image[] enemyImages = null;
    private int index = 0;

    private long duration = 500 * 1000000L; // 500[ms]
    private long startTime = 0;

    private long count = 0L;
    private long preCount;
    private boolean isPlus = true;

    public ImageEnemyAnimation(ImageView enemyView, Image[] images) {
      this.enemyView = enemyView;
      this.enemyImages = images;
      this.index = 0;
    }

    @Override
    public void handle(long now) {
      if (startTime == 0) {
        startTime = now;
      }

      preCount = count;
      count = (now - startTime) / duration;
      if (preCount != count) {
        if (isPlus) {
          index++;
        } else {
          index--;
        }
        if (index < 0 || 2 < index) {
          index = 1;
          isPlus = !isPlus; // true == !false, false == !true
        }
        enemyView.setImage(enemyImages[index]);
      }
    }
  }

  private class ImageAttackAnimation extends AnimationTimer {
    // アニメーション対象ノード
    private ImageView attackView = null;
    private Image[] attackImages = null;
    private int index = 0;

    private long duration = 100 * 1000000L; // 100[ms]
    private long startTime = 0;

    private long count = 0L;
    private long preCount;
    private boolean isPlus = true;

    public ImageAttackAnimation(ImageView attackView, Image[] images) {
      this.attackView = attackView;
      this.attackImages = images;
      this.index = 0;
    }

    @Override
    public void handle(long now) {
      if (startTime == 0) {
        startTime = now;
      }
      preCount = count;
      count = (now - startTime) / duration;
      if (preCount != count) {
        if (isPlus) {
          index++;
        } else {
          index--;
        }
        if (index < 0 || 5 < index) {
          index = 0;
          isPlus = !isPlus; // true == !false, false == !true
        }
        attackView.setImage(attackImages[index]);
      }
    }
  }

  private class ImageHealAnimation extends AnimationTimer {
    // アニメーション対象ノード
    private ImageView healView = null;
    private Image[] healImages = null;
    private int index = 0;

    private long duration = 100 * 1000000L; // 100[ms]
    private long startTime = 0;

    private long count = 0L;
    private long preCount;
    private boolean isPlus = true;

    public ImageHealAnimation(ImageView healView, Image[] images) {
      this.healView = healView;
      this.healImages = images;
      this.index = 0;
    }

    @Override
    public void handle(long now) {
      if (startTime == 0) {
        startTime = now;
      }
      preCount = count;
      count = (now - startTime) / duration;
      if (preCount != count) {
        if (isPlus) {
          index++;
        } else {
          index--;
        }
        if (index < 0 || 5 < index) {
          index = 0;
          isPlus = !isPlus; // true == !false, false == !true
        }
        healView.setImage(healImages[index]);
      }
    }
  }
}
// タイマーの残骸
/*
 * public void enemyAttack(){ Timer timer = new Timer(false); TimerTask task =
 * new TimerTask() {
 * 
 * @Override public void run() { timeline.stop(); } }; timer.schedule(task,
 * 1000); }
 */