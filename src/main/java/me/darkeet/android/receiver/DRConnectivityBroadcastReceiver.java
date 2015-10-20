/**
 * 
 */
package me.darkeet.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * Name: DRConnectivityBroadcastReceiver
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: 本地网络状态监听
 */
public class DRConnectivityBroadcastReceiver extends BroadcastReceiver {
    
    public interface OnNetworkAvailableListener {
        void onNetworkAvailable();
        
        void onNetworkUnavailable();
    }
    
    private boolean connection = false;
    private final ConnectivityManager connectivityManager; 
    private OnNetworkAvailableListener onNetworkAvailableListener;
    
    public DRConnectivityBroadcastReceiver(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        checkConnectionOnDemand();
    }
    
	private void checkConnectionOnDemand() {
        final NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || info.getState() != State.CONNECTED) {
        	 connection = false;
             if (onNetworkAvailableListener != null) { 
            	 onNetworkAvailableListener.onNetworkUnavailable();
             }
        } else {
        	connection = true;
            if (onNetworkAvailableListener != null) { 
            	onNetworkAvailableListener.onNetworkAvailable();
            }
        }
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        if (connection && intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
            connection = false;
            if (onNetworkAvailableListener != null) {
                onNetworkAvailableListener.onNetworkUnavailable();
            }
        }
        else if (!connection && !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
            connection = true;
            if (onNetworkAvailableListener != null) {
                onNetworkAvailableListener.onNetworkAvailable();
            }
        }
    }
    
    public boolean hasConnection() {
        return connection;
    }
    
    public void setOnNetworkAvailableListener(OnNetworkAvailableListener listener) {
        this.onNetworkAvailableListener = listener;
    }

    public void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this, filter);
    }
    
    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }
}
