package com.calc.function;

import java.io.File;
import java.io.IOException;

import com.calc.cview.CurlView;
import com.calc.cview.DirectionView;
import com.calc.cview.ExpressionView;
import com.calc.inter.INotifyer;

import expression.Expression;
import expression.item.OperatorFactory;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

public class CalcActivity extends Activity {
	
	private String expr = null;
	private CurlView show = null;
	private DirectionView directer = null;
	private ExpressionView expression = null;
	private ImageView more = null;

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
		setTheme(R.style.AppTheme);
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_calc);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		
		show = (CurlView) findViewById( R.id.show );
		directer = (DirectionView) findViewById(R.id.direction);
		expression = (ExpressionView) findViewById(R.id.expression);
		more = (ImageView) findViewById(R.id.more);
		
		findViewById(R.id.ok).setOnClickListener(new OnOKClick());
		more.setOnClickListener(new OnMoreClick());
		
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
	
	private class OnOKClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			setExpr(expression.getText().toString());
			
			InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(v.getWindowToken(), 0);
			
			Expression exp = new Expression(getExpr());
			if ( exp.compile() )
				show.setExpr(exp);
			else {
				Toast.makeText(getBaseContext(), R.string.fail_compile, Toast.LENGTH_SHORT).show();
			}			
		}
		
	}
	
	private class OnMoreClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			PopupMenu pop = new PopupMenu(getBaseContext(), more);
			MenuInflater inflater = pop.getMenuInflater();
			inflater.inflate(R.menu.more, pop.getMenu());
			
			pop.setOnMenuItemClickListener(new OnMoreItemClick());
			pop.show();
		}
		
	}
	
	/**
	 * 监听弹出菜单的点击事件
	 * @author xinting
	 *
	 */
	private class OnMoreItemClick implements OnMenuItemClickListener {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			
			String msg = "";
			
			if ( expr.equals("") ) {
				Toast.makeText(getBaseContext(), "No data.", Toast.LENGTH_LONG).show();
				return true;
			}
			
			switch ( item.getItemId() ) {
			case R.id.save_as:
				File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				if ( pic.exists() ) {
					String file = show.saveAs(pic.getPath(), CurlView.SAVE_AS_PIC_TYPE.PNG);
					if ( null != file ) {
						msg = "Save as " + file + " success.";
					} else {
						msg = "Sorry, save into " + pic + " failed.";
					}
				} else {
					msg = pic.getPath()+" not exists.";
				}
				break;
			default:break;
			}
			
			if ( !msg.equals("") ) {
				Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
			}
			return true;
			
		}
		
	}
}
