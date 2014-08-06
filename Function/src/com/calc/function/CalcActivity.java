package com.calc.function;

import com.calc.cview.CurlView;
import com.calc.cview.DirectionView;
import com.calc.inter.INotifyer;

import expression.Expression;
import expression.item.OperatorFactory;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

public class CalcActivity extends Activity {
	
	private String expr = null;
	private CurlView show = null;
	private DirectionView directer = null;

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public CurlView getShow() {
		return show;
	}

	public void setShow(CurlView show) {
		this.show = show;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calc);
		
		OperatorFactory.initialize(getAssets());
		
		setShow((CurlView) findViewById( R.id.show ));
		directer = (DirectionView) findViewById(R.id.direction);
		show.register(INotifyer.Event.ONDRAW, directer);
	}
	
	public void onOKClick(View v) {
		EditText editExpr = (EditText) findViewById(R.id.expression);
		setExpr(editExpr.getText().toString());
		
		InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(v.getWindowToken(), 0);
		
		Expression exp = new Expression(getExpr());
		exp.compile();
		show.setExpr(exp);
	}
}
