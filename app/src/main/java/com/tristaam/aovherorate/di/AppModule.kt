package com.tristaam.aovherorate.di

import com.tristaam.aovherorate.presentation.ui.MainViewModel
import com.tristaam.aovherorate.presentation.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::MainViewModel)
}