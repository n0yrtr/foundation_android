package jp.n0yrtr.foundation_android.data.repository.user

import io.reactivex.Single
import jp.n0yrtr.foundation_android.data.api.github.GitHubApiClient
import jp.n0yrtr.foundation_android.domain.model.User
import jp.n0yrtr.foundation_android.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val gitHubApiClient: GitHubApiClient
) : UserRepository {

    override fun fetchUser(): Single<User> {
        return gitHubApiClient.getUser()
            .map {
                it.toDomain()
            }
    }

}
