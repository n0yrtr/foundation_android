package jp.n0yrtr.foundation_android.data.repository.memo

import jp.n0yrtr.foundation_android.data.realm.entity.RealmMemo
import jp.n0yrtr.foundation_android.domain.model.Memo

fun Memo.toRealm(): RealmMemo {
    return RealmMemo(
       memo = this.memo
    )
}

fun RealmMemo.toDomain(): Memo {
    return Memo(
        memo = this.memo
    )
}

