package com.marko.presentation.coindetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marko.domain.entities.CoinId
import com.marko.domain.usecases.FetchCoin
import com.marko.domain.usecases.unsafeRun
import com.marko.presentation.base.BaseViewModel
import com.marko.presentation.dispatchers.CoroutineDispatchers
import com.marko.presentation.entities.Coin
import com.marko.presentation.mappers.toPresentation
import com.marko.presentation.result.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Load [Coin] details
 *
 * @param dispatchers [CoroutineDispatchers]
 *
 * @param fetchCoin [FetchCoin]
 */
class CoinDetailsViewModel @Inject constructor(
	private val dispatchers: CoroutineDispatchers,
	private val fetchCoin: FetchCoin
) : BaseViewModel(dispatchers = dispatchers) {

	/**
	 * [MutableLiveData] holding fetched [Coin], exposed as [LiveData]
	 */
	private val _result = MutableLiveData<Result<Coin>>()
	val result: LiveData<Result<Coin>>
		get() = _result

	/**
	 * Start fetching flow
	 */
	fun fetch(coinId: CoinId) {
		launch(context = dispatchers.io) {
			fetchCoin(coinId).unsafeRun { result ->
				result.fold(
					{ _result.postValue(Result.Error(it)) },
					{ _result.postValue(Result.Success(it.toPresentation())) }
				)
			}
		}
	}
}