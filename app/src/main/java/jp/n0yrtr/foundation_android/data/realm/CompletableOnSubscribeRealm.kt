package jp.co.ocs.will.data.realm

import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.realm.Realm
import io.realm.exceptions.RealmException

/**
 * @author naoya nishida
 */
abstract class CompletableOnSubscribeRealm : CompletableOnSubscribe {

    override fun subscribe(emitter: CompletableEmitter) {
        val realm: Realm?

        try {
            realm = Realm.getDefaultInstance()
        } catch (e: Exception) {
            emitter.onError(e)
            return
        }

        if (realm == null) {
            emitter.onError(NullPointerException("Realm needs migration"))
            return
        }

        realm.beginTransaction()
        try {
            run(realm)
            realm.commitTransaction()
            emitter.onComplete()
            return
        } catch (e: RuntimeException) {
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

    abstract fun run(realm: Realm)

}