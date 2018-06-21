package com.tml.libs.cutils.db;

//import tml.libs.core.DClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tml.libs.cutils.LoggableClass;
import com.tml.libs.cutils.StaticLogger;

/**
 * Created by TML.ITWORK on 12/19/2014.
 */
public abstract class LocalDB implements ILocalDB {
    protected boolean loaded = false;
    protected LocalDBHelper dbh;

    protected String[] createQueryList;
    protected String[] dropQueryList;

    @Override
    public int getDBVersion() {
        return 1;
    }

    public LocalDB ()
    {
    	
    }

	public int b2i(boolean value) {
		if (value) return 1;
		return  0;
	}

	public boolean i2b(int value) {
		if (value <= 0) return false;
		return  true;
	}

    protected Context mContext;
    protected boolean mIsDBVersionUpgraded = false;
	protected boolean mIsUpdated = false;
	boolean mHasUpdateRequest = false;
	
	public boolean isUpdated() {
		return mIsUpdated;
	}
	
	public void requestUpdate() {
		mHasUpdateRequest = true;
	}
		
	public boolean hasUpdateRequest() {
		return mHasUpdateRequest;
	}

	public void onUpdateContentFinished() {
		StaticLogger.I(this,"onUpdateContentFinished() isUpdated = true");
		mIsUpdated = true;
		mIsDBVersionUpgraded = false;
		mHasUpdateRequest = false;
	}
	
	@Override
	public void onUpgradeVersionFinished() {
		StaticLogger.I(this,"onUpgradeVersionFinished()");
		mIsDBVersionUpgraded = true;
		mIsUpdated = false;
		requestUpdate();
	}
	
    public boolean isLoaded() {
        return loaded;
    }

