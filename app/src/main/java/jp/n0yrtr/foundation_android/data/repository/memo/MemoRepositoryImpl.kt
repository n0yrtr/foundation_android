package jp.n0yrtr.foundation_android.data.repository.memo

import io.reactivex.Completable
import io.reactivex.Single
import jp.co.ocs.will.data.realm.RealmCompletableObservable
import jp.co.ocs.will.data.realm.RealmSingleObservable
import jp.n0yrtr.foundation_android.data.realm.entity.RealmMemo
import jp.n0yrtr.foundation_android.domain.model.Memo
import jp.n0yrtr.foundation_android.domain.repository.MemoRepository
import javax.inject.Inject


class MemoRepositoryImpl @Inject constructor(
) : MemoRepository {

    override fun fetchMemo(): Single<Memo> {
        return RealmSingleObservable.create { realm ->
            realm.where(RealmMemo::class.java).findFirst() ?: RealmMemo("")
        }.map {
            it.toDomain()
        }
    }

    override fun saveMemo(memo: Memo): Completable {
        return RealmCompletableObservable.create { realm ->
            realm.copyToRealm(memo.toRealm())
        }
    }
}
