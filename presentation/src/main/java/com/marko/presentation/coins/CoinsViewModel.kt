package com.marko.presentation.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marko.domain.usecases.FetchCoins
import com.marko.domain.usecases.invoke
import com.marko.domain.usecases.unsafeRun
import com.marko.presentation.base.BaseViewModel
import com.marko.presentation.dispatchers.CoroutineDispatchers
import com.marko.presentation.entities.Coin
import com.marko.presentation.mappers.toPresentation
import com.marko.presentation.result.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Loads [Coin] [List]
 *
 * @param dispatchers [CoroutineDispatchers]
 *
 * @param fetchCoins [FetchCoins]
 */
class CoinsViewModel @Inject constructor(
	private val dispatchers: CoroutineDispatchers,
	private val fetchCoins: FetchCoins
) : BaseViewModel(dispatchers = dispatchers) {

	/**
	 * [MutableLiveData] holding [Result] of fetching [Coin] [List], exposed as [LiveData]
	 */
	private val _result = MutableLiveData<Result<List<Coin>>>()
	val result: LiveData<Result<List<Coin>>>
		get() = _result

	/**
	 * Start fetching flow
	 */
	fun fetch() {
		launch(context = dispatchers.io) {
			_result.postValue(Result.Loading)
			fetchCoins().unsafeRun { result ->
				result.fold(
					{ _result.postValue(Result.Error(it)) },
					{ _result.postValue(Result.Success(it.toPresentation())) }
				)
			}
		}
	}
}