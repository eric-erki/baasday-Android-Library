package com.baasday;

import java.util.Map;

/**
 * <p>認証済みのユーザを表すクラスです。ユーザの作成はこのクラスを用いて行います。</p>
 * <p>createメソッドでユーザを作成し、getAuthenticationKeyメソッドが返す認証キーをアプリケーションで保存してください。</p>
 * <p>保存した認証キーを引数にしてBaasday#setUserAuthenticationKeyメソッドを呼び出せば、作成したユーザをfetchメソッドで取得できるようになります。</p>
 * @see Baasday#setUserAuthenticationKey(String)
 * @see BaasdayObject
 */
public class AuthenticatedUser extends User implements UpdatableObject {
    AuthenticatedUser(final Map<String, Object> values) {
        super(values);
    }

    /**
     * <p>認証キーを返します。</p>
     * <p>これはgetString("_authenticationKey")と同じです。</p>
     * @return 認証キー
     * @throws BaasdayException "_authenticationKey"に文字列以外が設定されている場合(通常は発生しません)
     * @see Baasday#setUserAuthenticationKey(String)
     */
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

    /**
     * <p>ユーザを作成します。baasdayサーバへの登録は即時に行われます。</p>
     * @param values ユーザが持つ値
     * @return 作成したユーザ
     * @throws BaasdayException 作成に失敗した場合
     */
    public static AuthenticatedUser create(final Map<String, Object> values) throws BaasdayException {
        return getInstance(User.create(values));
    }

    /**
     * <p>ユーザを作成します。baasdayサーバへの登録は即座に行われます。</p>
     * @return 作成したユーザ
     * @throws BaasdayException 作成に失敗した場合
     */
    public static AuthenticatedUser create() throws BaasdayException {
        return create(null);
    }

    /**
     * <p>Baasdayクラスに設定されている認証キーをもとにユーザを取得して返します。</p>
     * <p>ユーザを取得する前にBaasday#setUserAuthenticationKeyで認証キーを設定する必要があります。</p>
     * @return 取得したユーザ
     * @throws BaasdayException 取得に失敗した場合
     * @see Baasday#setUserAuthenticationKey(String)
     */
    public static AuthenticatedUser fetch() throws BaasdayException {
        return APIClient.fetch("me", AUTHENTICATED_USER_FACTORY);
    }

    /**
     * <p>このユーザを更新します。baasdayサーバへの反映は即時に反映されます。</p>
     * <p>valuesに含まれるフィールドを対応する値で更新します。</p>
     * <p>特別なキーを持つマップをフィールドの値として指定すると、そのフィールドに対して特別な更新を行えます($incなら数値の増加など)。UpdateOperationクラスを利用するとそのようなマップを簡単に作れます。</p>
     * <p>ユーザを更新する前にBaasday#setUserAuthenticationKeyで認証キーを設定する必要があります。</p>
     * @param values 更新するフィールドと値
     * @throws BaasdayException 更新内容が正しくない場合、サーバでの更新に失敗した場合
     * @see UpdateOperations
     * @see Baasday#setUserAuthenticationKey(String)
     */
    public void update(final Map<String, Object> values) throws BaasdayException {
        super.update(values);
    }
}
