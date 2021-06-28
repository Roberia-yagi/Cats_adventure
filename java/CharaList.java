/**
 * キャラの配列を作る関数
 * 小暮先生のプログラムをかなり参考にさせていただきました．
 */

public class CharaList {
    private CharaCollection charaCollection;
    private Chara Neko;

    CharaList(){
        charaCollection = new CharaCollection();
        Neko = charaCollection.getNewChara();
    }

    public Chara getChara(){
      return Neko;
    }
}
