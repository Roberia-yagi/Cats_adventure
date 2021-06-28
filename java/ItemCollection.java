import java.util.ArrayList;

/**
 * キャラのステータスの元となる数値を保有するクラス
 * 小暮先生のプログラムをかなり参考にさせていただきました．
 */

public class ItemCollection {
    static private ArrayList<Item> itemCollectionList;

    ItemCollection(){
        itemCollectionList = new ArrayList<Item>();

        addItem(new Item("Food", -10, 10, 0, 0, 0, 0, 0));
        addItem(new Item("Sword", 0, 0, 20, 0, 0, 0, 0));
        addItem(new Item("Sield", 0, 0, 0, 20, 0, 0, 0));
        addItem(new Item("Ring", 0, 0, 0, 0, 5, 0, 0));
        addItem(new Item("Money", 0, 0, 0, 0, 0, 10, 0));
    }

    public void addItem(Item a) {
        itemCollectionList.add(a);
    }

    public Item getNewItem(int i) {
        Item cloneItem = itemCollectionList.get(i);

        Item newItem = new Item (cloneItem.getName(),
                                cloneItem.getHp(),
                                cloneItem.getMp(),
                                cloneItem.getStr(),
                                cloneItem.getDef(),
                                cloneItem.getLuk(),
                                cloneItem.getMoney(),
                                cloneItem.getExp());

        return newItem;
    }

    public int getSizeOfItemCollectionList(){
        return itemCollectionList.size();
    }
}
