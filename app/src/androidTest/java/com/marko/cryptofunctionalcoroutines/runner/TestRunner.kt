package com.marko.cryptofunctionalcoroutines.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.marko.cryptofunctionalcoroutines.app.TestApp

class TestRunner : AndroidJUnitRunner() {

	override fun newApplication(
		cl: ClassLoader?,
		className: String?,
		context: Context?
	): Application = super.newApplication(cl, TestApp::class.java.name, context)
}