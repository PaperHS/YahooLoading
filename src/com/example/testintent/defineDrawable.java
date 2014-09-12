package com.example.testintent;

import android.R.color;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Property;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

public class defineDrawable extends Drawable implements Animatable{

	Paint circlPaint;
	ValueAnimator anim;
	ObjectAnimator animator;
	ObjectAnimator centerAnimator;
	private float PressedProgress;
	private float centerProgress = 100;
	boolean isSotp = false;
	
	public defineDrawable() {
		circlPaint = new Paint();
		circlPaint.setAlpha(125);
		circlPaint.setColor(Color.BLUE);
		circlPaint.setStyle(Style.FILL);
		circlPaint.setAntiAlias(true);
		animator = ObjectAnimator.ofFloat(this, pressedProgressProperty, 0);
		animator.setDuration(2000);
		animator.setRepeatCount(10);
		animator.setInterpolator(new LinearInterpolator());
		centerAnimator = ObjectAnimator.ofFloat(this, CenterProgressProperty,100 ,0);
		centerAnimator.setDuration(800);
		centerAnimator.setInterpolator(new AccelerateInterpolator());
		animator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				centerAnimator.start();
				
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				Log.e("hshs", "onAnimationCancel");
				centerAnimator.start();
				
			}
		});
	}
	
	@Override
	public void draw(Canvas canvas) {
		drawTouchCircle(canvas);
	}
	
	
	private void drawTouchCircle(Canvas canvas) {
		canvas.save();
		canvas.restore();
		canvas.translate(canvas.getWidth()/2, canvas.getHeight()/2);
        canvas.rotate(-PressedProgress);
		canvas.drawCircle(centerProgress*0.71f, centerProgress*0.71f, 25, circlPaint);
		canvas.drawCircle(0, centerProgress, 25, circlPaint);
		canvas.drawCircle(centerProgress, 0, 25, circlPaint);
		canvas.drawCircle(-centerProgress*0.71f, -centerProgress*0.71f, 25, circlPaint);
		canvas.drawCircle(centerProgress*0.71f, -centerProgress*0.71f, 25, circlPaint);
		canvas.drawCircle(-centerProgress*0.71f, centerProgress*0.71f, 25, circlPaint);
		canvas.drawCircle(0, -centerProgress, 25, circlPaint);
		canvas.drawCircle(-centerProgress, 0, 25, circlPaint);
		if (isSotp && Math.abs(PressedProgress) < 2) {
			animator.cancel();
		}
	}
	
	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return PixelFormat.TRANSPARENT;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void start() {
		Log.e("hshs", "start");
		animator.setFloatValues(0,360);
		animator.start();
		isSotp = false;
	}

	@Override
	public void stop() {
		if (animator.isRunning()) {
			isSotp = true;
			return;
		}
		animator.setFloatValues(50,0);
		animator.start();
		
	}
	
	  private Property<defineDrawable, Float> pressedProgressProperty
      = new Property<defineDrawable, Float>(Float.class, "pressedProgress") {
      @Override
      public Float get(defineDrawable object) {
          return object.getPressedProgress();
      }

      @Override
      public void set(defineDrawable object, Float value) {
    	  
          object.setPressedProgress(value);
      }
  };

	protected Float getPressedProgress() {
		// TODO Auto-generated method stub
		return PressedProgress;
	}
	protected void setPressedProgress(Float value) {
		this.PressedProgress = value;
		invalidateSelf();
		
	}
	
	  private Property<defineDrawable, Float> CenterProgressProperty
      = new Property<defineDrawable, Float>(Float.class, "centerPressedProgress") {
      @Override
      public Float get(defineDrawable object) {
          return object.getCenterPressedProgress();
      }

      @Override
      public void set(defineDrawable object, Float value) {
    	  
          object.CenterPressedProgress(value);
      }
  };

	protected Float getCenterPressedProgress() {
		// TODO Auto-generated method stub
		return centerProgress;
	}

	protected void CenterPressedProgress(Float value) {
		this.centerProgress = value;
		invalidateSelf();
		
	}


}
