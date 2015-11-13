package me.darkeet.android.view.empty;

import android.view.View;
import android.view.ViewGroup;

/**
 * Name: EmptyViewListener
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/11 17:40
 * Desc:
 */
public interface EmptyViewListener {

    View generateEmptyView(ViewGroup parent);

    View generateErrorView(ViewGroup parent);

    View generateLoadingView(ViewGroup parent);
}
