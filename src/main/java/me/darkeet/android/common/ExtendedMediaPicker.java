package me.darkeet.android.common;

import java.io.File;
import android.net.Uri;
import android.os.Build;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import me.darkeet.android.log.DebugLog;
import me.darkeet.android.util.MediaUtils;
import me.darkeet.android.util.Toaster;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.ActivityNotFoundException;

public class ExtendedMediaPicker {
	private static final String TAG = ExtendedMediaPicker.class.getSimpleName();
	
	private static final int REQUEST_CODE_CROP_PHOTO = 2000;
	private static final int REQUEST_CODE_PICK_IMAGE = 2001;
    private static final int REQUEST_CODE_TAKE_PHOTO = 2002;
    
    private int mCropWidth;
    private int mCropHeight;
    
    private File mTempFile;
    private File mPhotoFile;
    
    private Activity mActivity;
    private OnMediaPickerListener mMediaPickerListener;
    
    public ExtendedMediaPicker(Activity activity) {
    	this.mActivity = activity;
    	this.mTempFile = createTempFile();
    }
    
    /**
     * 显示选择图片方式的提示信息框
     */
	public void showPickerView() {
    	AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
    	builder.setItems(new String[]{"使用相机拍照", "从手机相册选择"}, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					openSystemCamera();
				} else {
					openSystemPickImage();
				}
				dialog.dismiss();
			}
		});
    	builder.create().show();
    }
	
	/**
	 * 图片回调处理方法，确保在onActivityResult中调用
	 * 
	 * @param requestCode	请求状态码
	 * @param resultCode	响应结果码
	 * @param data			回调数据
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK){
			DebugLog.e(TAG, "requestCode = " + requestCode);
			DebugLog.e(TAG, "resultCode = " + resultCode);
			DebugLog.e(TAG, "data = " + data);
			return;
		} 

		switch (requestCode) {
			case REQUEST_CODE_PICK_IMAGE:
				File destFile=new File(MediaUtils.getPath(mActivity, data.getData()));
				DebugLog.v(TAG, "CHOOSE_PICTURE: uri = " + destFile.getPath());
				cropImageUri(destFile, mCropWidth, mCropHeight, REQUEST_CODE_CROP_PHOTO);
				break;
			case REQUEST_CODE_TAKE_PHOTO:
				DebugLog.v(TAG, "TAKE_PICTURE: uri = " + mPhotoFile.getPath());
				cropImageUri(mPhotoFile, mCropWidth, mCropHeight, REQUEST_CODE_CROP_PHOTO);
				break;
			case REQUEST_CODE_CROP_PHOTO:
				String imagePath=mTempFile.getAbsolutePath();
				DebugLog.v(TAG, "CROP_PICTURE: uri = " + imagePath);
				if (mMediaPickerListener != null){
					mMediaPickerListener.onSelectedMediaChanged(imagePath);
				}
				break;
		}
	}
	
	/*
	 * 调用系统相册功能；在4.3或以下可以直接用ACTION_GET_CONTENT的；在4.4或以上，官方建议用ACTION_OPEN_DOCUMENT；
	 * 注意两个版本返回的路径是完全不一样的，4.3返回的是带文件的路径；而4.4返回的却是content://com.android.providers.media.documents/document/image:3951
	 * 这样的，没有路径只有图片编号的uri.这样就无法根据图片路径来进行裁剪操作了.
	 */
	@SuppressLint("InlinedApi")
	private void openSystemPickImage() {
		Intent photoPickerIntent=null;
		if (Build.VERSION.SDK_INT < 19) {
			photoPickerIntent=new Intent(Intent.ACTION_GET_CONTENT);
		} else {
			photoPickerIntent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
		}
		photoPickerIntent.setType("image/*");
		photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
		mActivity.startActivityForResult(photoPickerIntent, REQUEST_CODE_PICK_IMAGE);
	}
	
	/*
	 * 启动系统拍照功能
	 */
	private void openSystemCamera() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			// 设置拍照存储目录
			mPhotoFile=new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");	
			// 启动拍照功能
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
			mActivity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
		} else {
			Toaster.show(mActivity, "Before you take photos please insert SD card");
		}
    }
	
	/*
	 * 创建临时截取图像文件
	 */
	private File createTempFile() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
		} else {
			return new File(mActivity.getFilesDir(), System.currentTimeMillis()+".jpg");
		}
	}
	
	/**
	 * 截取图像部分区域 
	 * @param dest
	 * @param outputX
	 * @param outputY
	 * @return
	 */
	private void cropImageUri(File dest, int outputX, int outputY, int requestCode){
		try {
			//android1.6以后只能传图库中图片
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(Uri.fromFile(dest), "image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", outputX);
			intent.putExtra("outputY", outputY);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", false);
			intent.putExtra("noFaceDetection", true);
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile));
			mActivity.startActivityForResult(intent, requestCode);
		} catch (ActivityNotFoundException ex) {
			Toaster.show(mActivity, "Can not find image crop app.");
		}
	}
	
	/**
	 * 设置回调结果监听事件
	 * @param listener
	 */
	public void setOnMediaPickerListener(OnMediaPickerListener listener) {
		this.mMediaPickerListener = listener;
	}
	
	
	public interface OnMediaPickerListener {
		
		void onSelectedMediaChanged(String mediaUri);
	}
}
