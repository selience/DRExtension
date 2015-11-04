
package me.darkeet.android.task;

import android.os.Build;
import java.lang.reflect.Field;
import java.util.concurrent.Executor;
import android.annotation.TargetApi;

public abstract class AbstractAsyncTask<Params, Progress, Result> extends
        android.os.AsyncTask<Params, Progress, Result> {
    protected TaskListener<Progress, Result> listener = null;

    public AbstractAsyncTask() {
        super();
    }

    public AbstractAsyncTask(TaskListener<Progress, Result> listener) {
        super();
        this.listener = listener;
    }

    public TaskListener<Progress, Result> getListener() {
        return listener;
    }

    public void setListener(TaskListener<Progress, Result> listener) {
        this.listener = listener;
    }

    @Override
    protected abstract Result doInBackground(Params... params);

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (listener != null) {
            listener.onCancelled();
            listener.onFinish();
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onCancelled(Result result) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            super.onCancelled(result);
        } else {
            super.onCancelled();
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (listener != null) {
            if (result != null) {
                listener.onSuccess(result);
            }

            listener.onFinish();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null) {
            listener.onStart();
        }
    }

    @Override
    protected void onProgressUpdate(Progress... values) {
        super.onProgressUpdate(values);
        if (listener != null) {
            listener.onProgressUpdate(values);
        }
    }

    /**
     * set the default Executor
     * 
     * @param executor
     */
    public static void setDefaultExecutor(Executor executor) {
        Class<?> c = null;
        Field field = null;
        try {
            c = Class.forName("android.os.AsyncTask");
            field = c.getDeclaredField("sDefaultExecutor");
            field.setAccessible(true);
            field.set(null, executor);
            field.setAccessible(false);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the default Executor
     * 
     * @return the default Executor
     */
    public static Executor getDefaultExecutor() {
        Executor exec = null;

        Class<?> c = null;
        Field field = null;
        try {
            c = Class.forName("android.os.AsyncTask");
            field = c.getDeclaredField("sDefaultExecutor");
            field.setAccessible(true);
            exec = (Executor)field.get(null);
            field.setAccessible(false);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return exec;
    }
}
