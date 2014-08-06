package com.calc.cview;

import com.calc.inter.INotifyer;
import com.calc.inter.IResponser;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DirectionView extends ImageView implements IResponser {
	
	private float cx = 0;
	private float cy = 0;
	
	private float angle = 45;
	
	public DirectionView(Context context) {
		super(context);
	}

	public DirectionView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DirectionView(Context context, AttributeSet attrs) {
		super(context, attrs);
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
