package com.marko.remote.mappers

import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinEntity
import com.marko.remote.entities.CoinRemote

/**
 * Mapping [CoinData] to [CoinRemote]
 *
 * @receiver [CoinData] that is being mapped to [CoinRemote]
 *
 * @return [CoinRemote] mapped from [CoinData]
 */
fun CoinData.toRemote(): CoinRemote = CoinRemote(
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
 * Mapping [CoinData] [List] to [CoinRemote] [List]
 *
 * @receiver [CoinData] [List] that is being mapped to [CoinRemote] [List]
 *
 * @return [CoinRemote] [List] mapped from [CoinData] [List]
 */
fun List<CoinData>.toRemote(): List<CoinRemote> = map { it.toRemote() }

/**
 * Mapping [CoinRemote] to [CoinEntity]
 *
 * @receiver [CoinRemote] that is being mapped to [CoinData]
 *
 * @return [CoinData] mapped from [CoinRemote]
 */
fun CoinRemote.toData(): CoinData = CoinData(
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
 * Mapping [CoinRemote] [List] to [CoinData] [List]
 *
 * @receiver [CoinRemote] [List] that is being mapped to [CoinData] [List]
 *
 * @return [CoinData] [List] mapped from [CoinRemote] [List]
 */
fun List<CoinRemote>.toData(): List<CoinData> = map { it.toData() }