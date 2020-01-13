package jp.n0yrtr.foundation_android.presentation

import androidx.lifecycle.AndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import jp.keita.kagurazaka.rxproperty.ReadOnlyRxProperty
import jp.keita.kagurazaka.rxproperty.RxProperty
import jp.keita.kagurazaka.rxproperty.toReadOnlyRxProperty
import jp.n0yrtr.foundation_android.App
import jp.n0yrtr.foundation_android.domain.model.User
import jp.n0yrtr.foundation_android.domain.usecase.GetUser
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val context: App,
    private val getUser: GetUser
) : AndroidViewModel(context) {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val user = RxProperty<User>()
    // Property
    val userName: ReadOnlyRxProperty<String> = user.map { it.name }.toReadOnlyRxProperty()
    val userRepositoryUrl: ReadOnlyRxProperty<String> = user.map { it.reposUrl }.toReadOnlyRxProperty()

    init {
        getUser.get().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { user, throwable ->
            throwable?.let {
                Timber.e(it)
            }
            this.user.set(user)
        }.apply {
            disposables.add(this)
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}