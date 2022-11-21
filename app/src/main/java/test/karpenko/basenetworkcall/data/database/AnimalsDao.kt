package test.karpenko.basenetworkcall.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.karpenko.basenetworkcall.models.Animal

@Dao
interface AnimalsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeList(animalsList: List<Animal>)

    @Query("select * from animals order by name desc limit 5 ")
    suspend fun getAllAnime(): List<Animal>

}