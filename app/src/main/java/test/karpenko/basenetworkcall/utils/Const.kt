package test.karpenko.basenetworkcall.utils

import test.karpenko.basenetworkcall.models.Animal

const val BASE_URL = "https://zoo-animal-api.herokuapp.com/animals/rand/"
const val URL = "https://zoo-animal-api.herokuapp.com/animals/rand/7"

fun merge(first: List<Animal>?, second: List<Animal>?): List<Animal>? {
    val list: MutableList<Animal> = ArrayList()
    if (first != null) {
        list.addAll(first)
    }
    if (second != null) {
        list.addAll(second)
    }
    return list
}