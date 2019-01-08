package com.marko.data.coins

import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinId

/**
 * Cache layer access point
 */
interface CoinsCacheRepository {

	/**
	 * Save coins into database
	 *
	 * @param coins [CoinData] [List] that should be saved into database
	 */
	suspend fun saveCoins(coins: List<CoinData>)

	/**
	 * Save coin
	 *
	 * @param coin [CoinData] list of coins that should be saved into database
	 */
	suspend fun saveCoin(coin: CoinData)

	/**
	 * Query coins from database
	 *
	 * @return [CoinData] [List]
	 */
	suspend fun queryCoins(): List<CoinData>

	/**
	 * Query coin from database
	 *
	 * @param coinId [CoinId]
	 *
	 * @return [CoinData]
	 */
	suspend fun queryCoin(coinId: CoinId): CoinData
}