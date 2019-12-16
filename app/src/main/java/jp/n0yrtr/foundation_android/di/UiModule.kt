package jp.n0yrtr.foundation_android.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.n0yrtr.foundation_android.di.module.MainModule
import jp.n0yrtr.foundation_android.presentation.MainActivity

@Module
internal abstract class UiModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    // Activity(共通系)
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

}