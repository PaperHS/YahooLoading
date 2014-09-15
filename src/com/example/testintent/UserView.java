package com.example.testintent;

import android.content.Context;
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

public class UserView extends View{
	
	defineDrawable d;
	Paint p = new Paint();
	private int	centerX;
	private int	centerY;
	private int	outerRadius;
	public UserView(Context context) {
		this(context,null,0);
	}

	public UserView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	
	public UserView(Context context, AttributeSet attrs,int defStyle){
		super(context, attrs, defStyle);
		this.setClickable(true);
		this.setFocusable(true);
		d = new defineDrawable();
		p.setAlpha(255);
		p.setColor(Color.RED);
		p.setStyle(Style.FILL);
		p.setAntiAlias(true);
		d.setCallback(this);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		d.draw(canvas);
		canvas.drawCircle(centerX, centerY, 20, p);
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
	
}
