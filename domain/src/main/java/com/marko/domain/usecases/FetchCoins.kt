package com.marko.domain.usecases

import arrow.effects.IO
import com.marko.domain.coins.CoinsRepository
import com.marko.domain.entities.CoinEntity
import javax.inject.Inject

/**
 * Executes business logic for fetching [CoinEntity]s
 *
 * @param coinsRepository [CoinsRepository] data layer access point
 */
class FetchCoins @Inject constructor(
	private val coinsRepository: CoinsRepository
) : UseCase<Unit, List<CoinEntity>>() {

	override suspend fun execute(parameters: Unit): IO<List<CoinEntity>> =
		coinsRepository.getCoins()
}