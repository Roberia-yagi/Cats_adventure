/**
 * アイテムのステータスを型作るクラス
 */

public class Item {
  private String _name;
  private int _hp;
  private int _mp;
  private int _str;
  private int _def;
  private int _luk;
  private int _money;
  private int _exp;

  Item(String n, int h, int p, int st, int d, int l, int m, int e) {
      this._name = n;
      this._hp   = h;
      this._mp   = p;
      this._str  = st;
      this._def  = d;
      this._luk  = l;
      this._money = m;
      this._exp = e;
  }

  public String getName() { return this._name; }
  public int getHp()    { return this._hp; }
  public int getMp()    { return this._mp; }
  public int getStr()   { return this._str; }
  public int getDef()   { return this._def; }
  public int getLuk()   { return this._luk; }
  public int getMoney() { return this._money;}
  public int getExp() { return this._exp;}

  public void setName(String name)  { this._name = name; }
  public void setHp(int hp)       { this._hp = hp; }
  public void setMp(int mp)       { this._mp = mp; }
  public void setStr(int str)     { this._str = str; }
  public void setDef(int def)     { this._def = def; }
  public void setLuk(int luk)     { this._luk = luk; }
  public void setMoney(int money) { this._money = money; }
  public void setExp(int exp) { this._exp = exp; }
}
