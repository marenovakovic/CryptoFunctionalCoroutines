package com.marko.remote.retrofit

import retrofit2.Response

object RetrofitRunner {

	suspend fun <A> execute(request: suspend () -> Response<A>): A {
		val result = request().body() !!
		return result
	}
}