package com.example.cardcharity.presentation.activities.main

import android.app.Application
import com.example.cardcharity.presentation.activities.main.list.ModelShop
import com.example.cardcharity.presentation.activities.main.list.ModelType
import com.example.cardcharity.presentation.activities.main.list.RecyclerAdapterShop
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.repository.Api
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.utils.capitalize
import com.example.cardcharity.utils.getRandomString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel(application: Application) : BaseViewModel(application) {
    lateinit var adapter: RecyclerAdapterShop

    fun initializeRecyclerAdapter() {
        adapter = RecyclerAdapterShop()
    }

    //TODO callback successful and failure without codes
    fun requestModelsForRecyclerView(callback: RequestCallback) {
        Api.shopService.getAllShops().enqueue(object : Callback<List<Shop>> {
            override fun onResponse(call: Call<List<Shop>>, response: Response<List<Shop>>) {
                val result = response.body()
                if (result != null && result.isNotEmpty()) {
                    //TODO
                    //val mock = mockModels()
                    buildModelsForAdapter(result)
                    callback.onSuccessful()
                } else {
                    callback.onFailure()
                }
                callback.onComplete()
            }

            override fun onFailure(call: Call<List<Shop>>, t: Throwable) {
                callback.onFailure()
                callback.onComplete()
                Timber.e(t)
            }
        })
    }

    interface RequestCallback {
        fun onSuccessful()
        fun onFailure()
        fun onComplete()
    }

    //TODO delete
    private fun mockModels(): List<Shop> {
        return arrayListOf<Shop>().apply {
            val count = 10
            for (i in 0..count) {
                val shop = Shop()
                shop.id = 3
                shop.name = getRandomString(10)
                this.add(shop)
            }
        }
    }

    private fun buildModelsForAdapter(rawModels: List<Shop>) {
        if (rawModels.isNotEmpty()) {
            val models = arrayListOf<ModelShop>()

            //Sorting list by alphabet
            val sortedList = rawModels.sortedBy { it.name.lowercase() }

            //Last character in shop name
            var lastCharacter = ""
            for (rawShop in sortedList) {
                rawShop.name = rawShop.name.capitalize()
                val firstCharacter = rawShop.name.first().toString().lowercase()

                //If firstChar != lastChar - add title model
                if (firstCharacter != lastCharacter) {
                    lastCharacter = firstCharacter

                    val modelTitle = ModelShop(ModelType.TITLE, null, lastCharacter)
                    models.add(modelTitle)
                }

                val model = ModelShop(ModelType.MODEL, rawShop, null)
                models.add(model)

                adapter.updateModels(newModels = models)
            }
        }
    }
}