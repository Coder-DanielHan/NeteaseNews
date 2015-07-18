package com.danielhan.neteasenews.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.danielhan.neteasenews.bean.ColumnBean;

/**
 * 桌面
 * @author hankun
 */
public class ColumnDao implements Database {
	private SQLiteDatabase mDB;
	private Context context;
	public static List<ColumnBean> defaultUserChannels;
	public static List<ColumnBean> defaultOtherChannels;
	static {
		defaultUserChannels = new ArrayList<ColumnBean>();
		defaultOtherChannels = new ArrayList<ColumnBean>();
		defaultUserChannels.add(new ColumnBean(1, "头条", 1, 1));
		defaultUserChannels.add(new ColumnBean(2, "娱乐", 2, 1));
		defaultUserChannels.add(new ColumnBean(3, "热点", 3, 1));
		defaultUserChannels.add(new ColumnBean(4, "体育", 4, 1));
		defaultUserChannels.add(new ColumnBean(5, "北京", 5, 1));
		defaultUserChannels.add(new ColumnBean(6, "财经", 6, 1));
		defaultUserChannels.add(new ColumnBean(7, "科技", 7, 1));
		defaultUserChannels.add(new ColumnBean(8, "段子", 8, 1));
		defaultUserChannels.add(new ColumnBean(9, "图片", 9, 1));
		defaultUserChannels.add(new ColumnBean(10, "汽车", 10, 1));
		defaultUserChannels.add(new ColumnBean(11, "时尚", 11, 1));
		defaultUserChannels.add(new ColumnBean(12, "轻松一刻", 12, 1));
		defaultUserChannels.add(new ColumnBean(13, "军事", 13, 1));
		defaultUserChannels.add(new ColumnBean(14, "历史", 14, 1));
		defaultUserChannels.add(new ColumnBean(15, "房产", 15, 1));
		defaultUserChannels.add(new ColumnBean(16, "游戏", 16, 1));
		defaultUserChannels.add(new ColumnBean(17, "彩票", 17, 1));
		defaultUserChannels.add(new ColumnBean(18, "原创", 18, 1));
		defaultUserChannels.add(new ColumnBean(19, "政务", 19, 1));
		defaultUserChannels.add(new ColumnBean(20, "云课堂", 20, 1));
		defaultUserChannels.add(new ColumnBean(21, "NBA", 21, 1));
		defaultUserChannels.add(new ColumnBean(22, "画报", 22, 1));
		
		defaultOtherChannels.add(new ColumnBean(23, "社会", 1, 0));
		defaultOtherChannels.add(new ColumnBean(24, "影视", 2, 0));
		defaultOtherChannels.add(new ColumnBean(25, "中国足球", 3, 0));
		defaultOtherChannels.add(new ColumnBean(26, "国际足球", 4, 0));
		defaultOtherChannels.add(new ColumnBean(27, "CBA", 5, 0));
		defaultOtherChannels.add(new ColumnBean(28, "跑步", 6, 0));
		defaultOtherChannels.add(new ColumnBean(29, "手机", 7, 0));
		defaultOtherChannels.add(new ColumnBean(30, "数码", 8, 0));
		defaultOtherChannels.add(new ColumnBean(31, "移动互联", 9, 0));
		defaultOtherChannels.add(new ColumnBean(32, "家居", 10, 0));
		defaultOtherChannels.add(new ColumnBean(33, "旅游", 11, 0));
		defaultOtherChannels.add(new ColumnBean(34, "健康", 12, 0));
		defaultOtherChannels.add(new ColumnBean(35, "读书", 13, 0));
		defaultOtherChannels.add(new ColumnBean(36, "酒香", 14, 0));
		defaultOtherChannels.add(new ColumnBean(37, "教育", 15, 0));
		defaultOtherChannels.add(new ColumnBean(38, "亲子", 16, 0));
		defaultOtherChannels.add(new ColumnBean(39, "葡萄酒", 17, 0));
		defaultOtherChannels.add(new ColumnBean(40, "你照吗", 18, 0));
		defaultOtherChannels.add(new ColumnBean(41, "暴雪游戏", 19, 0));
		defaultOtherChannels.add(new ColumnBean(42, "情感", 20, 0));
		defaultOtherChannels.add(new ColumnBean(43, "值得买", 21, 0));
		defaultOtherChannels.add(new ColumnBean(44, "跟帖", 22, 0));
		defaultOtherChannels.add(new ColumnBean(45, "博客", 23, 0));
		defaultOtherChannels.add(new ColumnBean(46, "论坛", 24, 0));
	}

	public ColumnDao(Context context) {
		this.mDB = SQLHelper.getHelper(context).getWritableDatabase();
		this.context=context;
	}
	
	

	/**
	 * 添加
	 */
	public synchronized void insert(List<ColumnBean> columnList) {
		try {
			for(ColumnBean bean:columnList){
				ContentValues values = new ContentValues();
				values.put(ID, bean.getId());
				values.put(NAME, bean.getName());
				values.put(ORDERID, bean.getOrderId());
				values.put(SELECTED, bean.getSelected());
				mDB.insert(TABLE_COLUMN, null, values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mDB.close(); 
		}
	}
	
	/**
	 * 获取
	 * @return
	 */
	public synchronized List<ColumnBean> getColumn(String selected) {
		Cursor cursor= null;
		List<ColumnBean> columnList = new ArrayList<ColumnBean>();
		try {
			cursor = mDB.query(TABLE_COLUMN, new String[] { ID, NAME, ORDERID,
					SELECTED},
					SELECTED + "=?", new String[] { selected}, null, null, null);
			while (cursor.moveToNext()) {
				ColumnBean bean = new ColumnBean();
				bean.setId(cursor.getInt(cursor.getColumnIndex(ID)));
				bean.setName(cursor.getString(cursor.getColumnIndex(NAME)));
				bean.setOrderId(cursor.getInt(cursor.getColumnIndex(ORDERID)));
				bean.setSelected(cursor.getInt(cursor.getColumnIndex(SELECTED)));
				columnList.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			cursor.close();
		}
		return columnList;
	}
	
	
}
