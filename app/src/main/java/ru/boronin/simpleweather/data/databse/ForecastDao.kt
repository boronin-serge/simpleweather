package ru.boronin.simpleweather.data.databse

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertForecast(forecast: ForecastEntity)

  @Query("SELECT * from forecast_table ORDER BY ROWID ASC LIMIT 1")
  fun getLastForecast(): ForecastEntity?

  @Query("DELETE FROM forecast_table")
  fun deleteAll()
}
