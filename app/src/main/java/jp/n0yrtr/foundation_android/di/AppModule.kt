package jp.n0yrtr.foundation_android.di

import android.app.Application
import dagger.Module
import dagger.Provides
import jp.n0yrtr.foundation_android.AppLifecycleCallbacks
import jp.n0yrtr.foundation_android.MyAppLifecycleCallbacks
import jp.n0yrtr.foundation_android.MyLifecycleHandler
import javax.inject.Singleton

@Module(includes = [])
class AppModule {
    @Singleton
    @Provides
    fun provideActivityLifecycleCallbacks(): Application.ActivityLifecycleCallbacks =  MyLifecycleHandler()

    @Singleton
    @Provides
    fun provideAppLifecycleCallbacks(): AppLifecycleCallbacks =  MyAppLifecycleCallbacks()
}