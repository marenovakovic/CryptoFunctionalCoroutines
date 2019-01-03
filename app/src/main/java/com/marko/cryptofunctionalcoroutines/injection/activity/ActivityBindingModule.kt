package com.marko.cryptofunctionalcoroutines.injection.activity

import com.marko.cryptofunctionalcoroutines.coindetails.CoinDetailsActivity
import com.marko.cryptofunctionalcoroutines.coindetails.CoinDetailsModule
import com.marko.cryptofunctionalcoroutines.home.HomeModule
import com.marko.cryptofunctionalcoroutines.home.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

	@ActivityScope
	@ContributesAndroidInjector(modules = [HomeModule::class])
	abstract fun mainActivity(): MainActivity

	@ActivityScope
	@ContributesAndroidInjector(modules = [CoinDetailsModule::class])
	abstract fun coinDetailsActivity(): CoinDetailsActivity
}