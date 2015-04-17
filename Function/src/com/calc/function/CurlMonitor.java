package com.calc.function;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import log.Log;
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
	private Context context;
	
	//是否原点被初始化
	private boolean bOriginInit = false;
	
	private Log log;
	/**
	 * 保存所有成功编译并绘制的函数
	 */
	private HashSet<String> history;
	private LayoutInflater inflater;
	private HistoryListAdapter adapter;
	
	static public enum Switch { ON, OFF };
	
	private CurlMonitor() {
		setMoveable(Switch.ON);
		
		history = new HashSet<String>();
		log = Log.instance();
	}
	
	static public CurlMonitor instance() {
		if ( monitor == null ) monitor = new CurlMonitor();
		return monitor;
	}
	
	public void initialize( Context context ) {
		this.context = context;
		
		if ( inflater == null ) {
			inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		}
	}
	
	public boolean isOriginInit() {
		return bOriginInit;
	}
	
	public void initOrigin() {
		bOriginInit = true;
	}

	public void appendHistory( String expr ) {
		history.add( expr );
		log.debug( "CurlMonitor add history ", expr );
	}
	
	public HistoryListAdapter getHistoryAdapter() {
		if ( adapter == null ) {
			adapter = new HistoryListAdapter();
		}
		return adapter;
	}
	
	/**
	 * 返回第几个历史记录
	 * @param pos
	 * @return 第pos个历史记录
	 */
	public String getHistory( int pos ) {
		String rs = null;
		for ( String hist : history ) {
			rs = hist;
			if ( pos-- <= 0 ) break;
		}
		log.debug( "CurlMonitor: position ", Integer.toString( pos ), "expr ", rs );
		return rs;
	}
	
	class HistoryListAdapter extends BaseAdapter {

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
			historyItem.setText( getHistory( position ) );
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
