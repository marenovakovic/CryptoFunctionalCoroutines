package com.marko.domain.coins

import com.marko.domain.entities.CoinEntity
import com.marko.domain.entities.CoinId
import com.marko.domain.operations.SuspendedOperation

/**
 * Point of access to [CoinEntity]
 */
interface CoinsRepository {

	/**
	 * Get [CoinEntity] [List]
	 *
	 * @return [SuspendedOperation] containing [CoinEntity] [List]
	 */
	suspend fun getCoins(): SuspendedOperation<List<CoinEntity>>

	/**
	 * Get single [CoinEntity]
	 *
	 * @param coinId [CoinId]
	 *
	 * @return [SuspendedOperation] containing [CoinEntity]
	 */
	suspend fun getCoin(coinId: CoinId): SuspendedOperation<CoinEntity>
}