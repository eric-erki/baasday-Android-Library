package com.baasday;

import java.util.Map;
import java.util.UUID;

/**
 * <p>端末情報を保持するクラスです。</p>
 * <p>インスタンスはAuthenticatedUser#currentDeviceで取得します。更新はAuthenticatedUser#updateDeviceを使用します。</p>
 * <p>端末情報の取得、更新を行う前にBaasdayクラスのsetDeviceIdメソッドで端末IDを設定する必要があります。まだ一度も端末情報を保存していない場合はgenerateDeviceIdメソッドで端末IDを生成し、それをBaasdayクラスに設定します。生成した端末IDはアプリケーション内に保存してください。二度目以降は保存した端末IDをBaaasdayクラスに設定して使用します。</p>
 */
public class Device extends BasicObject {
    Device(final Map<String, Object> values) {
        super(values);
    }

    /**
     * <p>端末IDを生成します。</p>
     * @return 端末ID
     */
    public static String generateDeviceid() {
        return "android:" + UUID.randomUUID().toString();
    }

    /**
     * <p>Google Cloud Messaging用のレジストレーションIDを設定します。</p>
     * <p>レジストレーションIDはGCMRegistrar#registerおよびGCMRegistrar#getRegistrationIdで取得します。</p>
     * @param registrationId レジストレーションID
     */
    public void setRegistrationId(final String registrationId) {
        this.setValue("pushNotification", Utility.singleEntryMap("gcm", Utility.singleEntryMap("registrationId", registrationId)));
    }
}
