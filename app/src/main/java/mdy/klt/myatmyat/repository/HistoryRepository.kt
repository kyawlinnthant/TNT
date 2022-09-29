package mdy.klt.myatmyat.repository

import kotlinx.coroutines.flow.Flow
import mdy.klt.myatmyat.data.PayOff

interface HistoryRepository {
    suspend fun addItem(payOff: PayOff)
    suspend fun updateManagerProfit(managerProfit: Long, id: Long)
    suspend fun deleteAllItem()
    suspend fun deleteItem(id: Long)
    suspend fun getHistoryWithDate(startDate: Long, endDate: Long): Flow<List<PayOff>>
    suspend fun getItems(): Flow<List<PayOff>>
    suspend fun getNetBalance() : Flow<Float>
}