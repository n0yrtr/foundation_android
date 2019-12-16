package jp.n0yrtr.foundation_android

import android.app.Activity
import android.util.Log
import android.webkit.WebView
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import io.realm.Realm
import io.realm.RealmConfiguration
import jp.n0yrtr.foundation_android.di.applyAutoInjector
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class App : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var appLifecycleCallbacks: AppLifecycleCallbacks
    @Inject
    lateinit var activityLifecycleCallbacks: ActivityLifecycleCallbacks

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    override fun onCreate() {
        super.onCreate()

        applyAutoInjector()
        setupRealm()
        appLifecycleCallbacks.onCreate(this)
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)

        // https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling
        RxJavaPlugins.setErrorHandler {
            var exception = it
            if (exception is UndeliverableException) {
                exception = it.cause
            }
            Log.d(this.javaClass.simpleName, "%s".format(exception.javaClass.simpleName), exception)
            if (exception is UnknownHostException) {
                Log.d(this.javaClass.simpleName, "UnknownHostException")
                return@setErrorHandler
            }
            if ((exception is IOException)) {
                Log.d(this.javaClass.simpleName, "IOException")
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (exception is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if ((exception is NullPointerException) || (exception is IllegalArgumentException)) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), exception)
                return@setErrorHandler
            }
            if (exception is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), exception)
                return@setErrorHandler
            }
            Log.w("Undeliverable exception", exception.cause)
        }

        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    override fun onTerminate() {
        appLifecycleCallbacks.onTerminate(this)
        super.onTerminate()
    }

    /**
     * Realmのセットアップ
     */
    private fun setupRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .schemaVersion(1) // Must be bumped when the schema changes
            .migration(MyRealmMigration()) // Migration to run instead of throwing an exception
            .build()
        Realm.setDefaultConfiguration(config)

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build());
    }
}