package com.flatstack.mytddapplication.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatstack.mytddapplication.entities.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg movies: Movie)

    @Query("SELECT * FROM movies WHERE imdbID = :id")
    fun findById(id: String): LiveData<Movie>
}
