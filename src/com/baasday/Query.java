package com.baasday;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>複数のオブジェクトを取得するときの抽出条件を表すクラスです。</p>
 */
public class Query {
    /**
     * <p>ソート順を指定するときのフィールドの情報を表すクラスです。</p>
     */
    public static class FieldOrder {
        private String field;
        private boolean isDescending;

        /**
         * <p>フィールド名と降順かどうかからオブジェクトを作成します。</p>
         * @param field フィールド名
         * @param isDescending 降順の場合はtrue、昇順の場合はfalse
         */
        public FieldOrder(final String field, final boolean isDescending) {
            this.field = field;
            this.isDescending = isDescending;
        }

        /**
         * <p>フィールド名からオブジェクトを作成します。順序は昇順です。</p>
         * @param field フィールド名
         */
        public FieldOrder(final String field) {
            this(field, false);
        }

        /**
         * <p>フィールド名を返します。</p>
         * @return フィールド名
         */
        public String getField() {
            return this.field;
        }

        /**
         * <p>フィールド名を設定します。</p>
         * @param field フィールド名
         */
        public void setField(final String field) {
            this.field = field;
        }

        /**
         * <p>降順かどうかを返します。</p>
         * @return 降順の場合はture
         */
        public boolean isDescending() {
            return this.isDescending;
        }

        /**
         * <p>降順かどうかを設定します。</p>
         * @param isDescending 降順の場合はtrue、昇順の場合はfalse
         */
        public void setDescending(final boolean isDescending) {
            this.isDescending = isDescending;
        }
    }

    /**
     * <p>フィルタを簡潔に記述するためのユーティリティです。</p>
     * <p>「priceフィールドの値が100以上、またはnameフィールドの値が"apple"」というフィルタを作成するときは</p>
     * <pre>
     * Filters.or(Filters.greaterThanOrEqual("price", 100), Filters.equal("name", "apple"))
     * </pre>
     * <p>のように使用します。</p>
     */
    public static final class Filters {
        private Filters() {
        }

        /**
         * <p>フィールドの値が指定された値と一致しないことを条件とするフィルタの特別なキーです。</p>
         */
        public static final String NOT_EQUAL = "$ne";

        /**
         * <p>フィールドの値が指定された値より小さいことを条件とするフィルタの特別なキーです。</p>
         */
        public static final String LESS_THAN = "$lt";

        /**
         * <p>フィールドの値が指定された値以下であることを条件とするフィルタの特別なキーです。</p>
         */
        public static final String LESS_THAN_OR_EQUAL = "$lte";

        /**
         * <p>フィールドの値が指定された値より大きいことを条件とするフィルタの特別なキーです。</p>
         */
        public static final String GREATER_THAN = "$gt";

        /**
         * <p>フィールドの値が指定された値以上であることを条件とするフィルタの特別なキーです。</p>
         */
        public static final String GREATER_THAN_OR_EQUAL = "$gte";

        /**
         * <p>複数のフィルタのすべてを満たすことを条件とするフィルタの特別なキーです。</p>
         */
        public static final String AND = "$and";

        /**
         * <p>複数のフィルタのうちどれかひとつでも満たすことを条件とするフィルタの特別なキーです。</p>
         */
        public static final String OR = "$or";

        /**
         * <p>指定されたフィルタを満たさないことを条件とするフィルタの特別なキーです。</p>
         */
        public static final String NOT = "$not";

        /**
         * <p>フィールドの値が指定されたリストに含まれることを条件とするフィルタの特別なキーです。</p>
         */
        public static final String IN = "$in";

        private static Map<String, Object> fieldFilter(final String field, final String operator, final Object value) {
            return Utility.singleEntryMap(field, (Object) Utility.singleEntryMap(operator, value));
        }

        /**
         * <p>フィールドの値が指定された値と一致することを条件とするフィルタを返します。</p>
         * @param field フィールド名
         * @param value 値
         * @return フィルタ
         */
        public static Map<String, Object> equal(final String field, final Object value) {
            return Utility.singleEntryMap(field, value);
        }

        /**
         * <p>フィールドの値が指定された値と一致しないことを条件とするフィルタを返します。</p>
         * @param field フィールド名
         * @param value 値
         * @return フィルタ
         */
        public static Map<String, Object> notEqual(final String field, final Object value) {
            return fieldFilter(field, NOT_EQUAL, value);
        }

        /**
         * <p>フィールドの値が指定された値より小さいことを条件とするフィルタを返します。</p>
         * @param field フィールド名
         * @param value 値
         * @return フィルタ
         */
        public static Map<String, Object> lessThan(final String field, final Object value) {
            return fieldFilter(field, LESS_THAN, value);
        }

        /**
         * <p>フィールドの値が指定された値以下であることを条件とするフィルタを返します。</p>
         * @param field フィールド名
         * @param value 値
         * @return フィルタ
         */
        public static Map<String, Object> lessThanOrEqual(final String field, final Object value) {
            return fieldFilter(field, LESS_THAN_OR_EQUAL, value);
        }

