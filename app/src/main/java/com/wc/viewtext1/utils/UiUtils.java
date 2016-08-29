package com.wc.viewtext1.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * package : com.wc.viewtext1.utils.UiUtils
 * author : wc
 * description :
 * time : create at 2016/8/19 14:56.
 */
public class UiUtils {

	//获取窗口宽度
	public static int getScreenWidthPixels(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	//dip转px
	public static int dipToPx(Context context, int dip) {
		return (int) (dip * getScreenDensity(context) + 0.5f);
	}

	//获取屏幕分辨率
	public static float getScreenDensity(Context context) {
		try {
			DisplayMetrics dm = new DisplayMetrics();
			((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
					.getMetrics(dm);
			return dm.density;
		} catch (Exception e) {
			return DisplayMetrics.DENSITY_DEFAULT;
		}
	}

}
