package com.marko.cryptofunctionalcoroutines.coindetails

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.marko.cryptofunctionalcoroutines.R
import com.marko.cryptofunctionalcoroutines.base.BaseActivity
import com.marko.cryptofunctionalcoroutines.extensions.loadImage
import com.marko.cryptofunctionalcoroutines.extensions.observeNonNull
import com.marko.domain.entities.CoinId
import com.marko.presentation.coindetails.CoinDetailsViewModel
import com.marko.presentation.entities.Coin
import com.marko.presentation.result.Result
import kotlinx.android.synthetic.main.activity_coin_details.*
import timber.log.Timber

/**
 * [BaseActivity] responsible for displaying [Coin] details
 */
class CoinDetailsActivity : BaseActivity() {

	companion object {
		const val EXTRA_COIN_ID = "coin_id"
	}

	private val viewModel: CoinDetailsViewModel by lazy(LazyThreadSafetyMode.NONE) {
		ViewModelProviders.of(this, factory).get(CoinDetailsViewModel::class.java)
	}

	private val coinId: CoinId by lazy(LazyThreadSafetyMode.NONE) {
		intent.getIntExtra(EXTRA_COIN_ID, - 1)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_coin_details)

		viewModel.fetch(coinId = coinId)
		viewModel.result.observeNonNull(this, ::handleResult)
	}

	private fun handleResult(result: Result<Coin>) = when (result) {
		is Result.Loading -> Unit
		is Result.Success -> result.data.run {
			coinDetailsLogo.loadImage(logo)
			coinDetailsName.text = name
			coinDetailsSymbol.text = symbol
			coinDetailsBTCPrice.text = priceInBTC.toString()
			coinDetailsUSDPrice.text = price.toString()
			coinDetailsMaxSupply.text = maxSupply.toString()
			coinDetailsInExistance.text = inExistenceSupply.toString()
			coinDetailsCirculating.text = circulatingSupply.toString()
			coinDetailsTradedInLast24h.text = tradedIn24h.toString()
		}
		is Result.Error -> Timber.e(result.throwable)
	}
}
