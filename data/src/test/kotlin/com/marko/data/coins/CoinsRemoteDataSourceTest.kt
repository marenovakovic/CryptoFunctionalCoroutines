package com.marko.data.coins

import com.marko.data.entities.CoinData
import com.marko.data.factory.DataCoinFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CoinsRemoteDataSourceTest {

	private val remoteRepository = mockk<CoinsRemoteRepository>()
	private val remoteDataSource = CoinsRemoteDataSource(remoteRepository)

	@Test
	fun `test remote data source fetchCoins result and does it call remote repository`() =
		runBlocking {
			val coins = DataCoinFactory.coinDatas
			stubCoins(coins)

			val result = remoteDataSource.getCoins()

			assert(result == coins)
			coVerify(exactly = 1) { remoteDataSource.getCoins() }
		}

	@Test
	fun `test is exception thrown when trying to save coins`() {
		assertThrows<Exception> {
			runBlocking { remoteDataSource.saveCoins(DataCoinFactory.coinDatas) }
		}
	}

	@Test
	fun `test remote data source fetchCoin result and does it call remote repository`() =
		runBlocking {
			val coinId = 1

			val coin = DataCoinFactory.createCoinData(id = coinId)
			stubCoin(coin)

			val result = remoteDataSource.getCoin(coinId = coinId)

			assert(result == coin)
			coVerify(exactly = 1) { remoteRepository.fetchCoin(coinId = coinId) }
		}

	private fun stubCoins(coins: List<CoinData>) {
		coEvery { remoteRepository.fetchCoins() } returns coins
	}

	private fun stubCoin(coin: CoinData) {
		coEvery { remoteRepository.fetchCoin(coinId = any()) } returns coin
	}
}