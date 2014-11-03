package com.example.exceptiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 注册默认的未捕捉异常处理类
		Thread.setDefaultUncaughtExceptionHandler(AppException
				.getAppExceptionHandler());
		AppManager.getAppManager().addActivity(this);

	}

	public void btn(View view) {
		// 除零错误，程序会崩溃
		int c = 1 / 0;
	}

}
