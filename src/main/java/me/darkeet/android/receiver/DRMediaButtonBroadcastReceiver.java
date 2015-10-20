package me.darkeet.android.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.view.KeyEvent;

/**
 * Name: DRMediaButtonBroadcastReceiver
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: 广播接收者监测媒体播放按键； 有线和无线耳机上都有媒体播放按键，如播放、暂停、停止、快进和快退，
 *              当用户操作这些键时，系统会广播一个含有ACTION_MEDIA_BUTTON动作的intent。
 *
 * <intent-filter>
 * 		<action android:name="android.intent.action.MEDIA_BUTTON" />
 * </intent-filter>
 */
public class DRMediaButtonBroadcastReceiver extends BroadcastReceiver {

	public interface OnMediaButtonStateListener {

        void onMediaButtonStateChanged(Intent intent, KeyEvent event);
    }
	
	private OnMediaButtonStateListener onMediaButtonStateListener;
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
			KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
			if (onMediaButtonStateListener != null) {
				onMediaButtonStateListener.onMediaButtonStateChanged(intent, event);
				this.abortBroadcast();
			}
		}
	}

	/** Start listening for button presses */
	public void register(AudioManager am, String packageName) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_MEDIA_BUTTON);
		ComponentName component = new ComponentName(packageName, DRMediaButtonBroadcastReceiver.class.getName());
		am.registerMediaButtonEventReceiver(component);
	}

	/** Stop listening for button presses */
	public void unregister(AudioManager am, String packageName) {
		ComponentName component = new ComponentName(packageName, DRMediaButtonBroadcastReceiver.class.getName());
		am.unregisterMediaButtonEventReceiver(component);
	}
	
	public void setOnMediaButtonStateListener(OnMediaButtonStateListener listener) {
		this.onMediaButtonStateListener = listener;
	}
}
