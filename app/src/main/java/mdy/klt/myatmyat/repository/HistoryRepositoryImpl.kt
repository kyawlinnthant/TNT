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

    override suspend fun updateManagerProfit(managerProfit: Long, id: Long) {
        dao.updateManagerProfit(managerProfit = managerProfit, id = id)
    }

    override suspend fun deleteAllItem() {
        dao.deleteAllHistory()
    }

    override suspend fun deleteItem(id: Long) {
        dao.deleteItemHistory(id = id)
    }

    override suspend fun getHistoryWithDate(startDate: Long, endDate: Long): Flow<List<PayOff>> {
        return dao.getHistoryWithDate(startDate = startDate, endDate = endDate)
    }

    override suspend fun getItems(): Flow<List<PayOff>> {
        return dao.getAllHistory()
    }

    override suspend fun getNetBalance(): Flow<Float> {
        return dao.getTotalNetBalance()
    }
}