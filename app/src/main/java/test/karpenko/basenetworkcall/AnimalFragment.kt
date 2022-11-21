package test.karpenko.basenetworkcall

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import test.karpenko.basenetworkcall.adapter.AnimalAdapter
import test.karpenko.basenetworkcall.data.api.AnimalApiFactory
import test.karpenko.basenetworkcall.data.database.AnimalsDatabase
import test.karpenko.basenetworkcall.databinding.FragmentAnimalBinding
import test.karpenko.basenetworkcall.models.Animal


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
        getAnimals()

        binding.animalsFromDB.setOnClickListener {
            getAnimalsFromDatabase()
        }

    }


    private fun initAdapter() {
        animalAdapter = AnimalAdapter()
        binding.animalsRecyclerView.apply {
            adapter = animalAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    //Task 1
    private fun getAnimals() {
        lifecycleScope.launch {
            val response = AnimalApiFactory.animalService.getRandomAnimalsCount("10")
            if (response.isSuccessful) {
                animalAdapter.differ.submitList(response.body())
                response.body()?.let {
                    AnimalsDatabase.getInstance(requireContext()).animalsDao().insertAnimeList(it)
                }
            }
        }
    }

    private fun getAnimalsFromDatabase(){
        lifecycleScope.launch {
            val result = AnimalsDatabase.getInstance(requireContext()).animalsDao().getAllAnime()
            if (result.isNotEmpty()){
                animalAdapter.differ.submitList(result)
            }
        }
    }

}