package com.marko.data.coins

import arrow.effects.IO
import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinId
import javax.inject.Inject

class CoinsCacheDataSource @Inject constructor(
	private val cacheRepository: CoinsCacheRepository
) : CoinsDataSource {

	override fun saveCoins(coins: List<CoinData>) = cacheRepository.saveCoins(coins)

	override fun saveCoin(coin: CoinData) = cacheRepository.saveCoin(coin = coin)

	override fun getCoins(): IO<List<CoinData>> = cacheRepository.queryCoins()

	override fun getCoin(coinId: CoinId): IO<CoinData> = cacheRepository.queryCoin(coinId = coinId)
}