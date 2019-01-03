package com.marko.cryptofunctionalcoroutines.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.marko.cryptofunctionalcoroutines.R
import com.marko.cryptofunctionalcoroutines.base.BaseActivity
import com.marko.cryptofunctionalcoroutines.coindetails.CoinDetailsActivity
import com.marko.cryptofunctionalcoroutines.extensions.observeNonNull
import com.marko.cryptofunctionalcoroutines.extensions.startActivity
import com.marko.presentation.coins.CoinsViewModel
import com.marko.presentation.entities.Coin
import com.marko.presentation.result.Result
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

/**
 * [BaseActivity] responsible for displaying [Coin] [List]
 */
class MainActivity : BaseActivity() {

	private val viewModel: CoinsViewModel by lazy(LazyThreadSafetyMode.NONE) {
		ViewModelProviders.of(this, factory).get(CoinsViewModel::class.java)
	}

	private val coinsAdapter: CoinsAdapter by lazy(LazyThreadSafetyMode.NONE) {
		CoinsAdapter { startActivity<CoinDetailsActivity>(CoinDetailsActivity.EXTRA_COIN_ID to it) }
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		viewModel.fetch()
		viewModel.result.observeNonNull(this, ::handleResult)

		coinsRecyclerView.adapter = coinsAdapter
		coinsRecyclerView.layoutManager =
				LinearLayoutManager(this).apply { isItemPrefetchEnabled = true }
		coinsRecyclerView.setHasFixedSize(true)
	}

	private fun handleResult(result: Result<List<Coin>>) = when (result) {
		is Result.Loading -> coinsProgressBar.show()
		is Result.Success -> {
			coinsAdapter.coins = result.data
			coinsProgressBar.hide()
		}
		is Result.Error -> {
			Timber.e(result.throwable)
			coinsProgressBar.hide()
		}
	}
}
