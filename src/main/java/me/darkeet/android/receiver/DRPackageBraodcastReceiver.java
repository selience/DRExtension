package me.darkeet.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Name: DRPackageBraodcastReceiver
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/13 16:41
 * Desc: 应用安装以及卸载监听
 */
public class DRPackageBraodcastReceiver extends BroadcastReceiver {

    public interface OnPackageStateChangeListener {

        void onPackageStateChanged(Intent intent);
    }

    private OnPackageStateChangeListener onPackageStateChangeListener;

    public void setOnPackageStateChangeListener(OnPackageStateChangeListener listener) {
        this.onPackageStateChangeListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (onPackageStateChangeListener != null) {
            onPackageStateChangeListener.onPackageStateChanged(intent);
        }
    }

    public void register(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        context.registerReceiver(this, intentFilter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }
}
