package com.marko.presentation.coindetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.effects.IO
import com.marko.domain.entities.CoinEntity
import com.marko.domain.usecases.FetchCoin
import com.marko.presentation.PresentationCoinsFactory
import com.marko.presentation.dispatchers.TestCoroutineDispatchers
import com.marko.presentation.entities.Coin
import com.marko.presentation.mappers.toPresentation
import com.marko.presentation.result.Result
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CoinDetailsViewModelTest {

	@get: Rule
	val rule = InstantTaskExecutorRule()

	private val dispatchers = TestCoroutineDispatchers()
	private val fetchCoin = mockk<FetchCoin>()
	private val viewModel = CoinDetailsViewModel(dispatchers = dispatchers, fetchCoin = fetchCoin)

	@Test
	fun `test CoinDetailsViewModel result when no exception is thrown does it call use case`() {
		val observer = mockObserver<Result<Coin>>().stubObserving()
		viewModel.result.observeForever(observer)

		val coinId = 1
		val coin = PresentationCoinsFactory.createCoinEntity(id = coinId)
		stubCoin(coin)

		viewModel.fetch(coinId = coinId)

		verify(exactly = 1) { observer.onChanged(Result.Success(coin.toPresentation())) }
		coVerify(exactly = 1) { fetchCoin(coinId) }
	}

	@Test
	fun `test CoinDetailsViewModel result when exceptions ARE thrown`() {
		val observer = mockObserver<Result<Coin>>().stubObserving()
		viewModel.result.observeForever(observer)

		val coinId = 1

		val throwable = Throwable("jeb' se")
		stubThrow(throwable)

		viewModel.fetch(coinId = coinId)

		verify(exactly = 1) { observer.onChanged(Result.Error(throwable)) }
		coVerify(exactly = 1) { fetchCoin(coinId) }
	}

	private fun stubCoin(coin: CoinEntity) {
		coEvery { fetchCoin(any()) } returns IO.just(coin)
	}

	private fun stubThrow(throwable: Throwable) {
		coEvery { fetchCoin(any()) } throws throwable
	}

	private fun <T> mockObserver() = mockk<Observer<T>>()

	private inline fun <reified T : Any> Observer<T>.stubObserving(): Observer<T> {
		every { onChanged(any()) } returns Unit

		return this
	}
}