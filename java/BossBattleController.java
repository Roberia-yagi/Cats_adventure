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
 * 戦闘時の入力対する処理を司るクラス
 * ボス専用 BattleController.javaとほぼ同じなので割愛
 * 
 * @author Group19
 * @Version 4.0
 */

public class BossBattleController implements Initializable {
  private int id;
  private int Chara_Level;
  private int Chara_HP;
  private int Chara_MP;
  private int Chara_STR;
  private int Chara_DEF;
  private int Chara_LUK;
  private int Chara_MONEY;
  private int Chara_EXP;
  private int Enemy_HP;
  private int Enemy_MP;
  private int Enemy_STR;
  private int Enemy_DEF;
  private int Enemy_LUK;
  private int Enemy_MONEY;
  private int Enemy_EXP;
  private BossCollection bossCollection;
  private Chara boss;
  private final String[] dirStrings = { "d", "l", "r", "u" };
  private final String[] kindStrings = { "1", "2", "3" };
  private final String enemyPngPathBefore = "png/battle/boss";
  private final String[] attackKindStrings = { "1", "2", "3", "4", "5", "6" };
  private final String[] healKindStrings = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" };
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

  @Override
  public void initialize(URL url, ResourceBundle rb) {
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

  public void initializeStates() {
    bossCollection = new BossCollection();
    boss = bossCollection.getNewBoss(0);
    Chara_Level = MapGameController.charaStats.getChara().getLevel().getCur();
    Chara_HP = MapGameController.charaStats.getChara().getHp().getCur();
    Chara_MP = MapGameController.charaStats.getChara().getMp().getCur();
    Chara_STR = MapGameController.charaStats.getChara().getStr().getCur();
    Chara_DEF = MapGameController.charaStats.getChara().getDef().getCur();
    Chara_LUK = MapGameController.charaStats.getChara().getLuk().getCur();
    Chara_MONEY = MapGameController.charaStats.getChara().getMoney().getCur();
    Chara_EXP = MapGameController.charaStats.getChara().getExp().getCur();

    Enemy_HP = boss.getHp().getCur();
    Enemy_STR = boss.getStr().getCur();
    Enemy_DEF = boss.getDef().getCur();
    Enemy_LUK = boss.getLuk().getCur();
    Enemy_EXP = boss.getExp().getCur();
  }

  public void initializeEnemyAnimation() {
    enemyImages = new Image[4][6];
    enemyImageViews = new ImageView[6];
    enemyImageAnimations = new ImageEnemyAnimation[4];
    for (int i = 0; i < 4; i++) {
      enemyImages[i] = new Image[3];
      for (int j = 0; j < 3; j++) {
        enemyImages[i][j] = new Image(enemyPngPathBefore + dirStrings[i] + kindStrings[j] + pngPathAfter, 100, 0, true,
            false);
      }
      enemyImageViews[i] = new ImageView(enemyImages[i][0]);
      enemyImageAnimations[i] = new ImageEnemyAnimation(enemyImageViews[i], enemyImages[i]);
    }
    enemyImageAnimations[0].start();
    enemyPane.getChildren().clear();
    enemyPane.getChildren().add(getEnemyImageView());
  }

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

  public void changeText(String newTxt) {
    battleText2.setText(oldTxt);
    oldTxt = newTxt;
    battleText1.setText(oldTxt);
  }

  public String changeStatus(int value) {
    return FullWidth(Integer.toString(value));
  }

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

  public boolean levelUpChecker() {
    if (Chara_Level == 1 && 300 <= Chara_EXP && Chara_EXP <= 1000) {
      return true;
    } else if (Chara_Level == 2 && 1000 <= Chara_EXP && Chara_EXP <= 2000) {
      return true;
    } else if (Chara_Level == 3 && 2000 <= Chara_EXP && Chara_EXP <= 5000) {
      return true;
    } else if (Chara_Level == 4 && 5000 <= Chara_EXP && Chara_EXP <= 8000) {
      return true;
    }
    return false;
  }

  public void lose() {
    MapGame.getInstance().lose();
  }

  public void win() {
    MapGame.getInstance().clear();
  }

  public void attackAnimation() {
    attackImageAnimations[0][0].start();
    sound.play_SE_attack();
    enemyPane.getChildren().add(getAttackImageView(0));
    timeline = new Timeline(new KeyFrame(new Duration(600), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        // ここに処理を記述
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

  public void spellAnimation() {
    attackImageAnimations[1][0].start();
    sound.play_SE_spell();
    enemyPane.getChildren().add(getAttackImageView(1));
    timeline = new Timeline(new KeyFrame(new Duration(600), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        // ここに処理を記述
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

  public void healAnimation() {
    healImageAnimations[0].start();
    sound.play_SE_heal();
    enemyPane.getChildren().add(getHealImageView());
    timeline = new Timeline(new KeyFrame(new Duration(1400), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        // ここに処理を記述
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

  public void damaged() {
    int damage = Enemy_STR / 10;
    Chara_HP -= damage;
    changeText("あいてから" + changeStatus(damage) + "のダメージをうけた");
    hp.setText(changeStatus(Chara_HP));
    if (Chara_HP <= 0) {
      lose();
    }
  }

  public void attack(ActionEvent event) {
    if (Enemy_HP > 0) {
      attackAnimation();
      int damage = 0;
      damage = Chara_STR / 10;
      changeText("あいてに" + changeStatus(damage) + "のダメージをあたえた");
      Enemy_HP -= damage;
      if (Enemy_HP <= 0) {
        win();
      } else {
        damaged();
      }
    }
  }

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

  public void heal(ActionEvent event) {
    if (Enemy_HP > 0) {
      if (Chara_MP >= 10) {
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
      }
    } else {
      changeText("ＭＰがたりない！");
    }
  }

  public void retreat(ActionEvent event) {
    changeText("ボスからはにげられない！");
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
