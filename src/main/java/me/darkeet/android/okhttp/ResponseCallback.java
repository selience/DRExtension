
package me.darkeet.android.okhttp;

public abstract class ResponseCallback<Result> {

    public void onPreExecute() {
    }

    public abstract Result doInBackground(BetterHttpResponse response);


    public abstract void onPostExecute(Result result);


    public void onProgressUpdate(long transferred, long totalSize) {
    }

    public void onError(Throwable error) {
    }

    public void onFinish() {
    }

    public void onCancel() {
    }
}
