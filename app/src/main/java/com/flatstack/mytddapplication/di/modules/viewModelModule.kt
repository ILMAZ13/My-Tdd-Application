package com.flatstack.mytddapplication.di.modules

import androidx.lifecycle.ViewModelProvider
import com.flatstack.mytddapplication.ui.search.SearchViewModel
import com.flatstack.mytddapplication.ui.util.ViewModelFactory
import com.flatstack.mytddapplication.ui.util.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val viewModelModule = Kodein.Module(name = "viewModelModule") {
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(dkodein) }

    bindViewModel<SearchViewModel>() with provider { SearchViewModel(instance()) }
}
