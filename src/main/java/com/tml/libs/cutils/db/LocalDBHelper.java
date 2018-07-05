package com.tml.libs.cutils.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tml.libs.cutils.StaticLogger;

/**
 * Created by TML.ITWORK on 12/19/2014.
 */
public class LocalDBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqldb;

    ILocalDB idb;
    Context c;
    public LocalDBHelper	(Context context, boolean loadAsNew, ILocalDB iLocalDB) {
        super(new DatabaseContext(context, loadAsNew, iLocalDB.getDBLocalFilePath()), iLocalDB.getLocalDBName(), null, iLocalDB.getDBVersion());
        StaticLogger.D(this, "getWritableDatabase() FILE PATH: " + iLocalDB.getDBLocalFilePath() + " ver " + iLocalDB.getDBVersion());
        
        c = context;
        idb = iLocalDB;
        

        sqldb = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StaticLogger.D(this, "onCreate() dbpath:" + db.getPath() + " dbver:" + db.getVersion());

        String[] list = idb.getCreateQueryList();
        for(int i = 0; i < list.length; i++) {
            StaticLogger.D(this, "    CREATE QUERY "+ i + ":\n" + list[i]);
            db.execSQL(list[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	StaticLogger.D(this, "LocalDBHelper::" + String.format(idb.getLocalDBName() + "::onUpgrade(oldVersion %d to newVersion %d)", oldVersion, newVersion));

        idb.onUpgrade_beforeDropOldTables(db);
        String[] list = idb.getDropQueryList();
        for(int i = 0; i < list.length; i++)
            db.execSQL(list[i]);

        onCreate(db);
        
        idb.onUpgradeVersionFinished();
    }

    public Cursor rawQuery(String sql) {
        return sqldb.rawQuery(sql, null);
    }

    public void deleteTable(String name) {
        sqldb.execSQL("DELETE FROM " + name);
        
        
    }


    /**
     * UPDATE TABLE SET COL = value (bit: 1 or 0)
     * @param table
     * @param column
     */
//    public void setColBool(String table, String column, boolean b) {
//        sqldb.execSQL(
//                String.format("UPDATE %s SET %s = %d", table, column, b ? 1 : 0));
//    }

    public void setColInt(String table, String column, int value) {
        sqldb.execSQL(
                String.format("UPDATE %s SET %s = %d", table, column, value));
    }


    /**
     * DELETE TABLE WHERE COL = value (bit: 1 or 0)
     * @param table
     * @param column
     * @param b
     */
    public void deleteByColBool(String table, String column, boolean b) {
        sqldb.execSQL(
                String.format("DELETE FROM %s WHERE %s=%d", table, column, b ? 1 : 0));
    }


    public boolean exec(String sql)
    {
        //DBG.LD(TAG, "exec: " + sql);
        try {
            sqldb.execSQL(sql);
            return true;
        }
        catch (Exception ex) {
            StaticLogger.E(this, "unexpected ex: ", ex);
        }
        return false;
    }
    
    public SQLiteDatabase getSQLLite()
    {
    	return sqldb;
    }


    public boolean isTableExist(String tableName) {
        String sql = String.format("SELECT name FROM sqlite_master WHERE type='table' AND name='%s'", tableName);
        Cursor c = rawQuery(sql);
        if (c == null)
            return false;

        if (!c.moveToFirst())
        {
            c.close();
            return  false;
        }

//        String name = c.getString(0);
        c.close();
//        return name.equals(tableName);
        return true;
    }

    public int getTableRowCount(String tableName) {
    	StaticLogger.D(this, idb.getLocalDBName() + "::getTableRowCount");
        String sql = String.format("SELECT COUNT(*) FROM [%s]", tableName);
        Cursor c = rawQuery(sql);
        if (c == null)
            return 0;
        if (!c.moveToFirst())
            return 0;
        int n = c.getInt(0);
        c.close();
        return n;
    }
}
