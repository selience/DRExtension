package me.darkeet.android.rxjava;

import java.util.HashMap;
import java.util.Map;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Name: HttpRequest
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2016/3/17 18:21
 * Desc:
 */
public class SubscriptionManager {

    private Map<String, CompositeSubscription> mTasks = new HashMap<>();

    private static class SingletonHolder {
        private static final SubscriptionManager INSTANCE = new SubscriptionManager();
    }

    public static SubscriptionManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private SubscriptionManager() {
    }


    public void add(String taskName, Subscription subscription) {
        CompositeSubscription compositeSubscription = mTasks.get(taskName);
        if (compositeSubscription != null) {
            compositeSubscription.add(subscription);
        } else {
            mTasks.put(taskName, new CompositeSubscription(subscription));
        }
    }

    public void remove(String taskName, Subscription subscription) {
        CompositeSubscription compositeSubscription = mTasks.get(taskName);
        if (compositeSubscription != null) {
            compositeSubscription.remove(subscription);
        }
    }

    public void clear(String taskName) {
        CompositeSubscription compositeSubscription = mTasks.get(taskName);
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
    }

    public void unsubscribe(String taskName) {
        CompositeSubscription compositeSubscription = mTasks.get(taskName);
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }
}
