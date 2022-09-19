package mdy.klt.myatmyat.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mdy.klt.myatmyat.common.Constants

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayOff(payOff: PayOff)

//    @Delete(entity = PayOff::class)
//    suspend fun deleteAllHistory(payOff: PayOff)

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    suspend fun deleteAllHistory()

    @Query("DELETE FROM ${Constants.TABLE_NAME} WHERE id = :id ")
    suspend fun deleteItemHistory(id: Long)

    @Query("SELECT * FROM ${Constants.TABLE_NAME} ORDER BY currentTime DESC")
    fun getAllHistory() : Flow<List<PayOff>>

    @Query("SELECT SUM(totalProfit) FROM ${Constants.TABLE_NAME}")
    fun getTotalNetBalance() : Flow<Float>

}