import java.util.ArrayList;

/**
 * ボスのステータスの元となる数値を保有するクラス
 * 小暮先生のプログラムをかなり参考にさせていただきました．
 */

public class BossCollection {
    private ArrayList<Chara> bossCollectionList;

    BossCollection(){
        bossCollectionList = new ArrayList<Chara>();
    
        addBoss(new Chara("Boss",
                    new Param("Lvl", 5, 5),
                     new Param("HP" ,  700, 700),
                     new Param("MP" ,  50, 50),
                     new Param("STR", 300, 500),
                     new Param("DEF", 300, 500),
                     new Param("LUK", 300, 500),
                     new Param("Money", 0, 0),
                     new Param("EXP", 100, 10000)));
    }

    public void addBoss(Chara a) {
        bossCollectionList.add(a);
    }

    public int getBossCollectionListSize(){
        return bossCollectionList.size();
    }

    public Chara getNewBoss(int r) {
        Chara cloneChara = bossCollectionList.get(r);

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
