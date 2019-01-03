package com.marko.remote.coins

import arrow.integrations.retrofit.adapter.CallK
import com.marko.remote.entities.CoinRemote
import com.marko.remote.entities.CoinResponse
import com.marko.remote.entities.CoinStatus
import com.marko.remote.entities.CoinsResponse
import com.marko.remote.factory.RemoteCoinFactory
import com.marko.remote.mappers.toData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import org.junit.jupiter.api.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class CoinsRemoteRepositoryImplTest {

	private val coinsService = mockk<CoinsService>()
	private val remoteRepository = CoinsRemoteRepositoryImpl(coinsService)

	@Test
	fun `test remote repository fetchCoins result`() = runBlocking {
		val coins = RemoteCoinFactory.coinRemotes
		stubCoins(coins)

		val result = remoteRepository.fetchCoins().unsafeRunSync()

		assert(result == coins.toData())
		coVerify(exactly = 1) { coinsService.getCoins() }
	}

	@Test
	fun `test remote repository fetchCoin result`() = runBlocking {
		val coinId = 1

		val coin = RemoteCoinFactory.createCoinRemote(id = 1)
		stubCoin(coin)

		val result = remoteRepository.fetchCoin(coinId = coinId).unsafeRunSync()

		assert(result == coin.toData())
		coVerify(exactly = 1) { coinsService.getCoinDetails(coinId = coinId) }
	}

	private fun stubCoins(coins: List<CoinRemote>) {
		val response = CoinsResponse(coins = coins, status = CoinStatus())
		val call = object : Call<CoinsResponse> {
			override fun enqueue(callback: Callback<CoinsResponse>) =
				callback.onResponse(this, this.execute())

			override fun isExecuted(): Boolean = true
			override fun clone(): Call<CoinsResponse> = this
			override fun isCanceled(): Boolean = false
			override fun cancel() = Unit
			override fun execute(): Response<CoinsResponse> = Response.success(response)
			override fun request(): Request = Request.Builder().build()
		}
		val callK = CallK(call)
		coEvery { coinsService.getCoins() } returns callK
	}

	private fun stubCoin(coin: CoinRemote) {
		val response = CoinResponse(coin = coin, status = CoinStatus())
		val call = object : Call<CoinResponse> {
			override fun enqueue(callback: Callback<CoinResponse>) =
				callback.onResponse(this, this.execute())

			override fun isExecuted(): Boolean = true
			override fun clone(): Call<CoinResponse> = this
			override fun isCanceled(): Boolean = false
			override fun cancel() = Unit
			override fun execute(): Response<CoinResponse> = Response.success(response)
			override fun request(): Request = Request.Builder().build()
		}
		val callK = CallK(call)
		coEvery { coinsService.getCoinDetails(any(), any()) } returns callK
	}
}