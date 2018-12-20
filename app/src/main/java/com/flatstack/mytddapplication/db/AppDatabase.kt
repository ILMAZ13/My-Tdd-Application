package com.flatstack.mytddapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flatstack.mytddapplication.db.daos.MovieDao
import com.flatstack.mytddapplication.entities.Movie

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [DbTypeConverters::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
