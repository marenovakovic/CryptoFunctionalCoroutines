package com.marko.domain.usecases

import arrow.effects.IO

/**
 * Executes business logic
 */
abstract class UseCase<in P, R> {

	/**
	 * Override this to execute business logic
	 *
	 * @param parameters [P] parameters needed to run use case
	 *
	 * @return [IO] with the result
	 */
	abstract suspend fun execute(parameters: P): IO<R>

	/**
	 * Executes business logic asynchronously and returns [IO] with the result [R]
	 *
	 * @param parameters [P] parameters needed to run use case
	 *
	 * @return [IO] with the result
	 */
	suspend operator fun invoke(parameters: P): IO<R> = execute(parameters)
}

suspend operator fun <R> UseCase<Unit, R>.invoke() = this.invoke(Unit)