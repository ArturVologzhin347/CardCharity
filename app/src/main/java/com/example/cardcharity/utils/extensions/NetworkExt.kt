package com.example.cardcharity.utils.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.cardcharity.repository.network.ssl.NullHostNameVerifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import java.io.File
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.*

@Suppress("DEPRECATION")
fun isNetworkAvailable(context: Context): Boolean {
    (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
        for (networkInfo in allNetworkInfo) {
            if (networkInfo.state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }
}

//Ignore all certificates for https connection
fun OkHttpClient.Builder.toUnsafe() {
    val trustManager = getTrustAllCertsManager()
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, arrayOf(trustManager), SecureRandom())
    val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
    hostnameVerifier(NullHostNameVerifier())
    sslSocketFactory(sslSocketFactory, trustManager)
}


@SuppressLint("TrustAllX509TrustManager")
fun getTrustAllCertsManager() =
    @SuppressLint("CustomX509TrustManager") object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return emptyArray()
        }
    }


fun OkHttpClient.Builder.setupResponsesCache(context: Context, size: Long) {
    val httpCacheDirectory = File(context.cacheDir, "responses")
    cache(Cache(httpCacheDirectory, size))
}

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun <T> Response<T>.errorSuspending(): String = withContext(Dispatchers.IO) {
    return@withContext checkNotNull(this@errorSuspending.errorBody()?.string())
}


fun HttpLoggingInterceptor.setDebugMode(isDebug: Boolean): HttpLoggingInterceptor =
    this.apply { level = if (isDebug) LOG_HTTP_BODY else LOG_HTTP_NONE }


private val LOG_HTTP_BODY = HttpLoggingInterceptor.Level.BODY
private val LOG_HTTP_NONE = HttpLoggingInterceptor.Level.NONE
