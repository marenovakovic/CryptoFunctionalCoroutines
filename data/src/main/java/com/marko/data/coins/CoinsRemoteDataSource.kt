package com.marko.data.coins

import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinId
import javax.inject.Inject

/**
 * Data source communicating with remote layer
 *
 * @param coinsRemoteRepository [CoinsRemoteRepository]
 */
class CoinsRemoteDataSource @Inject constructor(
	private val coinsRemoteRepository: CoinsRemoteRepository
) : CoinsDataSource {

	override suspend fun saveCoins(coins: List<CoinData>) =
		throw IllegalArgumentException("CoinsRemoteDataSource does not support caching")

	override suspend fun saveCoin(coin: CoinData) =
		throw IllegalArgumentException("CoinsRemoteDataSource does not support caching")

	override suspend fun getCoins(): List<CoinData> = coinsRemoteRepository.fetchCoins()

	override suspend fun getCoin(coinId: CoinId): CoinData =
		coinsRemoteRepository.fetchCoin(coinId = coinId)
}