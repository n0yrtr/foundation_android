package jp.n0yrtr.foundation_android.data.sharedpreferences

import android.content.SharedPreferences
import jp.co.ocs.will.data.sharedpreferences.SharedPreferenceKey

/**
 * Preferenceストレージ
 */
abstract class Storage<T>(private val prefs: SharedPreferences) {
    /**
     * 取得
     * @param defValue デフォルト値
     */
    abstract fun get(defValue: T): T

    /**
     * 保存
     */
    abstract fun save(value: T)

    /**
     * クリア
     */
    fun clear() {
        val editor = prefs.edit()
        editor.remove(getKey().name)
        editor.apply()
    }
    /** SharedPreferenceKeyの取得 */
    protected abstract fun getKey() : SharedPreferenceKey
}

/**
 * StringのPreferenceストレージ
 */
abstract class StringStorage(private val prefs: SharedPreferences): Storage<String>(prefs) {

    override fun get(defValue: String): String {
        return prefs.getString(getKey().name, defValue)
    }

    /**
     * 取得
     */
    fun get(): String? {
        return prefs.getString(getKey().name, null)
    }

    override fun save(value: String) {
        val editor = prefs.edit()
        editor.putString(getKey().name, value)
        editor.apply()
    }
}

/**
 * StringSetのPreferenceストレージ
 */
abstract class StringSetStorage(private val prefs: SharedPreferences): Storage<Set<String>>(prefs) {

    override fun get(defValue: Set<String>): Set<String> {
        return prefs.getStringSet(getKey().name, defValue)
    }

    override fun save(value: Set<String>) {
        val editor = prefs.edit()
        editor.putStringSet(getKey().name, value)
        editor.apply()
    }
}

/**
 * IntのPreferenceストレージ
 */
abstract class IntStorage(private val prefs: SharedPreferences): Storage<Int>(prefs) {

    override fun get(defValue: Int): Int {
        return prefs.getInt(getKey().name, defValue)
    }

    override fun save(value: Int) {
        val editor = prefs.edit()
        editor.putInt(getKey().name, value)
        editor.apply()
    }
}

/**
 * LongのPreferenceストレージ
 */
abstract class LongStorage(private val prefs: SharedPreferences): Storage<Long>(prefs) {

    override fun get(defValue: Long): Long {
        return prefs.getLong(getKey().name, defValue)
    }

    override fun save(value: Long) {
        val editor = prefs.edit()
        editor.putLong(getKey().name, value)
        editor.apply()
    }
}

/**
 * BooleanのPreferenceストレージ
 */
abstract class BooleanStorage(private val prefs: SharedPreferences): Storage<Boolean>(prefs) {

    override fun get(defValue: Boolean): Boolean {
        return prefs.getBoolean(getKey().name, defValue)
    }

    override fun save(value: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(getKey().name, value)
        editor.apply()
    }
}