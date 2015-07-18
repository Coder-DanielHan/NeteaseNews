package com.danielhan.neteasenews.bean;

import java.io.Serializable;

/** 
 * ITEM�Ķ�Ӧ���򻯶�������
 *  */
public class ColumnBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6465237897027410019L;
	/** 
	 * ��Ŀ��ӦID
	 *  */
	public int id;
	/** 
	 * ��Ŀ��ӦNAME
	 *  */
	public String name;
	/** 
	 * ��Ŀ�������е�����˳��  rank
	 *  */
	public int orderId;
	/** 
	 * ��Ŀ�Ƿ�ѡ��
	 *  */
	public int selected;

	public ColumnBean() {
	}

	public ColumnBean(int id, String name, int orderId,int selected) {
		this.id = Integer.valueOf(id);
		this.name = name;
		this.orderId = Integer.valueOf(orderId);
		this.selected = Integer.valueOf(selected);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public String toString() {
		return "ChannelItem [id=" + this.id + ", name=" + this.name
				+ ", selected=" + this.selected + "]";
	}
}