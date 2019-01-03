package com.marko.domain.usecases

import arrow.effects.IO
import com.marko.domain.coins.CoinsRepository
import com.marko.domain.entities.CoinEntity
import com.marko.domain.entities.CoinId
import javax.inject.Inject

/**
 * Executed business logic for fetching [CoinEntity]
 *
 * @param coinsRepository [CoinsRepository] data layer access point
 */
class FetchCoin @Inject constructor(
	private val coinsRepository: CoinsRepository
) : UseCase<CoinId, CoinEntity>() {

	override suspend fun execute(parameters: CoinId): IO<CoinEntity> =
		coinsRepository.getCoin(coinId = parameters)
}