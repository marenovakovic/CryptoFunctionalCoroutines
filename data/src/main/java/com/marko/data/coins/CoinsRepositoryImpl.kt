package com.marko.data.coins

import arrow.core.Try
import arrow.instances.`try`.foldable.find
import arrow.instances.`try`.monad.monad
import arrow.instances.`try`.monadError.monadError
import arrow.instances.list.monad.flatMap
import arrow.typeclasses.bindingCatch
import com.marko.data.injection.DI
import com.marko.data.mappers.toEntity
import com.marko.domain.coins.CoinsRepository
import com.marko.domain.entities.CoinEntity
import com.marko.domain.entities.CoinId
import com.marko.domain.operations.SuspendedOperation
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

	override suspend fun getCoins(): SuspendedOperation<List<CoinEntity>> = {
		Try {
			val result = remoteDataSource.getCoins()
			result
		}
			.fold(
			{ cacheDataSource.getCoins() },
			{ it }
		)
			.toEntity()

//		println(Thread.currentThread().name)
//		try {
//			println(Thread.currentThread().name)
//			val coins = remoteDataSource.getCoins()
//			cacheDataSource.saveCoins(coins)
//			coins.toEntity()
//		} catch (t: Throwable) {
//			t.printStackTrace()
//			cacheDataSource.getCoins().toEntity()
//		}
	}

	/**
	 * Check cache source for coin and if it has logo URL return it
	 * If if doesn't have logo URL fetch coin, save it into database and return coin with logo URL
	 */
	override suspend fun getCoin(coinId: CoinId): SuspendedOperation<CoinEntity> {
		val cachedCoin = cacheDataSource.getCoin(coinId = coinId)
		if (cachedCoin.logo.isNotEmpty()) {
			return { cachedCoin.toEntity() }
		}

		val fetchedCoin = remoteDataSource.getCoin(coinId = coinId)
		cacheDataSource.saveCoin(cachedCoin.copy(logo = fetchedCoin.logo))

		return { cachedCoin.copy(logo = fetchedCoin.logo).toEntity() }
	}
}