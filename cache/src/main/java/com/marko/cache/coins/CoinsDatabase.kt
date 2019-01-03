package com.marko.cache.coins

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marko.cache.entities.CoinCache

@Database(entities = [CoinCache::class], version = 1)
abstract class CoinsDatabase : RoomDatabase() {

	abstract fun coinsDao(): CoinsDao
}