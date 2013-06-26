package com.baasday;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>baasdayサーバ上に保存されるオブジェクトの共通の基底クラスです。フィールドの値の取得、baasday上のデータの更新、削除の機能を提供します。</p>
 *
 * <p>フィールドの値はNumber(数値)、String(文字列)、Boolean(ブール)、Date(日付)、List、Map、nullで表されます。</p>
 */
public abstract class BaasdayObject extends BasicObject {
    BaasdayObject(final Map<String, Object> values) {
        super(values);
    }

    /**
     * <p>IDを返します。</p>
     * <p>これはgetString("_id")と同じです。</p>
     * @return ID
     * @throws BaasdayException "_id"フィールドに文字列以外が設定されている場合(通常は発生しません)
     */
    public String getId() throws BaasdayException {
        return this.getString("_id");
    }

    /**
     * <p>作成日時を返します。</p>
     * <p>これはgetDate("_createdAt")と同じです。</p>
     * @return 作成日時
     * @throws BaasdayException "_createdAt"フィールドに日付以外が設定されている場合(通常は発生しません)
     */
    public Date getCreatedAt() throws BaasdayException {
        return this.getDate("_createdAt");
    }

    /**
     * <p>更新日時を返します。</p>
     * <p>これはgetDate("_updatedAt")と同じです。</p>
     * @return 更新日時
     * @throws BaasdayException "_updatedAt"フィールドに日付以外が設定されている場合(通常は発生しません)
     */
    public Date getUpdatedAt() throws BaasdayException {
        return this.getDate("_updatedAt");
    }

    abstract String apiPath() throws BaasdayException;


    void update(final Map<String, Object> values) throws BaasdayException {
        this.setValues(new APIClient().put(this.apiPath()).requestJson(values).doRequest());
    }

    void delete() throws BaasdayException {
        new APIClient().delete(this.apiPath()).doRequest();
    }
}