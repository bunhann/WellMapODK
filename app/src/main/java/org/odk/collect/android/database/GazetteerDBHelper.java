package org.odk.collect.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.odk.collect.android.application.Collect;
import org.odk.collect.android.model.Province;

/**
 * Created by bunhann on 12/4/14.
 */
public class GazetteerDBHelper extends ODKSQLiteOpenHelper {
    /**
     * Create a helper object to create, open, and/or manage a database. The database is not
     * actually created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param path    to the file
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database
     */

    private static final String DB_NAME = "gazetteer.db";
    private static final int DB_VERSION = 1;
    private static final String DB_PATH = Collect.GAZETTER_PATH;

    //TABLE NAME
    public static final String TABLE_Province       = "province";
    public static final String TABLE_District       = "district";
    public static final String TABLE_Commune        = "commune";
    public static final String TABLE_VILLAGE        = "village";

    //Model
    private Province province;

    public GazetteerDBHelper(Context context) {
        super(DB_PATH, DB_NAME, null, DB_VERSION);
        province = new Province(context);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized SQLiteDatabase openDbRead(){
        return this.getReadableDatabase();
    }

    public synchronized SQLiteDatabase writeDb(){
        return this.getWritableDatabase();
    }

}
