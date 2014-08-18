package com.calc.function;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import expression.item.Operator;
import expression.item.OperatorFactory;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class HelpActivity extends Activity {
	
	Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		intent = getIntent();
		
		findViewById(R.id.help_close).setOnClickListener(new OnHelpCloseClick());
		((ListView) findViewById(R.id.help_list)).setAdapter(new HelpOperatorsAdapter());
	}
	
	@SuppressLint("ViewHolder")
	private class HelpOperatorsAdapter extends BaseAdapter {
		
		private Operator[] operators = null;
		
		private LayoutInflater inflater = null;
		
		public HelpOperatorsAdapter() {
			operators = new Operator[OperatorFactory.getOperators().size()];
			OperatorFactory.getOperators().values().toArray(operators);
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return operators.length;
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
			convertView  = inflater.inflate(R.layout.help_list_item, parent, false);
			
			TextView opview = (TextView) convertView.findViewById(R.id.item_operator);
			TextView descview = (TextView) convertView.findViewById(R.id.item_desc);
			
			opview.setText(operators[position].getName());
			descview.setText(operators[position].getDescription());
			return convertView;
		}

	}
	
	private class OnHelpCloseClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			setResult(0, intent);
			finish();
		}
		
	}
}
