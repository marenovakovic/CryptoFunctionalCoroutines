package com.marko.domain.usecases

import arrow.effects.IO
import com.marko.domain.coins.CoinsRepository
import com.marko.domain.entities.CoinEntity
import com.marko.domain.factory.DomainCoinsFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class FetchCoinTest {

	private val repository = mockk<CoinsRepository>()
	private val fetchCoin = FetchCoin(repository)

	@Test
	fun `check fetchCoin result and does it call repository`() = runBlocking {
		val coinId = 1

		val coin = DomainCoinsFactory.createCoinEntity(id = coinId)
		stubCoin(coin)

		val result = fetchCoin(coinId).unsafeRunSync()

		assert(result == coin)
		coVerify(exactly = 1) { repository.getCoin(coinId = coinId) }
	}

	private fun stubCoin(coin: CoinEntity) {
		coEvery { repository.getCoin(any()) } returns IO.just(coin)
	}
}