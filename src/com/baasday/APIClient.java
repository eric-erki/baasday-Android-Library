package com.baasday;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

class APIClient {
    private String requestMethod;
    private String path;
    private Map<String, Object> queryParameters;
    private Map<String, Object> requestJson;

    APIClient requestMethod(final String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    APIClient path(final String path) {
        this.path = path;
        return this;
    }

    APIClient queryParameters(final Map<String, Object> query) {
        this.queryParameters = query;
        return this;
    }

    APIClient query(final Query query) {
        return this.queryParameters(query == null ? null : query.toQueryParameters());
    }

    APIClient requestJson(final Map<String, Object> requestJson) {
        this.requestJson = requestJson;
        return this;
    }

    APIClient get(final String path) {
        return this.requestMethod("GET").path(path);
    }

    APIClient post(final String path) {
        return this.requestMethod("POST").path(path);
    }

    APIClient put(final String path) {
        return this.requestMethod("PUT").path(path);
    }

    APIClient delete(final String path) {
        return this.requestMethod("delete").path(path);
    }

    private static void setAuthenticationHeaders(final HttpURLConnection connection) {
        connection.setRequestProperty("X-Baasday-Application-Id", Baasday.getApplicationId());
        connection.setRequestProperty("X-Baasday-Application-Api-Key", Baasday.getApiKey());
        if (Baasday.getUserAuthenticationKey() != null) {
            connection.setRequestProperty("X-Baasday-Application-User-Authentication-Key", Baasday.getUserAuthenticationKey());
        }
    }

    private static String queryString(final Map<String, Object> parameters) {
        final StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            builder.append(URLEncoder.encode(entry.getKey()));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString()));
        }
        return builder.toString();
    }

    HttpURLConnection createConnection() throws BaasdayException {
        String path;
        if (this.queryParameters != null) {
            path = this.path + "?" + queryString(this.queryParameters);
        } else {
            path = this.path;
        }
        try {
            final HttpURLConnection connection = (HttpURLConnection) new URL(Baasday.API_URL_ROOT + path).openConnection();
            setAuthenticationHeaders(connection);
            connection.setRequestMethod(this.requestMethod);
            if (this.requestJson != null) {
                connection.getOutputStream().write(Utility.jsonString(this.requestJson).getBytes());
            }
            return connection;
        } catch (final MalformedURLException exception) {
            throw new BaasdayException(exception);
        } catch (final IOException exception) {
            throw new BaasdayException(exception);
        }
    }

    Map<String, Object> doRequest() throws BaasdayException {
        final HttpURLConnection connection = this.createConnection();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            final InputStream inputStream = connection.getInputStream();
            Utility.copy(inputStream, byteArrayOutputStream);
            inputStream.close();
            return Utility.mapFromJSONString(new String(byteArrayOutputStream.toByteArray()));
        } catch (final IOException exception) {
            throw new BaasdayException(exception);
        }
    }

    static interface BaasdayObjectFactory<T> {
        public abstract T createFromAPIResult(final Map<String, Object> values) throws BaasdayException;
    }

    static Map<String, Object> create(final String path, final Map<String, Object> values) throws BaasdayException {
        return new APIClient().post(path).requestJson(values).doRequest();
    }

    static <T> T create(final String path, final Map<String, Object> values, final BaasdayObjectFactory<T> factory) throws BaasdayException {
        return factory.createFromAPIResult(create(path, values));
    }

    static Map<String, Object> fetch(final String path) throws BaasdayException {
        return new APIClient().get(path).doRequest();
    }

    static <T> T fetch(final String path, final BaasdayObjectFactory<T> factory) throws BaasdayException {
        return factory.createFromAPIResult(fetch(path));
    }

    private static int countFromListAPIResult(final Map<String, Object> result) throws BaasdayException {
        if (!result.containsKey(("_count"))) throw new BaasdayException("A JSON returned by the server does not contain the field \"_count\".");
        final Object count = result.get("_count");
        if (!(count instanceof Number)) throw new BaasdayException("A value of the field \"_count\" in a JSON returned by the server is not a number.");
        return ((Number) count).intValue();
    }

    private static List<Map<String, Object>> contentsFromListAPIResult(final Map<String, Object> result) throws BaasdayException {
        if (!(result.containsKey("_contents"))) throw new BaasdayException("A JSON returned by the server does not contain the field \"_contents\".");
        final Object value = result.get("_contents");
        if (!(value instanceof List)) throw new BaasdayException("A value of the field \"_contents\" in a JSON returned by the server is not a list.");
        @SuppressWarnings("unchecked")
        final List<Map<String, Object>> contents = (List<Map<String, Object>>) value;
        return contents;
    }

    static ListResult<Map<String, Object>> fetchAll(final String path, final Query query) throws BaasdayException {
        final Map<String, Object> result = new APIClient().get(path).query(query).doRequest();
        return new ListResult<Map<String, Object>>(countFromListAPIResult(result), contentsFromListAPIResult(result));
    }

    static <T> ListResult<T> fetchAll(final String path, final Query query, final BaasdayObjectFactory<T> factory) throws BaasdayException {
        return fetchAll(path, query).convertContents(new ListResult.ContentConverter<Map<String, Object>, T>() {
            public T convert(final Map<String, Object> sourceContent) throws BaasdayException {
                return factory.createFromAPIResult(sourceContent);
            }
        });
    }
}
