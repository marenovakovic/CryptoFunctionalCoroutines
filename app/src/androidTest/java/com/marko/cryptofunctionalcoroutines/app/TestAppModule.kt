package com.marko.cryptofunctionalcoroutines.app

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.marko.cache.coins.CoinsCacheRepositoryImpl
import com.marko.cache.coins.CoinsDao
import com.marko.cache.coins.CoinsDatabase
import com.marko.cryptofunctionalcoroutines.dispatchers.TestCoroutineDispatchers
import com.marko.data.coins.*
import com.marko.data.injection.DI
import com.marko.domain.coins.CoinsRepository
import com.marko.presentation.dispatchers.CoroutineDispatchers
import com.marko.remote.coins.CoinsRemoteRepositoryImpl
import com.marko.remote.coins.CoinsService
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class TestAppBindingModule {

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

@Module(includes = [TestAppBindingModule::class])
class TestAppModule {

	@Provides
	fun provideContext(): Context = InstrumentationRegistry.getInstrumentation().context

	@Provides
	@Singleton
	fun provideDispatchers(): CoroutineDispatchers = TestCoroutineDispatchers()

	@Provides
	@Singleton
	fun provideCoinsDatabase(context: Context): CoinsDatabase =
		Room.inMemoryDatabaseBuilder(context, CoinsDatabase::class.java).build()

	@Provides
	@Singleton
	fun provideCoinsService(): CoinsService = mockk()

	@Provides
	@Singleton
	fun provideCoinsDao(): CoinsDao = mockk()
}