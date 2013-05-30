package com.baasday;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>baasdayサーバ上に保存されるスコアランキングのエントリーです。</p>
 * <p>スコアと任意のフィールドを持ち、複数のスコアランキングを作成することができます。</p>
 * <p>スコアは整数のみが許容され、値の大きいものが上位になります。少数を扱う場合はアプリケーションで変換してください(小数点以下3桁まで使う場合は1000倍する等)。値の小さいものを上位にする場合は-1をかけてください。</p>
 * @see BaasdayObject
 */
public class LeaderboardEntry extends BaasdayObject implements UpdatableObject, DeletableObject {
    private final String leaderboardName;

    LeaderboardEntry(final String leaderboardName, final Map<String, Object> values) {
        super(values);
        this.leaderboardName = leaderboardName;
    }

    /**
     * <p>スコアを返します。</p>
     * <p>これはgetInt("_score")と同じです。</p>
     * @return スコア
     * @throws BaasdayException "_score"フィールドに含まれる値が数値でない場合(通常は発生しません)
     */
    public int getScore() throws BaasdayException {
        return this.getInt("_score");
    }

    /**
     * <p>このエントリーのスコアランキング内での順位を返します。</p>
     * <p>これはgetInt("_rank")と同じです。</p>
     * <p>同一スコアの場合は同じ順位になります。</p>
     * @return 順位
     * @throws BaasdayException "_rank"フィールドに含まれる値が数値でない場合(通常は発生しません)
     * @see com.baasday.LeaderboardEntry#getOrder()
     */
    public int getRank() throws BaasdayException {
        return this.getInt("_rank");
    }

    /**
     * <p>このエントリーのスコアランキング内での順番を返します。</p>
     * <p>これはgetInt("_order")と同じです。</p>
     * <p>同一スコアの場合は先に登録されたものが上位になります。</p>
     * @return 順番
     * @throws BaasdayException "_order"フィールドに含まれる値が数値でない場合(通常は発生しません)
     * @see com.baasday.LeaderboardEntry#getRank()
     */
    public int getOrder() throws BaasdayException {
        return this.getInt("_order");
    }

    private static String leaderboardAPIPath(final String leaderboardName) {
        return "leaderboards/" + leaderboardName;
    }

    private static String apiPath(final String leaderboardName, final String id) {
        return leaderboardAPIPath(leaderboardName) + "/" + id;
    }

    String apiPath() throws BaasdayException {
        return apiPath(this.leaderboardName, this.getId());
    }

    private static class LeaderboardEntryFactory implements APIClient.BaasdayObjectFactory<LeaderboardEntry> {
        private final String leaderboardName;

        LeaderboardEntryFactory(final String leaderboardName) {
            this.leaderboardName = leaderboardName;
        }

        public LeaderboardEntry createFromAPIResult(final Map<String, Object> values) throws BaasdayException {
            return new LeaderboardEntry(this.leaderboardName, values);
        }
    }

    /**
     * <p>指定されたスコアランキングにエントリーを追加します。baasdayサーバへの追加は即時に行われます。</p>
     * <p>スコアランキングが存在しない場合は自動的に作成されます。</p>
     * @param leaderboardName スコアランキング名
     * @param values エントリーが持つ値。"_score"フィールドにスコアが設定されている必要があります
     * @return 追加したエントリー
     * @throws BaasdayException 追加に失敗した場合
     */
    public static LeaderboardEntry create(final String leaderboardName, final Map<String, Object> values) throws BaasdayException {
        return APIClient.create(leaderboardAPIPath(leaderboardName), values, new LeaderboardEntryFactory(leaderboardName));
    }

    /**
     * <p>指定されたスコアランキングにエントリーを追加します。baasdayサーバへの追加は即時に行われます。</p>
     * <p>スコアランキングが存在しない場合は自動的に作成されます。</p>
     * @param leaderboardName スコアランキング名
     * @param score スコア
     * @param values エントリーが持つスコア以外の値
     * @return 追加したエントリー
     * @throws BaasdayException 追加に失敗した場合
     */
    public static LeaderboardEntry create(final String leaderboardName, final int score, final Map<String, Object> values) throws BaasdayException {
        final Map<String, Object> mergedValues = values == null ? new HashMap<String, Object>() : new HashMap<String, Object>(values);
        mergedValues.put("_score", score);
        return create(leaderboardName, mergedValues);
    }

    /**
     * <p>指定されたスコアランキングにエントリーを追加します。baasdayサーバへの追加は即時に行われます。</p>
     * <p>スコアランキングが存在しない場合は自動的に追加されます。</p>
     * @param leaderboardName スコアランキング名
     * @param score スコア
     * @return 追加したエントリー
     * @throws BaasdayException 追加に失敗した場合
     */
    public static LeaderboardEntry create(final String leaderboardName, final int score) throws BaasdayException {
        return create(leaderboardName, score, null);
    }

    /**
     * <p>指定されたスコアランキング内の指定されたIDを持つエントリーを取得して返します。</p>
     * @param leaderboardName スコアランキング名
     * @param id ID
     * @return エントリー
     * @throws BaasdayException 取得に失敗した場合、指定されたIDを持つエントリーが存在しない場合
     */
    public static LeaderboardEntry fetch(final String leaderboardName, final String id) throws BaasdayException {
        return APIClient.fetch(apiPath(leaderboardName, id), new LeaderboardEntryFactory(leaderboardName));
    }

    /**
     * <p>指定されたスコアランキング内のエントリーを取得して返します。</p>
     * <p>フィルタとソート順と最大待ち時間は指定できません。ソート順はスコアの大きい順です。最大取得件数を指定しない場合や101以上を指定した場合、最大で100件返します。</p>
     * @param leaderboardName スコアランキング名
     * @param query 抽出条件。取得開始位置と最大取得件数だけが有効です。
     * @return 取得結果
     * @throws BaasdayException 取得に失敗した場合
     */
    public static ListResult<LeaderboardEntry> fetchAll(final String leaderboardName, final Query query) throws BaasdayException {
        return APIClient.fetchAll(leaderboardAPIPath(leaderboardName), query, new LeaderboardEntryFactory(leaderboardName));
    }

    /**
     * <p>指定されたスコアランキング内のエントリーを取得して返します。</p>
     * <p>スコアの大きい順に最大で100件返します。</p>
     * @param leaderboardName スコアランキング名
     * @return 取得結果
     * @throws BaasdayException 取得に失敗した場合
     */
    public static ListResult<LeaderboardEntry> fetchAll(final String leaderboardName) throws BaasdayException {
        return fetchAll(leaderboardName, null);
    }

    /**
     * <p>このエントリーを更新します。baasdayサーバへの反映は即時に反映されます。</p>
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
     * <p>このエントリーをbaasdayサーバ上から削除します。</p>
     * @throws BaasdayException 削除に失敗した場合
     */
    public void delete() throws BaasdayException {
        super.delete();
    }
}
