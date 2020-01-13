package jp.n0yrtr.foundation_android.domain.usecase

import io.reactivex.Completable
import io.reactivex.Single
import jp.n0yrtr.foundation_android.domain.model.Memo
import jp.n0yrtr.foundation_android.domain.repository.MemoRepository
import javax.inject.Inject

/**
 * Memo情報保存 実装
 */
class SaveMemoImpl @Inject constructor(private val memoRepository: MemoRepository) : SaveMemo {
    override fun save(memo: Memo): Completable {
        return memoRepository.saveMemo(memo)
    }
}