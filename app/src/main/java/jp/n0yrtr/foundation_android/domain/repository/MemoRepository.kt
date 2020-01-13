package jp.n0yrtr.foundation_android.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import jp.n0yrtr.foundation_android.domain.model.Memo
import jp.n0yrtr.foundation_android.domain.model.User

/**
 * ユーザー情報 リポジトリ
 */
interface MemoRepository {
    fun fetchMemo(): Single<Memo>
    fun saveMemo(memo: Memo): Completable

}
