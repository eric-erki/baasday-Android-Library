package com.baasday;

import java.util.Map;

/**
 * <p>baasdayサーバ上に保存される汎用的なオブジェクトです。</p>
 * <p>任意のフィールドを持ち、複数のコレクションに分けてアイテムを保存できます。</p>
 * @see BaasdayObject
 */
public class Item extends BaasdayObject implements UpdatableObject, DeletableObject {
    private final String collectionName;

    Item(final String collectionName, final Map<String, Object> values) {
        super(values);
        this.collectionName = collectionName;
    }

    /**
     * <p>このアイテムが含まれるコレクションの名前を返します。</p>
     * @return コレクション名
     */
    public String getCollectionName() {
        return this.collectionName;
    }

    private static String collectionAPIPath(final String collectionName) {
        return "items/" + collectionName;
    }

    private static String apiPath(final String collectionName, final String id) {
        return collectionAPIPath(collectionName) + "/" + id;
    }

    String apiPath() throws BaasdayException {
        return apiPath(this.collectionName, this.getId());
    }

    private static class ItemFactory implements APIClient.BaasdayObjectFactory<Item> {
        private final String collectionName;

        ItemFactory(final String collectionName) {
            this.collectionName = collectionName;
        }

        public Item createFromAPIResult(final Map<String, Object> values) throws BaasdayException {
            return new Item(this.collectionName, values);
        }
    }

    /**
     * <p>指定されたコレクションにアイテムを追加します。baasdayサーバへの追加は即時に行われます。<p>
     * <p>コレクションが存在しない場合は自動的に作成されます。</p>
     * @param collectionName コレクション名
     * @param values アイテムが持つ値
     * @return 追加したアイテム
     * @throws BaasdayException 追加に失敗した場合
     */
    public static Item create(final String collectionName, final Map<String, Object> values) throws BaasdayException {
        return APIClient.create(collectionName, values, new ItemFactory(collectionName));
    }

    /**
     * <p>指定されたコレクションにアイテムを追加します。baasdayサーバへの追加は即時に行われます。</p>
     * <p>コレクションが存在しない場合は自動的に作成されます。</p>
     * @param collectionName コレクション名
     * @return 追加したアイテム
     * @throws BaasdayException 追加に失敗した場合
     */
    public static Item create(final String collectionName) throws BaasdayException {
        return create(collectionName, null);
    }

    /**
     * <p>指定されたコレクション内の指定されたIDを持つアイテムを取得して返します。</p>
     * @param collectionName コレクション名
     * @param id ID
     * @return アイテム
     * @throws BaasdayException 取得に失敗した場合、指定されたコレクションが存在しない場合、指定されたIDを持つアイテムが存在しない場合
     */
    public static Item fetch(final String collectionName, final String id) throws BaasdayException {
        return APIClient.fetch(apiPath(collectionName, id), new ItemFactory(collectionName));
    }

    /**
     * <p>指定されたコレクション内のアイテムを取得して返します。</p>
     * <p>最大取得件数を指定しない場合や101以上を指定した場合は、最大で100件返します。</p>
     * @param collectionName コレクション名
     * @param query 抽出条件
     * @return 取得結果。コレクションが存在しない場合は空の結果
     * @throws BaasdayException 取得に失敗した場合、抽出条件が正しくない場合
     */
    public static ListResult<Item> fetchAll(final String collectionName, final Query query) throws BaasdayException {
        return APIClient.fetchAll(collectionAPIPath(collectionName), query, new ItemFactory(collectionName));
    }

    /**
     * <p>指定されたコレクション内のアイテムを取得して返します。</p>
     * <p>アイテムは最大で100件返します。</p>
     * @param collectionName コレクション名
     * @return 取得結果。コレクションが存在しない場合は空の結果
     * @throws BaasdayException 取得に失敗した場合
     */
    public static ListResult<Item> fetchAll(final String collectionName) throws BaasdayException {
        return fetchAll(collectionName, null);
    }

    /**
     * <p>このアイテムを更新します。baasdayサーバへの反映は即時に反映されます。</p>
     * <p>valuesに含まれるフィールドを対応する値で更新します。</p>
     * <p>特別なキーを持つマップをフィールドの値として指定すると、そのフィールドに対して特別な更新を行えます($incなら数値の増加など)。UpdateOperationクラスを利用するとそのようなマップを簡単に作れます。</p>
     * @param values 更新するフィールドと値
     * @throws BaasdayException 更新内容が正しくない場合、サーバでの更新に失敗した場合
     * @see UpdateOperations
     */
    public void update(final Map<String, Object> values) throws BaasdayException {
        super.update(values);
    }

    /**
     * <p>このアイテムをbaasdayサーバ上から削除します。</p>
     * @throws BaasdayException 削除に失敗した場合
     */
    public void delete() throws BaasdayException {
        super.delete();
    }
}
