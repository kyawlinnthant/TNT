package mdy.klt.myatmyat.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PayOff::class],
    version = 7
)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun dao() : HistoryDao
}