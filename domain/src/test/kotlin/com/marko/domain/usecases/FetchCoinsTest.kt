package com.marko.domain.usecases

import com.marko.domain.coins.CoinsRepository
import com.marko.domain.entities.CoinEntity
import com.marko.domain.factory.DomainCoinsFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class FetchCoinsTest {

	private val repository = mockk<CoinsRepository>()
	private val fetchCoins = FetchCoins(coinsRepository = repository)

	@Test
	fun `does use case calls repository`() = runBlocking {
		val coins = DomainCoinsFactory.coinEntities
		stubCoins(coins)

		fetchCoins().safeRun()

		coVerify(exactly = 1) { repository.getCoins() }
	}

	@Test
	fun `check use case result`() = runBlocking {
		val coins = DomainCoinsFactory.coinEntities
		stubCoins(coins)

		val result = fetchCoins().safeRun()

		assert(result == coins)
		coVerify(exactly = 1) { repository.getCoins() }
	}

	private fun stubCoins(coins: List<CoinEntity>) {
		coEvery { repository.getCoins() } returns { coins }
	}
}