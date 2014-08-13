package com.calc.function;

import java.io.IOException;

import com.calc.cview.CurlView;
import com.calc.cview.DirectionView;
import com.calc.cview.ExpressionView;
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
import android.widget.Toast;

public class CalcActivity extends Activity {
	
	private String expr = null;
	private CurlView show = null;
	private DirectionView directer = null;
	private ExpressionView expression = null;

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
		
		show = (CurlView) findViewById( R.id.show );
		directer = (DirectionView) findViewById(R.id.direction);
		expression = (ExpressionView) findViewById(R.id.expression);
		
		//初始化操作符工厂
		try {
			OperatorFactory.initialize(getAssets().open("operators.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//设置自动提示
		String[] tips = new String[OperatorFactory.getOperators().keySet().size()];
		OperatorFactory.getOperators().keySet().toArray(tips);
		expression.setTips(tips);
		
		//收听函数画图事件，收听者为小箭头
		show.register(INotifyer.Event.ONDRAW, directer);
	}
	
	public void onOKClick(View v) {
		setExpr(expression.getText().toString());
		
		InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(v.getWindowToken(), 0);
		
		Expression exp = new Expression(getExpr());
		if ( exp.compile() )
			show.setExpr(exp);
		else {
			Toast.makeText(getBaseContext(), R.string.fail_compile, Toast.LENGTH_SHORT);
		}
	}
}
