package jp.n0yrtr.foundation_android.domain.usecase

import io.reactivex.Single
import jp.n0yrtr.foundation_android.domain.model.User
import jp.n0yrtr.foundation_android.domain.repository.UserRepository
import javax.inject.Inject

/**
 *
 * ユーザー情報取得 実装
 */
class GetUserImpl @Inject constructor(private val userRepository: UserRepository) : GetUser {
    override fun get(): Single<User> {
        return userRepository.fetchUser()
    }
}