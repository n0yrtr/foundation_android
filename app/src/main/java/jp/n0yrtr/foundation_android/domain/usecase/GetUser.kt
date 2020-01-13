package jp.n0yrtr.foundation_android.domain.usecase

import io.reactivex.Single
import jp.n0yrtr.foundation_android.domain.model.User

interface GetUser {
    /**
     * ユーザー情報取得
     */
    fun get(): Single<User>
}