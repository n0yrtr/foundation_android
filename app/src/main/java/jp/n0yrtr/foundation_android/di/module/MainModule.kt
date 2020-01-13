package jp.n0yrtr.foundation_android.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import jp.n0yrtr.foundation_android.di.ViewModelKey
import jp.n0yrtr.foundation_android.domain.repository.UserRepository
import jp.n0yrtr.foundation_android.domain.usecase.GetUser
import jp.n0yrtr.foundation_android.domain.usecase.GetUserImpl
import jp.n0yrtr.foundation_android.presentation.MainViewModel

@Module
internal abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGetUser(userRepository: UserRepository): GetUser = GetUserImpl(userRepository = userRepository)
    }

}
