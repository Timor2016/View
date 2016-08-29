package com.wc.viewtext1.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wc.viewtext1.R;
import com.wc.viewtext1.view.MySportView;

/**
 * package : com.wc.viewtext1.activity.MySportActivity
 * author : wc
 * description :
 * time : create at 2016/8/17 17:06.
 */
public class MySportActivity extends AppCompatActivity {
	private MySportView sportView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_sport);

		sportView = (MySportView) findViewById(R.id.sport_view);
		sportView.setmTargetPercent(100);
		sportView.setTextColor(Color.BLACK);
	}
}
