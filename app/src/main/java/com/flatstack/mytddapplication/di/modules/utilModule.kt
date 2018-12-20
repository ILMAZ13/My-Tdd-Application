package com.flatstack.mytddapplication.di.modules

import com.flatstack.mytddapplication.AppExecutors
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val utilModule = Kodein.Module(name = "utilModule") {
    bind<AppExecutors>() with singleton { AppExecutors() }
}
