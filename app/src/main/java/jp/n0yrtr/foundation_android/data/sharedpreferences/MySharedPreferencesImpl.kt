package jp.co.ocs.will.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import io.reactivex.Completable
import jp.n0yrtr.foundation_android.data.sharedpreferences.*


/**
 * @author naoya nishida
 */

class MySharedPreferencesImpl(context: Context) : MySharedPreferences {
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
}