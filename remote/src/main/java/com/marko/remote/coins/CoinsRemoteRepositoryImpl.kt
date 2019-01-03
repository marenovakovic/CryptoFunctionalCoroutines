package com.marko.remote.coins

import arrow.effects.IO
import arrow.effects.fix
import arrow.effects.instances.io.async.async
import com.marko.data.coins.CoinsRemoteRepository
import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinId
import com.marko.remote.mappers.toData
import javax.inject.Inject

class CoinsRemoteRepositoryImpl @Inject constructor(
	private val coinsService: CoinsService
) : CoinsRemoteRepository {

	override fun fetchCoins(): IO<List<CoinData>> =
		coinsService.getCoins()
			.async(IO.async())
			.fix()
			.flatMap {
				if (it.isSuccessful) IO.just(it.body() !!.coins)
				else IO.raiseError(Throwable(message = it.message()))
			}
			.map { it.toData() }

	override fun fetchCoin(coinId: CoinId): IO<CoinData> =
		coinsService.getCoinDetails(coinId = coinId)
			.async(IO.async())
			.fix()
			.flatMap {
				if (it.isSuccessful) IO.just(it.body() !!.coin)
				else IO.raiseError(Throwable(message = it.message()))
			}
			.map { it.toData() }
}