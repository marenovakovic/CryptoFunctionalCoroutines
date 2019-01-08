package com.marko.domain.typeclass

import arrow.core.Either
import arrow.core.Try
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

fun <A> Deferred<A>.a(): Async<A> = Async(this)

class Async<out A>(private val deferred: Deferred<A>) {

	companion object {
		fun <A> just(value: A): Async<A> = CompletableDeferred(value).a()
	}

	suspend fun unsafeRun(
		context: CoroutineContext = Dispatchers.Unconfined,
		callback: (Either<Throwable, A>) -> Unit
	) {
		val either = Try { withContext(context) { deferred.await() } }.toEither()
		callback(either)
	}
}