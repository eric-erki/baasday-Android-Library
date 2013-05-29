package com.baasday;

public class Baasday {
    static final String API_URL_ROOT = "http://baasday.com/api/";
    private static final String version = "0.5";

    private static String applicationId;
    private static String apiKey;
    private static String userAuthenticationKey;

    public static void setup(final String applicationId, final String apiKey) {
        Baasday.applicationId = applicationId;
        Baasday.apiKey = apiKey;
    }

    static String getApplicationId() {
        return Baasday.applicationId;
    }

    static String getApiKey() {
        return Baasday.apiKey;
    }

    public static void setUserAuthenticationKey(final String userAuthenticationKey) {
        Baasday.userAuthenticationKey = userAuthenticationKey;
    }

    static String getUserAuthenticationKey() {
        return Baasday.userAuthenticationKey;
    }

    static String getVersion() {
        return Baasday.version;
    }
}
