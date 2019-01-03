package com.marko.data.coins

import arrow.effects.IO
import com.marko.data.entities.CoinData
import com.marko.data.factory.DataCoinFactory
import com.marko.data.mappers.toEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class CoinsRepositoryImplTest {

	private val remoteDataSource = mockk<CoinsDataSource>()
	private val cacheDataSource = mockk<CoinsDataSource>()
	private val coinsRepository =
		CoinsRepositoryImpl(remoteDataSource = remoteDataSource, cacheDataSource = cacheDataSource)

	@Test
	fun `test coins repository getCoins result and does it call data sources`() {
		val coins = DataCoinFactory.coinDatas
		remoteDataSource.stubCoins(coins)
		cacheDataSource.stubCoins(coins)
		stubSave()

		val result = coinsRepository.getCoins().unsafeRunSync()

		assert(result == coins.toEntity())
		coVerify(exactly = 1) { remoteDataSource.getCoins() }
		coVerify(exactly = 0) { cacheDataSource.getCoins() }
	}

	@Test
	fun `test coins repository fetCoins result when error occurs and is cache data source called in that case`() {
		val throwable = Throwable("jeb' se")
		remoteDataSource.stubThrow(throwable)

		val coins = DataCoinFactory.coinDatas
		cacheDataSource.stubCoins(coins)

		val result = coinsRepository.getCoins().unsafeRunSync()
		assert(result == coins.toEntity())
		coVerify(exactly = 1) { remoteDataSource.getCoins() }
		coVerify(exactly = 1) { cacheDataSource.getCoins() }
	}

	@Test
	fun `test coins repository getCoin result, does it saved coin with empty logo and does it call data source`() {
		val coinId = 1

		val coin = DataCoinFactory.createCoinData(id = coinId, logo = "")
		remoteDataSource.stubCoin(coin)
		cacheDataSource.stubCoin(coin)
		stubSave()

		val result = coinsRepository.getCoin(coinId = coinId).unsafeRunSync()

		assert(result == coin.toEntity())
		coVerify(exactly = 1) { cacheDataSource.getCoin(coinId = coinId) }
		coVerify(exactly = 1) { remoteDataSource.getCoin(coinId = coinId) }
		coVerify(exactly = 1) { cacheDataSource.saveCoin(coin = coin) }
	}

	@Test
	fun `test coins repository getCoin result, does it saved coin with NON empty logo and does it call data source`() {
		val coinId = 1

		val coin = DataCoinFactory.createCoinData(id = coinId)
		cacheDataSource.stubCoin(coin)

		val result = coinsRepository.getCoin(coinId = coinId).unsafeRunSync()

		assert(result == coin.toEntity())
		coVerify(exactly = 1) { cacheDataSource.getCoin(coinId = coinId) }
		coVerify(exactly = 0) { remoteDataSource.getCoin(coinId = coinId) }
		coVerify(exactly = 0) { cacheDataSource.saveCoin(coin = coin) }
	}

	private fun CoinsDataSource.stubCoins(coins: List<CoinData>) {
		coEvery { getCoins() } returns IO.just(coins)
	}

	private fun CoinsDataSource.stubCoin(coin: CoinData) {
		coEvery { getCoin(coinId = any()) } returns IO.just(coin)
	}

	private fun stubSave() {
		every { cacheDataSource.saveCoins(any()) } returns Unit
		every { cacheDataSource.saveCoin(any()) } returns Unit
	}

	private fun CoinsDataSource.stubThrow(throwable: Throwable) {
		coEvery { getCoins() } throws throwable
	}
}