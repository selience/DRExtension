package me.darkeet.android.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.annotation.TargetApi;
import android.support.annotation.RequiresPermission;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author lilong@qiyi.com
 * @className IntentUtils
 * @create 2014年4月16日 上午11:44:14
 * @description 封装常用的Intent操作
 */
public class IntentUtils {

    public static final String MIME_TYPE_TEXT = "text/*";
    public static final String MIME_TYPE_IMAGE = "image/*";
    public static final String MIME_TYPE_EMAIL = "message/rfc822";

    private IntentUtils() {
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return !list.isEmpty();
    }

    public static Intent newEmailIntent(String subject, String body, Uri attachment, String... addresses) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (addresses != null) intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        if (body != null) intent.putExtra(Intent.EXTRA_TEXT, body);
        if (subject != null) intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (attachment != null) intent.putExtra(Intent.EXTRA_STREAM, attachment);
        intent.setType(MIME_TYPE_EMAIL);

        return intent;
    }

    public static Intent newShareIntent(Context context, String content, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND); //启动分享发送的属性  
        if (uri != null) {
            intent.putExtra(Intent.EXTRA_STREAM, uri); //分享流媒体
            intent.setType(MIME_TYPE_IMAGE);
            intent.putExtra("sms_body", content);
        } else {
            intent.setType(MIME_TYPE_TEXT);
        }
        intent.putExtra(Intent.EXTRA_TEXT, content);

        return intent;
    }

    public static Intent newOpenWebBrowserIntent(String urlString) {
        try {
            URL url = new URL(urlString);
            return new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Intent newSmsIntent(String phoneNumber, String body) {
        final Intent intent;
        if (phoneNumber == null || phoneNumber.trim().length() <= 0) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        }
        intent.putExtra("sms_body", body);
        return intent;
    }

    public static Intent newDialNumberIntent(String phoneNumber) {
        final Intent intent;
        if (phoneNumber == null || phoneNumber.trim().length() <= 0) {
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
        } else {
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.replace(" ", "")));
        }
        return intent;
    }

    public static Intent newCallNumberIntent(String phoneNumber) {
        final Intent intent;
        if (phoneNumber == null || phoneNumber.trim().length() <= 0) {
            intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"));
        } else {
            intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber.replace(" ", "")));
        }
        return intent;
    }

    /**
     * 启动WiFi网络设置Intent
     */
    public static Intent newWiFiSettingIntent() {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        return intent;
    }

    /**
     * 启动GPS定位设置Intent
     */
    public static Intent newGpsSettingsIntent() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        return intent;
    }

    /**
     * 截取图像部分区域 ;魅族的机器没有返回data字段，但是返回了filePath
     *
     * @param uri
     * @param outputX
     * @param outputY
     * @return
     */
    public static Intent newCropImageUri(Uri uri, int outputX, int outputY) {
        //android1.6以后只能传图库中图片
        //http://www.linuxidc.com/Linux/2012-11/73940.htm
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true"); //发送裁剪信号
        intent.putExtra("aspectX", 1);    //X方向上的比例
        intent.putExtra("aspectY", 1);    //Y方向上的比例
        intent.putExtra("outputX", outputX); //裁剪区的宽
        intent.putExtra("outputY", outputY); //裁剪区的高
        intent.putExtra("scale", true);//是否保留比例
        //拍摄的照片像素较大，建议直接保存URI，否则内存溢出，较小图片可以直接返回Bitmap
        /*Bundle extras = data.getExtras();
        if (extras != null) {
			Bitmap bitmap = extras.getParcelable("data");
	    }*/
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("circleCrop", false); // 圆形裁剪区域
        intent.putExtra("return-data", false);  //是否返回数据
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); // 图片格式
        intent.putExtra("noFaceDetection", true); //关闭人脸检测
        return intent;
    }

    /**
     * 创建系统应用管理Intent
     */
    public static Intent newManageApplicationIntent() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");

        return intent;
    }

    /**
     * 创建应用安装Intent
     */
    public static Intent newInstallApkIntent(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkFile.getPath()),
                "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 创建卸载应用Intent
     */
    public static Intent newUnInstallApkIntent(String packageName) {
        return new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
    }

    /**
     * 创建文件扫描Intent
     */
    public static Intent newScanFileIntent(File filePath) {
        return new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(filePath));
    }


    /**
     * 启动系统拍照功能，6.0系统以上注意处理运行时权限
     */
    @RequiresPermission(value = Manifest.permission.CAMERA)
    public static Intent newTakePhoto(File file) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        return intent;
    }

    /*
    * 调用系统相册功能；在4.3或以下可以直接用ACTION_GET_CONTENT的；在4.4或以上，官方建议用ACTION_OPEN_DOCUMENT；
    * 注意两个版本返回的路径是完全不一样的，4.3返回的是带文件的路径；而4.4返回的却是content://com.android.providers.media.documents/document/image:3951
    * 这样的，没有路径只有图片编号的uri.这样就无法根据图片路径来进行裁剪操作了.
    */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static Intent newPickImage() {
        Intent photoPickerIntent;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        }
        photoPickerIntent.setType("image/*");
        photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
        return photoPickerIntent;
    }
}
