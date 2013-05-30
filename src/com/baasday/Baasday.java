package com.baasday;

/**
 * <p>アプリケーション内でのbaasdayの設定を保持するクラスです。</p>
 * <p>最初にsetupメソッドでアプリケーションIDとAPIキーを設定する必要があります。</p>
 * <p>以前に作成したユーザを取得したり、ユーザを更新する場合はsetUserAuthenticationKeyでユーザの認証キーを設定します。認証キーはユーザの作成時に取得できるので、アプリケーション内で別途保存しておく必要があります。</p>
 * @see AuthenticatedUser
 */
public final class Baasday {
    static final String API_URL_ROOT = "http://baasday.com/api/";
    private static final String version = "0.5";

    private static String applicationId;
    private static String apiKey;
    private static String userAuthenticationKey;

    private Baasday() {
    }

    /**
     * <p>アプリケーションIDとAPIキーを設定します。</p>
     * @param applicationId アプリケーションID
     * @param apiKey APIキー
     */
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

    /**
     * <p>ユーザの認証キーを設定します。</p>
     * @param userAuthenticationKey ユーザの認証キー
     * @see AuthenticatedUser
     */
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
