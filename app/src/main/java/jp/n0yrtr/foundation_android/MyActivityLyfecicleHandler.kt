package jp.n0yrtr.foundation_android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class MyLifecycleHandler @Inject constructor() : Application.ActivityLifecycleCallbacks {

    private var createdActivity: Int = 0
    private var resumedActivity: Int = 0
    private var pausedActivity: Int = 0
    private var startedActivity: Int = 0
    private var stoppedActivity: Int = 0
    private var destroyedActivity: Int = 0
    private var isBackgroundNow: Boolean = true

    /** アプリがforegroundになったときのアクティビティ **/
    private var activityWhenForeGroundApp : AppCompatActivity? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ++createdActivity
        if (createdActivity == destroyedActivity + 1) {
            // 起動時の処理
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        ++destroyedActivity
    }

    override fun onActivityResumed(activity: Activity) {
        ++resumedActivity
        if (isBackgroundNow) {
            // 復帰時の処理
            isBackgroundNow = false
            activityWhenForeGroundApp = activity as AppCompatActivity

        }
    }

    override fun onActivityPaused(activity: Activity) {
        ++pausedActivity
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        ++startedActivity
    }

    override fun onActivityStopped(activity: Activity) {
        ++stoppedActivity
        if (activityWhenForeGroundApp == activity) {
            activityWhenForeGroundApp = null
        }
        if (stoppedActivity == startedActivity) {
            // バックグラウンドへ移行したタイミング
            isBackgroundNow = true
        }
    }
}