        /**
         * <p>フィールドの値が指定された値より大きいことを条件とするフィルタを返します。</p>
         * @param field フィールド名
         * @param value 値
         * @return フィルタ
         */
        public static Map<String, Object> greaterThan(final String field, final Object value) {
            return fieldFilter(field, GREATER_THAN, value);
        }

        /**
         * <p>フィールドの値が指定された値以上であることを条件とするフィルタを返します。</p>
         * @param field フィールド名
         * @param value 値
         * @return フィルタ
         */
        public static Map<String, Object> greaterThanOrEqual(final String field, final Object value) {
            return fieldFilter(field, GREATER_THAN_OR_EQUAL, value);
        }

        /**
         * <p>指定された複数のフィルタのすべてを満たすことを条件とするフィルタを返します。</p>
         * @param filters フィルタのリスト
         * @return フィルタ
         */
        public static Map<String, Object> and(final List<Map<String, Object>> filters) {
            return Utility.singleEntryMap(AND, (Object) filters);
        }

        /**
         * <p>指定された複数のフィルタのすべてを満たすことを条件とするフィルタを返します。</p>
         * @param filters 複数のフィルタ
         * @return フィルタ
         */
        public static Map<String, Object> and(final Map<String, Object> ... filters) {
            return and(Arrays.asList(filters));
        }

        /**
         * <p>指定された複数のフィルタのうちどれかひとつでも満たすことを条件とするフィルタを返します。</p>
         * @param filters フィルタのリスト
         * @return フィルタ
         */
        public static Map<String, Object> or(final List<Map<String, Object>> filters) {
            return Utility.singleEntryMap(OR, (Object) filters);
        }

        /**
         * <p>指定された複数のフィルタのうちどれかひとつでも満たすことを条件とするフィルタを返します。</p>
         * @param filters 複数のフィルタ
         * @return フィルタ
         */
        public static Map<String, Object> or(final Map<String, Object> ... filters) {
            return or(Arrays.asList(filters));
        }

        /**
         * <p>指定されたフィルタを満たさないことを条件とするフィルタを返します。</p>
         * @param filter フィルタ
         * @return フィルタ
         */
        public static Map<String, Object> not(final Map<String, Object> filter) {
            return Utility.singleEntryMap(NOT, (Object) filter);
        }

        /**
         * <p>フィールドの値が指定されたリストに含まれることを条件とするフィルタを返します。</p>
         * @param field フィールド
         * @param values リスト
         * @return フィルタ
         */
        public static Map<String, Object> in(final String field, final List<Object> values) {
            return fieldFilter(field, IN, values);
        }

        /**
         * <p>フィールドの値が指定された配列に含まれることを条件とするフィルタを返します。</p>
         * @param field フィールド
         * @param values 配列
         * @return フィルタ
         */
        public static Map<String, Object> in(final String field, final Object[] values) {
            return in(field, Arrays.asList(values));
        }
    }

    private Map<String, Object> filter;
    private List<FieldOrder> order;
    private Integer skip;
    private Integer limit;
    private Integer waitSeconds;

    /**
     * <p>フィルタを返します。</p>
     * @return フィルタ
     */
    public Map<String, Object> filter() {
        return this.filter;
    }

    /**
     * <p>フィルタを設定します。</p>
     * <p>filterに含まれるフィールドに対応する値を持つオブジェクトだけが返されるようになります。</p>
     * <p>特別なキーを持つマップをフィルタの値として指定すると、特別な条件でフィルタリングができます($gtなら値より大きいものなど)。Query.Filtersクラスを利用するとそのようなマップを簡単に作成できます。</p>
     * @param filter フィルタ
     * @return このオブジェクト
     * @see Query.Filters
     */
    public Query filter(final Map<String, Object> filter) {
        this.filter = filter;
        return this;
    }

    /**
     * <p>フィルタを未設定の状態にします。</p>
     * @return このオブジェクト
     */
    public Query unsetFilter() {
        this.filter = null;
        return this;
    }

    /**
     * <p>フィルタが設定されているかどうかを返します。</p>
     * @return フィルタが設定されている場合はtrue、設定されていない場合はfalse
     */
    public boolean hasFilter() {
        return this.filter != null;
    }

    /**
     * <p>ソート順を返します。</p>
     * @return ソート順
     */
    public List<FieldOrder> order() {
        return this.order;
    }

    /**
     * <p>ソート順を設定します。</p>
     * @param fieldOrders ソート順
     * @return このオブジェクト
     */
    public Query order(final List<FieldOrder> fieldOrders) {
        this.order = fieldOrders;
        return this;
    }

