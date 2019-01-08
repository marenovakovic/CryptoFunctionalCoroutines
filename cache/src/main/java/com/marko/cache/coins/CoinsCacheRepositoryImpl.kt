package com.marko.cache.coins

import com.marko.cache.mappers.toCache
import com.marko.cache.mappers.toData
import com.marko.data.coins.CoinsCacheRepository
import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinId
import javax.inject.Inject

class CoinsCacheRepositoryImpl @Inject constructor(
	private val coinsDao: CoinsDao
) : CoinsCacheRepository {

	override suspend fun saveCoins(coins: List<CoinData>) =
		coinsDao.insertCoins(coins = coins.toCache())

	override suspend fun saveCoin(coin: CoinData) = coinsDao.insertCoin(coin = coin.toCache())

	override suspend fun queryCoins(): List<CoinData> = coinsDao.queryCoins().toData()

	override suspend fun queryCoin(coinId: CoinId): CoinData =
		coinsDao.queryCoin(coinId = coinId).toData()
}