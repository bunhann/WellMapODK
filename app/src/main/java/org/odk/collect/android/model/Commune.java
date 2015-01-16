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
public class Commune {

    public static final String _CCODE = "CCode";
    public static final String _PREFIX = "prefix";
    public static final String _CNAME_EN = "CName_en";
    public static final String _CNAME_KH = "CName_kh";
    public static final String _DCODE = "DCode";
    public static final String _MODIFIED_DATE = "modified_date";
    public static final String _MODIFIED_BY = "modified_by";
    public static final String _STATUS = "status";
    
    private int cCode;
    private int prefix;
    private String cNameEn;
    private String cNameKh;
    private int dCode;
    private Date modDate;
    private int modBy;
    private int status;
    
    private Context context;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public Commune(Context context) {
        this.context = context;
    }

    public Commune(int cCode, int prefix, String cNameEn, String cNameKh, int dCode, Date modDate, int modBy, int status) {
        this.cCode = cCode;
        this.prefix = prefix;
        this.cNameEn = cNameEn;
        this.cNameKh = cNameKh;
        this.dCode = dCode;
        this.modDate = modDate;
        this.modBy = modBy;
        this.status = status;
    }

    public int getcCode() {
        return cCode;
    }

    public void setcCode(int cCode) {
        this.cCode = cCode;
    }

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public String getcNameEn() {
        return cNameEn;
    }

    public void setcNameEn(String cNameEn) {
        this.cNameEn = cNameEn;
    }

    public String getcNameKh() {
        return cNameKh;
    }

    public void setcNameKh(String cNameKh) {
        this.cNameKh = cNameKh;
    }

    public int getdCode() {
        return dCode;
    }

    public void setdCode(int dCode) {
        this.dCode = dCode;
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

    public Commune get(int communeId) throws ParseException {
        SQLiteDatabase db = new GazetteerDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(GazetteerDBHelper.TABLE_Commune, null, _CCODE + "=" + communeId,null, null, null, null, null);
        cursor.moveToFirst();
        Commune commune = null;
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            int ccode        = cursor.getInt(cursor.getColumnIndex(_CCODE));
            int prerfix       = cursor.getInt(cursor.getColumnIndex(_PREFIX));
            String cname        = cursor.getString(cursor.getColumnIndex(_CNAME_EN));
            String cnamekh      = cursor.getString(cursor.getColumnIndex(_CNAME_KH));
            int dcode        = cursor.getInt(cursor.getColumnIndex(_DCODE));
            String modifieddateStr = cursor.getString(cursor.getColumnIndex(_MODIFIED_DATE));
            Date modifieddate = df.parse(modifieddateStr);
            int modifiedby   = cursor.getInt(cursor.getColumnIndex(_MODIFIED_BY));
            int status       = cursor.getInt(cursor.getColumnIndex(_STATUS));

            commune = new Commune(ccode,prerfix,cname,cnamekh,dcode,modifieddate,modifiedby,status);
        }
        db.close();
        cursor.close();
        return commune;
    }

    public Commune getByNameKh(String comNameKh) throws ParseException {
        SQLiteDatabase db = new GazetteerDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(GazetteerDBHelper.TABLE_Commune, null, _CNAME_KH + "=?",new String[]{comNameKh}, null, null, null, null);
        cursor.moveToFirst();
        Commune commune = null;
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            int ccode        = cursor.getInt(cursor.getColumnIndex(_CCODE));
            int prerfix       = cursor.getInt(cursor.getColumnIndex(_PREFIX));
            String cname        = cursor.getString(cursor.getColumnIndex(_CNAME_EN));
            String cnamekh      = cursor.getString(cursor.getColumnIndex(_CNAME_KH));
            int dcode        = cursor.getInt(cursor.getColumnIndex(_DCODE));
            String modifieddateStr = cursor.getString(cursor.getColumnIndex(_MODIFIED_DATE));
            Date modifieddate = df.parse(modifieddateStr);
            int modifiedby   = cursor.getInt(cursor.getColumnIndex(_MODIFIED_BY));
            int status       = cursor.getInt(cursor.getColumnIndex(_STATUS));

            commune = new Commune(ccode,prerfix,cname,cnamekh,dcode,modifieddate,modifiedby,status);
        }
        db.close();
        cursor.close();
        return commune;
    }

    public List<Commune> getByDistrictId(int districtId) throws ParseException {
        List<Commune> communeList = new ArrayList<Commune>();
        SQLiteDatabase db = new GazetteerDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(GazetteerDBHelper.TABLE_Commune, null, _DCODE + "=" + districtId,null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int ccode        = cursor.getInt(cursor.getColumnIndex(_CCODE));
                int prerfix       = cursor.getInt(cursor.getColumnIndex(_PREFIX));
                String cname        = cursor.getString(cursor.getColumnIndex(_CNAME_EN));
                String cnamekh      = cursor.getString(cursor.getColumnIndex(_CNAME_KH));
                int dcode        = cursor.getInt(cursor.getColumnIndex(_DCODE));
                String modifieddateStr = cursor.getString(cursor.getColumnIndex(_MODIFIED_DATE));
                Date modifieddate = df.parse(modifieddateStr);
                int modifiedby   = cursor.getInt(cursor.getColumnIndex(_MODIFIED_BY));
                int status       = cursor.getInt(cursor.getColumnIndex(_STATUS));

                Commune commune = new Commune(ccode,prerfix,cname,cnamekh,dcode,modifieddate,modifiedby,status);
                // Adding contact to list
                communeList.add(commune);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return communeList;

    }
}
