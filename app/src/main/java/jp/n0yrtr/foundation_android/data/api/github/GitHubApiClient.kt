package jp.n0yrtr.foundation_android.data.api.github

import io.reactivex.Single
import jp.n0yrtr.foundation_android.data.api.github.entitiy.users.response.ResponseGetUser
import retrofit2.http.GET

/**
 * gitHub API用クライアント
 * @author naoya nishida
 */

interface GitHubApiClient {

    companion object {
        val BASE_URL: String = "https://api.github.com"
    }
    /**
     * ユーザー情報取得API
     */
    @GET("users/n0yrtr")
    fun getUser(): Single<ResponseGetUser>

}
