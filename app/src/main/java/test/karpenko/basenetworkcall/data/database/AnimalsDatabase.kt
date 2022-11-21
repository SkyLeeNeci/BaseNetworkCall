package test.karpenko.basenetworkcall.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import test.karpenko.basenetworkcall.models.Animal

@Database(entities = [Animal::class], version = 1, exportSchema = false)
abstract class AnimalsDatabase: RoomDatabase() {

    companion object{
        private var db: AnimalsDatabase? = null
        private const val DB_NAME = "mainDB"
        private val LOCK = Any()

        fun getInstance(context: Context): AnimalsDatabase{
            synchronized(LOCK){
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    AnimalsDatabase::class.java,
                    DB_NAME
                ).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun animalsDao(): AnimalsDao

}