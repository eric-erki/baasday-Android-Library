package com.baasday;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>認証済みのユーザを表すクラスです。ユーザの作成はこのクラスを用いて行います。</p>
 * <p>createメソッドでユーザを作成し、getAuthenticationKeyメソッドが返す認証キーをアプリケーション内に保存してください。</p>
 * <p>保存した認証キーをBaasday#setUserAuthenticationKeyで設定すれば、作成したユーザをfetchメソッドで取得できるようになります。</p>
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

    /**
     * <p>BDBaasdayクラスに設定されている端末IDに対応した端末情報を返します。</p>
     * <p>端末情報を取得する前にBaasday#setDeviceIdで端末IDを設定する必要があります。詳細はDeviceクラスを参照してください。</p>
     * <p>端末情報がまだ保存されていない場合は空の端末情報を返します。</p>
     * @return 端末情報
     * @throws BaasdayException 端末IDが設定されていない場合
     * @see Device
     */
    public Device getCurrentDevice() throws BaasdayException {
        final String currentDeviceId = Baasday.getDeviceId();
        if (currentDeviceId == null) throw new BaasdayException("no device ID is set on the class Baasday");
        final List<Object> devices = this.getList("_devices");
        if (devices != null) {
            for (final Object element : devices) {
                if (!(element instanceof Map)) continue;
                @SuppressWarnings("unchecked")
                final Map<String, Object> deviceValues = (Map<String, Object>) element;
                final Object deviceId = deviceValues.get("_id");
                if (deviceId != null && deviceId.equals(currentDeviceId)) return new Device(deviceValues);
            }
        }
        return new Device(Utility.singleEntryMap("_id", (Object) currentDeviceId));
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

    /**
     * <p>端末情報を更新します。</p>
     * @param device 端末情報
     * @throws BaasdayException 更新に失敗した場合
     */
    public void updateDevice(final Device device) throws BaasdayException {
        this.update(Utility.singleEntryMap("_devices", (Object) Arrays.asList(device)));
    }
}
