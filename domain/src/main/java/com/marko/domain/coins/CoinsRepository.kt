package com.marko.domain.coins

import arrow.effects.IO
import com.marko.domain.entities.CoinEntity
import com.marko.domain.entities.CoinId

/**
 * Point of access to [CoinEntity]
 */
interface CoinsRepository {

	/**
	 * Get [CoinEntity] [List]
	 *
	 * @return [IO] containing [CoinEntity] [List]
	 */
	fun getCoins(): IO<List<CoinEntity>>

	/**
	 * Get single [CoinEntity]
	 *
	 * @param coinId [CoinId]
	 *
	 * @return [IO] containing [CoinEntity]
	 */
	fun getCoin(coinId: CoinId): IO<CoinEntity>
}