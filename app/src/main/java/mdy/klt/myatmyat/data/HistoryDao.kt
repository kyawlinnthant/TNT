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

    @Query("SELECT * FROM ${Constants.TABLE_NAME} ORDER BY saveDateMilli DESC")
    fun getAllHistory() : Flow<List<PayOff>>

    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE saveDateMilli BETWEEN :startDate AND :endDate")
    fun getHistoryWithDate(startDate: Long, endDate: Long) : Flow<List<PayOff>>

    @Query("SELECT SUM(totalProfit) FROM ${Constants.TABLE_NAME}")
    fun getTotalNetBalance() : Flow<Float>

}