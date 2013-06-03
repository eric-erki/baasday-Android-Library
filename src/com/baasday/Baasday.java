package com.baasday;

/**
 * <p>アプリケーション内でのbaasdayの設定を保持するクラスです。</p>
 * <p>最初にsetupメソッドでアプリケーションIDとAPIキーを設定する必要があります。</p>
 * <p>以前に作成したユーザを取得したり、ユーザを更新する場合はsetUserAuthenticationKeyでユーザの認証キーを設定します。認証キーはユーザの作成時に取得できるので、アプリケーション内で別途保存しておく必要があります。</p>
 * @see AuthenticatedUser
 */
public final class Baasday {
    private static final String API_URL_ROOT = "https://baasday.com/api/";
    private static final String version = "0.5";

    private static String applicationId;
    private static String apiKey;
    private static String userAuthenticationKey;
    private static String apiURLRoot = API_URL_ROOT;

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

    /**
     * <p>baasday Web APIのURLを返します。</p>
     * @return baasday Web APIのURL
     */
    public static String getApiURLRoot() {
        return apiURLRoot;
    }

    /**
     * <p>baasday Web APIのURLを設定します。通常は変更する必要はありません。</p>
     * @param apiURLRoot baasday Web APIのURL
     */
    public static void setApiUrlRoot(final String apiURLRoot) {
        Baasday.apiURLRoot = apiURLRoot;
    }
}
