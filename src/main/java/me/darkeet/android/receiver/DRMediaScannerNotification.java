
package me.darkeet.android.receiver;

import java.io.File;
import android.net.Uri;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;

/**
 * Name: DRMediaScannerNotification
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: 扫面Sdcard
 */
public class DRMediaScannerNotification implements MediaScannerConnectionClient {
    private File mRootFile;
    private MediaScannerConnection mConnection;
    private OnMediaScannerStateListener mOnMediaScannerStateListener;

    public DRMediaScannerNotification(Context context, File rootFile) {
        this.mRootFile = rootFile;
        this.mConnection = new MediaScannerConnection(context, this);
    }

    public void start() {
        mConnection.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        mConnection.scanFile(mRootFile.getAbsolutePath(), null);
        if (mOnMediaScannerStateListener != null) {
            mOnMediaScannerStateListener.onMediaScannerConnected();
        }
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mConnection.disconnect();
        if (mOnMediaScannerStateListener != null) {
            mOnMediaScannerStateListener.onScanCompleted(path, uri);
        }
    }

    public interface OnMediaScannerStateListener {

        void onMediaScannerConnected();

        void onScanCompleted(String path, Uri uri);
    }
}
