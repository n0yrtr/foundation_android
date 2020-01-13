package jp.co.ocs.will.data.realm

import android.content.Context
import io.reactivex.Single
import io.realm.Realm

/**
 * @author naoya nishida
 */
sealed class RealmSingleObservable {
    companion object {
        fun <T> create(function: (Realm) -> T): Single<T> {
            return Single.create (object: SingleOnSubscribeRealm<T>() {
                override fun get(realm: Realm): T? {
                    return function(realm)
                }
            })

        }
    }
}