package com.chatglobal.data.local
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): MessageDao
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(ctx: Context) = INSTANCE ?: synchronized(this) { Room.databaseBuilder(ctx.applicationContext, AppDatabase::class.java, "chatglobal.db").build().also { INSTANCE = it } }
    }
}
