package me.darkeet.android.common;

import android.net.Uri;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import me.darkeet.android.log.DebugLog;

/**
 * Name: ImageCrop
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/5 15:48
 * Desc: 裁剪图片处理类
 */
public class Crop {

    private static final int DEFAULT_MAX_WIDTH = 120;

    private static final int DEFAULT_MAX_HEIGHT = 120;

    private static final String INTENT_ACTION_CROP = "com.android.camera.action.CROP";


    private Intent cropIntent;


    public static void start(Uri source, Uri output, Activity activity, int requestCode) {
        new Crop(source).output(output).asSquare().withScale(true)
                .withOutSize(DEFAULT_MAX_WIDTH, DEFAULT_MAX_HEIGHT)
                .returnData(false).start(activity, requestCode);
    }

    /**
     * 用图片路径构造裁剪对象
     */
    public Crop(Uri source) {
        cropIntent = new Intent(INTENT_ACTION_CROP);
        cropIntent.setDataAndType(source, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("noFaceDetection", true);
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    }

    /**
     * 设置裁剪图片保存路径
     */
    public Crop output(Uri output) {
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        return this;
    }

    /**
     * 设置裁剪图片区域比例
     *
     * @param x
     * @param y
     */
    public Crop withAspect(int x, int y) {
        cropIntent.putExtra(Extra.ASPECT_X, x);
        cropIntent.putExtra(Extra.ASPECT_Y, y);
        return this;
    }

    /**
     * 裁剪图片保持1:1比例
     */
    public Crop asSquare() {
        cropIntent.putExtra(Extra.ASPECT_X, 1);
        cropIntent.putExtra(Extra.ASPECT_Y, 1);
        return this;
    }

    /**
     * 设置裁剪图片区域大小
     *
     * @param width  裁剪宽度
     * @param height 裁剪高度
     */
    public Crop withOutSize(int width, int height) {
        cropIntent.putExtra(Extra.OUTPUT_X, width);
        cropIntent.putExtra(Extra.OUTPUT_Y, height);
        return this;
    }

    /**
     * 裁剪区域是否缩放
     *
     * @param scale
     */
    public Crop withScale(boolean scale) {
        cropIntent.putExtra(Extra.SCALE, scale);
        return this;
    }

    /**
     * 是否返回裁剪数据，true可以从intent.getData()取得图片数据；参见output()方法；
     */
    public Crop returnData(boolean returnData) {
        cropIntent.putExtra(Extra.RETURN_DATA, returnData);
        return this;
    }

    /**
     * 发送系统intent，开始裁剪图片
     *
     * @param fragment    fragment对象
     * @param requestCode 请求码
     */
    public void start(Fragment fragment, int requestCode) {
        try {
            DebugLog.d("Crop", cropIntent.getExtras().toString());
            fragment.startActivityForResult(cropIntent, requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送系统intent，开始裁剪图片
     *
     * @param activity    上下文对象activity
     * @param requestCode 请求码
     */
    public void start(Activity activity, int requestCode) {
        try {
            activity.startActivityForResult(cropIntent, requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    static interface Extra {
        String SCALE = "scale";
        String ASPECT_X = "aspect_x";
        String ASPECT_Y = "aspect_y";
        String OUTPUT_X = "outputX";
        String OUTPUT_Y = "outputY";
        String RETURN_DATA = "return-data";
    }
}
