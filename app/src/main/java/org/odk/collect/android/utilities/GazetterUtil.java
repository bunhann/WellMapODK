package org.odk.collect.android.utilities;

/**
 * Created by bunhann on 12/23/14.
 */
public class GazetterUtil {

    private static String _provinceId;
    private static String _districtId;
    private static String _communeId;
    private static String _villageId;

    public synchronized static String get_provinceId() {
        return _provinceId;
    }

    public synchronized static void set_provinceId(String provinceId) {
        GazetterUtil._provinceId = provinceId;
    }

    public synchronized static String get_districtId() {
        return _districtId;
    }

    public synchronized static void set_districtId(String districtId) {
        GazetterUtil._districtId = districtId;
    }

    public synchronized static String get_communeId() {
        return _communeId;
    }

    public synchronized static void set_communeId(String communeId) {
        GazetterUtil._communeId = communeId;
    }

    public synchronized static String get_villageId() {
        return _villageId;
    }

    public synchronized static void set_villageId(String villageId) {
        GazetterUtil._villageId = villageId;
    }
}
