package com.marko.cryptofunctionalcoroutines.app

import androidx.test.platform.app.InstrumentationRegistry
import com.marko.cryptofunctionalcoroutines.injection.application.TestApplicationComponent
import com.marko.cryptofunctionalcoroutines.injection.application.testAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class TestApp : DaggerApplication() {

	companion object {
		val component: TestApplicationComponent =
			(InstrumentationRegistry.getInstrumentation().context as TestApp).testComponent
	}

	override fun applicationInjector(): AndroidInjector<out DaggerApplication> = testComponent

	private val testComponent = testAppComponent
}