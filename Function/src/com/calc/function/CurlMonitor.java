package com.calc.function;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CurlMonitor {
	
	static private CurlMonitor monitor = null;

	private Switch moveable = Switch.ON;
	
	/**
	 * 保存所有成功编译并绘制的函数
	 */
	private ArrayList<String> history;
	private LayoutInflater inflater;
	private HistoryListAdapter adapter;
	
	static public enum Switch { ON, OFF };
	
	private CurlMonitor() {
		setMoveable(Switch.ON);
		
		history = new ArrayList<String>();
		adapter = new HistoryListAdapter();
	}
	
	static public CurlMonitor instance() {
		if ( monitor == null ) monitor = new CurlMonitor();
		return monitor;
	}

	public void appendHistory( String expr ) {
		history.add( expr );
	}
	
	public void showHistoryInList( Context context, ListView view ) {
		if ( null == inflater ) {
			inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		}
		
		if ( null == view.getAdapter() )
			view.setAdapter( adapter );
		
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * 返回第几个历史记录
	 * @param pos
	 * @return 第pos个历史记录
	 */
	public String getHistory( int pos ) {
		return history.get( pos );
	}
	
	private class HistoryListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return history.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if ( convertView == null ) {
				convertView = inflater.inflate( R.layout.history_list_item, parent, false);
			}
			
			TextView historyItem = (TextView) convertView.findViewById( R.id.history_item_content );
			historyItem.setText( history.get(position) );
			return convertView;
		}

	}
	
	/**
	 * 是否已经被锁定
	 * @return true 锁定 flase 未锁定
	 */
	public Switch getMoveable() {
		return moveable;
	}

	/**
	 * 设置图像锁定
	 * @param moveable 
	 * true 锁定图像
	 * false 释放锁定
	 */
	public void setMoveable(Switch moveable) {
		this.moveable = moveable;
	}
	
	/**
	 * 转换锁定状态
	 */
	public void toggleMoveable() {
		if ( getMoveable() == Switch.ON )
			setMoveable( Switch.OFF );
		else
			setMoveable( Switch.ON );
	}

}
