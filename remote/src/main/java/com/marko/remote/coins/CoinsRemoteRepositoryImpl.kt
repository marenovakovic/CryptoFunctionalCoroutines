package com.marko.remote.coins

import com.marko.data.coins.CoinsRemoteRepository
import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinId
import com.marko.remote.mappers.toData
import javax.inject.Inject

class CoinsRemoteRepositoryImpl @Inject constructor(
	private val coinsService: CoinsService
) : CoinsRemoteRepository {

	override suspend fun fetchCoins(): List<CoinData> = coinsService.getCoins().await().coins.toData()

	override suspend fun fetchCoin(coinId: CoinId): CoinData =
		coinsService.getCoinDetails(coinId = coinId).await().coin.toData()
}