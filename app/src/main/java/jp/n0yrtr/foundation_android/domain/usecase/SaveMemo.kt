package jp.n0yrtr.foundation_android.domain.usecase

import io.reactivex.Completable
import io.reactivex.Single
import jp.n0yrtr.foundation_android.domain.model.Memo

interface SaveMemo {
    /**
     * メモ情報保存
     */
    fun save(memo: Memo): Completable
}