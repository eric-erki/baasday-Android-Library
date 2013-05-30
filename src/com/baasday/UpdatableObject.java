package com.baasday;

import java.util.Map;

/**
 * <p>更新可能なbaasdayサーバ上のオブジェクトを表します。</p>
 */
public interface UpdatableObject {
    /**
     * <p>このオブジェクトを更新します。baasdayサーバへの反映は即時に反映されます。</p>
     * <p>valuesに含まれるフィールドを対応する値で更新します。</p>
     * <p>特別なキーを持つマップをフィールドの値として指定すると、そのフィールドに対して特別な更新を行えます($incなら数値の増加など)。UpdateOperationクラスを利用するとそのようなマップを簡単に作れます。</p>
     * @param values 更新するフィールドと値
     * @throws BaasdayException 更新内容が正しくない場合、サーバでの更新に失敗した場合
     * @see UpdateOperations
     */
    public void update(final Map<String, Object> values) throws BaasdayException;
}
