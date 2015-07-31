package org.iresearch.android.downloader;

/**
 * The status of The DownloadTaskTask
 *
 * @author selience <loveselience@gmail.com>
 * @version v1.0
 * @date Sep 29, 2013
 */
public class DownloadStatus {

    /**
     * the DownloadTask has successfully completed.
     */
    public static final int STATUS_PENDING = 0x00000001;

    /**
     * The DownloadTask is currently running.
     */
    public static final int STATUS_RUNNING = 0x00000002;

    /**
     * The DownloadTask is stopped.
     */
    public static final int STATUS_STOPPED = 0x00000003;

    /**
     * The DownloadTask has successfully completed.
     */
    public static final int STATUS_FINISHED= 0x00000004;

    /**
     * The DownloadTask has failed (and will not be retried).
     */
    public static final int STATUS_FAILED =  0x00000005;

    /**
     * The DownloadTask has been deleted.
     */
    public static final int STATUS_DELETED = 0x00000006;
}
