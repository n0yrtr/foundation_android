package jp.co.ocs.will.data.realm

import android.content.Context
import io.reactivex.Completable
import io.realm.Realm

/**
 * @author naoya nishida
 */
sealed class RealmCompletableObservable {
    companion object {
        fun create(consumer: (Realm) -> Unit): Completable {
            return Completable.create (object: CompletableOnSubscribeRealm() {
                override fun run(realm: Realm) {
                    consumer(realm)
                }
            })
        }
    }
}