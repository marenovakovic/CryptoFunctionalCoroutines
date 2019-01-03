package com.marko.data.coins

import arrow.effects.IO
import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinId

/**
 * Remote layer access point
 */
interface CoinsRemoteRepository {

	/**
	 * Fetch coins from API
	 *
	 * @return [IO] containing fetched coins [CoinData] [List]
	 */
	fun fetchCoins(): IO<List<CoinData>>

	/**
	 * Fetch coin from API
	 *
	 * @param coinId [CoinId]
	 *
	 * @return [IO] containing fetched coins [CoinData]
	 */
	fun fetchCoin(coinId: CoinId): IO<CoinData>
}