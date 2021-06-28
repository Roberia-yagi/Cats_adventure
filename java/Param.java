
/**
 * キャラのパラメータを管理するクラス
 * 小暮先生のプログラムをかなり参考にさせていただきました．
 */

public class Param {
    private String _name;
    private int _cur;
    private int _curMax;
    private int _max;

    Param(String name, int cur, int max){
		this._name   = name;   // name
		this._max    = max;    // upper limit
		this._curMax = cur;    // current max 
		this._cur    = cur;    // current 
    }

    public String getName(){ return this._name; }
    public int getCur()    { return this._cur; }
    public int getCurMax() { return this._curMax; }
    public int getMax()    { return this._max; }
    public void setName(String name) { this._name = name; }
    public void setCur(int cur)     {
		this._cur = cur;
		if (this._cur > this._max) {  this._cur = this._max; }
    }
    public void setMax(int max)     { this._max = max; }
}
