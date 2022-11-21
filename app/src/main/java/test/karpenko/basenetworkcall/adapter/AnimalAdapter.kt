package test.karpenko.basenetworkcall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import okhttp3.internal.notify
import test.karpenko.basenetworkcall.databinding.ItemAnimalBinding
import test.karpenko.basenetworkcall.models.Animal

class AnimalAdapter: RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    inner class AnimalViewHolder(view: ItemAnimalBinding): RecyclerView.ViewHolder(view.root) {
        val animalName = view.animalName
        val animalLatinName = view.animalLatinName
        val animalId = view.animalId
    }

    private val callback = object : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, callback)

    /*var animalsList: List<Animal> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        return AnimalViewHolder(
            ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = differ.currentList[position]
        holder.animalName.text = animal.name
        holder.animalLatinName.text = animal.latin_name
        holder.animalId.text = animal.id.toString()
    }

    override fun getItemCount() = differ.currentList.size

}