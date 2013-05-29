package com.baasday;

import java.util.Map;

public class AuthenticatedUser extends User {
    public AuthenticatedUser(final Map<String, Object> values) {
        super(values);
    }

    public String getAuthenticationKey() throws BaasdayException {
        return this.getString("_authenticationKey");
    }

    String apiPath() {
        return "me";
    }

    private static AuthenticatedUser getInstance(final User user) {
        return new AuthenticatedUser(user.getValues());
    }

    private static class AuthenticatedUserFactory implements APIClient.BaasdayObjectFactory<AuthenticatedUser> {
        public AuthenticatedUser createFromAPIResult(final Map<String, Object> values) throws BaasdayException {
            return new AuthenticatedUser(values);
        }
    }
    private static final AuthenticatedUserFactory AUTHENTICATED_USER_FACTORY = new AuthenticatedUserFactory();

    public static AuthenticatedUser create(final Map<String, Object> values) throws BaasdayException {
        return getInstance(User.create(values));
    }

    public static AuthenticatedUser create() throws BaasdayException {
        return create(null);
    }

    public static AuthenticatedUser fetch() throws BaasdayException {
        return APIClient.fetch("me", AUTHENTICATED_USER_FACTORY);
    }
}
