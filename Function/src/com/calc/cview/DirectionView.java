package com.calc.cview;

import com.calc.function.CurlMonitor;
import com.calc.function.CurlMonitor.Switch;
import com.calc.inter.INotifyer;
import com.calc.inter.IResponser;

import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class DirectionView extends ImageView implements IResponser {
	
	private float cx = 0;
	private float cy = 0;
	
	private float angle = 45;
	
	private final int	  alphaOn = 255;
	private final int	  alphaOff = (int) (255*0.4);
	
	private CurlMonitor monitor = null;
	
	public DirectionView(Context context) {
		super(context);
	}

	public DirectionView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DirectionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		monitor = CurlMonitor.instance();
		if ( monitor.getMoveable() == Switch.OFF ) {
			setImageAlpha( alphaOff );
		} else {
			setImageAlpha( alphaOn );
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch ( event.getAction() ) {
		case MotionEvent.ACTION_UP:
			if ( monitor.getMoveable() == Switch.ON ) {
				monitor.setMoveable( Switch.OFF );
				setImageAlpha( alphaOff );
			} else {
				monitor.setMoveable( Switch.ON );
				setImageAlpha( alphaOn );
			}
			break;
		}
		return true;
	}

	@Override
	public void action(INotifyer notifyer) {
		CurlView cv = (CurlView) notifyer;
		cx = this.getLeft() + this.getWidth() / 2;
		cy = this.getTop() + this.getHeight() / 2;
		
		float tx = cv.getCX()-cx;
		float ty = cy-cv.getCY();
		double rotation = (tx) / (Math.sqrt(tx*tx+ty*ty));
		rotation = Math.acos(rotation)*180/Math.PI;
		if ( ty < 0 ) rotation = 360 - rotation;
		this.setRotation((float) (360-rotation+angle));
	}

}
