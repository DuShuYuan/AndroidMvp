package com.dsy.mvp.component;

import android.app.Activity;

import java.util.HashSet;
import java.util.Set;

public class ActivityManager {
    private Set<Activity> allActivities;
    private static ActivityManager mInstance;

    public synchronized static ActivityManager getInstance() {
        if (mInstance == null) {
            mInstance = new ActivityManager();
        }
        return mInstance;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void finishAll() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
    }
}
