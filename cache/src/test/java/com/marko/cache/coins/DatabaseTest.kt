package com.marko.cache.coins

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
abstract class DatabaseTest {

	@get:Rule
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	private val context = InstrumentationRegistry.getInstrumentation().context

	private lateinit var database: CoinsDatabase

	protected val coinsDao: CoinsDao
		get() = database.coinsDao()

	@Before
	fun setUp() {
		database = Room.inMemoryDatabaseBuilder(context, CoinsDatabase::class.java)
			.allowMainThreadQueries()
			.build()
	}

	@After
	fun cleanUp() = database.close()
}