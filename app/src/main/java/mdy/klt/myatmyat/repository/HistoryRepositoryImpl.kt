package mdy.klt.myatmyat.repository

import kotlinx.coroutines.flow.Flow
import mdy.klt.myatmyat.data.HistoryDatabase
import mdy.klt.myatmyat.data.PayOff
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val db: HistoryDatabase
) : HistoryRepository{
    private val dao = db.dao()
    override suspend fun addItem(payOff: PayOff) {
        dao.insertPayOff(payOff = payOff)
    }

    override suspend fun deleteItem(payOff: PayOff) {
        dao.deleteHistory(payOff = payOff)
    }

    override suspend fun getItems(): Flow<List<PayOff>> {
        return dao.getAllHistory()
    }

    override suspend fun getNetBalance(): Flow<Float> {
        return dao.getTotalNetBalance()
    }
}