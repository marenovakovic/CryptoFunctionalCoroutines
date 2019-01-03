package com.marko.cryptofunctionalcoroutines.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marko.cryptofunctionalcoroutines.injection.viewmodel.ViewModelKey
import com.marko.presentation.coins.CoinsViewModel
import com.marko.presentation.injection.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TestHomeModule {

	@Binds
	@IntoMap
	@ViewModelKey(CoinsViewModel::class)
	abstract fun coinsViewModel(bind: CoinsViewModel): ViewModel

	@Binds
	abstract fun viewModelFactory(bind: ViewModelFactory): ViewModelProvider.Factory
}