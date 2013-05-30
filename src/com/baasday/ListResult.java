package com.baasday;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>baasdayサーバから複数のオブジェクトを取得した時の結果を表すクラスです。</p>
 * @param <E> 取得したオブジェクトのクラス
 */
public class ListResult<E> {
    private final int count;
    private final List<E> contents;

    ListResult(final int count, final List<E> contents) {
        this.count = count;
        this.contents = contents;
    }

    /**
     * <p>抽出した結果の件数を返します。</p>
     * <p>Queryに指定したフィルタを適用した件数で、取得開始位置と最大取得件数の影響は受けません。</p>
     * @return 件数
     */
    public int getCount() {
        return this.count;
    }

    /**
     * <p>取得したオブジェクトのリストを返します。</p>
     * @return 取得したオブジェクトのリスト
     */
    public List<E> getContents() {
        return this.contents;
    }

    static interface ContentConverter<E, T> {
        public T convert(final E sourceContent) throws BaasdayException;
    }

    <T> ListResult<T> convertContents(final ContentConverter<E, T> contentConverter) throws BaasdayException {
        final List<T> convertedContents = new ArrayList<T>(this.getContents().size());
        for (final E sourceContent : this.getContents()) convertedContents.add(contentConverter.convert(sourceContent));
        return new ListResult<T>(this.getCount(), convertedContents);
    }
}
