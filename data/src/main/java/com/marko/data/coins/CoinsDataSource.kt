package com.marko.data.coins

import arrow.effects.IO
import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinId

/**
 * Data source that defines common functionality that repository needs
 */
interface CoinsDataSource {

	/**
	 * Save coins
	 *
	 * @param coins [CoinData] [List] list of coins that should be saved
	 */
	suspend fun saveCoins(coins: List<CoinData>)

	/**
	 * Save coin
	 *
	 * @param coin [CoinData] list of coins that should be saved
	 */
	suspend fun saveCoin(coin: CoinData)

	/**
	 * Get coins
	 *
	 * @return [IO] containing fetched [CoinData] [List]
	 */
	suspend fun getCoins(): List<CoinData>

	/**
	 * Get single coin
	 *
	 * @param coinId [CoinId]
	 *
	 * @return [IO] containing [CoinData]
	 */
	suspend fun getCoin(coinId: CoinId): CoinData
}