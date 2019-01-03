package com.marko.presentation.dispatchers

import kotlin.coroutines.CoroutineContext

interface CoroutineDispatchers {

	val main: CoroutineContext

	val io: CoroutineContext

	val async: CoroutineContext
}