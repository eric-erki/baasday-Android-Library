package com.baasday;

import android.text.format.Time;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

final class Utility {
    private Utility() {
    }

    static void copy(final InputStream from, final OutputStream to) throws IOException {
        final byte[] buffer = new byte[1024];
        while (from.read(buffer) != -1) to.write(buffer);
    }

    static <K, V> Map<K, V> singleEntryMap(final K key, final V value) {
        final Map<K, V> result = new HashMap<K, V>();
        result.put(key, value);
        return result;
    }

    private static Object fixObjectForJSON(final Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) object;
            final Map<String, Object> result = new HashMap<String, Object>();
            for (final Map.Entry<String, Object> entry : map.entrySet()) {
                result.put(entry.getKey(), fixObjectForJSON(entry.getValue()));
            }
            return result;
        } else if (object instanceof List) {
            final List<Object> result = new ArrayList<Object>(((List) object).size());
            for (final Object value : (List) object) {
                result.add(fixObjectForJSON(value));
            }
            return result;
        } else if (object instanceof Date) {
            final Map<String, Object> result = new HashMap<String, Object>();
            result.put("$type", "datetime");
            final Time time = new Time();
            time.set(((Date) object).getTime());
            result.put("$value", time.format3339(false));
            return result;
        } else {
            return object;
        }
    }

    static String jsonString(final Map<String, Object> values) {
        final Object fixed = fixObjectForJSON(values);
        assert(fixed instanceof Map);
        @SuppressWarnings("unchecked")
        final Map<String, Object> fixedMap = (Map<String, Object>) fixed;
        return new JSONObject(fixedMap).toString();
    }

    private static Object fixObjectInJSON(final Object object) throws BaasdayException {
        if (object == null) {
            return null;
        } else if (object instanceof JSONObject) {
            final JSONObject jsonObject = (JSONObject) object;
            try {
                if (jsonObject.has("$type") && "datetime".equals(jsonObject.get("$type")) && jsonObject.has("$value")) {
                    final Object value = jsonObject.get("$value");
                    if (value == null || !(value instanceof String)) return null;
                    final Time time = new Time();
                    if (!time.parse3339((String) value)) return null;
                    return new Date(time.toMillis(true));
                }
            } catch (final JSONException exception) {
                throw new BaasdayException(exception);
            }
            final Map<String, Object> result = new HashMap<String, Object>();
            final Iterator keysIterator = jsonObject.keys();
            while (keysIterator.hasNext()) {
                final String key = keysIterator.next().toString();
                result.put(key, fixObjectInJSON(result.get(key)));
            }
            return result;
        } else if (object instanceof JSONArray) {
            final JSONArray jsonArray = (JSONArray) object;
            final int length = jsonArray.length();
            final List<Object> result = new ArrayList<Object>(length);
            for (int index = 0; index < length; ++index) {
                try {
                    result.add(fixObjectInJSON(jsonArray.get(index)));
                } catch (final JSONException exception) {
                    throw new BaasdayException(exception);
                }
            }
            return result;
        } else {
            return object;
        }
    }

    static Map<String, Object> mapFromJSONString(final String json) throws BaasdayException {
        try {
            final Object fixed = fixObjectInJSON(new JSONObject(json));
            if (!(fixed instanceof Map)) throw new BaasdayException("The API result is not a JSON Object: " + json);
            @SuppressWarnings("unchecked")
            final Map<String, Object> result = (Map<String, Object>) fixed;
            return result;
        } catch (final JSONException exception) {
            throw new BaasdayException(exception);
        }
    }
}
