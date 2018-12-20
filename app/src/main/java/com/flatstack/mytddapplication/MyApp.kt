package com.flatstack.mytddapplication

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.flatstack.mytddapplication.di.initKodein
import io.fabric.sdk.android.Fabric
import org.kodein.di.KodeinAware

class MyApp : Application(), KodeinAware {
    override val kodein = initKodein(this)

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }
}
