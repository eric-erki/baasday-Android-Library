package com.baasday;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query {
    public static class FieldOrder {
        private String field;
        private boolean isDescending;

        public FieldOrder(final String field, final boolean isDescending) {
            this.field = field;
            this.isDescending = isDescending;
        }

        public FieldOrder(final String field) {
            this(field, false);
        }

        public String getField() {
            return this.field;
        }

        public void setField(final String field) {
            this.field = field;
        }

        public boolean isDescending() {
            return this.isDescending;
        }

        public void setDescending(final boolean isDescending) {
            this.isDescending = isDescending;
        }
    }

    private Map<String, Object> filter;
    private List<FieldOrder> order;
    private Integer skip;
    private Integer limit;
    private Integer wait;

    public Map<String, Object> getFilter() {
        return this.filter;
    }

    public void setFilter(final Map<String, Object> filter) {
        this.filter = filter;
    }

    public void unsetFilter() {
        this.filter = null;
    }

    public boolean hasFilter() {
        return this.filter != null;
    }

    public List<FieldOrder> getOrder() {
        return this.order;
    }

    public void setOrder(final List<FieldOrder> fieldOrders) {
        this.order = fieldOrders;
    }

    public void setOrder(final FieldOrder ... fieldOrders) {
        this.setOrder(Arrays.asList(fieldOrders));
    }

    public void setOrder(final String ... fields) {
        final int length = fields.length;
        final FieldOrder[] fieldOrders = new FieldOrder[length];
        for (int index = 0; index < length; ++index) {
            fieldOrders[index] = new FieldOrder(fields[index]);
        }
        this.setOrder(fieldOrders);
    }

    public void unsetOrder() {
        this.order = null;
    }

    public boolean hasOrder() {
        return this.order != null;
    }

    public int getSkip() {
        return this.skip;
    }

    public void setSkip(final int skip) {
        this.skip = skip;
    }

    public void unsetSkip() {
        this.skip = null;
    }

    public boolean hasSkip() {
        return this.skip != null;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(final int limit) {
        this.limit = limit;
    }

    public void unsetLimit() {
        this.limit = null;
    }

    public boolean hasLimit() {
        return this.limit != null;
    }

    public int getWait() {
        return this.wait;
    }

    public void setWait(final int wait) {
        this.wait = wait;
    }

    public void unsetWait() {
        this.wait = null;
    }

    public boolean hasWait() {
        return this.wait != null;
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

    Map<String, Object> toQueryParameters() {
        final Map<String, Object> queryParameters = new HashMap<String, Object>();
        if (this.hasFilter()) queryParameters.put("filter", Utility.jsonString(this.getFilter()));
        if (this.hasOrder()) queryParameters.put("order", orderString(this.getOrder()));
        if (this.hasSkip()) queryParameters.put("skip", this.getSkip());
        if (this.hasLimit()) queryParameters.put("limit", this.getLimit());
        if (this.hasWait()) queryParameters.put("wait", this.getWait());
        return queryParameters;
    }
}
