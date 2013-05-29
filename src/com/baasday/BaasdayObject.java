package com.baasday;

import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class BaasdayObject {
    private Map<String, Object> values;

    BaasdayObject(final Map<String, Object> values) {
        this.values = values;
    }

    public Map<String, Object> getValues() {
        return this.values;
    }

    public Object get(final String field) {
        return this.values.get(field);
    }

    public boolean has(final String field) {
        return this.values.containsKey(field);
    }

    public boolean isNull(final String field) {
        return this.has(field) && this.get(field) == null;
    }

    private static BaasdayException exceptionForInvalidType(final String field, final String typeName) throws BaasdayException {
        return new BaasdayException("The value of the field " + field + " cannot be coerced to " + typeName);
    }

    public String getString(final String field) throws BaasdayException {
        final Object value = this.get(field);
        if (value == null) return null;
        if (!(value instanceof String)) throw exceptionForInvalidType(field, "a string");
        return (String) value;
    }

    public Number getNumber(final String field) throws BaasdayException {
        final Object value = this.get(field);
        if (value == null) return null;
        if (!(value instanceof Number)) throw exceptionForInvalidType(field, "a number");
        return (Number) value;
    }

    public int getInt(final String field) throws BaasdayException {
        final Number value = this.getNumber(field);
        return value == null ? 0 : value.intValue();
    }

    public long getLong(final String field) throws BaasdayException {
        final Number value = this.getNumber(field);
        return value == null ? 0 : value.longValue();
    }

    public double getDouble(final String field) throws BaasdayException {
        final Number value = this.getNumber(field);
        return value == null ? 0 : value.doubleValue();
    }

    public boolean getBoolean(final String field) throws BaasdayException {
        final Object value = this.get(field);
        if (value == null) return false;
        if (!(value instanceof Boolean)) throw exceptionForInvalidType(field, "a boolean");
        return (Boolean) this.get(field);
    }

    public List<Object> getList(final String field) throws BaasdayException {
        final Object value = this.get(field);
        if (value == null) return null;
        if (!(value instanceof List)) throw exceptionForInvalidType(field, "a list");
        @SuppressWarnings("unchecked")
        final List<Object> result = (List<Object>) this.get(field);
        return result;
    }

    public Map<String, Object> getMap(final String field) throws BaasdayException {
        final Object value = this.get(field);
        if (value == null) return null;
        if (!(value instanceof Map)) throw exceptionForInvalidType(field, "a map");
        @SuppressWarnings("unchecked")
        final Map<String, Object> result = (Map<String, Object>) this.get(field);
        return result;
    }

    public Date getDate(final String field) throws BaasdayException {
        final Object value = this.get(field);
        if (value == null) return null;
        if (!(value instanceof Date)) throw exceptionForInvalidType(field, "a date");
        return (Date) value;
    }

    public String getId() throws BaasdayException {
        return this.getString("_id");
    }

    public Date getCreatedAt() throws BaasdayException {
        return this.getDate("_createdAt");
    }

    public Date getUpdatedAt() throws BaasdayException {
        return this.getDate("_updatedAt");
    }

    abstract String apiPath() throws BaasdayException;

    public void update(final Map<String, Object> values) throws BaasdayException {
        this.values = new APIClient().put(this.apiPath()).requestJson(values).doRequest();
    }

    public void delete() throws BaasdayException {
        new APIClient().delete(this.apiPath()).doRequest();
    }
}