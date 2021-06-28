import java.util.ArrayList;

/**
 * キャラのステータスの元となる数値を保有するクラス
 * 小暮先生のプログラムをかなり参考にさせていただきました．
 */

public class CharaCollection {
    private ArrayList<Chara> charaCollectionList;

    CharaCollection(){
        charaCollectionList = new ArrayList<Chara>();
    
        addChara(new Chara("Neko",
                     new Param("Lvl", 1, 5),
                     new Param("HP" ,  50, 50),
                     new Param("MP" ,  0, 0),
                     new Param("STR", 50, 255),
                     new Param("DEF", 50, 255),
                     new Param("LUK", 50, 255),
                     new Param("Money", 100, 255),
                     new Param("EXP", 0, 10000)));
    }

    public void addChara(Chara a) {
        charaCollectionList.add(a);
    }

    public int getCharaCollectionListSize(){
        return charaCollectionList.size();
    }
    
    public Chara getNewChara() {
        Chara cloneChara = charaCollectionList.get(0);

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
