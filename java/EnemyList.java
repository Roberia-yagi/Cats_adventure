/**
 * 敵の配列を作る関数
 * 小暮先生のプログラムをかなり参考にさせていただきました．
 */

public class EnemyList {
    private EnemyCollection enemyCollection;
    private int enemyType;
    private Chara enemyStats;

    EnemyList(){
        enemyCollection = new EnemyCollection();
        int r = (int)(Math.random() * enemyCollection.getEnemyCollectionListSize());
        enemyType = r;
        enemyStats = enemyCollection.getNewEnemy(r);
    }

    public Chara getEnemyStats() {
        return enemyStats;
    }

    public int getEnemyType(){
        return enemyType;
    }
}
