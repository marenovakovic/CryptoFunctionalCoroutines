package com.marko.cryptofunctionalcoroutines.app

import android.content.Context
import androidx.room.Room
import com.marko.cache.coins.CoinsCacheRepositoryImpl
import com.marko.cache.coins.CoinsDao
import com.marko.cache.coins.CoinsDatabase
import com.marko.data.coins.*
import com.marko.data.injection.DI
import com.marko.domain.coins.CoinsRepository
import com.marko.presentation.dispatchers.CoroutineDispatchers
import com.marko.presentation.dispatchers.CoroutineDispatchersImpl
import com.marko.remote.coins.CoinsRemoteRepositoryImpl
import com.marko.remote.coins.CoinsService
import com.marko.remote.coins.coinsService
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class AppBindingModule {

	@Binds
	@Singleton
	abstract fun remoteRepository(bind: CoinsRemoteRepositoryImpl): CoinsRemoteRepository

	@Binds
	@Singleton
	abstract fun cacheRepository(bind: CoinsCacheRepositoryImpl): CoinsCacheRepository

	@Binds
	@Singleton
	@Named(DI.REMOTE_DATA_SOURCE)
	abstract fun remoteDataSource(bind: CoinsRemoteDataSource): CoinsDataSource

	@Binds
	@Singleton
	@Named(DI.CACHE_DATA_SOURCE)
	abstract fun cacheDataSource(bind: CoinsCacheDataSource): CoinsDataSource

	@Binds
	@Singleton
	abstract fun coinsRepository(bind: CoinsRepositoryImpl): CoinsRepository
}

@Module(includes = [AppBindingModule::class])
class AppModule(private val context: Context) {

	@Provides
	fun provideContext(): Context = context

	@Provides
	@Singleton
	fun provideCoinsService(): CoinsService = coinsService

	@Provides
	@Singleton
	fun provideDispatchers(): CoroutineDispatchers = CoroutineDispatchersImpl()

	@Provides
	@Singleton
	fun provideCoinsDatabase(context: Context): CoinsDatabase =
		Room.databaseBuilder(context, CoinsDatabase::class.java, "coins_db").build()

	@Provides
	@Singleton
	fun provideCoinsDao(database: CoinsDatabase): CoinsDao = database.coinsDao()
}