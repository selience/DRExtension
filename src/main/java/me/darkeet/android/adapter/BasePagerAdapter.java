
package me.darkeet.android.adapter;

import java.util.List;
import android.os.Bundle;
import java.util.ArrayList;
import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;
import me.darkeet.android.log.DebugLog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Name: BasePagerAdapter
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: 封装常用adpater操作
 */
public abstract class BasePagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private final List<TabInfo> mFragments;
    private final SparseArray<Fragment> registerFragments;

    public BasePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        this.mFragments = new ArrayList<TabInfo>();
        this.registerFragments = new SparseArray<Fragment>();
    }

    @Override
    public Fragment getItem(int position) {
        TabInfo info = mFragments.get(position);
        Fragment fragment=Fragment.instantiate(mContext, info.clss.getName(), info.args);
        registerFragments.put(position, fragment);
        DebugLog.d("getItem >> " + position + "-" + fragment);
        return fragment;
    }

    @Override
    public int getCount() {
    	return mFragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        DebugLog.d("instantiateItem >> " + position + "-" + fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registerFragments.remove(position);
        super.destroyItem(container, position, object);
        DebugLog.d("destroyItem >> " + position + "-" + object);
    }

    public final Fragment getFragment(int position) {
        return registerFragments.get(position);
    }
    
    public final void addFragment(Class<? extends Fragment> clss, Bundle args) {
    	mFragments.add(new TabInfo(clss, args));
    	notifyDataSetChanged();
    }

    static final class TabInfo {
        private final Class<?> clss;
        private final Bundle args;

        TabInfo(Class<?> _class, Bundle _args) {
            clss = _class;
            args = _args;
        }
    }
}
