package com.wc.viewtext1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * package : com.wc.viewtext1.view.WaterRippleView
 * author : wc
 * description :
 * time : create at 2016/8/19 15:00.
 */
public class WaterRippleView extends View {


	private Paint mWavePaint;
	private int waveHight;
	private float mCycleFactorW;


	public WaterRippleView(Context context) {
		this(context, null);
	}

	public WaterRippleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WaterRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		init();
	}

	private void init() {
		mWavePaint = new Paint();
		mWavePaint.setAntiAlias(true);
		mWavePaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		int height;
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

		// 将周期定为view总宽度
		mCycleFactorW = (float) (2 * Math.PI / width);
		//设置View宽高
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
