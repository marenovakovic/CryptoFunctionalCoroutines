package com.marko.cryptofunctionalcoroutines.base

import com.marko.presentation.dispatchers.CoroutineDispatchers
import com.marko.presentation.injection.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Base Activity class that provides [CoroutineScope] and injects [ViewModelFactory]
 */
abstract class BaseActivity : DaggerAppCompatActivity(), CoroutineScope {

	@Inject
	lateinit var dispatchers: CoroutineDispatchers

	@Inject
	lateinit var factory: ViewModelFactory

	/**
	 * Parent Activity [Job]
	 */
	private val job = Job()

	/**
	 * [CoroutineContext] [Dispatchers.Main] + [job]
	 */
	override val coroutineContext: CoroutineContext
		get() = dispatchers.main + job

	/**
	 * Clear [job] because we don't need it any more
	 */
	override fun onDestroy() {
		super.onDestroy()

		job.cancel()
	}
}