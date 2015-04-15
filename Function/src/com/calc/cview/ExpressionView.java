package com.calc.cview;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

public class ExpressionView extends MultiAutoCompleteTextView {
	
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
		
		setTokenizer(new OperatorTokenizer());
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

	public class OperatorTokenizer implements MultiAutoCompleteTextView.Tokenizer {
		
		private final char[] tokens = { '+', '-', '*', '/' };

		private boolean isToken(char c) {
			for ( char tc : tokens ) {
				if ( tc == c ) return true;
			}
			return false;
		}
		
		@Override
		public int findTokenStart(CharSequence text, int cursor) {
			
			int i = cursor;
			
			while ( i > 0 && !isToken(text.charAt(i-1)) ) i--;
			
			return i;
		}

		@Override
		public int findTokenEnd(CharSequence text, int cursor) {
			
			int i = cursor;
			
			while ( i < text.length() ) {
				if ( isToken(text.charAt(i)) ) break;
				else i++;
			}
			
			return i;
		}

		@Override
		public CharSequence terminateToken(CharSequence text) {
			return text;
		}
		
	}

}
