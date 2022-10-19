package test.karpenko.basenetworkcall.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimalApiFactory {

    private const val BASE_URL = "https://zoo-animal-api.herokuapp.com/animals/rand/"

    private val retrofit = Retrofit.Builder()
        .client(OkHttpClient().newBuilder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val animalService: AnimalService = retrofit.create(AnimalService::class.java)
}