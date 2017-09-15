package com.tml.libs.cutils.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

public class DatabaseContext extends ContextWrapper {
    boolean mIsLoadAsNewDB = false;

    static String mDBLocalPath;
    public DatabaseContext(Context base, boolean loadAsNew, String dbLocalFilePath) {
        super(base);
        mIsLoadAsNewDB = loadAsNew;
        mDBLocalPath = dbLocalFilePath;
    }

    public static File util_getDatabasePath() {
        File sdcard = Environment.getExternalStorageDirectory();
        File dbFile = new File(sdcard.getAbsolutePath(), mDBLocalPath);
        String dbFilePath = dbFile.getAbsolutePath();
        		
        if (!dbFilePath.endsWith(".db")) {
        	dbFilePath += ".db";
        }

        dbFile = new File(dbFilePath);

        if (!dbFile.getParentFile().exists()) {
        	dbFile.getParentFile().mkdirs();
        }
//        DBG.LD(TAG,
//                "getDatabasePath() = "
//                        + dbFile.getAbsolutePath());

        return dbFile;
    }

    @Override
    public File getDatabasePath(String name) {
        return util_getDatabasePath();
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory) {

        //SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(
        //		getDatabasePath(name), null);
        SQLiteDatabase result = super.openOrCreateDatabase(name, mode,
                factory);
//        DBG.LD(TAG, "openOrCreateDatabase(" + name + ",,) = "
//                + result.getPath());

        return result;
    }
}