package me.darkeet.android.utils;

import android.net.Uri;
import java.net.Inet4Address;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

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
	public NetworkType checkNetworkType(Context context) {
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
		                return NetworkType.NETTYPE_MOBILE_2G; // 2G
		            case TelephonyManager.NETWORK_TYPE_UMTS:
		            case TelephonyManager.NETWORK_TYPE_EVDO_0:
		            case TelephonyManager.NETWORK_TYPE_EVDO_A:
		            case TelephonyManager.NETWORK_TYPE_HSDPA:
		            case TelephonyManager.NETWORK_TYPE_HSUPA:
		            case TelephonyManager.NETWORK_TYPE_HSPA:
		            case TelephonyManager.NETWORK_TYPE_EVDO_B:
		            case TelephonyManager.NETWORK_TYPE_EHRPD:
		            case TelephonyManager.NETWORK_TYPE_HSPAP:
		                return NetworkType.NETTYPE_MOBILE_3G; // 3G
		            case TelephonyManager.NETWORK_TYPE_LTE:
		                return NetworkType.NETTYPE_MOBILE_4G; // 4G
	            }
		    } else if (networkInfo.getType()==ConnectivityManager.TYPE_WIFI) {
		        return NetworkType.NETTYPE_WIFI;
		    }
		}
		return NetworkType.NETTYPE_UNKNOWN;
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
    
    /** 设置网络代理APN  */
    public static void setProxy(Context context, DefaultHttpClient httpClient) {
		try {
			// APN网络的API是隐藏的,获取手机的APN设置,需要通过ContentProvider来进行数据库查询
			// 取得全部的APN列表：content://telephony/carriers；
			// 取得当前设置的APN：content://telephony/carriers/preferapn；
			// 取得current=1的APN：content://telephony/carriers/current；
			ContentValues values = new ContentValues();
			Cursor cursor = context.getContentResolver().query(
					Uri.parse("content://telephony/carriers/preferapn"), null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				if (cursor.moveToFirst()) {
					int colCount = cursor.getColumnCount();
					for (int i = 0; i < colCount; i++) {
						values.put(cursor.getColumnName(i), cursor.getString(i));
					}
				}
				cursor.close();
			}
            // 中国移动WAP设置：  APN：CMWAP；代理：10.0.0.172；端口：80;   
            // 中国联通WAP设置：  APN：UNIWAP；代理：10.0.0.172；端口：80;   
            // 中国联通3GWAP设置  APN：3GWAP；代理：10.0.0.172；端口：80; 
			// 中国电信WAP设置：  APN: CTWAP；代理：10.0.0.200；端口：80;
			String proxyHost = (String) values.get("proxy");
			if (proxyHost!=null && proxyHost.length()>0 && !isWiFiConnected(context)) {
				int prot = Integer.parseInt(String.valueOf(values.get("port")));
				httpClient.getCredentialsProvider().setCredentials(
						new AuthScope(proxyHost, prot),
						new UsernamePasswordCredentials((String) values.get("user"), (String) values.get("password")));
				HttpHost proxy = new HttpHost(proxyHost, prot);
				httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public static enum NetworkType {
        NETTYPE_WIFI,
        NETTYPE_MOBILE_2G,
        NETTYPE_MOBILE_3G, 
        NETTYPE_MOBILE_4G, 
        NETTYPE_UNKNOWN,
    }
}
