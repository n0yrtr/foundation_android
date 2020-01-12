package jp.n0yrtr.foundation_android

import android.app.Application
import timber.log.Timber

class MyAppLifecycleCallbacks : AppLifecycleCallbacks {

  override fun onCreate(application: Application) {
      Timber.d("app create")
  }

  override fun onTerminate(application: Application) {
      Timber.d("app terminate")
  }

}