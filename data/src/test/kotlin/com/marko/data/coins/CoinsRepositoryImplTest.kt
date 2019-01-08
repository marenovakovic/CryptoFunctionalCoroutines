package com.marko.data.coins

import com.marko.data.entities.CoinData
import com.marko.data.factory.DataCoinFactory
import com.marko.data.mappers.toEntity
import com.marko.domain.usecases.safeRun
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class CoinsRepositoryImplTest {

	private val remoteDataSource = mockk<CoinsDataSource>()
	private val cacheDataSource = mockk<CoinsDataSource>()
	private val coinsRepository =
		CoinsRepositoryImpl(remoteDataSource = remoteDataSource, cacheDataSource = cacheDataSource)

	@Test
	fun `test coins repository getCoins result and does it call data sources`() = runBlocking {
		val coins = DataCoinFactory.coinDatas
		remoteDataSource.stubCoins(coins)
		cacheDataSource.stubCoins(coins)
		stubSave()

		val result = coinsRepository.getCoins().safeRun()

		assert(result == coins.toEntity())
		coVerify(exactly = 1) { remoteDataSource.getCoins() }
		coVerify(exactly = 0) { cacheDataSource.getCoins() }
	}

	@Test
	fun `test coins repository fetCoins result when error occurs and is cache data source called in that case`() =
		runBlocking {
			val throwable = Throwable("jeb' se")
			remoteDataSource.stubThrow(throwable)

			val coins = DataCoinFactory.coinDatas
			cacheDataSource.stubCoins(coins)

			val result = coinsRepository.getCoins().safeRun()
			assert(result == coins.toEntity())
			coVerify(exactly = 1) { remoteDataSource.getCoins() }
			coVerify(exactly = 1) { cacheDataSource.getCoins() }
		}

	@Test
	fun `test coins repository getCoin result, does it saved coin with empty logo and does it call data source`() =
		runBlocking {
			val coinId = 1

			val coin = DataCoinFactory.createCoinData(id = coinId, logo = "")
			remoteDataSource.stubCoin(coin)
			cacheDataSource.stubCoin(coin)
			stubSave()

			val result = coinsRepository.getCoin(coinId = coinId).safeRun()

			assert(result == coin.toEntity())
			coVerify(exactly = 1) { cacheDataSource.getCoin(coinId = coinId) }
			coVerify(exactly = 1) { remoteDataSource.getCoin(coinId = coinId) }
			coVerify(exactly = 1) { cacheDataSource.saveCoin(coin = coin) }
		}

	@Test
	fun `test coins repository getCoin result, does it returns saved coin with NON empty logo and does it call data source`() =
		runBlocking {
			val coinId = 1

			val coin = DataCoinFactory.createCoinData(id = coinId, logo = "logo")
			remoteDataSource.stubCoin(coin)
			cacheDataSource.stubCoin(coin)
			stubSave()

			val result = coinsRepository.getCoin(coinId = coinId).safeRun()

			assert(result == coin.toEntity())
			coVerify(exactly = 0) { remoteDataSource.getCoin(coinId = coinId) }
			coVerify(exactly = 1) { cacheDataSource.getCoin(coinId = coinId) }
			coVerify(exactly = 0) { cacheDataSource.saveCoin(coin = coin) }
		}

	@Test
	fun `test coins repository getCoin result, does it returns saved coin with empty logo and does it call data source`() =
		runBlocking {
			val coinId = 1

			val coin = DataCoinFactory.createCoinData(id = coinId, logo = "")
			remoteDataSource.stubCoin(coin)
			cacheDataSource.stubCoin(coin)
			stubSave()

			val result = coinsRepository.getCoin(coinId = coinId).safeRun()

			assert(result == coin.toEntity())
			coVerify(exactly = 1) { remoteDataSource.getCoin(coinId = coinId) }
			coVerify(exactly = 1) { cacheDataSource.getCoin(coinId = coinId) }
			coVerify(exactly = 1) { cacheDataSource.saveCoin(coin = coin) }
		}

	private fun CoinsDataSource.stubCoins(coins: List<CoinData>) {
		coEvery { getCoins() } returns coins
	}

	private fun CoinsDataSource.stubCoin(coin: CoinData) {
		coEvery { getCoin(coinId = any()) } returns coin
	}

	private fun stubSave() {
		coEvery { cacheDataSource.saveCoins(any()) } returns Unit
		coEvery { cacheDataSource.saveCoin(any()) } returns Unit
	}

	private fun CoinsDataSource.stubThrow(throwable: Throwable) {
		coEvery { getCoins() } throws throwable
	}
}