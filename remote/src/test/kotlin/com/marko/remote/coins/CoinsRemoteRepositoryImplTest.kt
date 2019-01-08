package com.marko.remote.coins

import com.marko.remote.entities.CoinRemote
import com.marko.remote.entities.CoinResponse
import com.marko.remote.entities.CoinStatus
import com.marko.remote.entities.CoinsResponse
import com.marko.remote.factory.RemoteCoinFactory
import com.marko.remote.mappers.toData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class CoinsRemoteRepositoryImplTest {

	private val coinsService = mockk<CoinsService>()
	private val remoteRepository = CoinsRemoteRepositoryImpl(coinsService)

	@Test
	fun `test remote repository fetchCoins result`() = runBlocking {
		val coins = RemoteCoinFactory.coinRemotes
		stubCoins(coins)

		val result = remoteRepository.fetchCoins()

		assert(result == coins.toData())
		coVerify(exactly = 1) { coinsService.getCoins() }
	}

	@Test
	fun `test remote repository fetchCoin result`() = runBlocking {
		val coinId = 1

		val coin = RemoteCoinFactory.createCoinRemote(id = 1)
		stubCoin(coin)

		val result = remoteRepository.fetchCoin(coinId = coinId)

		assert(result == coin.toData())
		coVerify(exactly = 1) { coinsService.getCoinDetails(coinId = coinId) }
	}

	private fun stubCoins(coins: List<CoinRemote>) {
		val response = CoinsResponse(coins = coins, status = CoinStatus())
		coEvery { coinsService.getCoins() } returns CompletableDeferred(response)
	}

	private fun stubCoin(coin: CoinRemote) {
		val response = CoinResponse(coin = coin, status = CoinStatus())
		coEvery { coinsService.getCoinDetails(any(), any()) } returns CompletableDeferred(response)
	}
}