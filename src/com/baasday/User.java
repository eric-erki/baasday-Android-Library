package com.baasday;

import java.util.Map;

public class User extends BaasdayObject {
    public User(final Map<String, Object> values) {
        super(values);
    }

    private static final String USERS_API_PATH = "users";

    private static String apiPath(final String id) {
        return USERS_API_PATH + "/" + id;
    }

    String apiPath() throws BaasdayException {
        return apiPath(this.getId());
    }

    private static class UserFactory implements APIClient.BaasdayObjectFactory<User> {
        public User createFromAPIResult(final Map<String, Object> values) throws BaasdayException {
            return new User(values);
        }
    }
    private static final UserFactory USER_FACTORY = new UserFactory();

    public static User create(final Map<String, Object> values) throws BaasdayException {
        return APIClient.create(USERS_API_PATH, values, USER_FACTORY);
    }

    public static User create() throws BaasdayException {
        return create(null);
    }

    public static User fetch(final String id) throws BaasdayException {
        return APIClient.fetch(apiPath(id), USER_FACTORY);
    }

    public static ListResult<User> fetchAll(final Query query) throws BaasdayException {
        return APIClient.fetchAll(USERS_API_PATH, query, USER_FACTORY);
    }

    public static ListResult<User> fetchAll() throws BaasdayException {
        return fetchAll(null);
    }
}
