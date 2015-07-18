package com.danielhan.neteasenews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper implements Database{
	public static final String DB_NAME = "database.db";
	public static final int VERSION = 1;
	private static SQLHelper instance;
	
	private Context context;
	public SQLHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		this.context = context;
	}
	
	public static SQLHelper getHelper(Context context) {
		if (instance == null)
			instance = new SQLHelper(context);
		return instance;
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table if not exists "+TABLE_COLUMN +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				ID + " INTEGER , " +
				NAME + " TEXT , " +
				ORDERID + " INTEGER , " +
				SELECTED + " SELECTED)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}
