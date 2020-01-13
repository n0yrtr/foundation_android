package jp.co.ocs.will.data.realm

import android.content.Context
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.realm.Realm
import io.realm.exceptions.RealmException

/**
 * @author naoya nishida
 */
abstract class SingleOnSubscribeRealm<T>: SingleOnSubscribe<T> {

    override fun subscribe(emitter: SingleEmitter<T>) {
        var realm: Realm? = null

        try {
            realm = Realm.getDefaultInstance()
        } catch (e: Exception) {
            emitter.onError(e)
        }

        if (realm == null) {
            emitter.onError(NullPointerException("Realm needs migration"))
            return
        }

        realm.beginTransaction()
        try {
            val obj = get(realm)
            realm.commitTransaction()
            obj?.let {
                emitter.onSuccess(it)
            }
            return
        } catch (e: RuntimeException ) {
            emitter.onError(RealmException("Error during transaction.", e))
            realm.cancelTransaction()
            return
        } catch (e: Error) {
            emitter.onError(e)
            try {
                realm.cancelTransaction()
            } catch (e: IllegalStateException) {
                emitter.onError(e)
            }
            return
        } finally {
            realm.close()
        }

    }

    abstract fun get(realm: Realm): T?

}