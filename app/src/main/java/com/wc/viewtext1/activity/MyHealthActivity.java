package com.wc.viewtext1.activity;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wc.viewtext1.R;

/**
 * package : com.wc.viewtext1.activity.MyHealthActivity
 * author : wc
 * description :
 * time : create at 2016/8/17 17:12.
 */
public class MyHealthActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.health_view);
	}
}
