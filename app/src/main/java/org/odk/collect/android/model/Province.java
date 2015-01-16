package org.odk.collect.android.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.odk.collect.android.database.GazetteerDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bunhann on 12/4/14.
 */
public class Province {

    public static final String _PROCODE = "PROCODE";
    public static final String _PREFIX = "prefix";
    public static final String _PROVINCE = "PROVINCE";
    public static final String _PROVINCE_KH = "PROVINCE_KH";

    private int proCode;
    private int prefix;
    private String province;
    private String provinceKh;

    private Context context;

    public Province(Context context) {
        this.context = context;
    }

    public Province(int proCode, int prefix, String province, String provinceKh) {
        this.proCode = proCode;
        this.prefix = prefix;
        this.province = province;
        this.provinceKh = provinceKh;
    }

    public int getProCode() {
        return proCode;
    }

    public void setProCode(int proCode) {
        this.proCode = proCode;
    }

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceKh() {
        return provinceKh;
    }

    public void setProvinceKh(String provinceKh) {
        this.provinceKh = provinceKh;
    }

    public Province get(int provinceId){
        SQLiteDatabase db = new GazetteerDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(GazetteerDBHelper.TABLE_Province, null, _PROCODE + " = " + provinceId, null, null, null, null, null);
        cursor.moveToFirst();
        Province p = null;
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            int code     = cursor.getInt(cursor.getColumnIndex(_PROCODE));
            int pre      = cursor.getInt(cursor.getColumnIndex(_PREFIX));
            String pro      = cursor.getString(cursor.getColumnIndex(_PROVINCE));
            String pro_kh   = cursor.getString(cursor.getColumnIndex(_PROVINCE_KH));
            p = new Province(code,pre, pro,pro_kh);
        }
        cursor.close();
        db.close();
        return p;
    }

    public Province getByNameKh(String proNameKh){
        SQLiteDatabase db = new GazetteerDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(GazetteerDBHelper.TABLE_Province, null, _PROVINCE_KH + " = ?" , new String[] {proNameKh}, null, null, null, null);
        cursor.moveToFirst();
        Province p = null;
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            int code     = cursor.getInt(cursor.getColumnIndex(_PROCODE));
            int pre      = cursor.getInt(cursor.getColumnIndex(_PREFIX));
            String pro      = cursor.getString(cursor.getColumnIndex(_PROVINCE));
            String pro_kh   = cursor.getString(cursor.getColumnIndex(_PROVINCE_KH));
            p = new Province(code,pre, pro,pro_kh);
        }
        cursor.close();
        db.close();
        return p;
    }

    public List<Province> getListProvinces(){
        List<Province> provinceList = new ArrayList<Province>();
        SQLiteDatabase db = new GazetteerDBHelper(context).openDbRead();
        String selectQuery =  "SELECT  * FROM " + GazetteerDBHelper.TABLE_Province + " ORDER BY " + _PROVINCE + " ASC;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        Province p = null;
        if (cursor.moveToFirst()) {
            do {
                int code     = cursor.getInt(cursor.getColumnIndex(_PROCODE));
                int pre      = cursor.getInt(cursor.getColumnIndex(_PREFIX));
                String pro      = cursor.getString(cursor.getColumnIndex(_PROVINCE));
                String pro_kh   = cursor.getString(cursor.getColumnIndex(_PROVINCE_KH));
                 p = new Province(code,pre,pro,pro_kh);
                // Adding contact to list
                provinceList.add(p);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return provinceList;
    }
    public List<Province> getListFiveProvinces(){
        List<Province> provinceList = new ArrayList<Province>();
        SQLiteDatabase db = new GazetteerDBHelper(context).openDbRead();
        String selectQuery =  "SELECT  * FROM " + GazetteerDBHelper.TABLE_Province
        + " WHERE " + _PROCODE + " = 1 OR " + _PROCODE + " = 2 OR "
        + _PROCODE + " = 3 OR " + _PROCODE + " = 14 OR "
        + _PROCODE + " = 17 OR " + _PROCODE + " = 15 "
        + " ORDER BY " + _PROVINCE + " ASC;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        Province p = null;
        if (cursor.moveToFirst()) {
            do {
                int code     = cursor.getInt(cursor.getColumnIndex(_PROCODE));
                int pre      = cursor.getInt(cursor.getColumnIndex(_PREFIX));
                String pro      = cursor.getString(cursor.getColumnIndex(_PROVINCE));
                String pro_kh   = cursor.getString(cursor.getColumnIndex(_PROVINCE_KH));
                p = new Province(code,pre,pro,pro_kh);
                // Adding contact to list
                provinceList.add(p);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return provinceList;
    }

}
