/**
 * 
 */
package me.darkeet.android.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/**
 * Name: DRMediaCardStateBroadcastReceiver
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: 监听SDCard装载状态
 *
 * <intent-filter>
 *	 <action android:name="android.intent.action.MEDIA_MOUNTED" />
 *	 <action android:name="android.intent.action.MEDIA_EJECT" />
 *	 <action android:name="android.intent.action.MEDIA_UNMOUNTED" />
 *	 <action android:name="android.intent.action.MEDIA_SHARED" />
 *	 <action android:name="android.intent.action.MEDIA_SCANNER_STARTED" />
 *	 <action android:name="android.intent.action.MEDIA_SCANNER_FINISHED" />
 *	 <action android:name="android.intent.action.MEDIA_REMOVED" />
 *	 <action android:name="android.intent.action.MEDIA_BAD_REMOVAL" />
 *	 <data android:scheme="file" />
 * </intent-filter>
 */
public class DRMediaCardStateBroadcastReceiver extends BroadcastReceiver {

	public interface OnMediaCardAvailableListener {
        void onMediaCardStateMounted();
        
        void onMediaCardStateUnMounted();
    }

	private OnMediaCardAvailableListener onMediaCardAvailableListener;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_MEDIA_MOUNTED.equals(intent.getAction())) {
		    if (onMediaCardAvailableListener!=null) {
		    	onMediaCardAvailableListener.onMediaCardStateMounted();
		    }
		} else {
		    if (onMediaCardAvailableListener!=null) {
		    	onMediaCardAvailableListener.onMediaCardStateUnMounted();
		    }
		}
	}
	
	public void register(Context context) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
		intentFilter.addAction(Intent.ACTION_MEDIA_SHARED);
		intentFilter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
		intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);  
		intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED); 
		intentFilter.addDataScheme("file");
		context.registerReceiver(this, intentFilter);
	}

	public void unregister(Context context) {
		context.unregisterReceiver(this);
	}
	
	public void setOnMediaCardAvailableListener(OnMediaCardAvailableListener listener) {
		this.onMediaCardAvailableListener = listener;
	}
}
