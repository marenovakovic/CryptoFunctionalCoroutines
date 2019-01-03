package com.marko.cryptofunctionalcoroutines.injection.activity

import com.marko.cryptofunctionalcoroutines.home.MainActivity
import com.marko.cryptofunctionalcoroutines.home.TestHomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestActivityBindingModule {

	@ContributesAndroidInjector(modules = [TestHomeModule::class])
	@ActivityScope
	abstract fun testMainActivity(): MainActivity
}