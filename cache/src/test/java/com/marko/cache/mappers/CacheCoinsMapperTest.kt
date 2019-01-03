package com.marko.cache.mappers

import com.marko.cache.entities.CoinCache
import com.marko.cache.factory.CacheCoinFactory
import com.marko.data.entities.CoinData
import org.junit.jupiter.api.Test

internal class CacheCoinsMapperTest {

	@Test
	fun `test CoinEntity to CoinCache mapping`() {
		val coinData = CacheCoinFactory.createCoinData()
		val coinCache = coinData.toCache()

		assertCoins(coinData, coinCache)
	}

	@Test
	fun `test CoinEntity list to CoinCache list mapping`() {
		val coinDatas = CacheCoinFactory.coinDatas
		val coinCaches = coinDatas.toCache()

		assert(coinDatas.size == coinCaches.size)
		repeat(coinDatas.size) { assertCoins(coinDatas[it], coinCaches[it]) }
	}

	@Test
	fun `test CoinCache to CoinEntity mapping`() {
		val coinCache = CacheCoinFactory.createCoinCache()
		val coinEntity = coinCache.toData()

		assertCoins(coinEntity, coinCache)
	}

	@Test
	fun `test CoinCache list to CoinEntity list mapping`() {
		val coinCaches = CacheCoinFactory.coinCaches
		val coinEntities = coinCaches.toData()

		assert(coinEntities.size == coinCaches.size)
		repeat(coinEntities.size) { assertCoins(coinEntities[it], coinCaches[it]) }
	}

	private fun assertCoins(coinData: CoinData, coinCache: CoinCache) {
		assert(coinData.id == coinCache.id)
		assert(coinData.name == coinCache.name)
		assert(coinData.symbol == coinCache.symbol)
		assert(coinData.price == coinCache.price)
		assert(coinData.priceInBTC == coinCache.priceInBTC)
		assert(coinData.inExistenceSupply == coinCache.inExistenceSupply)
		assert(coinData.circulatingSupply == coinCache.circulatingSupply)
		assert(coinData.maxSupply == coinCache.maxSupply)
		assert(coinData.tradedIn24h == coinCache.tradedIn24h)
	}
}