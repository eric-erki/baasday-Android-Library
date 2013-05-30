package com.baasday;

/**
 * <p>baasdayサーバ上から削除可能なオブジェクトを表します。</p>
 */
public interface DeletableObject {
    /**
     * <p>このオブジェクトをbaasdayサーバ上から削除します。</p>
     * @throws BaasdayException 削除に失敗した場合
     */
    public void delete() throws BaasdayException;
}
