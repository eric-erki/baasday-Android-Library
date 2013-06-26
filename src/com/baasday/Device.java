package com.baasday;

import java.util.Map;
import java.util.UUID;

public class Device extends BasicObject {
    Device(final Map<String, Object> values) {
        super(values);
    }

    public static String generateDeviceid() {
        return "android:" + UUID.randomUUID().toString();
    }

    public void setRegistrationId(final String registrationId) {
        this.setValue("pushNotification", Utility.singleEntryMap("gcm", Utility.singleEntryMap("registrationId", registrationId)));
    }
}
