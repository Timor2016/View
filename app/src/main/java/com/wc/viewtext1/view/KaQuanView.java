package com.wc.viewtext1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * package : com.wc.viewtext1.view.KaQuanView
 * author : wc
 * description :
 * time : create at 2016/8/18 9:36.
 */
public class KaQuanView extends LinearLayout {

	private int radius = 10;
	private int gap = 8;
	private int circleCount;
	//留下的部分
	private float remain;
	private Paint mPaint;

	public KaQuanView(Context context) {
		this(context, null);
	}

	public KaQuanView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public KaQuanView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//消除锯齿
		mPaint.setDither(true);//防抖动



		mPaint.setColor(Color.WHITE);
		mPaint.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (remain==0){
			remain = (int)(w-gap)%(2*radius+gap);
		}
		circleCount = (int) ((w-gap)/(2*radius+gap));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (int i=0;i<circleCount;i++){
			float x = gap+radius+remain/2+((gap+radius*2)*i);
			canvas.drawCircle(x,0,radius,mPaint);
			canvas.drawCircle(x,getHeight(),radius,mPaint);
		}

	}
}
