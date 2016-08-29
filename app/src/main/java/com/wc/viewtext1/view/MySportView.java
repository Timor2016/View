package com.wc.viewtext1.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.wc.viewtext1.R;

/**
 * package : com.wc.viewtext1.view.MySportView
 * author : wc
 * description : 微博运动积分的实现
 * time : create at 2016/8/17 14:05.
 */
public class MySportView extends View {

	private String text;
	private String unitText;


	private int textColor;
	private int textSize;
	private int outCircleColor;
	private int inCircleColor;
	private Paint mPaint, circlePaint;
	//绘制文本范围
	private Rect mBound;
	private RectF circleRect;
	private float mCurrentAngle;
	private float mStartSweepValue;
	private float mCurrentPercent, mTargetPercent;
	private int inCircleBound, outCircleBound;

	public MySportView(Context context) {
		this(context, null);
	}

	public MySportView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MySportView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray array = context.getTheme()
				.obtainStyledAttributes(attrs, R.styleable.MySportView, defStyleAttr, 0);
		int n = array.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = array.getIndex(i);
			switch (attr) {
				case R.styleable.MySportView_titleColor:
					// 默认颜色设置为黑色
					textColor = array.getColor(attr, Color.BLACK);
					break;
				case R.styleable.MySportView_titleSize:
					// 默认设置为16sp，TypeValue也可以把sp转化为px
					textSize = array.getDimensionPixelSize(attr, transformSP(16));
					break;
				case R.styleable.MySportView_outCircleColor:
					// 默认颜色设置为黑色
					outCircleColor = array.getColor(attr, Color.BLACK);
					break;
				case R.styleable.MySportView_inCircleColor:
					// 默认颜色设置为黑色
					inCircleColor = array.getColor(attr, Color.BLACK);
					break;
				case R.styleable.MySportView_inCircleBound:
					inCircleBound = array.getDimensionPixelSize(attr, transformDP(6));
					break;
				case R.styleable.MySportView_outCircleBound:
					outCircleBound = array.getDimensionPixelSize(attr, transformDP(50));
					break;
			}

		}
		array.recycle();
		init();
	}

	private void init() {
		//创建画笔
		mPaint = new Paint();
		circlePaint = new Paint();
		//设置是否抗锯齿
		mPaint.setAntiAlias(true);
		//圆环开始角度 (-90° 为12点钟方向)
		mStartSweepValue = -90;
		//当前角度
		mCurrentAngle = 0;
		//当前百分比
		mCurrentPercent = 0;
		inCircleBound = 7;
		outCircleBound = 10;
		//绘制文本的范围
		mBound = new Rect();
		//单位
		unitText = "%";
	}

	//view设置为wrap_count时
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
		//设置View宽高
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//设置外圆的颜色
		mPaint.setColor(outCircleColor);
		//设置外圆为空心
		mPaint.setStyle(Paint.Style.STROKE);
		//设置画笔宽度
		mPaint.setStrokeWidth(inCircleBound);
		//画外圆
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2-inCircleBound, mPaint);
		//设置字体颜色
		mPaint.setColor(textColor);
		//设置字体大小
		mPaint.setTextSize(textSize);
		//画笔宽度
		mPaint.setStrokeWidth(1);
		//画笔类型
		mPaint.setStyle(Paint.Style.FILL);
		//得到字体的宽高范围
		text = String.valueOf(mCurrentPercent);
		mPaint.getTextBounds(text, 0, text.length(), mBound);
		//绘制字体
		canvas.drawText(text, getWidth() / 2 - mBound.width() / 2,
				getWidth() / 2 + mBound.height() / 2, mPaint);
		//设置字体大小
		mPaint.setTextSize(textSize / 3);
		//绘制字体
		canvas.drawText(unitText, getWidth() * 3 / 5, getWidth() / 3, mPaint);

		circlePaint.setAntiAlias(true);
		circlePaint.setStyle(Paint.Style.STROKE);
		//设置圆弧的宽度
		circlePaint.setStrokeWidth(outCircleBound);
		//设置圆弧的颜色
		circlePaint.setColor(inCircleColor);
		//圆弧范围
		circleRect = new RectF(20+inCircleBound, 20+inCircleBound, getWidth() - 20-inCircleBound, getWidth() - 20-inCircleBound);
		//绘制圆弧
		canvas.drawArc(circleRect, mStartSweepValue, mCurrentAngle, false, circlePaint);
		//判断当前百分比是否小于设置目标的百分比
		if (mCurrentPercent < mTargetPercent) {
			//当前百分比+1
			mCurrentPercent += 1;
			//当前角度+360
			mCurrentAngle += 3.6;
			//每100ms重画一次
			//每隔指定的时间调用View的invalidate()方法
			postInvalidateDelayed(100);
		}
	}

	private int transformSP(int value) {
		return (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
	}

	private int transformDP(int value) {
		return (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
	}

	//获取当前Text
	public String getText() {
		return text;
	}

	public String getUnitText() {
		return unitText;
	}

	public void setUnitText(String unitText) {
		this.unitText = unitText;
	}

	//获取text颜色
	public int getTextColor() {
		return textColor;
	}

	//设置text颜色
	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	//获取文本大小
	public int getTextSize() {
		return textSize;
	}

	//设置文本大小
	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	//获取外圈颜色
	public int getOutCircleColor() {
		return outCircleColor;
	}

	//设置外圈颜色
	public void setOutCircleColor(int outCircleColor) {
		this.outCircleColor = outCircleColor;
	}

	//获取内圈颜色
	public int getInCircleColor() {
		return inCircleColor;
	}

	//设置内圈颜色
	public void setInCircleColor(int inCircleColor) {
		this.inCircleColor = inCircleColor;
	}

	//获取当前开始的角度
	public float getmStartSweepValue() {
		return mStartSweepValue;
	}

	//设置当前开始的角度
	public void setmStartSweepValue(float mStartSweepValue) {
		this.mStartSweepValue = mStartSweepValue;
	}

	//获取当前的百分比
	public float getmCurrentPercent() {
		return mCurrentPercent;
	}

	//设置当前的百分比
	public void setmCurrentPercent(float mCurrentPercent) {
		this.mCurrentPercent = mCurrentPercent;
	}

	//获取目标百分比
	public float getmTargetPercent() {
		return mTargetPercent;
	}

	//设置目标百分比
	public void setmTargetPercent(float mTargetPercent) {
		this.mTargetPercent = mTargetPercent;
	}
}
