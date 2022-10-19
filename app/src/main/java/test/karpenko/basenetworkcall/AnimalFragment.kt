package test.karpenko.basenetworkcall

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import retrofit2.Call
import retrofit2.Response
import test.karpenko.basenetworkcall.adapter.AnimalAdapter
import test.karpenko.basenetworkcall.api.AnimalApiFactory
import test.karpenko.basenetworkcall.databinding.FragmentAnimalBinding
import test.karpenko.basenetworkcall.models.Animal
import test.karpenko.basenetworkcall.utils.URL
import java.io.IOException


class AnimalFragment : Fragment() {

    private var retrofitResponse = mutableListOf<Animal>()
    private var okHttpResponse = mutableListOf<Animal>()
    private var summaryResponse = mutableListOf<Animal>()

    private lateinit var binding: FragmentAnimalBinding
    private lateinit var animalAdapter: AnimalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        init()
    }


    private fun initAdapter() {
        animalAdapter = AnimalAdapter()
        binding.animalsRecyclerView.apply {
            adapter = animalAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun getOkHttpResponse(){
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url(URL)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {

            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val string = response.body?.string()
                    val userType = object : TypeToken<MutableList<Animal>>() {}.type
                    okHttpResponse = (Gson().fromJson(string, userType))
                    Log.d("RESPONSE", "okHttpResponse $okHttpResponse")
                    summaryResponse.addAll(okHttpResponse)
                    Handler(Looper.getMainLooper()).post {
                        animalAdapter.differ.submitList(summaryResponse)
                    }
                }
            }

        })
    }

    private fun getRetrofitResponse()  {
        AnimalApiFactory.animalService.getRandomAnimalsCount("5")
            .enqueue(object : retrofit2.Callback<MutableList<Animal>> {
                override fun onResponse(
                    call: Call<MutableList<Animal>>,
                    response: Response<MutableList<Animal>>
                ) {
                    retrofitResponse = response.body()!!
                    Log.d("RESPONSE", "retrofitResponse $retrofitResponse")
                    summaryResponse.addAll(retrofitResponse)
                    Handler(Looper.getMainLooper()).post {
                        animalAdapter.differ.submitList(summaryResponse)
                    }
                }
                override fun onFailure(call: Call<MutableList<Animal>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun init(){
        getRetrofitResponse()
        getOkHttpResponse()
        Log.d("RESPONSE", "retrofitResponse $summaryResponse")
    }

}