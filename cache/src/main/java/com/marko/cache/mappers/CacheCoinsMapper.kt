package com.marko.cache.mappers

import com.marko.cache.entities.CoinCache
import com.marko.data.entities.CoinData

/**
 * Mapping [CoinData] to [CoinCache]
 *
 * @receiver [CoinData] that is being mapped to [CoinCache]
 *
 * @return [CoinCache] mapped from [CoinData]
 */
fun CoinData.toCache(): CoinCache = CoinCache(
	id = id,
	name = name,
	symbol = symbol,
	logo = logo,
	price = price,
	priceInBTC = priceInBTC,
	inExistenceSupply = inExistenceSupply,
	circulatingSupply = circulatingSupply,
	maxSupply = maxSupply,
	tradedIn24h = tradedIn24h
)

/**
 * Mapping [CoinData] [List] to [CoinCache] [List]
 *
 * @receiver [CoinData] [List] that is being mapped to [CoinCache] [List]
 *
 * @return [CoinCache] [List] mapped from [CoinData] [List]
 */
fun List<CoinData>.toCache(): List<CoinCache> = map { it.toCache() }

/**
 * Mapping [CoinCache] to [CoinData]
 *
 * @receiver [CoinCache] that is being mapped to [CoinData]
 *
 * @return [CoinData] mapped from [CoinCache]
 */
fun CoinCache.toData(): CoinData = CoinData(
	id = id,
	name = name,
	symbol = symbol,
	logo = logo,
	price = price,
	priceInBTC = priceInBTC,
	inExistenceSupply = inExistenceSupply,
	circulatingSupply = circulatingSupply,
	maxSupply = maxSupply,
	tradedIn24h = tradedIn24h
)

/**
 * Mapping [CoinCache] [List] to [CoinData] [List]
 *
 * @receiver [CoinCache] [List] that is being mapped to [CoinData] [List]
 *
 * @return [CoinData] [List] mapped from [CoinCache] [List]
 */
fun List<CoinCache>.toData(): List<CoinData> = map { it.toData() }