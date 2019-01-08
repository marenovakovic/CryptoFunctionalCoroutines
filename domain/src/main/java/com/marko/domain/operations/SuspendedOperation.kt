package com.marko.domain.operations

/**
 * [suspend] function that returns result
 */
// TODO replace with async/await
typealias SuspendedOperation<A> = suspend () -> A