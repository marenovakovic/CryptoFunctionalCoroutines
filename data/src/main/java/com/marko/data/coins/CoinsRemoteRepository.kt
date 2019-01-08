package com.marko.data.coins

import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinId

/**
 * Remote layer access point
 */
interface CoinsRemoteRepository {

	/**
	 * Fetch coins from API
	 *
	 * @return [CoinData] [List]
	 */
	suspend fun fetchCoins(): List<CoinData>

	/**
	 * Fetch coin from API
	 *
	 * @param coinId [CoinId]
	 *
	 * @return [CoinData]
	 */
	suspend fun fetchCoin(coinId: CoinId): CoinData
}