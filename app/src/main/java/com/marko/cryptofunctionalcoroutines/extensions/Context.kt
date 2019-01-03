package com.marko.cryptofunctionalcoroutines.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified A : Activity> Context.intentFor() = Intent(this, A::class.java)

inline fun <reified A : Activity> Context.startActivity(vararg params: Pair<String, Any>) =
	startActivity(
		intentFor<A>()
			.apply {
				for ((name, value) in params) {
					when (value) {
						is Int -> putExtra(name, value)
						is String -> putExtra(name, value)
						is Long -> putExtra(name, value)
					}
				}
			}
	)