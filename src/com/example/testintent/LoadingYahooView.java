package com.example.testintent;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoadingYahooView extends View implements ILoadingYahoo{
	
	defineDrawable d;
	Paint p = new Paint();
	private int	centerX;
	private int	centerY;
	private int	outerRadius;
	private Context context;
	public LoadingYahooView(Context context) {
		this(context,null,0);
	}

	public LoadingYahooView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	
	public LoadingYahooView(Context context, AttributeSet attrs,int defStyle){
		super(context, attrs, defStyle);
		this.setClickable(true);
		this.setFocusable(true);
		this.context = context;
		d = new defineDrawable();
		d.setCallback(this);
	}
	
	private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		d.draw(canvas);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		centerX = w / 2;
		centerY = h / 2;
		outerRadius = Math.min(w, h) / 2;
	}
	
	@Override
	public void setPressed(boolean pressed) {
		super.setPressed(pressed);
		if (pressed) {
			d.start();
		}else {
			d.stop();
		}
	}
	
	@Override
    protected boolean verifyDrawable(Drawable who) {
        return who == d || super.verifyDrawable(who);
    }

	@Override
	public void invalidateDrawable(Drawable drawable) {
		this.invalidate();
		super.invalidateDrawable(drawable);
	}

	@Override
	public void setColors(int[] colors) {
		Resources r = context.getResources();
		int[] c = new int[colors.length];
		for (int i = 0; i < c.length; i++) {
			c[i] = r.getColor(colors[i]);
		}
		
	}

	@Override
	public void setRadius(int radius) {
		// TODO Auto-generated method stub
		
	}
	
}
