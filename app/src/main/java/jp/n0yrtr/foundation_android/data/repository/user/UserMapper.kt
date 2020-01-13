package jp.n0yrtr.foundation_android.data.repository.user

import jp.n0yrtr.foundation_android.data.api.github.entitiy.users.response.ResponseGetUser
import jp.n0yrtr.foundation_android.domain.model.User

fun ResponseGetUser.toDomain(): User {
    return User(
        name = this.name,
        reposUrl = this.reposUrl
    )
}

