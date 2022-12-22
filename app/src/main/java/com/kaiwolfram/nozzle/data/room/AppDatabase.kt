package com.kaiwolfram.nozzle.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kaiwolfram.nozzle.data.room.dao.*
import com.kaiwolfram.nozzle.data.room.entity.*

@Database(
    entities = [
        ContactEntity::class,
        EventEntity::class,
        ProfileEntity::class,
        ReactionEntity::class,
        RepostEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun eventDao(): EventDao
    abstract fun profileDao(): ProfileDao
    abstract fun reactionDao(): ReactionDao
    abstract fun repostDao(): RepostDao
}
