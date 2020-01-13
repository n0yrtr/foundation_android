package jp.n0yrtr.foundation_android.di

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import jp.n0yrtr.foundation_android.App
import jp.n0yrtr.foundation_android.data.api.ApiConfig
import jp.n0yrtr.foundation_android.data.api.github.GitHubApiClient
import jp.n0yrtr.foundation_android.data.repository.memo.MemoRepositoryImpl
import jp.n0yrtr.foundation_android.data.repository.user.UserRepositoryImpl
import jp.n0yrtr.foundation_android.domain.repository.MemoRepository
import jp.n0yrtr.foundation_android.domain.repository.UserRepository
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.Arrays
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


@Module
internal object DataModule {

    @Singleton
    @Provides
    @JvmStatic
    fun providesMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    @JvmStatic
    fun providesOkHttp(app: App): OkHttpClient {
        val logging = getHttpLoggingInterceptor()
        val builder = OkHttpClient.Builder()
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, getTrustManager(app), null)
        return builder.connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
            .sslSocketFactory(sslContext.socketFactory)
            .hostnameVerifier(HostnameVerifier { hostname, session ->
                true
            })
            .connectTimeout(ApiConfig.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(ApiConfig.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor {
                val request = it.request()
                val response = it.proceed(request)
                if (!response.isSuccessful) {
                    Timber.tag("HTTP STATUS CODE ERROR").e(
                        "${response.code().toString()}\nrequest :\n   (URL) ${request.url()}\n   (METHOD) ${request.method()}\n   (HEADER) ${request.headers()}   (BODY) ${request.bodyToString()}\nresponse :\n   (HEADER) ${response.headers()}   (BODY) ${response.bodyToString()}"
                    )
                }
                val source = response.body()?.source()
                source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                response
            }
            .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun providesLiveIntranetWillApiClient(oktHttpClient: OkHttpClient, moshi: Moshi): GitHubApiClient {
        val retrofit = getRetrofitBuilder(oktHttpClient, moshi)
            .baseUrl(GitHubApiClient.BASE_URL)
            .build()
        return retrofit.create(GitHubApiClient::class.java)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun providesUserRepository(gitHubApiClient: GitHubApiClient): UserRepository {
        return UserRepositoryImpl(gitHubApiClient = gitHubApiClient);
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideMemoRepository(): MemoRepository {
        return MemoRepositoryImpl()
    }


    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging

    }

    private fun getRetrofitBuilder(oktHttpClient: OkHttpClient, moshi: Moshi): Retrofit.Builder {
        return Retrofit.Builder()
            .client(oktHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Throws(NoSuchAlgorithmException::class, KeyStoreException::class)
    private fun getTrustManager(context: App): Array<TrustManager> {
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
        }
        return trustManagers
    }


    @Throws(CertificateException::class, KeyStoreException::class)
    fun loadX509Certificate(ks: KeyStore, `is`: InputStream) {
        try {
            val factory = CertificateFactory.getInstance("X.509")
            val x509 = factory.generateCertificate(`is`) as X509Certificate
            val alias = x509.subjectDN.name
            ks.setCertificateEntry(alias, x509)
        } finally {
            try {
                `is`.close()
            } catch (e: IOException) {
                throw e
            }
        }
    }

    private fun Request.bodyToString(): String {
        return try {
            val copy = newBuilder().build()
            val buffer = Buffer()
            copy.body()?.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }

    }

    private fun Response.bodyToString(): String {
        return try {
            val copy = newBuilder().build()
            val buffer =
                copy.body()?.byteStream()?.bufferedReader()
            buffer?.readText() ?: ""
        } catch (e: IOException) {
            "did not work"
        }

    }


}