package com.marko.cryptofunctionalcoroutines.injection.application

import com.marko.cryptofunctionalcoroutines.app.App
import com.marko.cryptofunctionalcoroutines.app.AppModule
import com.marko.cryptofunctionalcoroutines.injection.activity.ActivityBindingModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
	modules = [
		AndroidInjectionModule::class,
		AndroidSupportInjectionModule::class,
		AppModule::class,
		ActivityBindingModule::class
	]
)
interface ApplicationComponent : AndroidInjector<App>

val App.appComponent: ApplicationComponent
	get() = DaggerApplicationComponent
		.builder()
		.appModule(AppModule(context = this))
		.build()