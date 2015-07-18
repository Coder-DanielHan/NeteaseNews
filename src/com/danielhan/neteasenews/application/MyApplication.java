package com.danielhan.neteasenews.application;

import java.util.List;

import android.app.Application;

import com.danielhan.neteasenews.bean.ColumnBean;
import com.danielhan.neteasenews.db.ColumnDao;

public class MyApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		insertColunmCache();
	}
	
	/**
	 * Ìí¼ÓÀ¸Ä¿»º´æ
	 */
	public void insertColunmCache(){
		List<ColumnBean> defaultUserChannels = new ColumnDao(getApplicationContext()).getColumn("1");
		if(defaultUserChannels.size()==0){
			new ColumnDao(getApplicationContext()).insert(ColumnDao.defaultOtherChannels);
			new ColumnDao(getApplicationContext()).insert(ColumnDao.defaultUserChannels);
		}
	}
}
