package com.wc.viewtext1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * package : com.wc.viewtext1.view.FlyRocket
 * author : wc
 * description :
 * time : create at 2016/8/19 13:50.
 */
public class FlyRocket extends View {



	private Paint mPaint;
	private Path path;
	private int xWidth;
	private int yHeight;

	public FlyRocket(Context context) {
		this(context, null);
	}

	public FlyRocket(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlyRocket(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mPaint = new Paint();
		path = new Path();
	}

	private int width;
	private int height;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			//如果布局里面没有设置固定值,这里取布局宽度的1/2
			width = widthSize * 1 / 2;
		}
		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = heightSize * 1 / 2;
		}

		xWidth = width * 1 / 2;
		yHeight = height * 1 / 5;

		//设置View宽高
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(10);
		path.reset();
		path.moveTo(width * 1 / 8, height * 1 / 5);
		path.quadTo(xWidth, yHeight, width * 7 / 8, height * 1 / 5);
		canvas.drawPath(path, mPaint);
		mPaint.setStyle(Paint.Style.FILL);
		//绘制控制点
		canvas.drawCircle(xWidth, yHeight, 10, mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				xWidth = x;
				yHeight = y;
				postInvalidate();
				break;
			case MotionEvent.ACTION_UP:
				xWidth = width * 1 / 2;
				yHeight = height * 1 / 5;
				postInvalidate();
				break;
		}


		return true;
	}
}
