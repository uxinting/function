package com.calc.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.calc.cview.CurlView;
import com.calc.cview.DirectionView;
import com.calc.cview.ExpressionView;
import com.calc.inter.INotifyer;

import expression.Expression;
import expression.item.Operator;
import expression.item.OperatorFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.input.InputManager;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
	private Button ok = null;
	
	private MathKeyboard mkeyboard;

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
		ok = (Button) findViewById(R.id.ok);
			
		ok.setOnClickListener(new OnOKClick());
		more.setOnClickListener(new OnMoreClick());
		expression.addTextChangedListener( new ExpressionTextWatcher() );
		expression.setOnClickListener( new ExpressionClickListener() );
		expression.setOnTouchListener( new ExpressionTouchListener() );
		
		//初始化键盘
		mkeyboard = new MathKeyboard((KeyboardView) findViewById(R.id.keyboard_view), this, expression);
		
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Bundle bundle = data.getExtras();
		show.setCXY(bundle.getFloat("cx"), bundle.getFloat("cy"));
	}
	
	private void showAboutDialog() {
		String about = "";
		about += "程序名称：" + getResources().getString( R.string.app_name ) + "\n";
		
		try {
			about += "版本：" + getPackageManager().getPackageInfo( getPackageName(), 0 ).versionName + "\n";
			
			BufferedReader br = new BufferedReader( new InputStreamReader( getAssets().open( "Readme.txt" ) ) );
			String line;
			while ( ( line = br.readLine() ) != null ) {
				about += line + "\n";
			}
		} catch ( Exception e ) {
			about += "版本：未知";
		}
		
		new AlertDialog.Builder( this )
		.setTitle( getResources().getString( R.string.about ) )
		.setIcon( android.R.drawable.ic_dialog_info )
		.setMessage( about )
		.setNegativeButton( android.R.string.yes, null )
		.show();
	}
	
	private class ExpressionTextWatcher implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			Expression exp = new Expression( expression.getText().toString() );
			if ( exp.compile() ) {
				ok.setBackgroundResource( R.drawable.ok_green );
			} else {
				ok.setBackgroundResource( R.drawable.ok_red );
			}
		}
		
	}
	
	private class ExpressionClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			mkeyboard.show();
		}
		
	}
	
	private class ExpressionTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			mkeyboard.show();
			return true;
		}
		
	}

	private class OnOKClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			mkeyboard.hide();
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
			
			switch ( item.getItemId() ) {
			case R.id.save_as:
				if ( expr == null ) {
					Toast.makeText(getBaseContext(), "No data.", Toast.LENGTH_LONG).show();
					return true;
				}
				
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
			case R.id.help:
				Intent intent = new Intent();
				
				//保存此时的cx,cy
				Bundle bundle = new Bundle();
				bundle.putFloat("cx", show.getCX());
				bundle.putFloat("cy", show.getCY());

				intent.putExtras(bundle);
				intent.setClass(CalcActivity.this, HelpActivity.class);
				startActivityForResult(intent, 0);
				break;
			case R.id.about:
				showAboutDialog();
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
