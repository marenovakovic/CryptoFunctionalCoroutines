package com.marko.cryptofunctionalcoroutines.app

import com.marko.cryptofunctionalcoroutines.injection.application.appComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

	override fun applicationInjector(): AndroidInjector<out DaggerApplication> = component

	private val component = appComponent
}