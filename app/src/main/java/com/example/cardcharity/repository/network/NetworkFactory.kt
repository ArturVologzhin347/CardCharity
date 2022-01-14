package com.example.cardcharity.repository.network

import com.example.cardcharity.domain.common.Event
import com.example.cardcharity.repository.network.exception.NoNetworkException
import com.example.cardcharity.utils.extensions.errorSuspending
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import kotlin.reflect.KClass

class NetworkFactory @Inject constructor(private val retrofit: Retrofit) {
    val url: HttpUrl = retrofit.baseUrl()

    suspend fun <T : Any> request(
        call: Call<T>,
        response: (event: Event<T>) -> Unit
    ) = withContext(Dispatchers.IO) {
        try {

            //Loading...
            response(Event.load())

            //Http request
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (response.isSuccessful) {
                            val body = checkNotNull(response.body())
                            response(Event.success(body))
                        } else {
                            val error = response.errorSuspending()
                            val exception = Exception(error)
                            response(Event.fail(exception))
                            Timber.w(error)
                        }
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    val exception = when (t) {
                        is IOException -> NoNetworkException()
                        else -> t
                    }

                    response(Event.fail(exception))
                    Timber.w(exception)
                }
            })

        } catch (exception: Exception) {
            Timber.e(exception)
        }
    }

    fun <T : Any> lazyApi(kClass: KClass<T>): Lazy<T> {
        return lazy(LazyThreadSafetyMode.NONE) { apiOf(kClass) }
    }


    private fun <T : Any> apiOf(kClass: KClass<T>): T {
        return retrofit.create(kClass.java)
    }
}
