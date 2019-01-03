package com.marko.data.coins

import arrow.effects.IO
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

	override fun saveCoins(coins: List<CoinData>) =
		throw IllegalArgumentException("CoinsRemoteDataSource does not support caching")

	override fun saveCoin(coin: CoinData) =
		throw IllegalArgumentException("CoinsRemoteDataSource does not support caching")

	override fun getCoins(): IO<List<CoinData>> = coinsRemoteRepository.fetchCoins()

	override fun getCoin(coinId: CoinId): IO<CoinData> =
		coinsRemoteRepository.fetchCoin(coinId = coinId)
}