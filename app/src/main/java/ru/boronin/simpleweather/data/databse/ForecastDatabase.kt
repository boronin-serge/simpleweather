package ru.boronin.simpleweather.data.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ForecastEntity::class], version = 1, exportSchema = false)
@TypeConverters(ModelConverters::class)
abstract class ForecastDatabase : RoomDatabase() {

  abstract fun forecastDao(): ForecastDao

  companion object {
    // Singleton prevents multiple instances of database opening at the
    // same time.
    @Volatile
    private var INSTANCE: ForecastDatabase? = null

    fun getDatabase(context: Context): ForecastDatabase {
      val tempInstance = INSTANCE
      if (tempInstance != null) {
        return tempInstance
      }
      synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          ForecastDatabase::class.java,
          "forecast_database"
        ).build()
        INSTANCE = instance
        return instance
      }
    }
  }
}
