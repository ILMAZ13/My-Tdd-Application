package com.flatstack.mytddapplication.di

import android.app.Application
import com.flatstack.mytddapplication.di.modules.dbModule
import com.flatstack.mytddapplication.di.modules.netModule
import com.flatstack.mytddapplication.di.modules.repoModule
import com.flatstack.mytddapplication.di.modules.utilModule
import com.flatstack.mytddapplication.di.modules.viewModelModule
import org.kodein.di.Kodein
import org.kodein.di.android.androidModule

fun initKodein(app: Application) =
    Kodein.lazy {
        import(androidModule(app))
        import(utilModule)
        import(netModule)
        import(dbModule)
        import(repoModule)
        import(viewModelModule)
    }
