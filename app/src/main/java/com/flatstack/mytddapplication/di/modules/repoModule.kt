package com.flatstack.mytddapplication.di.modules

import com.flatstack.mytddapplication.repository.MovieRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val repoModule = Kodein.Module(name = "repoModule") {
    bind<MovieRepository>() with singleton { MovieRepository(instance(), instance(), instance()) }
}
