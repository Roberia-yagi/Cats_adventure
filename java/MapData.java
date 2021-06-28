import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData {
    public static final int TYPE_NONE   = 0;
    public static final int TYPE_WALL   = 1;
    public static final int TYPE_ITEM = 2;
    public static final int TYPE_GOAL = 3;
    private static final String mapImageFiles[] = {
        "png/BASE.png",
        "png/WALL.png",
        "png/ITEM.png",
        "png/GOAL.png",
        "png/INN.png"
    };

    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private int[][] maps;
    private int width;
    private int height;

    MapData(int x, int y){
        mapImages     = new Image[5];
        mapImageViews = new ImageView[y][x];
        for (int i=0; i<5; i++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }

        width  = x;
        height = y;
        maps = new int[y][x];

        fillMap(MapData.TYPE_WALL);
        digMap(1, 3);
        digMapMore();
        putItem();
        //putInn();
        setImageViews();
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }

    public ImageView getImageView(int x, int y) {
        return mapImageViews[y][x];
    }

    public void setMap(int x, int y, int type){
        if (x < 1 || width <= x-1 || y < 1 || height <= y-1) {
            return;
        }
        maps[y][x] = type;
    }

    public void setImageViews() {
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
            }
        }
    }

    public void fillMap(int type){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                maps[y][x] = type;
            }
        }
    }

    public void digMap(int x, int y){
        setMap(x, y, MapData.TYPE_NONE);
        int[][] dl = {{0,1},{0,-1},{-1,0},{1,0}};
        int[] tmp;

        for (int i=0; i<dl.length; i++) {
            int r = (int)(Math.random()*dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i=0; i<dl.length; i++){
            int dx = dl[i][0];
            int dy = dl[i][1];
            if (getMap(x+dx*2, y+dy*2) == MapData.TYPE_WALL){
                setMap(x+dx, y+dy, MapData.TYPE_NONE);
                digMap(x+dx*2, y+dy*2);

            }
        }
    }
    public void digMapMore(){
        int start;
        int wallChain;

        for (int i = 1; i < height - 1; i++){
            start = 0;
            wallChain = 0;

            for(int j = 1; j < width - 1; j++){
                if (getMap(j, i) == MapData.TYPE_WALL){
                    wallChain++;
                    if (start == 0){
                        start = i;
                    }
                } else {
                    start = 0;
                    wallChain = 0;
                    continue;
                }

                if (wallChain == 5){
                    for (int k = 2; k < 5; k++){
                        if (getMap(start + k, i - 1) != MapData.TYPE_WALL &&
                            getMap(start + k, i + 1) != MapData.TYPE_WALL){
                                setMap(start + k, i, MapData.TYPE_NONE);
                                start = 0;
                                wallChain = 0;
                                break;
                        }
                    }
                }
            }
        }

        for (int i = 1; i < width - 1; i++){
            start = 0;
            wallChain = 0;

            for(int j = 1; j < height - 1; j++){
                if (getMap(i, j) == MapData.TYPE_WALL){
                    wallChain++;

                    if (start == 0){
                        start = j;
                    }
                } else {
                    start = 0;
                    wallChain = 0;
                    continue;
                }
                for (int k = 2; k < 5; k++){
                    if (wallChain == 5){
                        if (getMap(i - 1, start + k) != MapData.TYPE_WALL &&
                            getMap(i + 1, start + k) != MapData.TYPE_WALL){
                                setMap(i, start + k, MapData.TYPE_NONE);
                                start = 0;
                                wallChain = 0;
                                break;
                        }
                    }
                }
            }
        }
    }
    /**
     * マップ生成時にマップ上にアイテムを配置するメソッド
     */
    public void putItem(){
        int h = getHeight()/2; // 7
        int w = getWidth()/2; //10
        for(int i = 0; i < 3; i++){
            while(true){
                int x = (int)(i%2*w + Math.random()* width/2);
                int y = (int)(i/2*h + Math.random()* height/2);
                if(getMap(x,y) == MapData.TYPE_NONE){
                    setMap(x, y, MapData.TYPE_ITEM);
                    break;
                }
            }
        }
    }

    /**
     * マップ生成時にゴールを配置するメソッド
     */

    public void putGoal(){
        setMap(39, 21, MapData.TYPE_GOAL);
    }


    public void printMap(){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                if (getMap(x,y) == MapData.TYPE_WALL){
                    System.out.print("++");
                }else{
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
    }
}