    public void open(Context c) {
        StaticLogger.I(this,"open()");
        try
        {
        	if (getLocalDBName() == null)
        		throw new Exception("DBName is NULL");
        	mContext = c;
            dbh = new LocalDBHelper(c, true, this);
            //dbh.L = L;
            onOpenSuccess();
            loaded = true;
        }
        catch (Exception ex)
        {
            StaticLogger.E(this,"open(): Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void close() {
        StaticLogger.I(this,"close");
        try {
            if (dbh != null)
                dbh.close();
        }catch (Exception ex) {

        }
    }

    public boolean isReadyToUse() {
    	if (isUpdated())
    		return true;
    	
    	StaticLogger.I(this,"DB not ready: not updated");
		return false;
	}
    
    public void deleteTable(String tableName) {
    	StaticLogger.I(this,"deleteTable: " + tableName);
    	try {
    		dbh.deleteTable(tableName);
    	}
    	catch(Exception ex) {
    		
    	}
	}
    
    public void Exec(String sql) {
    	StaticLogger.I(this,"Exec: " + sql);
    	if (dbh == null)
    	{
    		if (loaded)
    		{
    			StaticLogger.E(this,"DB has loaded but dbh == null!");
    			return;
    		}
    		else
    		{
    			StaticLogger.E(this,"DB must be loaded before call Exec(...)");
    		}
    	}
        dbh.exec(sql);
    }
    
    public int execGetInt32(int columnIndex, String selectSql, int defaultValue) {
    	Cursor c = dbh.rawQuery(selectSql);
    	if (c == null)
    		return defaultValue;
    	
    	if (c.getCount() == 0)
    		return defaultValue;
    	
    	if (c.getCount() > 1)
    		StaticLogger.W(this,"execGetInt32() expected return only 1 row, but actual return " + c.getCount() + " rows");
    	
    	if (c.getColumnCount() <= columnIndex)
    	{
    		StaticLogger.E(this,"execGetInt32(col " + columnIndex + ") but select only " + c.getColumnCount() + " columns");
    		return defaultValue;
    	}
    	
    	try
    	{
    		c.moveToFirst();
    		int value = c.getInt(columnIndex);
    		c.close();
    		return value;
    	}
    	catch(Exception ex) {
    		StaticLogger.E(this,"execGetInt32(col " + columnIndex + " as Int) but EXCEPTION Occur! SQL:" + selectSql, ex);
    	}
    	
		return defaultValue;
    }
    
    public String execGetString(int columnIndex, String selectSql) {
    	Cursor c = dbh.rawQuery(selectSql);
    	if (c == null)
    		return null;
    	
    	if (c.getCount() == 0)
    		return null;
    	
    	if (c.getCount() > 1)
    		StaticLogger.W(this,"execGetString() expected return only 1 row, but actual return " + c.getCount() + " rows");
    	
    	if (c.getColumnCount() <= columnIndex)
    	{
    		StaticLogger.E(this,"execGetString(col " + columnIndex + ") but select only " + c.getColumnCount() + " columns");
    		return null;
    	}
    	
    	try
    	{
    		c.moveToFirst();
    		String s = c.getString(columnIndex);
    		c.close();
    		return s;
    	}
    	catch(Exception ex) {
    		StaticLogger.E(this,"execGetString(col " + columnIndex + " as String) but EXCEPTION Occur! SQL:" + selectSql, ex);
    	}
    	
		return null;
	}

	/**
     * db is opened, you can access it now ( cache something here,... )
     */
    public void onOpenSuccess() {
		StaticLogger.I(this,getLocalDBName() + "::onOpenSuccess");
    }

    public boolean isEmpty() {
        return true;
    }

    @Override
    public String[] getCreateQueryList() {
        return createQueryList;
    }

    @Override
    public String[] getDropQueryList() {
        return dropQueryList;
    }

    protected String mDBLocalFilePath;
//    protected String mDBName;
    @Override
    public String getDBLocalFilePath() {
        return mDBLocalFilePath;
    }

//    @Override
//    public String getLocalDBName() {
//        return mDBName;
//    }

    public boolean isTableExist(String tableName) {
        return dbh.isTableExist(tableName);
    }

    public int getTableRowCount(String tableName) {

    	try {
    		return dbh.getTableRowCount(tableName);
    	}
    	catch(Exception ex) {
    		
    	}
    	return 0;
    }

	

	@Override
	public void onUpgrade_beforeDropOldTables(SQLiteDatabase db) {
		
	}
	
	public int GetInt(int columnIndex, String sql, int defaultValue) {
		Cursor c = dbh.rawQuery(sql);
		if (c == null)
			return defaultValue;
		
		if (c.moveToFirst() == false)
			return defaultValue;
		
		int val = c.getInt(columnIndex);
		c.close();
		
		return val;
	}
	
	
	
	public String[] ExecGet(int ncols, String sql)
	{
		Cursor c = dbh.rawQuery(sql);
		if (c == null)
			return null;
		
		if (c.moveToFirst() == false)
			return null;
		
		if (c.getCount() == 0)
			return null;
		
		String[] fields = new String[ncols];
		for(int i = 0; i < ncols; i++)
			fields[i] = c.getString(i);
		
		c.close();
		
		return fields;
	}
	
	public JSONArray selectArray(String sql) {
		Cursor c = dbh.rawQuery(sql);
		if (c == null)
			return null;
		
		if (c.moveToFirst() == false)
			return null;
		
		JSONArray ar = new JSONArray();
				
		c.moveToFirst();
		for(int i = 0; i < c.getCount(); i++)
		{
			JSONObject row = new JSONObject(); 
			for(int col = 0; col < c.getColumnCount(); col++)
			{
				String colName = c.getColumnName(col);
				int colIdx = c.getColumnIndex(colName);
				try {
					row.put(colName, c.getString(colIdx));
				} catch (JSONException e) {					
					e.printStackTrace();
					return null;
				}
			}
			ar.put(row);
			c.moveToNext();
		}
		return ar;
	}
}
