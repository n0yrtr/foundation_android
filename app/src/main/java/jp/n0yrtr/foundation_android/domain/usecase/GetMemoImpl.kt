package jp.n0yrtr.foundation_android.domain.usecase

import io.reactivex.Single
import jp.n0yrtr.foundation_android.domain.model.Memo
import jp.n0yrtr.foundation_android.domain.repository.MemoRepository
import javax.inject.Inject

/**
 * Memo情報取得 実装
 */
class GetMemoImpl @Inject constructor(private val memoRepository: MemoRepository) : GetMemo {
    override fun get(): Single<Memo> {
        return memoRepository.fetchMemo()
    }
}