package com.flatstack.mytddapplication

import android.app.Application
import com.flatstack.mytddapplication.di.initKodein
import org.kodein.di.KodeinAware

class MyApp : Application(), KodeinAware {
    override val kodein = initKodein(this)
}
