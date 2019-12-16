package jp.n0yrtr.foundation_android

import android.app.Application

interface AppLifecycleCallbacks {

  fun onCreate(application: Application)

  fun onTerminate(application: Application)
}