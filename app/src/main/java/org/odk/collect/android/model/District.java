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
 * Created by bunhann on 12/4/14.
 */
public class District {

    public static final String _DCODE = "DCode";
    public static final String _PREFIX = "prefix";
    public static final String _DNAME_EN = "DName_en";
    public static final String _DNAME_KH = "DName_kh";
    public static final String _PCODE = "PCode";
    public static final String _MODIFIED_DATE = "modified_date";
    public static final String _MODIFIED_BY = "modified_by";
    public static final String _STATUS = "status";

    private int dCode;
    private int prefix;
    private String dNameEn;
    private String dNameKh;
    private int pCode;
    private Date modDate;
    private int modBy;
    private int status;

    private Context context;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public District(Context context) {
        this.context = context;
    }

    public District(int dCode, int prefix, String dNameEn, String dNameKh, int pCode, Date modDate, int modBy, int status) {
        this.dCode = dCode;
        this.prefix = prefix;
        this.dNameEn = dNameEn;
        this.dNameKh = dNameKh;
        this.pCode = pCode;
        this.modDate = modDate;
        this.modBy = modBy;
        this.status = status;
    }

    public int getdCode() {
        return dCode;
    }

    public void setdCode(int dCode) {
        this.dCode = dCode;
    }

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public String getdNameEn() {
        return dNameEn;
    }

    public void setdNameEn(String dNameEn) {
        this.dNameEn = dNameEn;
    }

    public String getdNameKh() {
        return dNameKh;
    }

    public void setdNameKh(String dNameKh) {
        this.dNameKh = dNameKh;
    }

    public int getpCode() {
        return pCode;
    }

    public void setpCode(int pCode) {
        this.pCode = pCode;
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

    public District get(int districtId) throws ParseException {
        SQLiteDatabase db = new GazetteerDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(GazetteerDBHelper.TABLE_District, null, _DCODE + " = " + districtId,null, null, null, null, null);
        cursor.moveToFirst();
        District district = null;
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            int dcode        = cursor.getInt(cursor.getColumnIndex(_DCODE));
            int prerfix       = cursor.getInt(cursor.getColumnIndex(_PREFIX));
            String dname        = cursor.getString(cursor.getColumnIndex(_DNAME_EN));
            String dnamekh      = cursor.getString(cursor.getColumnIndex(_DNAME_KH));
            int pcode        = cursor.getInt(cursor.getColumnIndex(_PCODE));
            String modifieddateStr = cursor.getString(cursor.getColumnIndex(_MODIFIED_DATE));
            Date modifieddate = df.parse(modifieddateStr);
            int modifiedby   = cursor.getInt(cursor.getColumnIndex(_MODIFIED_BY));
            int status       = cursor.getInt(cursor.getColumnIndex(_STATUS));

            district = new District(dcode, prerfix ,dname,dnamekh,pcode,modifieddate,modifiedby,status);

        }
        db.close();
        cursor.close();
        return district;
    }

    public District getByNameKh(String distNameKh) throws ParseException {
        SQLiteDatabase db = new GazetteerDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(GazetteerDBHelper.TABLE_District, null, _DNAME_KH + " = ?",new String[]{distNameKh}, null, null, null, null);
        cursor.moveToFirst();
        District district = null;
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            int dcode        = cursor.getInt(cursor.getColumnIndex(_DCODE));
            int prerfix       = cursor.getInt(cursor.getColumnIndex(_PREFIX));
            String dname        = cursor.getString(cursor.getColumnIndex(_DNAME_EN));
            String dnamekh      = cursor.getString(cursor.getColumnIndex(_DNAME_KH));
            int pcode        = cursor.getInt(cursor.getColumnIndex(_PCODE));
            String modifieddateStr = cursor.getString(cursor.getColumnIndex(_MODIFIED_DATE));
            Date modifieddate = df.parse(modifieddateStr);
            int modifiedby   = cursor.getInt(cursor.getColumnIndex(_MODIFIED_BY));
            int status       = cursor.getInt(cursor.getColumnIndex(_STATUS));

            district = new District(dcode, prerfix ,dname,dnamekh,pcode,modifieddate,modifiedby,status);

        }
        db.close();
        cursor.close();
        return district;
    }

    public List<District> getByProvinceId(int provinceId) throws ParseException {
        List<District> districtList = new ArrayList<District>();
        SQLiteDatabase db = new GazetteerDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(GazetteerDBHelper.TABLE_District, null, _PCODE + " = " + provinceId,null, null, null, null, null);
        cursor.moveToFirst();
        District district = null;
        if (cursor.getCount()>0) {
            do {
                int dcode        = cursor.getInt(cursor.getColumnIndex(_DCODE));
                int prerfix       = cursor.getInt(cursor.getColumnIndex(_PREFIX));
                String dname        = cursor.getString(cursor.getColumnIndex(_DNAME_EN));
                String dnamekh      = cursor.getString(cursor.getColumnIndex(_DNAME_KH));
                int pcode        = cursor.getInt(cursor.getColumnIndex(_PCODE));
                String modifieddateStr = cursor.getString(cursor.getColumnIndex(_MODIFIED_DATE));
                Date modifieddate = df.parse(modifieddateStr);
                int modifiedby   = cursor.getInt(cursor.getColumnIndex(_MODIFIED_BY));
                int status       = cursor.getInt(cursor.getColumnIndex(_STATUS));

                district = new District(dcode, prerfix ,dname,dnamekh,pcode,modifieddate,modifiedby,status);
                // Adding contact to list
                districtList.add(district);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return districtList;

    }

}
