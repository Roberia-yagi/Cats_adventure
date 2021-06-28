/**
 * キャラのステータスのクラス
 * ステータス周りは小暮先生のプログラムをかなり参考にさせていただきました．
 */

public class Chara {
  private String _name;
  private Param _lvl;
  private Param _hp;
  private Param _mp;
  private Param _str;
  private Param _def;
  private Param _luk;
  private Param _money;
  private Param _exp;

  Chara(String n, Param lv, Param h, Param p, Param st, Param d, Param l, Param m, Param e) {
      this._name = n;
      this._lvl  = lv;
      this._hp   = h;
      this._mp   = p;
      this._str  = st;
      this._def  = d;
      this._luk  = l;
      this._money = m;
      this._exp = e;
  }

  public String getName() { return this._name; }
  public Param getLevel()    { return this._lvl; }
  public Param getHp()    { return this._hp; }
  public Param getMp()    { return this._mp; }
  public Param getStr()   { return this._str; }
  public Param getDef()   { return this._def; }
  public Param getLuk()   { return this._luk; }
  public Param getMoney() { return this._money;}
  public Param getExp()   { return this._exp; }

  public void setName(String name)  { this._name = name; }
  public void setLevel(Param lvl)  { this._lvl = lvl; }
  public void setHp(Param hp)       { this._hp = hp; }
  public void setMp(Param mp)       { this._mp = mp; }
  public void setStr(Param str)     { this._str = str; }
  public void setDef(Param def)     { this._def = def; }
  public void setLuk(Param luk)     { this._luk = luk; }
  public void setMoney(Param money) { this._money = money; }
  public void setExp(Param exp) { this._exp = exp; }
  /**
   * アイテム取得時にキャラのステータスを変更するメソッド
   */
  public void getItemEffect(Item item) {
      this._hp.setCur(this._hp.getCur() + item.getHp());
      this._mp.setCur(this._mp.getCur() + item.getMp());
      this._str.setCur(this._str.getCur() + item.getStr());
      this._def.setCur(this._def.getCur() + item.getDef());
      this._luk.setCur(this._luk.getCur() + item.getLuk());
      this._money.setCur(this._money.getCur() + item.getMoney());
      this._exp.setCur(this._exp.getCur() + item.getExp());
  }
}
