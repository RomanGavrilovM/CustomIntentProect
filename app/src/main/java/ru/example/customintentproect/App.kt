package ru.example.customintentproect

import android.app.Application
import android.content.Context
import ru.example.customintentproect.data.retrofit.DogsApi
import ru.example.customintentproect.data.retrofit.NetDogRepoImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    val dogRepo by lazy {
        NetDogRepoImpl(dogApi)
    }

    val baseUrl = "https://dog.ceo/api/breeds/image/"

    val dogApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .client(OkHttpClient.Builder().build())
        .build()
        .create(DogsApi::class.java)

}

val Context.app: App get() = applicationContext as App