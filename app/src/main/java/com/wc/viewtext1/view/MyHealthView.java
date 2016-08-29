package com.wc.viewtext1.view;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.wc.viewtext1.R;

/**
 * package : com.wc.viewtext1.view.MyHealthView
 * author : wc
 * description : 仿健康界面
 * time : create at 2016/8/17 15:43.
 */
public class MyHealthView extends View {

	private String mTitleText, mKeytextText, mEndtextText;
	private int mTitleColor, mKeytextColor, mEndtextColor, mBackgroundColor, mColor;
	private int mTitleSize, mKeytextSize, mEndtextSize;
	//画弧的画笔
	private Paint arcPaint;
	private RectF arcRect;
	//画背景
	private Paint bgPaint;
	//画字
	private Paint textPaint;
	private PathEffect effects;
	//虚线的画笔
	private Paint linePaint;
	//当前角度
	private float mCurrentAngle = 0;
	//开始角度
	private float mStartSweepValue;
	//当前百分比，目标百分比
	private float mCurrentPercent, mTargetPercent;
	//圆环宽度
	private int inCircleBound;
	private Path pathBg, linePath;

	//圆角竖条的距离,高度,平均高度
	private float rectSize, rectAgHeight;
	//圆角竖条的画笔
	private Paint rectPaint;
	private Path rectPath;
	//底部波纹
	private Paint weavPaint;
	private Path weavPath;
	//动画实现
	//动画效果的添加
	private AnimatorSet animSet;
	//背景的画笔
	private Paint backgroundPaint;
	//背景的坐标
	private int radiusBg, widthBg, heightBg;
	//圆环百分比
	private float arcNum;
	//步数
	private int walkNum;


	public MyHealthView(Context context) {
		this(context, null);
	}

	public MyHealthView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyHealthView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray array = context.getTheme()
				.obtainStyledAttributes(attrs, R.styleable.MyHealthView, defStyleAttr, 0);
		int n = array.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = array.getIndex(i);
			switch (attr) {
				case R.styleable.MyHealthView_mTitleSize:
					mTitleSize = array.getDimensionPixelSize(attr, transformSP(16));
					break;
				case R.styleable.MyHealthView_mTitleColor:
					mTitleColor = array.getColor(attr, Color.BLACK);
					break;
				case R.styleable.MyHealthView_mTitleText:
					mTitleText = array.getString(attr);
					break;
				case R.styleable.MyHealthView_mKeytextSize:
					mKeytextSize = array.getDimensionPixelSize(attr, transformSP(16));
					break;
				case R.styleable.MyHealthView_mKeytextColor:
					mKeytextColor = array.getColor(attr, Color.BLACK);
					break;
				case R.styleable.MyHealthView_mKeytextText:
					mKeytextText = array.getString(attr);
					break;
				case R.styleable.MyHealthView_mEndtextSize:
					mEndtextSize = array.getDimensionPixelSize(attr, transformSP(16));
					break;
				case R.styleable.MyHealthView_mEndtextColor:
					mEndtextColor = array.getColor(attr, Color.BLACK);
					break;
				case R.styleable.MyHealthView_mEndtextText:
					mEndtextText = array.getString(attr);
					break;
				case R.styleable.MyHealthView_mBackgroundColor:
					mBackgroundColor = array.getColor(attr, Color.BLACK);
					break;
				case R.styleable.MyHealthView_mColor:
					mColor = array.getColor(attr, Color.BLACK);
					break;
			}
		}

		array.recycle();
		init();
	}



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

			//如果布局里面没有设置固定值,这里取布局的宽度的 6/7
			width = widthSize * 6/7;
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			//如果布局里面没有设置固定值,这里取布局的高度的5/6
			height = heightSize * 5/6;
		}
		widthBg = width;
		heightBg = height;
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//绘制最底层的背景
		radiusBg = widthBg / 20;
		pathBg.moveTo(0, heightBg);
		pathBg.lineTo(0, radiusBg);
		pathBg.quadTo(0, 0, radiusBg, 0);
		pathBg.lineTo(widthBg - radiusBg, 0);
		pathBg.quadTo(widthBg, 0, widthBg, radiusBg);
		pathBg.lineTo(widthBg, heightBg);
		pathBg.lineTo(0, heightBg);
		backgroundPaint.setColor(Color.WHITE);
		canvas.drawPath(pathBg, backgroundPaint);

		//绘制圆弧
		arcPaint.setStrokeWidth(widthBg / 20);
		//设置空心
		arcPaint.setStyle(Paint.Style.STROKE);
		//防抖动
		arcPaint.setDither(true);
		//连接处为圆弧
		arcPaint.setStrokeJoin(Paint.Join.ROUND);
		//画笔的笔触为圆角
		arcPaint.setStrokeCap(Paint.Cap.ROUND);
		arcPaint.setColor(mBackgroundColor);
		//圆弧范围
		arcRect = new RectF(widthBg * 1 / 4, widthBg * 1 / 4, widthBg * 3 / 4, widthBg * 3 / 4);
		//绘制背景大圆弧
		canvas.drawArc(arcRect, 120, 300, false, arcPaint);

		arcPaint.setColor(mColor);
		//绘制分数小圆弧
		canvas.drawArc(arcRect, 120, arcNum, false, arcPaint);

		//绘制圆圈内的数字
		textPaint.setColor(mTitleColor);
		textPaint.setTextSize(widthBg / 10);
		canvas.drawText(String.valueOf("100"), widthBg * 3 / 8, widthBg * 1 / 2 + 20, textPaint);

		//绘制名次
		textPaint.setTextSize(widthBg / 15);
		canvas.drawText(String.valueOf("500"), widthBg * 1 / 2 - 15, widthBg * 3 / 4 + 10, textPaint);

		//绘制其他文字
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(widthBg / 25);
		canvas.drawText("截止13:45已走", widthBg * 3 / 8 - 10, widthBg * 5 / 12 - 10, textPaint);
		canvas.drawText("好友平均2781步", widthBg * 3 / 8 - 10, widthBg * 2 / 3 - 20, textPaint);
		canvas.drawText("第", widthBg * 1 / 2 - 50, widthBg * 3 / 4 + 10, textPaint);
		canvas.drawText("名", widthBg * 1 / 2 + 30, widthBg * 3 / 4 + 10, textPaint);



		//绘制底部文字
		weavPaint.setColor(Color.BLACK);
		weavPaint.setTextSize(widthBg / 20);
		canvas.drawText("成绩不错,继续努力哟!", widthBg * 1 / 10 - 20, heightBg * 11 / 12 + 50, weavPaint);

	}





	private void init() {
		pathBg = new Path();
		arcPaint = new Paint();
		textPaint = new Paint();
		bgPaint = new Paint();
		arcPaint.setAntiAlias(true);
		linePaint = new Paint();
		linePath = new Path();
		rectPaint = new Paint();
		weavPaint = new Paint();
		animSet = new AnimatorSet();
		backgroundPaint = new Paint();
		backgroundPaint.setAntiAlias(true);
		effects = new DashPathEffect(new float[]{5,5}, 1);
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
