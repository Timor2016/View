package com.wc.viewtext1.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wc.viewtext1.R;
import com.wc.viewtext1.view.MySportView;

public class MainActivity extends AppCompatActivity {


	private Button btn1, btn2,btn3,btn4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initClick();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button)findViewById(R.id.btn3);
		btn4 = (Button)findViewById(R.id.btn4);
	}

	private void initClick() {
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, MySportActivity.class);
				startActivity(intent);
			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, MyHealthActivity.class);
				startActivity(intent);
			}
		});
		btn3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, KaQuanActivity.class);
				startActivity(intent);
			}
		});
		btn4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, FlyRocketActivity.class);
				startActivity(intent);
			}
		});
	}
}
