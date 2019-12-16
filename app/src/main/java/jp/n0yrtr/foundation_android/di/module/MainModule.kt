package jp.n0yrtr.foundation_android.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import jp.n0yrtr.foundation_android.di.ViewModelKey
import jp.n0yrtr.foundation_android.presentation.MainViewModel

@Module
internal abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Module
    companion object {

    }

}
