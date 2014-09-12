package com.example.testintent;

import android.R.color;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Property;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public class defineDrawable extends Drawable implements Animatable,ILoadingYahoo{

	Paint circlPaint;
	private int mBallAlpha = 125;
	private int mBallRadius = 25;
	private float mCircleRadius = 100;
	ValueAnimator anim;
	ObjectAnimator animator;
	ObjectAnimator centerAnimator;
	ObjectAnimator mOutAnimator;
	ObjectAnimator mEndAnimator;
	private int[] mBallColors = new int[]{
		Color.BLUE , Color.CYAN , Color.GREEN,
		Color.LTGRAY , Color.YELLOW , Color.MAGENTA,
	};
	private Paint[] paints = new Paint[6];
	private Paint mEndInnerPaint;
	private float PressedProgress;
	private float mEndProgress;
	private float centerProgress = mCircleRadius;
	boolean isSotp = false;
	boolean isEnding = false;
	boolean isFirst ;
	Canvas tmpCanvas;
	
	public defineDrawable() {
		isFirst = true;
		mEndInnerPaint = new Paint();
		mEndInnerPaint.setAlpha(0);
		mEndInnerPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		mEndInnerPaint.setColor(Color.TRANSPARENT);
		mEndInnerPaint.setStyle(Style.FILL);
		mEndInnerPaint.setAntiAlias(true);
		circlPaint = new Paint();
		circlPaint.setAlpha(125);
		circlPaint.setColor(Color.BLUE);
		circlPaint.setStyle(Style.FILL);
		circlPaint.setAntiAlias(true);
		for (int i = 0; i < paints.length; i++) {
			paints[i] = new Paint();
			paints[i].setColor(mBallColors[i]);
			paints[i].setAlpha(mBallAlpha);
			paints[i].setStyle(Style.FILL);
			paints[i].setAntiAlias(true);
		}
		
		animator = ObjectAnimator.ofFloat(this, pressedProgressProperty, 0);
		animator.setDuration(2000);
		animator.setRepeatCount(10);
		animator.setInterpolator(new LinearInterpolator());
		centerAnimator = ObjectAnimator.ofFloat(this, CenterProgressProperty,120 ,0);
		centerAnimator.setDuration(400);
		centerAnimator.setInterpolator(new AccelerateInterpolator());
		mEndAnimator = ObjectAnimator.ofFloat(this, EndProgressProperty , 0 ,1500);
		mEndAnimator.setDuration(400);
		mEndAnimator.setInterpolator(new AccelerateInterpolator());
		mOutAnimator = ObjectAnimator.ofFloat(this, CenterProgressProperty,100 ,120);
		mOutAnimator.setDuration(100);
		mOutAnimator.setInterpolator(new DecelerateInterpolator());
		animator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				mOutAnimator.start();
				
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				mOutAnimator.start();
				
			}
		});
		mOutAnimator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				centerAnimator.start();
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				
			}
		});
		centerAnimator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator arg0) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				isEnding = true;
				mEndAnimator.start();
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				
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
		if (!isEnding) {
			canvas.rotate(-PressedProgress);
	        canvas.drawCircle(0, centerProgress, mBallRadius, paints[0]);
			canvas.drawCircle(centerProgress*0.87f, centerProgress*0.5f, mBallRadius, paints[1]);
			canvas.drawCircle(centerProgress*0.87f, -centerProgress*0.5f, mBallRadius, paints[2]);
			canvas.drawCircle(0, -centerProgress, mBallRadius, paints[3]);
			canvas.drawCircle(-centerProgress*0.87f, -centerProgress*0.5f, mBallRadius, paints[4]);
			canvas.drawCircle(-centerProgress*0.87f, centerProgress*0.5f, mBallRadius, paints[5]);
			if (isSotp && Math.abs(PressedProgress) < 2) {
				animator.cancel();
			}
		}else {
			canvas.drawCircle(0, 0, mBallRadius+mEndProgress, circlPaint);
		}
	}
	
	@Override
	public int getOpacity() {
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
		centerProgress = mCircleRadius;
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

	@Override
	public void setColors(int[] colors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRadius(int radius) {
		// TODO Auto-generated method stub
		
	}
	 private Property<defineDrawable, Float> EndProgressProperty
     = new Property<defineDrawable, Float>(Float.class, "endPressedProgress") {
     @Override
     public Float get(defineDrawable object) {
         return object.getEndPressedProgress();
     }

     @Override
     public void set(defineDrawable object, Float value) {
   	  
         object.setEndPressedProgress(value);
     }
 };

	protected Float getEndPressedProgress() {
		return mEndProgress;
	}

	protected void setEndPressedProgress(Float value) {
		this.mEndProgress = value;
		invalidateSelf();
	}

}
