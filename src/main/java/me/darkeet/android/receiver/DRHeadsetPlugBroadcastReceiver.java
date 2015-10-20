package me.darkeet.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Name: DRHeadsetPlugBroadcastReceiver
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: 广播接收者监测耳机插入和拔出; 此广播在AndroidManifest.xml中注册无效，需要在具体的Activity中注册和销毁，
 * 				后台监测可采用service方式启动:同 AudioManager.ACTION_AUDIO_BECOMING_NOISY广播；
 *
 * <intent-filter>
 * 		<action android:name="android.intent.action.HEADSET_PLUG" />
 * </intent-filter>
 */
public class DRHeadsetPlugBroadcastReceiver extends BroadcastReceiver {

	public interface OnHeadsetAvailableListener {
		void onHeadsetAvailable();

		void onHeadsetUnavailable();
	}

	private OnHeadsetAvailableListener onHeadsetAvailableListener;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.hasExtra("state")) { 
			// state 0代表拔出，1代表插入
			if (intent.getIntExtra("state", 0) == 0) {
				if (onHeadsetAvailableListener != null) { 
					onHeadsetAvailableListener.onHeadsetUnavailable();
				}
			} else if (intent.getIntExtra("state", 0) == 1) {
				if (onHeadsetAvailableListener != null) { 
					onHeadsetAvailableListener.onHeadsetAvailable();
				}
			}
		}
	}

	public void register(Context context) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
		context.registerReceiver(this, intentFilter);
	}

	public void unregister(Context context) {
		context.unregisterReceiver(this);
	}

	public void setOnHeadsetAvailableListener(OnHeadsetAvailableListener listener) {
		this.onHeadsetAvailableListener = listener;
	}
}
