import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;

/**
 * 敵の情報を保有するクラス
 * 基本的にMoveChara.javaと同じコードです．
 */
public class Boss {
    public static final int TYPE_DOWN  = 0;
    public static final int TYPE_LEFT  = 1;
    public static final int TYPE_RIGHT = 2;
    public static final int TYPE_UP    = 3;

    private final String[] dirStrings  = { "d", "l", "r", "u" };
    private final String[] kindStrings = { "1", "2", "3" };
    private final String pngPathBefore = "png/boss";
    private final String pngPathAfter  = ".png";

    private int posX;
    private int posY;

    private MapData mapData;

    private Image[][] enemyImages;
    private ImageView[] enemyImageViews;
    private ImageAnimation[] enemyImageAnimations;

    private int count   = 0;
    private int diffx   = 1;
    private int enemyDir;

    Boss(MapData mapData){
        this.mapData = mapData;

        enemyImages = new Image[4][3];
        enemyImageViews = new ImageView[4];
        enemyImageAnimations = new ImageAnimation[4];

        for (int i=0; i<4; i++) {
            enemyImages[i] = new Image[3];
            for (int j=0; j<3; j++) {
                enemyImages[i][j] = new Image(pngPathBefore + dirStrings[i] + kindStrings[j] + pngPathAfter);
            }
            enemyImageViews[i] = new ImageView(enemyImages[i][0]);
            enemyImageAnimations[i] = new ImageAnimation( enemyImageViews[i], enemyImages[i] );
        }

        posX = 39;
        posY = 21;

        setEnemyDir(TYPE_DOWN);
    }

    public void changeCount(){
        count = count + diffx;
        if (count > 2) {
            count = 1;
            diffx = -1;
        } else if (count < 0){
            count = 1;
            diffx = 1;
        }
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }


    public void setEnemyDir(int ed){
        enemyDir = ed;
        for (int i=0; i<4; i++) {
            if (i == enemyDir) {
                enemyImageAnimations[i].start();
            } else {
                enemyImageAnimations[i].stop();
            }
        }
    }


    public ImageView getEnemyImageView(){
        return enemyImageViews[enemyDir];
    }

    private class ImageAnimation extends AnimationTimer {
        // アニメーション対象ノード
        private ImageView   enemyView     = null;
        private Image[]     enemyImages   = null;
        private int         index       = 0;

        private long        duration    = 500 * 1000000L;   // 500[ms]
        private long        startTime   = 0;

        private long count = 0L;
        private long preCount;
        private boolean isPlus = true;

        public ImageAnimation( ImageView enemyView , Image[] images ) {
            this.enemyView   = enemyView;
            this.enemyImages = images;
            this.index      = 0;
        }

        @Override
        public void handle( long now ) {
            if( startTime == 0 ){ startTime = now; }

            preCount = count;
            count  = ( now - startTime ) / duration;
            if (preCount != count) {
                if (isPlus) {
                    index++;
                } else {
                    index--;
                }
                if ( index < 0 || 2 < index ) {
                    index = 1;
                    isPlus = !isPlus; // true == !false, false == !true
                }
                enemyView.setImage(enemyImages[index]);
            }
        }
    }
}
