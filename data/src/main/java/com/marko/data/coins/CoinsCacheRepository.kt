package com.marko.data.coins

import arrow.effects.IO
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
	fun saveCoins(coins: List<CoinData>)

	/**
	 * Save coin
	 *
	 * @param coin [CoinData] list of coins that should be saved into database
	 */
	fun saveCoin(coin: CoinData)

	/**
	 * Query coins from database
	 *
	 * @return [IO] containing queried coins [CoinData] [List]
	 */
	fun queryCoins(): IO<List<CoinData>>

	/**
	 * Query coin from database
	 *
	 * @param coinId [CoinId]
	 *
	 * @return [IO] containing queried coins [CoinData]
	 */
	fun queryCoin(coinId: CoinId): IO<CoinData>
}