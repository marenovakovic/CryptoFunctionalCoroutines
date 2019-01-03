package com.marko.presentation.coins

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.effects.IO
import com.marko.domain.entities.CoinEntity
import com.marko.domain.usecases.FetchCoins
import com.marko.domain.usecases.invoke
import com.marko.presentation.PresentationCoinsFactory
import com.marko.presentation.dispatchers.TestCoroutineDispatchers
import com.marko.presentation.entities.Coin
import com.marko.presentation.mappers.toEntity
import com.marko.presentation.result.Result
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CoinsViewModelTest {

	@get:Rule
	val rule = InstantTaskExecutorRule()

	private val dispatchers = TestCoroutineDispatchers()
	private val fetchCoins = mockk<FetchCoins>()
	private val viewModel = CoinsViewModel(dispatchers = dispatchers, fetchCoins = fetchCoins)

	@Test
	fun `test fetching result and does view model calls use case`() {
		val observer = mockObserver<Result<List<Coin>>>().stubObserving()
		viewModel.result.observeForever(observer)

		val coins = PresentationCoinsFactory.coins
		stubCoins(coins.toEntity())

		viewModel.fetch()

		verify(exactly = 1) { observer.onChanged(Result.Success(coins)) }
		coVerify(exactly = 1) { fetchCoins() }
	}

	@Test
	fun `test fetching when error occurs`() {
		val observer = mockObserver<Result<List<Coin>>>().stubObserving()
		viewModel.result.observeForever(observer)

		val throwable = Throwable("jeb' se")
		stubThrow(throwable)

		viewModel.fetch()

		verify(exactly = 1) { observer.onChanged(Result.Error(throwable)) }
		coVerify(exactly = 1) { fetchCoins() }
	}

	private fun stubCoins(coins: List<CoinEntity>) {

	}

	private fun stubThrow(throwable: Throwable) {
		coEvery { fetchCoins() } throws throwable
	}

	private fun <T> mockObserver() = mockk<Observer<T>>()

	private inline fun <reified T : Any> Observer<T>.stubObserving(): Observer<T> {
		every { onChanged(any()) } returns Unit

		return this
	}
}