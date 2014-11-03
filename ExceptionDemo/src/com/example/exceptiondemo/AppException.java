package com.example.exceptiondemo;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Looper;
import android.widget.Toast;

/**
 * 
 * @ClassName: com.example.exceptiondemo.AppException
 * @Description: 应用程序异常类：用于捕获异常
 * @author zhaokaiqiang
 * @date 2014-11-2 下午10:06:49
 * 
 */

public class AppException extends Exception implements UncaughtExceptionHandler {

	private static final long serialVersionUID = -6262909398048670705L;

	private String message;

	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private AppException() {
		super();
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	public AppException(String message, Exception excp) {
		super(message, excp);
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取APP异常崩溃处理对象
	 * 
	 * @param context
	 * @return
	 */
	public static AppException getAppExceptionHandler() {
		return new AppException();
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		}

	}

	/**
	 * 自定义异常处理
	 * 
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}

		final Activity activity = AppManager.getAppManager().currentActivity();

		if (activity == null) {
			return false;
		}

		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(activity, "程序要崩了", Toast.LENGTH_SHORT).show();
				new AlertDialog.Builder(activity).setTitle("提示")
						.setCancelable(false).setMessage("亲，程序马上崩溃了...")
						.setNeutralButton("没关系", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								AppManager.getAppManager().exitApp(activity);
							}
						}).create().show();
				Looper.loop();
			}
		}.start();

		return true;
	}

}
