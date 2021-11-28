package com.example.cardcharity.repository.network

import android.annotation.SuppressLint
import com.example.cardcharity.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.reflect.KClass


//https://stackoverflow.com/questions/37686625/disable-ssl-certificate-check-in-retrofit-library
abstract class RetrofitService(url: String) {
    private val interceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG_MODE) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

   protected val httpClient by lazy { buildUnsafeHttpClient() }

    private val retrofit = Retrofit.Builder().apply {
        baseUrl(url)
        addConverterFactory(GsonConverterFactory.create())
        client(httpClient)
    }.build()

    private fun <T : Any> apiOf(kClass: KClass<T>): T {
        return retrofit.create(kClass.java)
    }

    protected fun <T: Any> lazyApi(kClass: KClass<T>): Lazy<T> {
        return lazy { apiOf(kClass) }
    }

    @SuppressLint("TrustAllX509TrustManager")
    private fun buildUnsafeHttpClient(): OkHttpClient {
        try {
            val trustAllCerts: Array<TrustManager> = arrayOf(
                @SuppressLint("CustomX509TrustManager") object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            val sslClient = SSLContext.getInstance("SSL")
            sslClient.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslClient.socketFactory
            return OkHttpClient.Builder().apply {
                this.sslSocketFactory(sslSocketFactory)//TODO
                hostnameVerifier { _, _ -> true }
                addInterceptor(interceptor)
            }.build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    companion object {
        const val URL = "https://10.3.21.33:8443/"
    }
}