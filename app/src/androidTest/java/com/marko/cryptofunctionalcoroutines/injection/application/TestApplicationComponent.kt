package com.marko.cryptofunctionalcoroutines.injection.application

import com.marko.cryptofunctionalcoroutines.app.TestApp
import com.marko.cryptofunctionalcoroutines.app.TestAppModule
import com.marko.cryptofunctionalcoroutines.injection.activity.TestActivityBindingModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
	modules = [
		AndroidInjectionModule::class,
		AndroidSupportInjectionModule::class,
		TestAppModule::class,
		TestActivityBindingModule::class
	]
)
interface TestApplicationComponent : ApplicationComponent

val TestApp.testAppComponent: TestApplicationComponent
	get() = DaggerTestApplicationComponent.create()