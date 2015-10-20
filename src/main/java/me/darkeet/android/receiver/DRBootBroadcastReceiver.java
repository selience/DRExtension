/**
 * 
 */
package me.darkeet.android.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

/**
 * Name: DRBootBroadcastReceiver
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: 监控开机启动任务
 *
 * <intent-filter>
 * 		<action android:name="android.intent.action.BOOT_COMPLETED" />
 * </intent-filter>
 */
public class DRBootBroadcastReceiver extends BroadcastReceiver {

	public interface OnBootCompletedListener {
		
		void onStart(Intent intent);
    }
	
	private OnBootCompletedListener onBootCompletedListener;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (onBootCompletedListener!=null) {
			onBootCompletedListener.onStart(intent);
		}
	}

	public void register(Context context) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
		context.registerReceiver(this, intentFilter);
	}

	public void unregister(Context context) {
		context.unregisterReceiver(this);
	}
}
