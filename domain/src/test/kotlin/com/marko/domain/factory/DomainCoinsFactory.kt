package com.marko.domain.factory

import com.marko.domain.entities.CoinEntity

object DomainCoinsFactory {

	fun createCoinEntity(
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
	): CoinEntity = CoinEntity(
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

	val coinEntities: List<CoinEntity> = listOf(createCoinEntity(), createCoinEntity())
}