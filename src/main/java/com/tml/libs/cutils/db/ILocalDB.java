package com.tml.libs.cutils.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by TML.ITWORK on 12/19/2014.
 */
public interface ILocalDB {
    int getDBVersion();

    String[] getCreateQueryList();
    String[] getDropQueryList();

    String getDBLocalFilePath();
    String getLocalDBName();

	void onUpgradeVersionFinished();

	void onUpgrade_beforeDropOldTables(SQLiteDatabase db);
	
}
