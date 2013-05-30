package com.baasday;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>BaasdayObject#updateで指定する更新処理を簡潔に記述するためのユーティリティです。</p>
 * <p>「priceフィールドの値を20増加し、nameフィールドの値を"apple"に変更」という更新処理を作成するときは</p>
 * <pre>
 * UpdateOperations.mergeOperations(UpdateOperations.increment("price", 20), UpdateOperations.set("name", "apple"))
 * </pre>
 * <p>のように使用します。</p>
 */
public final class UpdateOperations {
    private UpdateOperations() {
    }

    /**
     * <p>数値を増加させる更新処理の特別なキーです。</p>
     */
    public static final String INCREMENT = "$inc";

    /**
     * <p>リストに値を追加する更新処理の特別なキーです。</p>
     */
    public static final String PUSH = "$push";

    /**
     * <p>リストに値が存在しないときだけ追加する更新処理の特別なキーです。</p>
     */
    public static final String PUSH_UNIQUE = "$pushUnique";

    /**
     * <p>リストから値を削除する更新処理の特別なキーです。</p>
     */
    public static final String PULL = "$pull";

    /**
     * <p>フィールドを削除する更新処理の特別なキーです。</p>
     */
    public static final String UNSET = "$unset";

    /**
     * <p>フィールドの値を指定された値に変更する更新処理を返します。</p>
     * @param field フィールド名
     * @param value 値
     * @return 更新処理
     */
    public static Map<String, Object> set(final String field, final Object value) {
        return Utility.singleEntryMap(field, value);
    }

    /**
     * <p>フィールドの値を増加させる更新処理を返します。</p>
     * @param field フィールド名
     * @param amount 増加量
     * @return 更新処理
     */
    public static Map<String, Object> increment(final String field, final Number amount) {
        return Utility.singleEntryMap(field, (Object) Utility.singleEntryMap(INCREMENT, amount));
    }

    /**
     * <p>フィールドの値を減少させる更新処理を返します。</p>
     * @param field フィールド名
     * @param amount 減少量
     * @return 更新処理
     */
    public static Map<String, Object> decrement(final String field, final Number amount) {
        return increment(field, -amount.doubleValue());
    }

    /**
     * <p>フィールドの値を配列として、指定された値を追加する更新処理を返します。</p>
     * @param field フィールド名
     * @param value 追加する値
     * @return 更新処理
     */
    public static Map<String, Object> push(final String field, final Object value) {
        return Utility.singleEntryMap(field, (Object) Utility.singleEntryMap(PUSH, value));
    }

    /**
     * <p>フィールドの値を配列として、指定された値が存在しないときだけ追加する更新処理を返します。</p>
     * @param field フィールド名
     * @param value 追加する値
     * @return 更新処理
     */
    public static Map<String, Object> pushUnique(final String field, final Object value) {
        return Utility.singleEntryMap(field, (Object) Utility.singleEntryMap(PUSH_UNIQUE, value));
    }

    /**
     * <p>フィールドの値を配列として、指定された値を削除する更新処理を返します。</p>
     * @param field フィールド名
     * @param value 削除する値
     * @return 更新処理
     */
    public static Map<String, Object> pull(final String field, final Object value) {
        return Utility.singleEntryMap(field, (Object) Utility.singleEntryMap(PULL, value));
    }

    /**
     * <p>フィールドを削除する更新処理を返します。</p>
     * @param field フィールド名
     * @return 更新処理
     */
    public static Map<String, Object> unset(final String field) {
        return Utility.singleEntryMap(field, (Object) Utility.singleEntryMap(UNSET, true));
    }

    /**
     * <p>複数の更新処理をひとつに結合します。</p>
     * <p>同じフィールドに対する更新処理は後に指定したものだけが有効になります。</p>
     * @param operations 更新処理
     * @return 更新処理
     */
    public static Map<String, Object> mergeOperations(final Map<String, Object> ... operations) {
        final Map<String, Object> result = new HashMap<String, Object>();
        for (final Map<String, Object> operation : operations) result.putAll(operation);
        return result;
    }
}
