package jp.n0yrtr.foundation_android.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import jp.n0yrtr.foundation_android.R
import jp.n0yrtr.foundation_android.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewmodel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewmodel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java).apply{
            // TODO liveDataのbindを書く
        }

        binding.viewModel = viewmodel
    }
}
