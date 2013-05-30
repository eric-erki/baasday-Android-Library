package com.baasday;

import java.util.Map;

/**
 * <p>アプリケーションのユーザを表すオブジェクトです。</p>
 * <p>任意のフィールドを持つことができます。</p>
 * <p>このクラスから更新を行うことはできません。更新する場合はAuthenticatedUserクラスを利用してください。</p>
 * @see BaasdayObject
 * @see AuthenticatedUser
 */
public class User extends BaasdayObject {
    User(final Map<String, Object> values) {
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

    static User create(final Map<String, Object> values) throws BaasdayException {
        return APIClient.create(USERS_API_PATH, values, USER_FACTORY);
    }

    /**
     * <p>指定されたIDを持つユーザを取得して返します。</p>
     * @param id ID
     * @return ユーザ
     * @throws BaasdayException 取得に失敗した場合、指定されたIDを持つユーザが存在しない場合
     */
    public static User fetch(final String id) throws BaasdayException {
        return APIClient.fetch(apiPath(id), USER_FACTORY);
    }

    /**
     * <p>ユーザを取得して返します。</p>
     * <p>最大待ち時間は指定できません。最大取得件数を指定しない場合や101以上を指定した場合、最大で100件返します。</p>
     * @param query 抽出条件
     * @return 取得結果
     * @throws BaasdayException 取得に失敗した場合、抽出条件が正しくない場合
     */
    public static ListResult<User> fetchAll(final Query query) throws BaasdayException {
        return APIClient.fetchAll(USERS_API_PATH, query, USER_FACTORY);
    }

    /**
     * <p>ユーザを取得して返します。</p>
     * @return 取得結果
     * @throws BaasdayException 取得に失敗した場合
     */
    public static ListResult<User> fetchAll() throws BaasdayException {
        return fetchAll(null);
    }
}
