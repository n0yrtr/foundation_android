package jp.n0yrtr.foundation_android.presentation

import androidx.lifecycle.AndroidViewModel
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import jp.n0yrtr.foundation_android.App
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val context: App)
    : AndroidViewModel(context) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}