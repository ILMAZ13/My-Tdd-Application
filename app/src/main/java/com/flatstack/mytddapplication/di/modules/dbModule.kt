package com.flatstack.mytddapplication.di.modules

import android.content.Context
import androidx.room.Room
import com.flatstack.mytddapplication.db.AppDatabase
import com.flatstack.mytddapplication.db.daos.MovieDao
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val dbModule = Kodein.Module(name = "dbModule") {
    bind<AppDatabase>() with singleton { provideDatabase(instance()) }

    bind<MovieDao>() with singleton { instance<AppDatabase>().movieDao() }
}

private fun provideDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
        .fallbackToDestructiveMigration()
        .build()
