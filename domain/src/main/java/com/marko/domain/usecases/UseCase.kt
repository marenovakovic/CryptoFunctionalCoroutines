package com.marko.domain.usecases

import arrow.core.Either
import arrow.core.Try
import com.marko.domain.operations.SuspendedOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Executes business logic
 */
abstract class UseCase<in P, R> {

	/**
	 * Override this to execute business logic
	 *
	 * @param parameters [P] parameters needed to run use case
	 *
	 * @return [SuspendedOperation] with the result
	 */
	abstract suspend fun execute(parameters: P): SuspendedOperation<R>

	/**
	 * Executes business logic asynchronously and returns [SuspendedOperation] with the result [R]
	 *
	 * @param parameters [P] parameters needed to run use case
	 *
	 * @return [SuspendedOperation] with the result
	 */
	suspend operator fun invoke(parameters: P): SuspendedOperation<R> = execute(parameters)
}

suspend operator fun <R> UseCase<Unit, R>.invoke(): SuspendedOperation<R> = this.invoke(Unit)

suspend fun <A> SuspendedOperation<A>.unsafeRun(
	context: CoroutineContext = Dispatchers.Unconfined,
	callback: (Either<Throwable, A>) -> Unit
) {
	val either = Try { withContext(context) { this@unsafeRun() } }.toEither()
	callback(either)
}

suspend fun <A> SuspendedOperation<A>.safeRun(context: CoroutineContext = Dispatchers.Unconfined) =
	withContext(context) { this@safeRun() }