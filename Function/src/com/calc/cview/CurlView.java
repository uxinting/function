package com.calc.cview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Formatter.BigDecimalLayoutForm;

import com.calc.function.CurlMonitor;
import com.calc.function.R;
import com.calc.function.CurlMonitor.Switch;
import com.calc.inter.INotifyer;
import com.calc.inter.IResponser;

import expression.Expression;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

public class CurlView extends View implements INotifyer {
	
	private Paint paint = null;
	private Resources r = null;
	
	private CurlMonitor monitor = null;
	
	private double cx = 0.0;
	private double cy = 0.0;
	private float unit = 25;
	private float unitTag = 2;
	
	private float oldx = 0;
	private float oldy = 0;
	
	private Expression expr = null;
	
	private ArrayList<Pair<Float, Float>> dots = null;
	private HashMap<Event, ArrayList<IResponser>> responsers = null;
	
	public static enum SAVE_AS_PIC_TYPE { BMP, PNG };
	
	public CurlView(Context context) {
		super(context);
	}

	public CurlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		r = getResources();
		monitor = CurlMonitor.instance();

		paint = new Paint();
		
		unit = r.getDimension(R.dimen.unit);
	}

	public CurlView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			oldx = event.getX();
			oldy = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			setCXY((float)(event.getX()-oldx+cx), (float)(event.getY()-oldy+cy));
			oldx = event.getX();
			oldy = event.getY();
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			break;
		case MotionEvent.ACTION_POINTER_UP:
			break;
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if ( expr == null ) {
			return;
		}
		
		paint.setColor(r.getColor(R.color.axes));
		paint.setStrokeWidth(r.getDimension(R.dimen.line));
		
		canvas.drawLines(getXYAxes(), paint);
		
		paint.setColor(r.getColor(R.color.line));
		updateDots();
		for ( Pair<Float, Float> p : dots ) {
			canvas.drawPoint(p.first, p.second, paint);
		}
		
		if ( responsers.containsKey(INotifyer.Event.ONDRAW) )
			for ( IResponser r : responsers.get(INotifyer.Event.ONDRAW) ) {
				r.action( this );
			}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int w = MeasureSpec.getSize(widthMeasureSpec);
		int h = MeasureSpec.getSize(heightMeasureSpec);
		
		cx = (w+r.getDimension(R.dimen.cx))%w;
		cy = (h+r.getDimension(R.dimen.cy))%h;
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void setExpr(Expression expr) {
		this.expr = expr;
		invalidate();
	}

	public void setCXY( float cx, float cy ) {
		if ( monitor.getMoveable() == Switch.OFF ) return;
		this.cx = cx;
		this.cy = cy;
		invalidate();
	}
	
	public float getCX() {
		return (float) cx;
	}
	
	public float getCY() {
		return (float) cy;
	}
	
	public String saveAs(String path, SAVE_AS_PIC_TYPE type) {
		Bitmap map = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(map);
		draw(canvas);
		
		String filePath = path + File.separator + "capture";
		try {
			switch (type) {
			case PNG:
				filePath += ".png";
				map.compress(CompressFormat.PNG, 90, new FileOutputStream(filePath));
				break;
			default:
				return null;
			}
		} catch (FileNotFoundException e) {
			return null;
		}
		return filePath;
	}
	
	@Override
	public void register(Event e, IResponser responser) {
		if ( responsers == null ) {
			responsers = new HashMap<INotifyer.Event, ArrayList<IResponser>>();
		}
		if ( !responsers.containsKey(e) ) {
			responsers.put(e, new ArrayList<IResponser>());
		}
		responsers.get(e).add(responser);
	}

	private float[] getXYAxes() {		
		ArrayList<Float> lines = new ArrayList<Float>();
		
		float[] cxy = {
			0, (float) cy, this.getWidth(), (float) cy,
			(float) cx, 0, (float) cx, this.getHeight()
		};
		for ( float f : cxy ) {
			lines.add( f );
		}
		
		for ( float x = (float) cx; x <= this.getWidth(); x += unit ) {
			lines.add( x );lines.add( (float) (cy-unitTag) );
			lines.add( x );lines.add( (float) cy );
		}
		
		for ( float x = (float) cx; x >= 0; x -= unit ) {
			lines.add( x );lines.add( (float) (cy+unitTag) );
			lines.add( x );lines.add( (float) cy );
		}
		
		for ( float y = (float) cy; y <= this.getHeight(); y += unit ) {
			lines.add( (float) cx+unitTag );lines.add( y );
			lines.add( (float) cx );lines.add( y );
		}
		
		for ( float y = (float) cy; y >= 0; y -= unit ) {
			lines.add( (float) cx+unitTag );lines.add( y );
			lines.add( (float) cx );lines.add( y );
		}
		
		float[] cor = new float[lines.size()];
		for ( int i = 0; i < lines.size(); i++ ) {
			cor[i] = lines.get(i);
		}
		return cor;
	}

	private void updateDots() {
		if ( dots == null ) {
			dots = new ArrayList<Pair<Float,Float>>();
		} else {
			dots.clear();
		}

		fillDots();
		
		if ( expr.hasEvenRoot() ) {
			expr.toggleRootResult();
			fillDots();
		}
	}
	
	/**
	 * 根据函数，填充连续的点
	 */
	private void fillDots() {
		float xmin = - getCX();
		float xmax = getWidth() - getCX();
		
		for ( float i = xmin; i < xmax; i++ ) {
			float y = (float) (double) (getCY()-expr.result(i/unit)*unit);
			if ( y < 0 || y > getHeight() ) continue;
			
			//为使所有的点连续，比较上一个点，如果不相邻，补充之间的点
			if ( !dots.isEmpty() ) {
				float lasty = dots.get(dots.size()-1).second;
				if ( Math.abs(y - lasty) > 1 ) {
					float midy = (float) (double) ( getCY()-expr.result((i+0.5)/unit)*unit );
					if ( lasty <= midy && lasty - y < -1 ) {
						for ( float j = lasty; j < midy; j++ ) {
							dots.add(new Pair<Float, Float>(getCX()+i-1, j));
						}
						for ( float k = midy; k < y; k++ ) {
							dots.add(new Pair<Float, Float>(getCX()+i, k));
						}
					}
					if ( lasty >= midy && lasty - y > 1 ) {
						for ( float j = lasty; j > midy; j-- ) {
							dots.add(new Pair<Float, Float>(getCX()+i-1, j));
						}
						for ( float k = midy; k > y; k-- ) {
							dots.add(new Pair<Float, Float>(getCX()+i, k));
						}
					}
				}
			}
			dots.add(new Pair<Float, Float>(getCX()+i, y));
		}
	}

}
