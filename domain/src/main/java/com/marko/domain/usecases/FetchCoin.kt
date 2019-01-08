package com.marko.domain.usecases

import com.marko.domain.coins.CoinsRepository
import com.marko.domain.entities.CoinEntity
import com.marko.domain.entities.CoinId
import com.marko.domain.operations.SuspendedOperation
import javax.inject.Inject

/**
 * Executed business logic for fetching [CoinEntity]
 *
 * @param coinsRepository [CoinsRepository] data layer access point
 */
class FetchCoin @Inject constructor(
	private val coinsRepository: CoinsRepository
) : UseCase<CoinId, CoinEntity>() {

	override suspend fun execute(parameters: CoinId): SuspendedOperation<CoinEntity> =
		coinsRepository.getCoin(coinId = parameters)
}