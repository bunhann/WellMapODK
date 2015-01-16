package org.odk.collect.android.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.odk.collect.android.database.GazetteerDBHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bunhann on 12/5/14.
 */
public class Village {

    public static final String _VCODE = "VCode";
    public static final String _PREFIX = "prefix";
    public static final String _VNAME_EN = "VName_en";
    public static final String _VNAME_KH = "VName_kh";
    public static final String _CCODE = "CCode";
    public static final String _MODIFIED_DATE = "modified_date";
    public static final String _MODIFIED_BY = "modified_by";
    public static final String _STATUS = "VStatus";

    private int vCode;
    private int prefix;
    private String vNameEn;
    private String vNameKh;
    private int cCode;
    private Date modDate;
    private int modBy;
    private int status;

    private Context context;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public Village(Context context) {
        this.context = context;
    }

    public Village(int vCode, int prefix, String vNameEn, String vNameKh, int cCode, Date modDate, int modBy, int status) {
        this.vCode = vCode;
        this.prefix = prefix;
        this.vNameEn = vNameEn;
        this.vNameKh = vNameKh;
        this.cCode = cCode;
        this.modDate = modDate;
        this.modBy = modBy;
        this.status = status;
    }

    public int getvCode() {
        return vCode;
    }

    public void setvCode(int vCode) {
        this.vCode = vCode;
    }

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public String getvNameEn() {
        return vNameEn;
    }

    public void setvNameEn(String vNameEn) {
        this.vNameEn = vNameEn;
    }

    public String getvNameKh() {
        return vNameKh;
    }

    public void setvNameKh(String vNameKh) {
        this.vNameKh = vNameKh;
    }

    public int getcCode() {
        return cCode;
    }

    public void setcCode(int cCode) {
        this.cCode = cCode;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public int getModBy() {
        return modBy;
    }

    public void setModBy(int modBy) {
        this.modBy = modBy;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Village get(int communeId) throws ParseException {
        SQLiteDatabase db = new GazetteerDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(GazetteerDBHelper.TABLE_VILLAGE, null, _VCODE + " = " + communeId, null, null, null, null, null);
        cursor.moveToFirst();
        Village village = null;
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            int vcode        = cursor.getInt(cursor.getColumnIndex(_VCODE));
            int prefix          = cursor.getInt(cursor.getColumnIndex(_PREFIX));
            String vnameen      = cursor.getString(cursor.getColumnIndex(_VNAME_EN));
            String vnamekh      = cursor.getString(cursor.getColumnIndex(_VNAME_KH));
            int ccode        = cursor.getInt(cursor.getColumnIndex(_CCODE));
            String modifieddateStr = cursor.getString(cursor.getColumnIndex(_MODIFIED_DATE));
            Date modifieddate = df.parse(modifieddateStr);
            int modifiedby   = cursor.getInt(cursor.getColumnIndex(_MODIFIED_BY));
            int status       = cursor.getInt(cursor.getColumnIndex(_STATUS));
            village = new Village(vcode,prefix,vnameen,vnamekh,ccode,modifieddate, modifiedby, status);
        }
        db.close();
        cursor.close();
        return village;
    }

    public Village getByNameKh(String vNameKh) throws ParseException {
        SQLiteDatabase db = new GazetteerDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(GazetteerDBHelper.TABLE_VILLAGE, null, _VNAME_KH + " = ?", new String[]{vNameKh}, null, null, null, null);
        cursor.moveToFirst();
        Village village = null;
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            int vcode        = cursor.getInt(cursor.getColumnIndex(_VCODE));
            int prefix          = cursor.getInt(cursor.getColumnIndex(_PREFIX));
            String vnameen      = cursor.getString(cursor.getColumnIndex(_VNAME_EN));
            String vnamekh      = cursor.getString(cursor.getColumnIndex(_VNAME_KH));
            int ccode        = cursor.getInt(cursor.getColumnIndex(_CCODE));
            String modifieddateStr = cursor.getString(cursor.getColumnIndex(_MODIFIED_DATE));
            Date modifieddate = df.parse(modifieddateStr);
            int modifiedby   = cursor.getInt(cursor.getColumnIndex(_MODIFIED_BY));
            int status       = cursor.getInt(cursor.getColumnIndex(_STATUS));
            village = new Village(vcode,prefix,vnameen,vnamekh,ccode,modifieddate, modifiedby, status);
        }
        db.close();
        cursor.close();
        return village;
    }

    public List<Village> getByCommuneId(int communeId) throws ParseException {
        List<Village> villageList = new ArrayList<Village>();
        SQLiteDatabase db = new GazetteerDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(GazetteerDBHelper.TABLE_VILLAGE, null, _CCODE + " = " + communeId, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int vcode        = cursor.getInt(cursor.getColumnIndex(_VCODE));
                int prefix          = cursor.getInt(cursor.getColumnIndex(_PREFIX));
                String vnameen      = cursor.getString(cursor.getColumnIndex(_VNAME_EN));
                String vnamekh      = cursor.getString(cursor.getColumnIndex(_VNAME_KH));
                int ccode        = cursor.getInt(cursor.getColumnIndex(_CCODE));
                String modifieddateStr = cursor.getString(cursor.getColumnIndex(_MODIFIED_DATE));
                Date modifieddate = df.parse(modifieddateStr);
                int modifiedby   = cursor.getInt(cursor.getColumnIndex(_MODIFIED_BY));
                int status       = cursor.getInt(cursor.getColumnIndex(_STATUS));

                Village village = new Village(vcode,prefix,vnameen,vnamekh,ccode,modifieddate, modifiedby, status);
                // Adding contact to list
                villageList.add(village);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return villageList;

    }
}
