package com.example.cardcharity.presentation.activities.main

import android.app.Application
import com.example.cardcharity.presentation.activities.main.list.ModelShop
import com.example.cardcharity.presentation.activities.main.list.ModelType
import com.example.cardcharity.presentation.activities.main.list.RecyclerAdapterShop
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.network.NetworkService
import com.example.cardcharity.utils.capitalize
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel(application: Application) : BaseViewModel(application) {
    lateinit var adapter: RecyclerAdapterShop

    fun initializeRecyclerAdapter() {
        adapter = RecyclerAdapterShop()
    }

    fun requestModelsForRecyclerView(callback: RequestCallback) {
        NetworkService.instance.getShopApi().getAllShops().enqueue(object : Callback<List<Shop>> {
            override fun onResponse(call: Call<List<Shop>>, response: Response<List<Shop>>) {
                val result = response.body()
                if (result != null) {
                    if (result.isEmpty()) {
                        callback.onResponse(RequestCallback.Type.ZERO_ITEMS)
                    } else {
                        //val mock = mockModels()
                        buildModelsForAdapter(result)
                        callback.onResponse(RequestCallback.Type.SUCCESSFUL)
                    }
                } else {
                    callback.onResponse(RequestCallback.Type.NULL_BODY)
                }
            }

            override fun onFailure(call: Call<List<Shop>>, t: Throwable) {
                callback.onResponse(RequestCallback.Type.FAILURE)
                callback.onFailure(call, t)
            }
        })
    }

    interface RequestCallback {
        fun onResponse(type: Type)
        fun onFailure(call: Call<List<Shop>>, t: Throwable)

        enum class Type {
            SUCCESSFUL,
            FAILURE,
            ZERO_ITEMS,
            NULL_BODY
        }
    }

    //TODO delete
    private fun mockModels(): List<Shop> {
        val shops = arrayListOf<Shop>()

        val count = 50
        val startId = 2

        for (i in 0..count) {
            val shop = Shop()
            shop.id = startId + i
            shop.name = getRandomString(10)
            shop.imageUrl = "12345678"
            shops.add(shop)
        }
        return shops
    }

    //TODO delete
    private val allowedChars = ('А'..'Я') + ('а'..'я')
    private fun getRandomString(length: Int): String {
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun buildModelsForAdapter(rawModels: List<Shop>) {
        if (rawModels.isNotEmpty()) {
            val models = arrayListOf<ModelShop>()

            //Sorting list by alphabet
            val sortedList = rawModels.sortedBy { it.name.lowercase() }

            //Last character in shop name
            var lastCharacter = ""
            for (rawShop in sortedList) {

                //TODO
                rawShop.imageUrl = "http://10d0-82-151-196-167.ngrok.io/code/?shop=2"
                rawShop.name = rawShop.name.capitalize()

                val firstCharacter = rawShop.name.first().toString().lowercase()

                //If firstChar != lastChar - add title model
                if (firstCharacter != lastCharacter) {
                    lastCharacter = firstCharacter

                    val modelTitle = ModelShop(
                        ModelType.TITLE,
                        null,
                        lastCharacter
                    )

                    models.add(modelTitle)
                }

                val model = ModelShop(ModelType.MODEL, rawShop, null)
                models.add(model)

                adapter.updateModels(newModels = models)
            }

        } else {
            Timber.e("MODELS LIST IS EMPTY")
        }
    }
}