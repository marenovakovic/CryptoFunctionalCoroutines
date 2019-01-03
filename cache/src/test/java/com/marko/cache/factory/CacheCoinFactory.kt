package com.marko.cache.factory

import com.marko.cache.entities.CoinCache
import com.marko.data.entities.CoinData

object CacheCoinFactory {

	fun createCoinData(
		id: Int = 1,
		name: String = "Bitcoin",
		symbol: String = "BTC",
		logo: String = "",
		price: Float = 0.0f,
		priceInBTC: Float = 0.0f,
		inExistenceSupply: Long = 1L,
		circulatingSupply: Long = 1L,
		maxSupply: Long = 1L,
		tradedIn24h: Long = 1L
	): CoinData = CoinData(
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

	val coinDatas: List<CoinData> = listOf(createCoinData(), createCoinData())

	fun createCoinCache(
		id: Int = 1,
		name: String = "Bitcoin",
		symbol: String = "BTC",
		logo: String = "",
		price: Float = 0.0f,
		priceInBTC: Float = 0.0f,
		inExistenceSupply: Long = 1L,
		circulatingSupply: Long = 1L,
		maxSupply: Long = 1L,
		tradedIn24h: Long = 1L
	): CoinCache = CoinCache(
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

	val coinCaches: List<CoinCache> = listOf(createCoinCache(), createCoinCache())
}