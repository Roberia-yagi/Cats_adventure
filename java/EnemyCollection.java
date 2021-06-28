import java.util.ArrayList;

/**
 * 敵のステータスの元となる数値を保有するクラス
 * 小暮先生のプログラムをかなり参考にさせていただきました．
 */

public class EnemyCollection {
    private ArrayList<Chara> enemyCollectionList;

    EnemyCollection(){
        enemyCollectionList = new ArrayList<Chara>();
    
        addEnemy(new Chara("Enemy1",
                     new Param("Lvl", 1, 5),
                     new Param("HP" ,  20, 50),
                     new Param("MP" ,  50, 50),
                     new Param("STR", 50, 255),
                     new Param("DEF", 50, 255),
                     new Param("LUK", 50, 255),
                     new Param("Money", 100, 255),
                     new Param("EXP", 50, 10000)));
                     
        addEnemy(new Chara("Enemy2",
                     new Param("Lvl", 2, 5),
                     new Param("HP" ,  50, 100),
                     new Param("MP" ,  100, 100),
                     new Param("STR", 100, 255),
                     new Param("DEF", 100, 255),
                     new Param("LUK", 100, 255),
                     new Param("Money", 100, 255),
                     new Param("EXP", 200, 10000)));
        addEnemy(new Chara("Enemy3",
                    new Param("Lvl", 3, 5),
                     new Param("HP" , 100, 500),
                     new Param("MP" ,  50, 50),
                     new Param("STR", 200, 255),
                     new Param("DEF", 200, 255),
                     new Param("LUK", 100, 255),
                     new Param("Money", 100, 255),
                     new Param("EXP", 500, 10000)));
        addEnemy(new Chara("Enemy4",
                     new Param("Lvl", 4, 5),
                     new Param("HP" , 200, 500),
                     new Param("MP" ,  50, 50),
                     new Param("STR", 255, 255),
                     new Param("DEF", 255, 255),
                     new Param("LUK", 100, 255),
                     new Param("Money", 100, 255),
                     new Param("EXP", 1000, 10000)));
    }

    public void addEnemy(Chara a) {
        enemyCollectionList.add(a);
    }

    public int getEnemyCollectionListSize(){
        return enemyCollectionList.size();
    }

    public Chara getNewEnemy(int r) {
        Chara cloneChara = enemyCollectionList.get(r);

        Chara newChara = new Chara (cloneChara.getName(),
                            new Param(cloneChara.getName(),
                                cloneChara.getLevel().getCurMax(),
                                cloneChara.getLevel().getMax()),
                            new Param(cloneChara.getName(),
                                cloneChara.getHp().getCurMax(),
                                cloneChara.getHp().getMax()),
                            new Param(cloneChara.getName(),
                                cloneChara.getMp().getCurMax(),
                                cloneChara.getMp().getMax()),
                            new Param(cloneChara.getName(),
                                cloneChara.getStr().getCurMax(),
                                cloneChara.getStr().getMax()),
                            new Param(cloneChara.getName(),
                                cloneChara.getDef().getCurMax(),
                                cloneChara.getDef().getMax()),
                            new Param(cloneChara.getName(),
                                cloneChara.getLuk().getCurMax(),
                                cloneChara.getLuk().getMax()),
                            new Param(cloneChara.getName(),
                                cloneChara.getMoney().getCurMax(),
                                cloneChara.getMoney().getMax()),
                            new Param(cloneChara.getName(),
                                cloneChara.getExp().getCurMax(),
                                cloneChara.getExp().getMax()));
        
        return newChara;
    }
}
