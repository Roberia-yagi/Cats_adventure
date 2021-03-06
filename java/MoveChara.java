import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;


public class MoveChara {
    public static final int TYPE_DOWN  = 0;
    public static final int TYPE_LEFT  = 1;
    public static final int TYPE_RIGHT = 2;
    public static final int TYPE_UP    = 3;

    private final String[] dirStrings  = { "d", "l", "r", "u" };
    private final String[] kindStrings = { "1", "2", "3" };
    private final String pngPathBefore = "png/neko";
    private final String pngPathAfter  = ".png";

    private static int posX;
    private static int posY;

    private MapData mapData;
    private EnemyList enemyList;

    private Image[][] charaImages;
    private ImageView[] charaImageViews;
    private ImageAnimation[] charaImageAnimations;

    private int count   = 0;
    private int diffx   = 1;
    private int charaDir;
    private int itemCount = 0;//

    MoveChara(int startX, int startY, MapData mapData){
        this.mapData = mapData;
        

        charaImages = new Image[4][3];
        charaImageViews = new ImageView[4];
        charaImageAnimations = new ImageAnimation[4];

        for (int i=0; i<4; i++) {
            charaImages[i] = new Image[3];
            for (int j=0; j<3; j++) {
                charaImages[i][j] = new Image(pngPathBefore + dirStrings[i] + kindStrings[j] + pngPathAfter);
            }
            charaImageViews[i] = new ImageView(charaImages[i][0]);
            charaImageAnimations[i] = new ImageAnimation( charaImageViews[i], charaImages[i] );
        }

        posX = startX;
        posY = startY;

        setCharaDir(TYPE_DOWN);
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

    public static int getPosX(){
        return posX;
    }

    public static int getPosY(){
        return posY;
    }

    /**
     * アイテムカウントを返すgetter
     */
    public int getItemCount(){
        return itemCount;
    }

    public void setCharaDir(int cd){
        charaDir = cd;
        for (int i=0; i<4; i++) {
            if (i == charaDir) {
                charaImageAnimations[i].start();
            } else {
                charaImageAnimations[i].stop();
            }
        }
    }

    public boolean canMove(int dx, int dy){
        if (mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_WALL){
            return false;
        } else if (mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_ITEM){
            mapData.setMap(posX+dx, posY+dy, MapData.TYPE_NONE);
            mapData.setImageViews();
            return true;
        } else if (mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_NONE
                || mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_INN
                || mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_GOAL){

            return true;
        }
        return false;
    }

    /**
     * 移動先がアイテムだった場合内部のアイテムカウントを増やしてtrue返すメソッド
     */
    public boolean pickItem(int dx, int dy){
        if (mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_ITEM){
            itemCount ++;
            if(itemCount == 3){
                mapData.putGoal();
            }
            return true;
        }
        return false;
    }

    public boolean move(int dx, int dy){
        if (canMove(dx,dy)){
            posX += dx;
            posY += dy;
            return true;
        }else {
            return false;
        }
    }

    public ImageView getCharaImageView(){
        return charaImageViews[charaDir];
    }

    /**
     * 移動先がゴールだった場合trueを返すメソッド
     */
    public boolean goalchecker(){
        if(getPosX()==39 && getPosY()==21 && getItemCount()==3){
            return true;
      }
      return false;
    }

    public boolean battleChecker(int enemy_x, int enemy_y){
        if(getPosX() == enemy_x && getPosY() == enemy_y){
            return true;
        }
        return false;
    }

    private class ImageAnimation extends AnimationTimer {
        // アニメーション対象ノード
        private ImageView   charaView     = null;
        private Image[]     charaImages   = null;
        private int         index       = 0;

        private long        duration    = 500 * 1000000L;   // 500[ms]
        private long        startTime   = 0;

        private long count = 0L;
        private long preCount;
        private boolean isPlus = true;

        public ImageAnimation( ImageView charaView , Image[] images ) {
            this.charaView   = charaView;
            this.charaImages = images;
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
                charaView.setImage(charaImages[index]);
            }
        }
    }
}
