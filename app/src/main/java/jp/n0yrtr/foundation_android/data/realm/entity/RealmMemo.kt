package jp.n0yrtr.foundation_android.data.realm.entity

import io.realm.RealmObject

/**
 *  メモ RealmObject
 */
open class RealmMemo(
    var memo: String = ""
) : RealmObject()