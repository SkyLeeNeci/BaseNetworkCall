package test.karpenko.basenetworkcall.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import test.karpenko.basenetworkcall.models.Animal

interface AnimalService {

    @GET("{count}")
    fun getRandomAnimalsCount(
        @Path("count") count: String
    ): Call<MutableList<Animal>>

}