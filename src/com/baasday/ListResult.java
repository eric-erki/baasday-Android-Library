package com.baasday;

import java.util.ArrayList;
import java.util.List;

public class ListResult<E> {
    private final int count;
    private final List<E> contents;

    public ListResult(final int count, final List<E> contents) {
        this.count = count;
        this.contents = contents;
    }

    public int getCount() {
        return this.count;
    }

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
