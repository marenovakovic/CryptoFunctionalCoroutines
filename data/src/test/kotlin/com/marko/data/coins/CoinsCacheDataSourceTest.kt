package com.marko.data.coins

import com.marko.data.entities.CoinData
import com.marko.data.factory.DataCoinFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class CoinsCacheDataSourceTest {

	private val repository = mockk<CoinsCacheRepository>()
	private val cacheDataSource = CoinsCacheDataSource(cacheRepository = repository)

	@Test
	fun `test cache data source queryCoins and does it call repository`() = runBlocking {
		val coins = DataCoinFactory.coinDatas
		stubCoins(coins)

		val result = cacheDataSource.getCoins()

		assert(coins == result)
		coVerify(exactly = 1) { repository.queryCoins() }
	}

	@Test
	fun `test save coins`() = runBlocking {
		val coins = DataCoinFactory.coinDatas

		stubSave()

		cacheDataSource.saveCoins(coins)

		coVerify(exactly = 1) { repository.saveCoins(coins) }
	}

	@Test
	fun `test cache data source getCoin and does it call repository`() = runBlocking {
		val coinId = 1

		val coin = DataCoinFactory.createCoinData(id = coinId)
		stubCoin(coin)

		val result = cacheDataSource.getCoin(coinId = coinId)

		assert(result == coin)
		coVerify(exactly = 1) { repository.queryCoin(coinId = coinId) }
	}

	private fun stubCoins(coins: List<CoinData>) {
		coEvery { repository.queryCoins() } returns coins
	}

	private fun stubCoin(coin: CoinData) {
		coEvery { repository.queryCoin(coinId = any()) } returns coin
	}

	private fun stubSave() {
		coEvery { repository.saveCoins(any()) } returns Unit
	}
}