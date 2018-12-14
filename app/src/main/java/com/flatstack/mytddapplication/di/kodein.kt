package com.flatstack.mytddapplication.di

import android.app.Application
import com.flatstack.mytddapplication.di.modules.netModule
import org.kodein.di.Kodein
import org.kodein.di.android.androidModule

fun initKodein(app: Application) =
    Kodein.lazy {
        import(androidModule(app))
        import(netModule)
    }
