package com.marko.cache.coins

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marko.cache.entities.CoinCache
import com.marko.domain.entities.CoinId

/**
 * [Dao] for interacting with database for [CoinCache] related actions
 */
@Dao
interface CoinsDao {

	/**
	 * Insert multiple [CoinCache] into the database
	 *
	 * @param coins [CoinCache] [List] to be inserted into the database
	 */
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertCoins(coins: List<CoinCache>)

	/**
	 * Insert single [CoinCache] into the database
	 *
	 * @param coin [CoinCache] to be inserted into the database
	 */
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertCoin(coin: CoinCache)

	/**
	 * Query all [CoinCache] from the database
	 *
	 * @return [CoinCache] [List]
	 */
	@Query("SELECT * FROM coins")
	fun queryCoins(): List<CoinCache>

	/**
	 * Query single [CoinCache] from the database
	 *
	 * @param coinId [CoinId]
	 *
	 * @return [CoinCache]
	 */
	@Query("SELECT * FROM coins WHERE id = :coinId")
	fun queryCoin(coinId: CoinId): CoinCache
}