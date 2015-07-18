package com.danielhan.neteasenews.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.danielhan.neteasenews.R;
import com.danielhan.neteasenews.bean.ColumnBean;

public class OtherAdapter extends BaseAdapter {
	private Context context;
	public List<ColumnBean> channelList;
	private TextView item_text;
	/** �Ƿ�ɼ� */
	boolean isVisible = true;
	/** Ҫɾ����position */
	public int remove_position = -1;

	public OtherAdapter(Context context, List<ColumnBean> channelList) {
		this.context = context;
		this.channelList = channelList;
	}

	@Override
	public int getCount() {
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public ColumnBean getItem(int position) {
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_column, null);
		item_text = (TextView) view.findViewById(R.id.text_item);
		ColumnBean channel = getItem(position);
		item_text.setText(channel.getName());
		if (!isVisible && (position == -1 + channelList.size())){
			item_text.setText("");
		}
		if(remove_position == position){
			item_text.setText("");
		}
		return view;
	}
	
	/** ��ȡƵ���б� */
	public List<ColumnBean> getChannnelLst() {
		return channelList;
	}
	
	/** ���Ƶ���б� */
	public void addItem(ColumnBean channel) {
		channelList.add(channel);
		notifyDataSetChanged();
	}

	/** ����ɾ����position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
		// notifyDataSetChanged();
	}

	/** ɾ��Ƶ���б� */
	public void remove() {
		channelList.remove(remove_position);
		remove_position = -1;
		notifyDataSetChanged();
	}
	/** ����Ƶ���б� */
	public void setListDate(List<ColumnBean> list) {
		channelList = list;
	}

	/** ��ȡ�Ƿ�ɼ� */
	public boolean isVisible() {
		return isVisible;
	}
	
	/** �����Ƿ�ɼ� */
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
}