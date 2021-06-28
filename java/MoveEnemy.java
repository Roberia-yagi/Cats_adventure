import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;

/**
 * 敵の情報を保有するクラス 基本的にMoveChara.javaと同じコードです．
 */
public class MoveEnemy {
    public static final int TYPE_DOWN = 0;
    public static final int TYPE_LEFT = 1;
    public static final int TYPE_RIGHT = 2;
    public static final int TYPE_UP = 3;

    private final String[] dirStrings = { "d", "l", "r", "u" };
    private final String[] kindStrings = { "1", "2", "3", "4", "5" };
    private final String[] enemyKindStrings = { "1", "2", "3", "4"};
    private final String pngPathBefore = "png/enemy";
    private final String pngPathAfter = ".png";

    private int posX;
    private int posY;

    private MapData mapData;
    private EnemyList[] enemyList;

    private Image[][] enemyImages;
    private ImageView[] enemyImageViews;
    private ImageAnimation[] enemyImageAnimations;

    private int count = 0;
    private int diffx = 1;
    private int enemyDir;
    private int enemyType;

    MoveEnemy(int k, MapData mapData, EnemyList[] enemyList) {
        this.mapData = mapData;
        this.enemyList = enemyList;
        enemyType = enemyList[k].getEnemyType();
        enemyImages = new Image[4][3];
        enemyImageViews = new ImageView[4];
        enemyImageAnimations = new ImageAnimation[4];
            for (int i = 0; i < 4; i++) {
                enemyImages[i] = new Image[3];
                for (int j = 0; j < 3; j++) {
                    enemyImages[i][j] = new Image(pngPathBefore + enemyKindStrings[enemyType] + dirStrings[i] + kindStrings[j] + pngPathAfter);
                }
                enemyImageViews[i] = new ImageView(enemyImages[i][0]);
                enemyImageAnimations[i] = new ImageAnimation(enemyImageViews[i], enemyImages[i]);
            }
        while (true){
            int x = (int)(mapData.getWidth() * Math.random());
            int y = (int)(mapData.getHeight() * Math.random());
            if((mapData.getMap(x, y) == MapData.TYPE_NONE) && (x != 1 || y != 1)){
                posX = x;
                posY = y;
                break;
            }
        }        


        setEnemyDir(TYPE_DOWN);
    }

    public void changeCount() {
        count = count + diffx;
        if (count > 2) {
            count = 1;
            diffx = -1;
        } else if (count < 0) {
            count = 1;
            diffx = 1;
        }
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void vanishEnemy() {
        posX = -1;
        posY = -1;
    }

    public void setEnemyDir(int ed) {
        enemyDir = ed;
        for (int i = 0; i < 4; i++) {
            if (i == enemyDir) {
                enemyImageAnimations[i].start();
            } else {
                enemyImageAnimations[i].stop();
            }
        }
    }

    public boolean canMove(int dx, int dy) {
        if (MoveChara.getPosX() == posX + dx && MoveChara.getPosY() == posY + dy) {
            return false;
        }
        if (mapData.getMap(posX + dx, posY + dy) == MapData.TYPE_NONE) {
            return true;
        }
        return false;
    }

    public int Direction(int dx, int dy){
        if(dx == 1){
            return TYPE_RIGHT;
        }
        else if(dx == -1){
            return TYPE_LEFT;
        }
        else if(dy == -1){
            return TYPE_UP;
        }
        else if(dy == 1){
            return TYPE_DOWN;
        }
        return TYPE_UP;
    }

    public boolean move() {
        while (true) {
            double r = Math.random();
            int dx = 0;
            int dy = 0;
            if (r > 0.5) {
                dx = (int) (Math.random() * 3) - 1;
            } else {
                dy = (int) (Math.random() * 3) - 1;
            }
            if (canMove(dx, dy)) {
                posX += dx;
                posY += dy;
                if(dx != 0 || dy != 0){
                    setEnemyDir(Direction(dx, dy));
                }
            return true;
            } else{
                return false;
            }
        }
    }

    public ImageView getEnemyImageView() {
        return enemyImageViews[enemyDir];
    }

    private class ImageAnimation extends AnimationTimer {
        // アニメーション対象ノード
        private ImageView enemyView = null;
        private Image[] enemyImages = null;
        private int index = 0;

        private long duration = 500 * 1000000L; // 500[ms]
        private long startTime = 0;

        private long count = 0L;
        private long preCount;
        private boolean isPlus = true;

        public ImageAnimation(ImageView enemyView, Image[] images) {
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
}
