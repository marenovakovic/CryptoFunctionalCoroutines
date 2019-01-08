package com.marko.cache.coins

import com.marko.cache.entities.CoinCache
import com.marko.cache.factory.CacheCoinFactory
import com.marko.cache.mappers.toCache
import com.marko.cache.mappers.toData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class CoinsCacheRepositoryImplTest {

	private val coinsDao = mockk<CoinsDao>()
	private val repository = CoinsCacheRepositoryImpl(coinsDao = coinsDao)

	@Test
	fun `test cache repository cache coins result and does it call coinsDao`() = runBlocking {
		val coins = CacheCoinFactory.coinCaches
		stubCoins(coins)

		val result = repository.queryCoins()

		assert(result == coins.toData())
		verify(exactly = 1) { coinsDao.queryCoins() }
	}

	@Test
	fun `test cache repository save coins`() = runBlocking {
		val coins = CacheCoinFactory.coinDatas
		stubSave()

		repository.saveCoins(coins)

		verify(exactly = 1) { coinsDao.insertCoins(coins.toCache()) }
	}

	@Test
	fun `test cache repository save coin`() = runBlocking {
		val coins = CacheCoinFactory.createCoinData()
		stubSave()

		repository.saveCoin(coins)

		verify(exactly = 1) { coinsDao.insertCoin(coins.toCache()) }
	}

	@Test
	fun `test cache repository queryCoin result and does it call coinsDao`() = runBlocking {
		val coinId = 1

		val coin = CacheCoinFactory.createCoinCache(id = coinId)
		stubCoin(coin)

		val result = repository.queryCoin(coinId = coinId)

		assert(result == coin.toData())
		verify(exactly = 1) { coinsDao.queryCoin(coinId = coinId) }
	}

	private fun stubCoins(coins: List<CoinCache>) {
		every { coinsDao.queryCoins() } returns coins
	}

	private fun stubCoin(coin: CoinCache) {
		every { coinsDao.queryCoin(coinId = any()) } returns coin
	}

	private fun stubSave() {
		every { coinsDao.insertCoins(any()) } returns Unit
		every { coinsDao.insertCoin(any()) } returns Unit
	}
}