    /**
     * <p>ソート順を設定します。</p>
     * @param fieldOrders ソート順
     * @return このオブジェクト
     */
    public Query order(final FieldOrder ... fieldOrders) {
        return this.order(Arrays.asList(fieldOrders));
    }

    /**
     * <p>ソート順を設定します。</p>
     * <p>指定されたフィールド名の昇順に設定します。</p>
     * @param fields ソート対象のフィールド名
     * @return このオブジェクト
     */
    public Query order(final String ... fields) {
        final int length = fields.length;
        final FieldOrder[] fieldOrders = new FieldOrder[length];
        for (int index = 0; index < length; ++index) {
            fieldOrders[index] = new FieldOrder(fields[index]);
        }
        return this.order(fieldOrders);
    }

    /**
     * <p>ソート順を未設定の状態にします。</p>
     * @return このオブジェクト
     */
    public Query unsetOrder() {
        this.order = null;
        return this;
    }

    /**
     * <p>ソート順が設定されているかどうかを返します。</p>
     * @return ソート順が設定されている場合はtrue、設定されていない場合はfalse
     */
    public boolean hasOrder() {
        return this.order != null;
    }

    /**
     * <p>取得開始位置を返します。</p>
     * @return 取得開始位置
     */
    public int skip() {
        return this.skip;
    }

    /**
     * <p>取得開始位置を設定します。</p>
     * @param skip 取得開始位置
     * @return このオブジェクト
     */
    public Query skip(final int skip) {
        this.skip = skip;
        return this;
    }

    /**
     * <p>取得開始位置を未設定の状態にします。</p>
     * @return このオブジェクト
     */
    public Query unsetSkip() {
        this.skip = null;
        return this;
    }

    /**
     * <p>取得開始位置が設定されているかどうかを返します。</p>
     * @return 取得開始位置が設定されている場合はtrue、設定されていない場合はfalse
     */
    public boolean hasSkip() {
        return this.skip != null;
    }

    /**
     * <p>最大取得件数を返します。</p>
     * @return 最大取得件数
     */
    public int limit() {
        return this.limit;
    }

    /**
     * <p>最大取得件数を設定します。</p>
     * <p>101以上を設定しても最大で100件しか返されません。</p>
     * @param limit 最大取得件数
     * @return このオブジェクト
     */
    public Query limit(final int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * <p>最大取得件数を未設定の状態にします。</p>
     * @return このオブジェクト
     */
    public Query unsetLimit() {
        this.limit = null;
        return this;
    }

    /**
     * <p>最大取得件数が設定されているかどうかを返します。</p>
     * @return 最大取得件数が設定されている場合はtrue、設定されていない場合はfalse
     */
    public boolean hasLimit() {
        return this.limit != null;
    }

    /**
     * <p>最大待ち時間を返します。</p>
     * @return 最大待ち時間(秒)
     */
    public int waitSeconds() {
        return this.waitSeconds;
    }

    /**
     * <p>最大待ち時間を設定します。</p>
     * <p>最大待ち時間を設定すると、条件を満たすオブジェクトが存在しない場合に、条件を満たすオブジェクトが作成されるまで設定された秒数(最大で30秒)だけサーバが応答を保留します。</p>
     * <p>これにより何度もリクエストしなくてもオブジェクトの作成をそれなりの精度で検出できます。サーバで応答が保留されている間は結果が返らないので、アプリケーションが停止しないように注意してください。</p>
     * @param waitSeconds 最大待ち時間
     * @return このオブジェクト
     */
    public Query waitSeconds(final int waitSeconds) {
        this.waitSeconds = waitSeconds;
        return this;
    }

    /**
     * <p>最大待ち時間を未設定の状態にします。</p>
     * @return このオブジェクト
     */
    public Query unsetWait() {
        this.waitSeconds = null;
        return this;
    }

    /**
     * <p>最大待ち時間が設定されているかどうかを返します。</p>
     * @return 最大待ち時間が設定されている場合はtrue、設定されていない場合はfalse
     */
    public boolean hasWaitSeconds() {
        return this.waitSeconds != null;
    }

    private static String orderString(final List<FieldOrder> fieldOrders) {
        final StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (final FieldOrder fieldOrder : fieldOrders) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append(",");
            }
            if (fieldOrder.isDescending()) stringBuilder.append("-");
            stringBuilder.append(fieldOrder.getField());
        }
        return stringBuilder.toString();
    }

    Map<String, Object> toRequestParameters() {
        final Map<String, Object> requestParameters = new HashMap<String, Object>();
        if (this.hasFilter()) requestParameters.put("filter", Utility.jsonString(this.filter()));
        if (this.hasOrder()) requestParameters.put("order", orderString(this.order()));
        if (this.hasSkip()) requestParameters.put("skip", this.skip());
        if (this.hasLimit()) requestParameters.put("limit", this.limit());
        if (this.hasWaitSeconds()) requestParameters.put("wait", this.waitSeconds());
        return requestParameters;
    }
}
