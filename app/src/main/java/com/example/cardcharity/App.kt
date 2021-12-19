package com.example.cardcharity

import android.app.Application
import android.content.Context
import android.content.SharedPreferences


import timber.log.Timber

/*

fun Shop.getLogoUrl(): String {
    return "${RetrofitService.URL}user/shop/$id/logo"
}

fun Shop.getCodeUrl(uid: String): String {
    return "${RetrofitService.URL}user/code/?shopId=$id&uid=$uid"
}




 @GET("/user/shop")
    fun getAllShops(): Call<List<Shop>>


SHOP
 @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String


    USER
      val email: String,
    val uid: String,
    val name: String,
    val avatar: Uri
 */

/*
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
 */


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG_MODE) {
            Timber.plant(Timber.DebugTree())
        }

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

}