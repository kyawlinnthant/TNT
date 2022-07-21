package mdy.klt.myatmyat.repository

import kotlinx.coroutines.flow.Flow
import mdy.klt.myatmyat.data.PayOff

interface HistoryRepository {
    suspend fun addItem(payOff: PayOff)
    suspend fun deleteItem(payOff: PayOff)
    suspend fun getItems(): Flow<List<PayOff>>
    suspend fun getNetBalance() : Flow<Float>
}