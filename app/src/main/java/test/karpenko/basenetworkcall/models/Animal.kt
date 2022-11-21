package test.karpenko.basenetworkcall.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class Animal(

 var name: String? = null,
 var latin_name: String? = null,
 @PrimaryKey
 var id: Int

)