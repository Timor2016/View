package com.wc.viewtext1.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.wc.viewtext1.R;

/**
 * package : com.wc.viewtext1.view.HealthCircleView
 * author : wc
 * description :
 * time : create at 2016/8/17 18:09.
 */
public class HealthCircleView extends View {

	private int textSize;
	private int keyTextSize;
	private int circleBackground;
	private int circleColor;

	private Paint circlrPaint;
	private Paint textPaint;

	private int widthBg;
	private int heightBg;

	//圆弧范围
	private RectF arcRect;
	//外层圆弧大小
	private float arcNum;
	private int walkNum, rankNum;

	//动画实现
	//动画效果的添加
	private AnimatorSet animSet;
	private int mySize = 2315, averageSize = 5000;


	public HealthCircleView(Context context) {
		this(context, null);
	}

	public HealthCircleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HealthCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//最后一位参数是从stytle中获取到的
		TypedArray array = context.getTheme().obtainStyledAttributes(
				attrs, R.styleable.HealthCircleView, defStyleAttr, R.style.HealthCircleView);
		int n = array.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = array.getIndex(i);
			switch (attr) {
				case R.styleable.HealthCircleView_textSize:
					textSize = array.getDimensionPixelSize(attr, transformSP(16));
					break;
				case R.styleable.HealthCircleView_keyTextSize:
					keyTextSize = array.getDimensionPixelSize(attr, transformSP(20));
					break;
				case R.styleable.HealthCircleView_circleBackground:
					circleBackground = array.getColor(attr, Color.GREEN);
					break;
				case R.styleable.HealthCircleView_circleColor:
					circleColor = array.getColor(attr, Color.GREEN);
					break;
			}
		}
		array.recycle();
		init();
	}

	private void init() {
		circlrPaint = new Paint();
		circlrPaint.setAntiAlias(true);
		//设置笔触为圆角
		circlrPaint.setStrokeCap(Paint.Cap.ROUND);
		textPaint = new Paint();
		animSet = new AnimatorSet();
	}

	//测量View的值
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int width;
		int height;
		//如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取父布局的大小
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {

			//如果布局里面没有设置固定值,这里取布局的宽度的1/2
			width = widthSize * 1 / 2;
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			//如果布局里面没有设置固定值,这里取布局的高度的3/4
			height = heightSize * 3 / 4;
		}
		widthBg = width;
		heightBg = height;
		setMeasuredDimension(width, height);
		startAnim();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//绘制圆弧
		circlrPaint.setStrokeWidth(widthBg / 20);
		//设置空心
		circlrPaint.setStyle(Paint.Style.STROKE);
		//防抖动
		circlrPaint.setDither(true);
		//连接处为圆弧
		circlrPaint.setStrokeJoin(Paint.Join.ROUND);
		circlrPaint.setColor(circleBackground);
		//圆弧范围
		arcRect = new RectF(widthBg * 1 / 4, widthBg * 1 / 4, widthBg * 3 / 4, widthBg * 3 / 4);
		canvas.drawArc(arcRect, 120, 300, false, circlrPaint);
		circlrPaint.setColor(circleColor);

		//绘制分数小圆弧
		/**
		 * 参数1：圆的范围大小
		 * 参数2：起始角度
		 * 参数3：圆心角角度，360为圆，180为半圆
		 * 参数4：中心
		 * 参数5：画笔Paint，可以设置画线or填充，设置颜色，设置线的粗细等等
		 */
		canvas.drawArc(arcRect, 120, arcNum, false, circlrPaint);
		textPaint.setColor(circleColor);
		textPaint.setTextSize(keyTextSize);
		canvas.drawText(String.valueOf(walkNum), widthBg * 3 / 8 + 26, widthBg * 1 / 2 + 20, textPaint);
		//绘制名次
		textPaint.setTextSize(widthBg / 15);
		canvas.drawText(String.valueOf(String.valueOf(rankNum)), widthBg * 1 / 2 - 10, widthBg * 3 / 4 + 18, textPaint);

		//绘制其他文字
		textPaint.setColor(circleBackground);
		textPaint.setTextSize(widthBg / 25);
		canvas.drawText("截止13:45已走", widthBg * 3 / 8 + 5, widthBg * 5 / 12 - 10, textPaint);
		canvas.drawText("好友平均2781步", widthBg * 3 / 8 - 10, widthBg * 2 / 3 - 20, textPaint);
		canvas.drawText("第", widthBg * 1 / 2 - 50, widthBg * 3 / 4 + 10, textPaint);
		canvas.drawText("名", widthBg * 1 / 2 + 30, widthBg * 3 / 4 + 10, textPaint);
	}

	private void startAnim() {
		//步数动画的实现
		ValueAnimator walkAnimator = ValueAnimator.ofInt(0, 5000);
		walkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				walkNum = (int) animation.getAnimatedValue();
				postInvalidate();
			}
		});

		//排名动画的实现
		ValueAnimator rankAnimator = ValueAnimator.ofInt(0, 8);
		rankAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				rankNum = (int) animation.getAnimatedValue();
				postInvalidate();
			}
		});


		double size = mySize;
		double avgSize = averageSize;
		if (size > avgSize) {
			size = avgSize;
		}
		//圆弧动画的实现
		//圆弧动画的实现
		ValueAnimator arcAnimator = ValueAnimator.ofFloat(0, (float) (size / avgSize * 300));
		arcAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				arcNum = (float) animation.getAnimatedValue();
				postInvalidate();
			}
		});

		animSet.setDuration(3000);
		animSet.playTogether(walkAnimator, rankAnimator, arcAnimator);
		animSet.start();
	}


	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public int getKeyTextSize() {
		return keyTextSize;
	}

	public void setKeyTextSize(int keyTextSize) {
		this.keyTextSize = keyTextSize;
	}

	public int getCircleBackground() {
		return circleBackground;
	}

	public void setCircleBackground(int circleBackground) {
		this.circleBackground = circleBackground;
	}

	public int getCircleColor() {
		return circleColor;
	}

	public void setCircleColor(int circleColor) {
		this.circleColor = circleColor;
	}

	private int transformSP(int value) {
		return (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
	}

	private int transformDP(int value) {
		return (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
	}
}
