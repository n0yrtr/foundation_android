package jp.n0yrtr.foundation_android.domain.repository

import io.reactivex.Single
import jp.n0yrtr.foundation_android.domain.model.User

/**
 * ユーザー情報 リポジトリ
 */
interface UserRepository {
    fun fetchUser(): Single<User>
}
