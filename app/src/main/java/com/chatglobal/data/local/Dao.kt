package com.chatglobal.data.local
import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY timestamp DESC") fun getAll(): Flow<List<MessageEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertList(list: List<MessageEntity>)
    @Insert(onConflict = OnConflictStrategy.IGNORE) suspend fun insertOne(e: MessageEntity)
    @Query("UPDATE messages SET status = :s WHERE id = :id") suspend fun updateStatus(id: String, s: String)
}
