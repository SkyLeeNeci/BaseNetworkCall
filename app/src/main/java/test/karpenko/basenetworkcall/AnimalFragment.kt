package test.karpenko.basenetworkcall

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import test.karpenko.basenetworkcall.adapter.AnimalAdapter
import test.karpenko.basenetworkcall.api.AnimalApiFactory
import test.karpenko.basenetworkcall.databinding.FragmentAnimalBinding
import test.karpenko.basenetworkcall.models.Animal
import java.io.IOException
import java.lang.IllegalArgumentException


class AnimalFragment : Fragment() {

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
        init3()
    }


    private fun initAdapter() {
        animalAdapter = AnimalAdapter()
        binding.animalsRecyclerView.apply {
            adapter = animalAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    //Task 1
    fun init1() {
        lifecycleScope.launch{
            val response = AnimalApiFactory.animalService.getRandomAnimalsCount("10")
            if (response.isSuccessful){
                animalAdapter.differ.submitList(response.body())
            }
        }
    }

    //Task2
    fun init2() =
        lifecycleScope.launch {
            var i = 0
            val listOfAnimals = mutableListOf<Animal>()
            val job: Job = launch(Dispatchers.IO) {
                while (i < 3) {
                    i += 1
                    val response = AnimalApiFactory.animalService.getRandomAnimalsCount("1")
                    if (response.isSuccessful) {
                        Log.d("MAIN_ACTIVITY", response.body().toString())
                        response.body()?.let { listOfAnimals.addAll(it) }
                    }else{
                        Log.d("MAIN_ACTIVITY", response.message())
                    }
                    //Log.d("MAIN_ACTIVITY", "$i")
                    delay( 500)
                }
            }
            delay(1000)
            Log.d("MAIN_ACTIVITY", "I'm tired of waiting!")
            //job.join()
            job.cancel()
            //job.cancelAndJoin()
            /*job.cancel()
            job.join()*/
            withContext(Dispatchers.Main){
                animalAdapter.differ.submitList(listOfAnimals)
            }
            Log.d("MAIN_ACTIVITY", " Now I can quit.")
        }

    //Task3
    fun init3(){

        val superJob = SupervisorJob()
        val job = Job()
        val superScope = CoroutineScope(Dispatchers.Default + superJob)
        val scope = CoroutineScope(Dispatchers.Default + job)

        /*scope.launch {
            Log.d("MAIN_ACTIVITY", " Now in launch")
            try {
                async {
                    Log.d("MAIN_ACTIVITY", " Now in async")
                    delay(1000)
                    Log.d("MAIN_ACTIVITY", " Now in async after delay")
                    throw Exception("Oops...")
                }.await()
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Snackbar.make(binding.rootConstrain, e.message.toString(), Snackbar.LENGTH_LONG)
                }
            }
            Log.d("MAIN_ACTIVITY", " Now in launch after async")
        }*/

        superScope.launch {
            Log.d("MAIN_ACTIVITY", " Now in launch")
            try {
                async {
                    Log.d("MAIN_ACTIVITY", " Now in async")
                    delay(1000)
                    Log.d("MAIN_ACTIVITY", " Now in async after delay")
                    throw CancellationException("Oops...")
                }.await()
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Snackbar.make(binding.rootConstrain, e.message.toString(), Snackbar.LENGTH_LONG).show()
                }
                Log.d("MAIN_ACTIVITY", " Now in launch after async")
            }
            Log.d("MAIN_ACTIVITY", " Now in launch after async")
        }

    }

}