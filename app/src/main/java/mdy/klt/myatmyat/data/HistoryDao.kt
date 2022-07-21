package mdy.klt.myatmyat.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mdy.klt.myatmyat.common.Constants

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayOff(payOff: PayOff)

    @Delete(entity = PayOff::class)
    suspend fun deleteHistory(payOff: PayOff)

    @Query("SELECT * FROM ${Constants.TABLE_NAME} ORDER BY timeStamp DESC")
    fun getAllHistory() : Flow<List<PayOff>>

    @Query("SELECT SUM(netBalance) FROM ${Constants.TABLE_NAME}")
    fun getTotalNetBalance() : Flow<Float>

}