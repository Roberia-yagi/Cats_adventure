/**
 * アイテムの配列を作るクラス
 */

public class ItemList {
    private ItemCollection itemCollection;
    private Item[] items = new Item[5];

    ItemList(){
        itemCollection = new ItemCollection();
        for(int i = 0; i < 5; i++){
            items[i] = itemCollection.getNewItem(i);
        }
    }

    public Item getItem(int i){
      return items[i];
    }
}
