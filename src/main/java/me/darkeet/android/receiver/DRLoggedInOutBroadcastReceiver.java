/**
 * 
 */
package me.darkeet.android.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

/**
 * Name: LoggedInOutBroadcastReceiver
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: 监听用户登陆或注销状态
 */
public class DRLoggedInOutBroadcastReceiver extends BroadcastReceiver {
	public static final String INTENT_ACTION_LOGGED_IN = "android.intent.action.LOGGED_IN";
	public static final String INTENT_ACTION_LOGGED_OUT = "android.intent.action.LOGGED_OUT";

	public interface OnLoggedInOutStateListener {
		void onLoggedIn();

		void onLoggedOut();
	}
	
	private OnLoggedInOutStateListener onLoggedInOutListener;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (INTENT_ACTION_LOGGED_IN.equals(intent.getAction())) {
			if (onLoggedInOutListener != null) { 
				onLoggedInOutListener.onLoggedIn();
			}
		} else if (INTENT_ACTION_LOGGED_OUT.equals(intent.getAction())) {
			if (onLoggedInOutListener != null) { 
				onLoggedInOutListener.onLoggedOut();
			}
		}
	}

	/** 注册广播接收者 */
	public void register(Context context) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(INTENT_ACTION_LOGGED_IN);
		intentFilter.addAction(INTENT_ACTION_LOGGED_OUT);
		context.registerReceiver(this, intentFilter);
	}

	/** 解绑广播接收者 */
	public void unregister(Context context) {
		context.unregisterReceiver(this);
	}

	public void setOnLoggedInOutListener(OnLoggedInOutStateListener listener) {
		this.onLoggedInOutListener = listener;
	}
}
