package test.karpenko.basenetworkcall.data.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import test.karpenko.basenetworkcall.models.Animal

interface AnimalService {

    @GET("{count}")
    suspend fun getRandomAnimalsCount(
        @Path("count") count: String
    ): Response<MutableList<Animal>>

}