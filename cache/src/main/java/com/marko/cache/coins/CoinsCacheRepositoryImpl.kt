package com.marko.cache.coins

import arrow.effects.IO
import com.marko.cache.mappers.toCache
import com.marko.cache.mappers.toData
import com.marko.data.coins.CoinsCacheRepository
import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinId
import javax.inject.Inject

class CoinsCacheRepositoryImpl @Inject constructor(
	private val coinsDao: CoinsDao
) : CoinsCacheRepository {

	override fun saveCoins(coins: List<CoinData>) = coinsDao.insertCoins(coins = coins.toCache())

	override fun saveCoin(coin: CoinData) = coinsDao.insertCoin(coin = coin.toCache())

	override fun queryCoins(): IO<List<CoinData>> = IO.just(coinsDao.queryCoins().toData())

	override fun queryCoin(coinId: CoinId): IO<CoinData> =
		IO.just(coinsDao.queryCoin(coinId = coinId).toData())
}