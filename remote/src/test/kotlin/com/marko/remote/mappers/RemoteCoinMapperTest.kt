package com.marko.remote.mappers

import com.marko.data.entities.CoinData
import com.marko.remote.entities.CoinRemote
import com.marko.remote.factory.RemoteCoinFactory
import org.junit.jupiter.api.Test

internal class RemoteCoinMapperTest {

	@Test
	fun `test CoinData to CoinRemote mapping`() {
		val coinData = RemoteCoinFactory.createCoinData()
		val coinRemote = coinData.toRemote()

		assertCoins(coinData, coinRemote)
	}

	@Test
	fun `test CoinData list to CoinRemote list mapping`() {
		val coinDatas = RemoteCoinFactory.coinDatas
		val coinRemotes = coinDatas.toRemote()

		assert(coinDatas.size == coinRemotes.size)
		repeat(coinDatas.size) { assertCoins(coinDatas[it], coinRemotes[it]) }
	}

	@Test
	fun `test CoinRemote to CoinData mapping`() {
		val coinRemote = RemoteCoinFactory.createCoinRemote()
		val coinData = coinRemote.toData()

		assertCoins(coinData, coinRemote)
	}

	@Test
	fun `test CoinRemote list to CoinData list mapping`() {
		val coinRemotes = RemoteCoinFactory.coinRemotes
		val coinDatas = coinRemotes.toData()

		assert(coinRemotes.size == coinDatas.size)
		repeat(coinDatas.size) { assertCoins(coinDatas[it], coinRemotes[it]) }
	}

	private fun assertCoins(coinData: CoinData, coinRemote: CoinRemote) {
		assert(coinData.id == coinRemote.id)
		assert(coinData.name == coinRemote.name)
		assert(coinData.symbol == coinRemote.symbol)
		assert(coinData.price == coinRemote.price)
		assert(coinData.priceInBTC == coinRemote.priceInBTC)
		assert(coinData.inExistenceSupply == coinRemote.inExistenceSupply)
		assert(coinData.circulatingSupply == coinRemote.circulatingSupply)
		assert(coinData.maxSupply == coinRemote.maxSupply)
		assert(coinData.tradedIn24h == coinRemote.tradedIn24h)
	}
}