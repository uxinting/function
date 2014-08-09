package com.calc.cview;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class ExpressionView extends AutoCompleteTextView {
	
	private String[]	tips = null;
	private ArrayAdapter<String> adapter = null;

	public ExpressionView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ExpressionView(Context context) {
		super(context);
	}

	public ExpressionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public String[] getTips() {
		return tips;
	}

	public void setTips(String[] tips) {
		this.tips = tips;
		
		if ( adapter == null )
			adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_dropdown_item_1line, tips);
		else {
			adapter.clear();
			adapter.addAll(tips);
		}
		
		setAdapter(adapter);
	}

}
