package com.marko.data.coins

import arrow.effects.IO
import arrow.effects.fix
import arrow.effects.instances.io.applicativeError.handleErrorWith
import arrow.effects.instances.io.monadError.monadError
import arrow.typeclasses.bindingCatch
import com.marko.data.injection.DI
import com.marko.data.mappers.toEntity
import com.marko.domain.coins.CoinsRepository
import com.marko.domain.entities.CoinEntity
import com.marko.domain.entities.CoinId
import javax.inject.Inject
import javax.inject.Named

/**
 * [CoinsRepository] implementation. Data layer coins access point
 *
 * @param remoteDataSource [CoinsDataSource] for accessing API
 *
 * @param cacheDataSource [CoinsDataSource] for accessing database
 */
class CoinsRepositoryImpl @Inject constructor(
	@Named(DI.REMOTE_DATA_SOURCE) private val remoteDataSource: CoinsDataSource,
	@Named(DI.CACHE_DATA_SOURCE) private val cacheDataSource: CoinsDataSource
) : CoinsRepository {

	override fun getCoins(): IO<List<CoinEntity>> = IO.monadError().bindingCatch {
		val coins = remoteDataSource.getCoins().bind()
		cacheDataSource.saveCoins(coins)
		coins
	}.fix().handleErrorWith { cacheDataSource.getCoins() }.map { it.toEntity() }

	/**
	 * Check cache source for coin and if it has logo URL return it
	 * If if doesn't have logo URL fetch coin, save it into database and return coin with logo URL
	 */
	override fun getCoin(coinId: CoinId): IO<CoinEntity> = IO.monadError().bindingCatch {
		val cachedCoin = cacheDataSource.getCoin(coinId = coinId).bind()
		if (cachedCoin.logo.isNotEmpty()) return@bindingCatch cachedCoin

		val fetchedCoin = remoteDataSource.getCoin(coinId = coinId).bind()
		cacheDataSource.saveCoin(cachedCoin.copy(logo = fetchedCoin.logo))

		cachedCoin.copy(logo = fetchedCoin.logo)
	}.fix().map { it.toEntity() }
}