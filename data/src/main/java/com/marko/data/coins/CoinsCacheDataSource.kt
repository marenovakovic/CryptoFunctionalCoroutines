package com.marko.data.coins

import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinId
import javax.inject.Inject

class CoinsCacheDataSource @Inject constructor(
	private val cacheRepository: CoinsCacheRepository
) : CoinsDataSource {

	override suspend fun saveCoins(coins: List<CoinData>) = cacheRepository.saveCoins(coins)

	override suspend fun saveCoin(coin: CoinData) = cacheRepository.saveCoin(coin = coin)

	override suspend fun getCoins(): List<CoinData> = cacheRepository.queryCoins()

	override suspend fun getCoin(coinId: CoinId): CoinData =
		cacheRepository.queryCoin(coinId = coinId)
}