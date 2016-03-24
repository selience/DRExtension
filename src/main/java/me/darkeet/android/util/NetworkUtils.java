package me.darkeet.android.util;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import java.net.Inet4Address;
import android.support.annotation.RequiresPermission;

/**
 * @className NetworkUtils
 * @create 2014年4月16日 上午10:22:34
 * @author Jacky.Lee
 * @description TODO 封装常用的网络操作 
 */
public final class NetworkUtils {

    private NetworkUtils() {
        throw new AssertionError("You are trying to create an instance for this utility class!");
    }
    
    /**
     * 检查当前网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

    public static boolean isWiFiConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return (networkInfo != null && networkInfo.isConnected());
   }
   
   public static boolean isMobileConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (networkInfo != null && networkInfo.isConnected());
   }
   
    /**
     * 获取当前设备IP地址
     */
	@RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String ipToString(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return ipIntToString(wifiInfo.getIpAddress());
	}

	// see http://androidsnippets.com/obtain-ip-address-of-current-device
	public static String ipIntToString(int ip) {
		try {
			byte[] bytes = new byte[4];
			bytes[0] = (byte) (0xff & ip);
			bytes[1] = (byte) ((0xff00 & ip) >> 8);
			bytes[2] = (byte) ((0xff0000 & ip) >> 16);
			bytes[3] = (byte) ((0xff000000 & ip) >> 24);
			return Inet4Address.getByAddress(bytes).getHostAddress();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前连接网络类型
	 */
	public static String checkNetworkType(Context context) {
		ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo!=null && networkInfo.isConnected()) {
		    if (networkInfo.getType()==ConnectivityManager.TYPE_MOBILE) {
		        switch (networkInfo.getSubtype()) {
		            case TelephonyManager.NETWORK_TYPE_GPRS:
		            case TelephonyManager.NETWORK_TYPE_EDGE:
		            case TelephonyManager.NETWORK_TYPE_CDMA:
		            case TelephonyManager.NETWORK_TYPE_1xRTT:
		            case TelephonyManager.NETWORK_TYPE_IDEN:
		                return "2G"; // 2G
		            case TelephonyManager.NETWORK_TYPE_UMTS:
		            case TelephonyManager.NETWORK_TYPE_EVDO_0:
		            case TelephonyManager.NETWORK_TYPE_EVDO_A:
		            case TelephonyManager.NETWORK_TYPE_HSDPA:
		            case TelephonyManager.NETWORK_TYPE_HSUPA:
		            case TelephonyManager.NETWORK_TYPE_HSPA:
		            case TelephonyManager.NETWORK_TYPE_EVDO_B:
		            case TelephonyManager.NETWORK_TYPE_EHRPD:
		            case TelephonyManager.NETWORK_TYPE_HSPAP:
		                return "3G"; // 3G
		            case TelephonyManager.NETWORK_TYPE_LTE:
		                return "4G"; // 4G
	            }
		    } else if (networkInfo.getType()==ConnectivityManager.TYPE_WIFI) {
		        return "WiFi";
		    }
		}
		return "Known";
	}
	
    /**
	 * 获取设备服务商信息  
	 * 需要加入权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>  
	 */
	public static String getProvidersName(Context ctx) {
		String providersName = "unknown";
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		// 返回唯一的用户ID;就是这张卡的编号 
		String IMSI = tm.getSubscriberId();
		if (IMSI!=null && IMSI.length()>0) {
			// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
				providersName = "中国移动";
			} else if (IMSI.startsWith("46001")) {
				providersName = "中国联通";
			} else if (IMSI.startsWith("46003")) {
				providersName = "中国电信";
			}
		}
		return providersName;
	}
}
